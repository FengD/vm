//package hirain.itd.hmi.demo.useless;
//
//import java.io.IOException;
//
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//import hirain.itd.hmi.demo.serviceimpl.WebSocketService;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.DatagramPacket;
//
//public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
//
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
//		ByteBuf buf = packet.copy().content();
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req, "UTF-8");
//		System.out.println(body);
//		try {
//			JSONObject jsonObject = JSON.parseObject(body);
//			Object carId = jsonObject.get("carId");
//			Object sendMsg = jsonObject.get("msg");
//			if (null != carId) {
//				int carIdInt = Integer.parseInt(carId.toString());
//				WebSocketSession webSocketSession = WebSocketService.get(carIdInt);
//				if (null != webSocketSession) {
//					TextMessage m = new TextMessage(sendMsg.toString());
//					try {
//						webSocketSession.sendMessage(m);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}