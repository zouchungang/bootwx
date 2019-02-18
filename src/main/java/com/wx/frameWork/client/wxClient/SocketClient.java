package com.wx.frameWork.client.wxClient;

import com.wx.bean.CallBack;
import com.wx.tools.ConfigService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SocketClient {
    private static Logger logger = Logger.getLogger(SocketClient.class);

    public interface OnReconnectListener {
        void onReconnect();
    }

    public interface MsgListener {
        void onNewMessage();
    }

    private String ip;
    private int port;
    private Channel channel = null;
    private static final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 4);

    private OnReconnectListener onReconnectListener = null;
    private CallBack connectSuccessCall = null;
    private ConcurrentHashMap<Integer, CallBack> backs = new ConcurrentHashMap<Integer, CallBack>();
    private ChannelFutureListener channelFutureListener;
    private ChannelFuture connectFuture;
    private ClientHandler handler;
    private Bootstrap bootstrap;
    private boolean destroying;

    public SocketClient(String longServerIp, int port, OnReconnectListener reconnectListener, MsgListener msgListener, CallBack callBack) {
        logger.info("建立和微信的链接：" + longServerIp);
        this.ip = longServerIp;
        this.port = port;
        this.onReconnectListener = reconnectListener;
        connectSuccessCall = callBack;
        channelFutureListener = cf -> {
            if (cf.isSuccess()) {
                logger.info("链接成功：" + longServerIp);
                channel = cf.channel();
                onReconnectListener.onReconnect();
                if (connectSuccessCall != null) {
                    connectSuccessCall.onData(null);
                    connectSuccessCall = null;
                }
            } else {
                logger.info("链接失败，重试：" + longServerIp);
                cf.channel().eventLoop().schedule(() -> doConnect(), 1, TimeUnit.SECONDS);
            }
        };

        handler = new ClientHandler(this, new ClientHandler.MsgCallBack() {
            @Override
            public void msgCallBack(int req, byte[] data) {
                invokeCallBack(req, data);
            }

            @Override
            public void newMsgCallBack() {
                msgListener.onNewMessage();
            }
        });

        createBootstrap();
    }

    protected synchronized void invokeCallBack(int resSeq, byte[] pkgs) {
        try {
            if (resSeq == 0) {
            } else {
                CallBack call = backs.remove(resSeq);
                if (call != null) {
                    call.onData(pkgs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBootstrap() {
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(handler);
                socketChannel.pipeline().addLast(new IdleStateHandler(1000, 1000, 1000, TimeUnit.MILLISECONDS));
            }
        });
        doConnect();
    }

    public void doConnect() {
        if (destroying) {
            return;
        }
        logger.info("开始建立链接：" + ip);
        if (connectFuture != null) {
            connectFuture.cancel(true);
            connectFuture = null;
        }
        connectFuture = bootstrap.connect(new InetSocketAddress(ip, port));
        connectFuture.addListener(channelFutureListener);
    }

    public synchronized void asynSend(byte[] sendBuff, CallBack back) {
        try {
            while (channel == null) {
                wait(1);
            }
            // 获取请求标识
            int reqSeq = ConfigService.byteArrayToInt(sendBuff, 12);
            backs.put(reqSeq, back);
            ByteBuf buf = channel.alloc().buffer(sendBuff.length);
            buf.writeBytes(sendBuff);
            channel.writeAndFlush(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void end() {
        destroying = true;
        if (channel != null) {
            channel.close();
            channel = null;
        }
        if (connectFuture != null) {
            connectFuture.cancel(true);
        }
    }
}
