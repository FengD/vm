package hirain.itd.hmi.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.api.Msg;
import hirain.itd.hmi.demo.api.Msg.Message;
import hirain.itd.hmi.demo.api.Msg.Message.MessageType;
import hirain.itd.hmi.demo.bean.vo.CarDisplayAction;
import hirain.itd.hmi.demo.serviceimpl.TcpService;
import hirain.itd.hmi.demo.serviceimpl.TcpWebSocketPairService;
import hirain.itd.hmi.demo.utils.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;
import io.swagger.annotations.ApiOperation;

@RestController
public class CarMsgController {
	@ApiOperation(value = "Send msg to car adu", notes = "Send msg to car adu")
	@PostMapping("/carDisplay/send")
	@CarLoginToken
	public Object sendToAdu(CarDisplayAction action) {
		Response response = new Response(null, HttpStatus.NOT_FOUND);

		SocketChannel ctx = TcpService.get(TcpWebSocketPairService.get(action.getCarName()));
		if (null != ctx) {
			Message msg = Msg.Message.newBuilder().setType(MessageType.ToAduMessage).setTo(Msg.ToAduMessage.newBuilder()
					.setPathNumOri(action.getPath()).setADVSwitch(action.getAdvSwitch()).build()).build();
//			System.out.println(msg);
			ByteBuf buf = ctx.alloc().buffer(msg.getSerializedSize());
			buf.writeBytes(msg.toByteArray());
			ctx.writeAndFlush(buf);
			response = new Response(null, HttpStatus.OK);
		}

		return response.getResponse();
	}
}