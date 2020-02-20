package hirain.itd.hmi.demo;

import hirain.itd.hmi.demo.config.handler.TcpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class TcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.ISO_8859_1));
		socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.ISO_8859_1));
		socketChannel.pipeline().addLast(new TcpServerHandler());
	}
}
