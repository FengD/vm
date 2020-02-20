package hirain.itd.hmi.demo.bean;

public class Route {
	private int route_id;
	private int project_id;
	private String name;
	private String route_line;
	private String route_point;
	private String start_point;
	private String end_point;

	public int getRoute_id() {
		return route_id;
	}

	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoute_line() {
		return route_line;
	}

	public void setRoute_line(String route_line) {
		this.route_line = route_line;
	}

	public String getRoute_point() {
		return route_point;
	}

	public void setRoute_point(String route_point) {
		this.route_point = route_point;
	}

	public String getStart_point() {
		return start_point;
	}

	public void setStart_point(String start_point) {
		this.start_point = start_point;
	}

	public String getEnd_point() {
		return end_point;
	}

	public void setEnd_point(String end_point) {
		this.end_point = end_point;
	}

}
