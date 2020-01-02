package hirain.itd.hmi.demo.config.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		socketChannel.pipeline().addLast(new TcpServerHandler());
	}
}
