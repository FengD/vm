package hirain.itd.hmi.demo.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
	Map<String, Object> map;
	HttpStatus status;

	public Response(Object data, HttpStatus status) {
		map = new HashMap<String, Object>();
		if (null != data) {
			map.put("data", data);
		}
		map.put("status", status.value());
		map.put("message", status.getReasonPhrase());
		this.status = status;
	}

	public Object getResponse() {
		return new ResponseEntity<Map<String, Object>>(map, this.status);
	}

}
