import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class test {
    public static void main(String[] args) {
        Channel channel = new NioServerSocketChannel();
        ChannelFuture future = channel.connect(new InetSocketAddress("localhost",8080));
        future.addListener(new ChannelFutureListener() {//注册一个ChannelFutureListener操作完成后获取通知
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    ChannelFuture wf = future.channel().writeAndFlush(buffer);
                }else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }

    public class ConnectHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client" + ctx.channel().remoteAddress() + "connected");
        }
    }
}
