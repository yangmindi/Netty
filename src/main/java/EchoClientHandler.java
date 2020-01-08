import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.Buffer;

@ChannelHandler.Sharable//标记实例可被多个Channel共享
public class EchoClientHandler extends SimpleChannelInboundHandler<Buffer> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {//当被通知Channel是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks", CharsetUtil.UTF_8));
    }


    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Buffer buffer) throws Exception {//记录以接收消息的转储
        System.out.println(buffer.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {//发生异常时记录错误并关闭Channel
        cause.printStackTrace();
        ctx.close();
    }
}
