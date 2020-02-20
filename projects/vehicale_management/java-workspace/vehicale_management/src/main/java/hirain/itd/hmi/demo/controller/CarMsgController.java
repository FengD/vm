package hirain.itd.hmi.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.api.Msg;
import hirain.itd.hmi.demo.api.Msg.Message;
import hirain.itd.hmi.demo.api.Msg.Message.MessageType;
import hirain.itd.hmi.demo.bean.vo.CarDisplayAction;
import hirain.itd.hmi.demo.serviceimpl.TcpService;
import hirain.itd.hmi.demo.serviceimpl.TcpWebSocketPairService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;

@RestController
public class CarMsgController {
	@PostMapping("/carDisplay/send")
	@CarLoginToken
	public void sendToAdu(CarDisplayAction action) {
		SocketChannel ctx = TcpService.get(TcpWebSocketPairService.get(action.getCarName()));
		if (null != ctx) {
			Message msg = Msg.Message.newBuilder().setType(MessageType.ToAduMessage)
					.setTo(Msg.ToAduMessage.newBuilder().setAction(action.getAction()).setPathNumOri(action.getPath())
							.setADVSwitch(action.getAdvSwitch()).build())
					.build();
//			System.out.println(msg);
			ByteBuf buf = ctx.alloc().buffer(msg.getSerializedSize());
			buf.writeBytes(msg.toByteArray());
			ctx.writeAndFlush(buf);
		}
	}
}