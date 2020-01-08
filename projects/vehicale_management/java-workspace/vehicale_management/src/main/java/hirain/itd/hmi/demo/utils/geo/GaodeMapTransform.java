package hirain.itd.hmi.demo.utils.geo;

import java.util.ArrayList;

public class GaodeMapTransform {
	public static String points(ArrayList<Point> points) {
		String geoPointList = "[";
		for (Point point : points) {
			String geoPointStr = point.toString() + ",";
			geoPointList = (new StringBuilder()).append(geoPointList).append(geoPointStr).toString();
		}
		geoPointList = geoPointList.substring(0, geoPointList.length() - 1);
		geoPointList += "]";
		return geoPointList;
	}
}
