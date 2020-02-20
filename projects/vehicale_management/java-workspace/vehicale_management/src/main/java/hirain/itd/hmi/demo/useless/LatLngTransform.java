package hirain.itd.hmi.demo.useless;

public class LatLngTransform {
	private static final double EARTHR = 6378137.0;
	private static final double f = 1 / 298.257223563;
	private static final double e2 = f * (2 - f);

	private double deltaX, deltaY, iniLat, iniLng, iniHeight;

	public double getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}

	public double getIniLat() {
		return iniLat;
	}

	public void setIniLat(double iniLat) {
		this.iniLat = iniLat;
	}

	public double getIniLng() {
		return iniLng;
	}

	public void setIniLng(double iniLng) {
		this.iniLng = iniLng;
	}

	public double getIniHeight() {
		return iniHeight;
	}

	public void setIniHeight(double iniHeight) {
		this.iniHeight = iniHeight;
	}

	public double GPSX2GPSLong(double GPSX) {
		double temp = Math.sqrt(1 - e2 * Math.pow(Math.sin(iniLat * Math.PI / 180), 2));
		double re = EARTHR / temp;
		double reh = (re + iniHeight) * Math.cos(iniLat * Math.PI / 180);
		double LoctnFactorX = reh * Math.PI / 180;
		return (GPSX + deltaX) / LoctnFactorX + iniLng;
	}

	public double GPSY2GPSLat(double GPSY) {
		double temp = Math.sqrt(1 - e2 * Math.pow(Math.sin(iniLat * Math.PI / 180), 2));
		double temp3 = Math.pow(temp, 3);
		double rn = EARTHR * (1 - e2) / temp3;
		double rnh = rn + iniHeight;
		double LoctnFactorY = rnh * Math.PI / 180;
		return (GPSY + deltaY) / LoctnFactorY + iniLat;
	}

}
