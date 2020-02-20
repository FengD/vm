package hirain.itd.hmi.demo.bean.vo;

import hirain.itd.hmi.demo.api.Msg.ActionType;

public class CarDisplayAction {
	private String carName;
	private int path;
	private int advSwitch;
	private ActionType action;

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

	public int getAdvSwitch() {
		return advSwitch;
	}

	public void setAdvSwitch(int advSwitch) {
		this.advSwitch = advSwitch;
	}

	public ActionType getAction() {
		return action;
	}

	public void setAction(ActionType action) {
		this.action = action;
	}

}
