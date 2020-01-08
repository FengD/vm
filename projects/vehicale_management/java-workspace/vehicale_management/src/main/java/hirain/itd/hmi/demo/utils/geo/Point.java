package hirain.itd.hmi.demo.utils.geo;

public class Point {

	private double latitude;
	private double longitude;

	public Point(String pointStr) {
		String coordinate[] = pointStr.split(" ");
		this.latitude = Double.parseDouble(coordinate[0]);
		this.longitude = Double.parseDouble(coordinate[1]);
	}

	public Point(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "{\"latitude\":" + this.latitude + ",\"longitude\":" + this.longitude + "}";
	}

}
