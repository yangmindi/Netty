import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class test {
    public static void main(String[] args) {
        Channel channel = new NioServerSocketChannel();
        ChannelFuture future = channel.connect(new InetSocketAddress("localhost",8080));
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {

            }
        })

    }
    public class ConnectHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client" + ctx.channel().remoteAddress() + "connected");
        }
    }
}
