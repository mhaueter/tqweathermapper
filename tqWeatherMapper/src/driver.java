 import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import shapefile.Shapefile;

import data.MdfData;
import draw.*;


public class driver {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws SizeException 
	 */
	public static void main(String[] args) throws Exception {
		
//		ShapeFile s = ShapeFile.Import("Florida_State_Borders.tqSHP", Format.mshp);
//		
//		WxImage i = new WxImage(520,720);
//		
//		Draw.DrawShape(s, i, 15, 15);
//		
//		Draw.drawWindBarb(400, 400, i, 40, 239);
//		
//		i.Print("test");
		
		
		//******************************************//
//		Projection proj = new MercatorProjection();
//		proj.setProjectionLatitudeDegrees(28.0908);
//		proj.setProjectionLongitudeDegrees(-81.9604);
//		proj.initialize();
//		
//		double y =  28.0908;
//		double x = -80.9604;
//		
//		y = y *.0174532925;
//		x = x *.0174532925;
//		
//		java.awt.geom.Point2D.Double dst = new java.awt.geom.Point2D.Double();
//		
//		proj.project(x, y, dst);
//	
//		System.out.println("x:" + x + ", y:" + y);
//		System.out.println(dst);
//		
//		proj.transformRadians(dst, dst);
//		
//		System.out.println(dst);
		
		Point2D.Double cent = new Point2D.Double(-83.911193,28.06);
		WxImage image = new WxImage(720,520,BufferedImage.TYPE_INT_RGB, cent);
		image.setBgColor(Color.white);
		
		Shapefile S = new Shapefile("st12_d00.dat");
		
		//System.out.println(points[15]);
		S.shapeVertices = image.drawShape(S.shapeVertices, Color.black); 

		//for(int i = 0; i < points.length; i++){
		//	System.out.println(points[i]);
		//}
		System.out.println(image.projToDisplayRatio);
		//image.drawVector(100, 50, 270, 28.06, -83.911193, Color.white);
		
		
		data.Data testing = new MdfData("test.mdf", "siteinfo.xml");
		testing.importData();
		
		System.out.println(testing.getDatum("ALTU", "TAIR"));
		
		image.drawWindBarb(testing.getDatum("NRMN", "WSPD")*2.237, testing.getDatum("NRMN", "WDIR"), 28.06, -83.911193, Color.black);
		image.testPrint("testmap1");
		
		
		
		
		
		
		

	}

}
