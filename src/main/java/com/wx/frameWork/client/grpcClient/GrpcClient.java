package com.wx.frameWork.client.grpcClient;

import com.wx.frameWork.proto.WechatGrpc;
import com.wx.tools.ConfigService;
import com.wx.tools.Settings;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;

public class GrpcClient {
    private String ip;
    private int port;
    private WechatGrpc.WechatBlockingStub stub;
    private ManagedChannel channel;

    public GrpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void create() {
        try {
            SslContextBuilder builder = GrpcSslContexts.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE);
            SslContext sslContext = builder.build();
            channel = NettyChannelBuilder.forAddress(ip, port)
                    .sslContext(sslContext)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Metadata data = new Metadata();
        data.put(Metadata.Key.of("appid", Metadata.ASCII_STRING_MARSHALLER), ConfigService.APPID);
        data.put(Metadata.Key.of("appkey", Metadata.ASCII_STRING_MARSHALLER), ConfigService.APPKEY);
        stub = WechatGrpc.newBlockingStub(channel);
        stub = MetadataUtils.attachHeaders(stub, data);
    }

    public void close() {
        if (channel != null) {
            channel.shutdownNow();
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public WechatGrpc.WechatBlockingStub getStub() {
        return stub;
    }

    public void setStub(WechatGrpc.WechatBlockingStub stub) {
        this.stub = stub;
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public void setChannel(ManagedChannel channel) {
        this.channel = channel;
    }
}