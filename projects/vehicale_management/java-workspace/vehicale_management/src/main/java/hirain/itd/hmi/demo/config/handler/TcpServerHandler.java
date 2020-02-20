package hirain.itd.hmi.demo.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import hirain.itd.hmi.demo.api.Msg.Message;
import hirain.itd.hmi.demo.serviceimpl.TcpService;
import hirain.itd.hmi.demo.serviceimpl.TcpWebSocketPairService;
import hirain.itd.hmi.demo.serviceimpl.WebSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Channel active......");
		TcpService.add(ctx.channel().id().asShortText(), (SocketChannel) ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message aduMsg = Message.parseFrom(msg.toString().getBytes("ISO-8859-1"));
		if (aduMsg.hasFrom()) {
			if (null == TcpWebSocketPairService.get(aduMsg.getFrom().getCarName())) {
				TcpWebSocketPairService.add(aduMsg.getFrom().getCarName(), ctx.channel().id().asShortText());
			}
//			System.out.println(aduMsg.getFrom().toString());
			try {
				String msgStr = "{\"type\":\"current_status\",\"content\":{";
				msgStr += "\"adv_status\":" + aduMsg.getFrom().getADVStatus() + ",";
				msgStr += "\"ev_aim\":" + aduMsg.getFrom().getEvAim() + ",";
				msgStr += "\"loc_flag\":" + aduMsg.getFrom().getLocFlag() + ",";
				msgStr += "\"gps_flag\":" + aduMsg.getFrom().getGPSFlagPos() + ",";
				msgStr += "\"actuator_error\":" + aduMsg.getFrom().getActuatorError() + ",";
				msgStr += "\"sensor_error\":" + aduMsg.getFrom().getSensorError() + ",";
				msgStr += "\"latitude\":" + aduMsg.getFrom().getXFusion() + ",";
				msgStr += "\"longitude\":" + aduMsg.getFrom().getYFusion() + ",";
				msgStr += "\"speed\":" + aduMsg.getFrom().getVehicleSpeed() + "}}";
				JSONObject sendMsg = JSONObject.parseObject(msgStr);
				if (null != aduMsg.getFrom().getCarName()) {
					WebSocketSession webSocketSession = WebSocketService.get(aduMsg.getFrom().getCarName());
					if (null != webSocketSession) {
						TextMessage m = new TextMessage(sendMsg.toString());
						webSocketSession.sendMessage(m);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		cause.printStackTrace();
//		ctx.close();
	}
}
