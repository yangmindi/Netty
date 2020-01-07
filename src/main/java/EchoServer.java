import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage:" + EchoServer.class.getSimpleName() + "<port>");
            return;
        }
        int port = Integer.parseInt(args[0]);//如果端口值不正确，抛出异常
        new EchoServer(port).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();//创建EventLoopGroup
        try {
            ServerBootstrap b = new ServerBootstrap();//创建ServerBootstrap
            b.group(group)
                    .channel(NioServerSocketChannel.class)//指定所使用的NIO传输channel
                    .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {//添加一个EchoServerHandler到子的ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);//因为标记为@Shareable，所以我们可以总是使用同样的实例
                        }
                    });
            ChannelFuture f = b.bind().sync();//异步地绑定服务器，调用sync（）方法阻塞等待直到绑定完成
            f.channel().closeFuture().sync();//获取Channel的CloseFuture，并阻塞当前线程直到他完成
        }finally {
            group.shutdownGracefully().sync();//关闭EventLoopGroup，释放所有的资源
        }
    }
}
