package hirain.itd.hmi.demo.bean;

public class CarProfile {
	private int car_profile_id;
	private String project;
	private String city;
	private int capacity;
	private String photo_path;

	public int getCar_profile_id() {
		return car_profile_id;
	}

	public void setCar_profile_id(int car_profile_id) {
		this.car_profile_id = car_profile_id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
}
