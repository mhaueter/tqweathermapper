/**
 * 
 */
package object;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;

/**
 * @author operator
 *
 */
public class ShapeFile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Polygon shape;
	private int xMax;
	private int xMin;
	private int yMax;
	private int yMin;
	final public int origXmin;
	final public int origYmin;
	private double cScale; //The current Scale of the Shape from 1000px height. 
	final private double oScale; // Scale from original to 1000px height
	
	
	
	public ShapeFile(Polygon poly){
		origXmin = getMin(poly.xpoints, poly.npoints);
		origYmin = getMin(poly.ypoints, poly.npoints);
		xMin = 0;
		yMin = 0;
		int dx = xMin - origXmin;
		int dy = yMin - origYmin;
		
		
		for(int i = 0; i < poly.npoints; i++){
			poly.xpoints[i] = poly.xpoints[i] + dx;
			poly.ypoints[i] = poly.ypoints[i] + dy;
		}
		int maxx = getMax(poly.xpoints, poly.npoints);
		int maxy = getMax(poly.ypoints, poly.npoints);
		
		double scaling = (double) 1000/maxy;
		oScale = scaling;
		cScale = 1;
		
		for(int i = 0; i < poly.npoints; i++){
			if(poly.xpoints[i] == maxx){
				poly.xpoints[i] *= scaling;
				xMax = poly.xpoints[i];
			}
			else{
				poly.xpoints[i] *= scaling;
			}
			
			if(poly.ypoints[i] == maxy){
				poly.ypoints[i] *= scaling;
				yMax = poly.ypoints[i];
			}
			else{
				poly.ypoints[i] *= scaling;
			}
		}
		shape = poly;
		
	}
	
	public void scale(double scale){
		for(int i = 0; i < shape.npoints; i++){
//			if(shape.xpoints[i] == xMax){
//				shape.xpoints[i] *= scale;
//				xMax = shape.xpoints[i];
//			}
//			else{
//				shape.xpoints[i] *= scale;
//			}
//			
//			if(shape.ypoints[i] == yMax){
//				shape.ypoints[i] *= scale;
//				yMax = shape.ypoints[i];
//			}
//			else{
//				shape.ypoints[i] *= scale;
//			}
			shape.xpoints[i] *= scale;
			shape.ypoints[i] *= scale;
		}
		xMax = getMax(shape.xpoints, shape.npoints);
		xMin = getMin(shape.xpoints, shape.npoints);
		yMax = getMax(shape.ypoints, shape.npoints);
		yMin = getMin(shape.ypoints, shape.npoints);
		cScale *=scale;
	}
	
	public void scaleTo(int height){
		double scale = (double) height / yMax;
		cScale = (double) height/1000;
		for(int i = 0; i < shape.npoints; i++){
			if(shape.xpoints[i] == xMax){
				shape.xpoints[i] *= scale;
				xMax = shape.xpoints[i];
			}
			else{
				shape.xpoints[i] *= scale;
			}
			
			if(shape.ypoints[i] == yMax){
				shape.ypoints[i] *= scale;
				yMax = shape.ypoints[i];
			}
			else{
				shape.ypoints[i] *= scale;
			}
		}
		
	}
	public static void Export(ShapeFile s, String filename) throws IOException{
		FileOutputStream fout = new FileOutputStream(filename + ".tqSHP");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(s);
		oos.close();
		fout.close();
	}
	public static ShapeFile Import(String file, Format f) throws IOException, ClassNotFoundException{
		if(f == Format.ascii){
			File input = new File(file);
			FileInputStream fis = new FileInputStream(input);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			Polygon poly = new Polygon();
			String buffer;
			
			while(((buffer = br.readLine()) != null)){
				if(buffer.equalsIgnoreCase("END")){
					break;
				}
				char[] charBuffer = buffer.toCharArray();
				String lon = "";
				String lat = "";
				for(int i = 6; i <  17; i++){
					lon = lon + charBuffer[i];
				}
				for(int i = 35; i < 50; i++){
					lat = lat + charBuffer[i];
				}
				
				double longitude = Double.parseDouble(lon);
				double lattitude = Double.parseDouble(lat);
				int x = (int) (10000000 * longitude);
				int y = 1 - (int) (10000000 * lattitude); // 1- for orientation correction (only for in western hemisphere) 
				
				poly.addPoint(x, y);
			}
			
			ShapeFile ret = new ShapeFile(poly);
			br.close();
			in.close();
			fis.close();
			return ret;
			
		}
		else if(f == Format.mshp){
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			ShapeFile ret = (ShapeFile) ois.readObject();
			
			ois.close();
			fin.close();
			
			return ret;
		}
		else{
			//TODO return to consol exit, and print error.
			return null;
		}
		
	}
	
	private int getMin(int[] val, int size){
		int ret = val[0];
		for(int i = 0; i < size; i++){
			if(val[i] < ret){
				ret = val[i];
			}
		}
		return ret;
	}
	
	private int getMax(int[] val, int size){
		int ret = val[0];
		for(int i = 0; i < size; i++){
			if(val[i] > ret){
				ret = val[i];
			}
		}
		return ret;
	}
	
	/**
	 * @return the shape
	 */
	public Polygon getShape() {
		return shape;
	}

	/**
	 * @return the xMax
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * @return the xMin
	 */
	public int getxMin() {
		return xMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the cScale
	 */
	public double getcScale() {
		return cScale;
	}

	
	public double getoScale() {
		return oScale;
	}

	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
