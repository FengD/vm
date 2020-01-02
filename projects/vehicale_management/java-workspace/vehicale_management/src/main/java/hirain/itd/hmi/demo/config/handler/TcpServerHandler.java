package hirain.itd.hmi.demo.config.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hirain.itd.hmi.demo.serviceimpl.WebSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Channel active......");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("msg: {}", msg.toString());
		try {
			JSONObject jsonObject = JSON.parseObject(msg.toString());
			Object carId = jsonObject.get("carId");
			Object sendMsg = jsonObject.get("msg");
			if (null != carId) {
				int carIdInt = Integer.parseInt(carId.toString());
				WebSocketSession webSocketSession = WebSocketService.get(carIdInt);
				if (null != webSocketSession) {
					TextMessage m = new TextMessage(sendMsg.toString());
					try {
						webSocketSession.sendMessage(m);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
