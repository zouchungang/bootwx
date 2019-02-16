package com.wx.frameWork.client.grpcClient;

import com.wx.frameWork.proto.WechatGrpc;
import com.wx.tools.ConfigService;
import io.grpc.ManagedChannelBuilder;
import io.netty.handler.ssl.*;
import org.apache.log4j.Logger;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import javax.net.ssl.SSLException;
import java.io.File;

public class GrpcClient {
    static Logger logger = Logger.getLogger(GrpcClient.class);
    private String ip;
    private int port;
    private File CerFile = null;
    private boolean ssl = false;
    private WechatGrpc.WechatBlockingStub stub;
    private ManagedChannel channel;
    public GrpcClient(String ip, int port) throws SSLException {
        this.ip = ip;
        this.port = port;
        create();
    }

    public void create() throws SSLException {
        create(ip,port,ssl);
    }

    public void create(File ChainFile ) throws SSLException {
        if(CerFile!=null){
            this.CerFile = ChainFile;
            this.ssl = true;
        }
        create(ip,port,ssl);
    }
    public void create(String host ,int port,boolean Ssl)  throws SSLException {
        ssl = Ssl;
        SslContext sslContext;
        if(Ssl){
            logger.info("使用 SSL/TLS 创建链接！[安全]");
            sslContext =  GrpcSslContexts.forClient().trustManager(CerFile).build();
            channel= NettyChannelBuilder.forAddress(host, port)
                    .sslContext(sslContext)
                    .build();
        } else {
            logger.info("不使用 SSL/TLS 创建链接！[不安全]");
            channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();
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
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public WechatGrpc.WechatBlockingStub getStub() {
        return stub;
    }

}
