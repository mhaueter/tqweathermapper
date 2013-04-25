package draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;

import com.jhlabs.map.proj.*;

/**
 * 
 * @author Michael Haueter
 *
 */


/**
 * This is a WxImage for drawing on to.  it has a height and width, and 
 * provides a print function and a map that is protected and linked 
 * to a TQ draw class.  
 * @author Michael Haueter
 *
 */
@SuppressWarnings("unused")
public class WxImage extends BufferedImage{
	

	/**
	 * The Graphics Object that will hold the map
	 */
	private Graphics2D g = this.createGraphics();
	
	/**
	 * The map Projection Type
	 */
	private Projection proj;
	
	/**
	 * The Map lat/lon center point defined in degrees.
	 */
	protected Point2D.Double center;

	
	/**
	 * These pixel buffers define how much space should be
	 * between the drawing area and the sides of the image. 
	 */
	
	protected int tPB; // the pixels between the top edge and drawing area
	protected int bPB; // the pixels between the bottom edge and drawing area
	protected int rPB; // The pixels between the right edge and drawing area
	protected int lPB; // The pixels between the left edge and drawing area
	
	/**
	 * The width of the image in pixels
	 */
	private int width;
	
	/**
	 * The height of the image in pixels
	 */
	private int height;
	//private String title;
	
	/**
	 * The drawing height of the image in pixels
	 * (height - (tPB + bPB))
	 */
	private int drawheight;
	
	/**
	 * The drawing width of the image in pixels
	 * (width - (rPB + lPB))
	 */
	private int drawwidth;
	
	/**
	 * This is the flag that tells if the 
	 * map has been scaled properly.
	 */
	boolean scaled = false;
	
	/**
	 * This is the ratio when converting from 
	 * a projection to a display. 
	 */
	public double projToDisplayRatio;
	
	/**
	 * Constructor for WXImage, which creates a blanke buffered image
	 * given the height and width.
	 * 
	 * @param width the width of the image in pixels
	 * @param height the height of the image in pixels
	 * @param imageType the type of the image (jpg, gif, etc)
	 * @param center the center of the image in lat/lon degrees.
	 */
	public WxImage(int width, int height, int imageType, Point2D.Double center) {
		super(width, height, imageType);
		this.center = center;
		this.proj = new MercatorProjection();//Default constructor uses mercator projection
		this.proj.setProjectionLatitudeDegrees(center.y * -1); // multiply by -1 because in computers we draw in the -y Cartesian quadrant 
		this.proj.setProjectionLongitudeDegrees(center.x);
		tPB = 5; 
		bPB = 5; 
		rPB = 5; 
		lPB = 5;
		this.height = height;
		this.width = width;
		drawheight = this.height - (tPB + bPB);
		drawwidth = this.width - (rPB + lPB);
		proj.initialize();
	}
	
	/**
	 * This creates an WxImage with a predefined projection
	 * 
	 * @param width the width of the image in pixels
	 * @param height the height of the image in pixels
	 * @param imageType the type of the image
	 * @param center the center of the image in lat/lon degrees
	 * @param proj the projection of the image
	 */
	public WxImage(int width, int height, int imageType, Point2D.Double center, Projection proj){
		super(width, height, imageType);
		this.center = center;
		this.proj = proj;
		tPB = 5; 
		bPB = 5; 
		rPB = 5; 
		lPB = 5;
		this.height = height;
		this.width = width;
		drawheight = height - (tPB + bPB);
		drawwidth = width - (rPB + lPB);
		//NOTE Projection must be pre-initialized.  
	}
	
	/**
	 * This makes a WxImage with given buffer for white space between the image
	 * borders and the drawing area.
	 * 
	 * @param width the width of the image in pixels
	 * @param height the height of the image in pixels
	 * @param imageType the image type
	 * @param center the center of the image in lat/lon degrees
	 * @param tbuffer the top buffer in pixels
	 * @param lbuffer the left buffer in pixels
	 * @param rbuffer the right buffer in pixels
	 * @param bbuffer the bottom buffer in pixels
	 */
	public WxImage(int width, int height, int imageType, Point2D.Double center, int tbuffer, int lbuffer, int rbuffer, int bbuffer){
		super(width, height, imageType);
		this.proj = new MercatorProjection();
		this.center = center;
		this.proj.setProjectionLatitudeDegrees(center.y * -1); //multiply by -1 because in computers we draw in the -y cartesian
		this.proj.setProjectionLongitudeDegrees(center.x);
		tPB = tbuffer;
		bPB = bbuffer;
		rPB = rbuffer;
		lPB = lbuffer;
		this.height = height;
		this.width = width;
		drawheight = height - (tPB + bPB);
		drawwidth = width - (rPB + lPB);
		proj.initialize();
	}
	
	/**
	 * This makes a new WxImage with given buffers and a pre-defined 
	 * projection. 
	 * 
	 * @param width the width of the image in pixels
	 * @param height the height of the image in pixels
	 * @param imageType the image Type
	 * @param center the center of the image in lat/lon degrees
	 * @param tbuffer the top buffer in pixels
	 * @param lbuffer the left buffer in pixels
	 * @param rbuffer the right buffer in pixels
	 * @param bbuffer the bottom buffer in pixels
	 * @param proj the pre-defined projection
	 */
	public WxImage(int width, int height, int imageType, Point2D.Double center, int tbuffer, int lbuffer, int rbuffer, int bbuffer, Projection proj){
		super(width, height, imageType);
		this.proj = proj;
		this.center = center;
		tPB = tbuffer;
		bPB = bbuffer;
		rPB = rbuffer;
		lPB = lbuffer;
		this.height = height;
		this.width = width;
		drawheight = height - (tPB + bPB);
		drawwidth = width - (rPB + lPB);
		// Projection must be pre-initialized
	}
	
	/**
	 * This method writes text to the image. 
	 * 
	 * @param f the Font for writing as an AWT FONT
	 * @param x the x coordinate of where to start as an int
	 * @param y the y coordinate of where to start as an int
	 * @param data the string to write 
	 * @param c the color as an AWT color.
	 * @return
	 */
	public boolean writeText(Font f, int x, int y, String data, Color c){
		g.setColor(c);
		g.setFont(f);
		g.drawString(data, x, y);
		return true;
	}
	
	/**
	 * This method sets the scale to the given lat/lons that have been transformed
	 * it maximizes screen usage. 
	 * @param points the points to use as a Point2D.Double[]
	 * @return
	 */
	public boolean setScale(Point2D.Double[] points){
		Double[] lonvals = new Double[points.length];
		Double[] latvals = new Double[points.length];
		
		for (int i = 0; i< points.length; i++){
			lonvals[i] = points[i].x;
			latvals[i] = points[i].y;
		}

		double maxLon = (double) Collections.max(Arrays.asList(lonvals));
		double maxLat = (double) Collections.max(Arrays.asList(latvals));
		double minLon = (double) Collections.min(Arrays.asList(lonvals));
		double minLat = (double) Collections.min(Arrays.asList(latvals));
		System.out.println(minLon);
		double latRatio = (this.drawheight)/(maxLat - minLat);
		double lonRatio = (this.drawwidth)/(maxLon - minLon);
		System.out.println("latRatio:" + minLat + ", lonRatio:" + minLon);
		if(latRatio < lonRatio){
			this.projToDisplayRatio = latRatio;
		}
		else{
			this.projToDisplayRatio = lonRatio;
		}
		this.scaled = true;
		
		return true;
	}
	
	/**
	 * This method draws a shape on the image and if it is the first
	 * the scale has not been set, then it calls the scale function
	 * and sets the scale to this shape. 
	 * 
	 * TODO add color.
	 * @param points
	 * @return
	 */
	public Point2D.Double[] drawShape(Point2D.Double[] points){
		Point2D.Double[] dst = new Point2D.Double[points.length];
		Point2D.Double hold = new Point2D.Double(0,0);
		for(int i = 0; i < points.length; i++){
			//this.proj.project(Math.toRadians(points[i].x), Math.toRadians(points[i].y), hold);
			//System.out.println(hold);
			points[i].y = points[i].y * -1; // this is to ajust for drawing on a computer screen.
			this.proj.transform(points[i],hold);
			dst[i] = new Point2D.Double(hold.getX(), hold.getY());
			//dst[i].setLocation(hold.getX(), hold.getY());
			
			//dst[i].setLocation(hold);
			
		}
		
		if(!this.scaled){
			setScale(dst);
		}
		int xpoints[] = new int[dst.length];
		int ypoints[] = new int[dst.length];
		for(int i = 0; i < dst.length; i++){
			xpoints[i] = (int) (dst[i].x*this.projToDisplayRatio + this.drawwidth/2 + this.lPB); //+ (double) (this.drawwidth/2 + this.lPB));
			ypoints[i] = (int) (dst[i].y*this.projToDisplayRatio + this.drawheight/2 + this.tPB);
			dst[i].setLocation(xpoints[i], ypoints[i]);
		}
		
		g.drawPolygon(xpoints, ypoints, dst.length);
		
		return dst;
	}
	
	public void drawContour(){
		//TODO contour mapping
	}
	
	public void drawGradient(){
		//TODO gradient mapping
	}
	
	public void writeData(){
		//TODO write data onto the image
	}
	
	public void drawVectors(){
		//TODO vectors mapping
	}
	
	/**
	 * 
	 * @param max
	 * @param value
	 * @param dir
	 * @param lat in degrees
	 * @param lon in degrees
	 * @param color
	 * @throws Exception 
	 */
	public void drawVector(double max, double speed, double dir, double lat, double lon, Color color) throws Exception{
		if(this.scaled != true){
			throw new Exception("ScaleNotSet");
		}
		Point2D.Double loc_t = new Point2D.Double(0,0);
		lat = lat*-1; // this is for drawing on computer screen.
		loc_t.setLocation(lon, lat);
		this.proj.transform(loc_t, loc_t);
		
		int x0 = (int) (loc_t.x*this.projToDisplayRatio + this.drawwidth/2 + this.lPB);
		int y0 = (int) (loc_t.y*this.projToDisplayRatio + this.drawheight/2 + this.tPB);
		
		System.out.println("x: " + x0 + ", y:" + y0);
		//Point loc = new Point();
		//loc.setLocation(xp, yp);
		
		double theta = dir * Math.PI/180;
		double tsin = Math.sin(theta);
		double tcos = Math.cos(theta);
		
		final int mlen = 40; // max length //TODO figure out proper max length.
		int len = 0;  //Length starts at 0
		
		this.g.setColor(color);
		
		if(speed < max){
			len = (int) ((speed/max)*mlen);
		}
		else{
			len = mlen;
		}
		
		int hlen = (int) (len/2); //This gets half the length, so that a vector can be drawn at a location
		
		// getting first end point of the vector
		int x1 = (int) (x0 + hlen * tsin);
		int y1 = (int) (y0 - hlen * tcos);
		
		//getting second end point of the vector
		int x2 = (int) (x0 - hlen * tsin);
		int y2 = (int) (y0 + hlen * tcos);
		
		if(speed > 3){
			g.drawLine(x1, y1, x2, y2);
			
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];
			int x_1 = -3;
			int y_1 = hlen - 3;
			int x_2 = 2;
			int y_2 = hlen - 3;
			
			xPoints[0] = x2;
            yPoints[0] = y2;
            
            xPoints[1] = (int) (x0 + (x_1 * tcos - y_1 * tsin));
            yPoints[1] = (int) (y0 + (x_1 * tsin + y_1 * tcos));
            
            xPoints[2] = (int) (x0 + (int) (x_2 * tcos - y_2 * tsin));
            yPoints[2] = (int) (y0 + (int) (x_2 * tsin + y_2 * tcos));
            
            g.drawPolygon(xPoints, yPoints, 3);
            g.fillPolygon(xPoints, yPoints, 3);
		}
		
		else{
			int radius = 6;
			this.g.drawArc((int)(x0-radius/2), (int)(y0-radius/2), radius, radius, 0, 360);
		}
		
		
	}
	
	/**
	 * This is for testing purposes only, a more robust print function
	 * will be written for final output.
	 * @param filename
	 * @throws IOException
	 */
	public void testPrint(String filename) throws IOException{
		File outputfile = new File(filename + ".png"); 
		ImageIO.write(this, "png", outputfile);
	}
//	/**
//	 * This prints the Image to a file after applying 3x3 anti-aliasing
//	 * 
//	 * @param output the file to print to as a String
//	 * @throws IOException
//	 */
//	public void Print(String output) throws IOException{
//		float blur = .03f;
//		float left = 1.0f - (8.0f * blur);
//		float[] BLUR3x3 = {blur, blur, blur, blur, left, blur, blur, blur, blur};
//		BufferedImage filtered = new BufferedImage(map.getWidth(), map.getHeight(),BufferedImage.TYPE_INT_RGB);
//		Kernel kernel = new Kernel(3,3,BLUR3x3);
//		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
//		cop.filter(map, filtered);
//		
//		File outputfile = new File(output + ".png");
//		ImageIO.write(filtered, "png", outputfile);
//	}
	
}

