package hirain.itd.hmi.demo.utils.geo;

import java.util.ArrayList;

public class PostgisTransform {
	
	public static ArrayList<Point> transformMultiPointStr(String multipointStr) {
		ArrayList<Point> points = new ArrayList<Point>();
		String list[] = multipointStr.split("[,()]");
		for(int i = 1; i < list.length;i++) {
			Point p = new Point(list[i]);
			points.add(p);
		}
		return points;
	}
	
	public static Point transformPointStr(String pointStr) {
		String list[] = pointStr.split("[()]");
		Point point = new Point(list[1]);
		return point;
	}

}
