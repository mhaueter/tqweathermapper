package draw;

import java.awt.Color;
import java.awt.geom.Line2D;

import object.*;
/**
 * This class contains methods used to draw into an image. 
 *  
 * @author Michael Haueter
 * 
 */
public class Draw {
	
	/**
	 * This Draws a shape onto the image, the shape is a Shapefile
	 * specific to the TeamQuack Library.  
	 * 
	 * @param shape the Shapefile to be drawn as a TQ ShapeFile
	 * @param image the image to draw to as a WxImage
	 * @param xOffset (distance from left and right borders) as an int
	 * @param yOffset (distance from top and bottom borders) as an int
	 * 
	 */
	public static void DrawShape(ShapeFile shape, WxImage image, int xOffset, int yOffset){
		double dscale; //Desired scale
		if((double)shape.getxMax()/(double) image.getWidth() > (double)shape.getyMax()/(double) image.getHeight()){
			dscale = (double) (image.getWidth()-(2*xOffset))/(double) shape.getxMax();
		}
		else{
			dscale = (double) (image.getHeight() - (2*yOffset))/(double) shape.getyMax();
		}
		shape.scale(dscale);
		
		for(int i = 0; i < shape.getShape().npoints; i++){
			if(i != shape.getShape().npoints - 1){
				drawLine(shape.getShape().xpoints[i] + xOffset, shape.getShape().ypoints[i] + yOffset, shape.getShape().xpoints[i+1] + xOffset ,shape.getShape().ypoints[i+1] + yOffset, image, 1, Color.black.getRGB());
			}
			//TODO else statement to extend last line
		}
		
	}
	
	/**
	 * This Draws a shape onto the image, the shape is a Shapefile
	 * speific to the TeamQuack Library
	 * 
	 * @param shape the shape to be drawn as a TQ ShapeFile
	 * @param image the image to draw to as a WxImage
	 * @param offset_L the distance from the left border as an int
	 * @param offset_R the distance from the right border as an int
	 * @param offset_T the distance from the top border as an int
	 * @param offset_B the distance from the bottom border as an int
	 */
	public static void DrawShape(ShapeFile shape, WxImage image, int offset_L, int offset_R, int offset_T, int offset_B){
		double dscale; //Desired scale
		if(shape.getxMax() > shape.getyMax()){
			dscale = (image.getWidth()-(offset_L + offset_R))/shape.getxMax();
		}
		else{
			dscale = (image.getHeight() - (offset_T + offset_B))/shape.getyMax();
		}
		shape.scale(dscale);
		
		for(int i = 0; i < shape.getShape().npoints; i++){
			if(i != shape.getShape().npoints - 1){
				drawLine(shape.getShape().xpoints[i], shape.getShape().ypoints[i], shape.getShape().xpoints[i+1],shape.getShape().ypoints[i+1], image, 1, Color.black.getRGB());
			}
		}
	}
	
	/**
	 * This draws a line using an Extended Java Line2D onto the given
	 * image.  
	 * @param line the line to be drawn as an Extended Java Line2D
	 * @param image the image to draw to as a WxImage
	 * @param width the line width in pixels as an int
	 * @param color the color to draw the line in as a RGB int
	 */
	public static void drawLine(Line2D line, WxImage image, int width, int color){
		//TODO placeholder for Line2D drawLine() function
	}
	
	/**
	 * This draws a line onto the given image using x and y coordinates of the points
	 * 
	 * @param x1 the x component of the first point
	 * @param y1 the y component of the first point
	 * @param x2 the x component of the second point
	 * @param y2 the y component of the second point
	 * @param image the image to draw to as a WxImage
	 * @param width the width of the line in pixels as an int
	 * @param color the color to draw the line in as a RGB int
	 */
	public static void drawLine(int x1, int y1, int x2, int y2, WxImage image, int width, int color){
		//TODO draw thickness by repeating these values with +1/-1 to x/y values
		
		int dx = Math.abs(x2-x1);
		int dy = Math.abs(y2-y1);
		int sx;
		int sy;
		if(x1 < x2){
			sx = 1;
		}
		else{
			sx = -1;
		}
		if(y1 < y2){
			sy = 1;
		}
		else{
			sy = -1;
		}
		
		int err = dx - dy;
		boolean flg = true;
		while(flg){
			//image.map.setRGB(x1, y1, color);
			if((x1==x2)&&(y1==y2)){
				flg = false;
				break;
			}
			int e2 = 2*err;
			
			if(e2 > -dy){
				err = err -dy;
				x1 = x1 + sx;
			}
			if(e2 < dx){
				err = err +dx;
				y1 = y1+sy;
			}
		}
	}
	
		
	/**
	 * This draws a contour grom the TQ GridData, this uses a bicubic
	 * algorithm in order to interpolate what the probable values between
	 * points are.
	 * 
	 * @param dat the data as TQ GridData
	 * @param inc the desired increments for the contour as an int
	 * @param image the image to draw to as a WxImage
	 */
	public static void drawContour(GridData dat, int inc, WxImage image){
		
	}
	
	public static void drawWindBarb(int x, int y, WxImage image, double speed, double dir){
	//	Graphics2D g = (Graphics2D) image.map.getGraphics();
		double windspeed = speed;
		double theta = (dir+90) * Math.PI/180;
		double sint = Math.sin(theta);
		double cost = Math.cos(theta);
		
		int len = 0;
		while (speed >= 48.0){
			len += 6;
			speed -= 50;
		}
		while(speed >= 8.0){
			len += 3;
			speed -= 10;
		}
		if(speed >= 3.0){
			len += 3;
		}
		double lenBarb = Math.max(len, 20);
		
		if (windspeed < .5) {
			int radius = 4;
			drawCircle(x,y,image,radius,Color.BLACK.getRGB());
		}
		else{
			int begx = 0;
			int begy = 0;
			int endx = (int) (lenBarb*cost);
			int endy = (int) (lenBarb*sint);
			drawLine(x + begx, y + begy, x + endx, y + endy, image, 0, Color.red.getRGB());
		}
		
//		// DRAWING FLAGS AND LINES
//		int start = 0;
//		speed = windspeed;
//		while(speed >= 48.0){
//			
//		}
		
	}
	
	@SuppressWarnings("unused")
	public static void drawCircle(int x, int y, WxImage image, int radius, int color){
		double alpha = Math.PI * 2 / 40;
		for(int i = 0; i < 40 ; i++){
			double theta = alpha * i;
			double x0 = Math.cos(theta)*radius;
			double y0 = Math.sin(theta)*radius;
			//image.map.setRGB((int)x0+x, (int)y0+y,color);
		}
	}
	
	@SuppressWarnings("unused")
	public static void drawRotatedTriangle(int x, int y, double cost, double sint, WxImage image, double x1, double y1, double x2, double y2, double x3, double y3){
		int[] xpoints = new int[3];
		int[] ypoints = new int[3];
		//TODO make Polygon, draw Filled Polygon.  
		//TODO maybe use Polygon as input instead of xs and ys
		
	}
	
	
}
