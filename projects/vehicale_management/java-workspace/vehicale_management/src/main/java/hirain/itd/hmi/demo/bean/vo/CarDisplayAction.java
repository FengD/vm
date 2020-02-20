package hirain.itd.hmi.demo.bean.vo;

import hirain.itd.hmi.demo.api.Msg.ToAduMessage.ActionType;

public class CarDisplayAction {
	private String carName;
	private int path;
	private ActionType advSwitch;

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getPath() {
		return path;
	}

	public void setPath(int path) {
		this.path = path;
	}

	public ActionType getAdvSwitch() {
		return advSwitch;
	}

	public void setAdvSwitch(ActionType advSwitch) {
		this.advSwitch = advSwitch;
	}
}
