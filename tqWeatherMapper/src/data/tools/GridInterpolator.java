package data.tools;

import java.awt.geom.Point2D;

import data.Data;

public class GridInterpolator {

	public static double[] Interpolate(Data d, String param){
		Point2D.Double[] points = d.getLocations();
		double[] data = d.getData(param);
		Point2D.Double[] dlPoints = new Point2D.Double[points.length];
		
		for(int i = 0; i < points.length; i++){
			Point2D.Double[] tmp = new Point2D.Double[3];
			tmp[0] = points[i];
			double dst = 999999999;
			int closest = i;
			
			for(int j = 0; j < points.length; j++){
				if(j == i){
					continue;
				}
				if(points[i].distance(points[j]) < dst){
					tmp[1] = points[j];
					dst = points[i].distance(points[j]);
					closest = j;
				}
			}
			dst = 999999999;
			
			for(int k = 0; k < points.length; k++){
				if((k == i) || (k == closest)){
					continue;
				}
				if(points[i].distance(points[k]) < dst){
					tmp[2] = points[k];
					dst = points[i].distance(points[k]);
				}
			}
			
			dlPoints[i] = triangulate(tmp);
			
		}
		
		
		//TODO do inserts of grid.  and interpolation
		
		
		return null;
		
	}
	
	private static Point2D.Double triangulate(Point2D.Double[] points){
		
		
		return null;
		
	}
}
