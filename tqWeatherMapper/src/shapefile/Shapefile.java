package shapefile;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class Shapefile {

	public Point2D.Double[] shapeVertices;
	
	public Shapefile(String file){ //Currently only imports ASCII shapefiles.  //TODO more types
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			String buffer;
			
			ArrayList<Point2D.Double> vals = new ArrayList<Point2D.Double>();
			DecimalFormat format = new DecimalFormat("#.##");
			String delims = "[ ]+";
			
			while(!(buffer = br.readLine()).equalsIgnoreCase("END")){
				
				String[] tmp = buffer.split(delims);
				//double x = format.parse(tmp[1]).doubleValue();
				//double y = format.parse(tmp[2]).doubleValue();
				
				double x = new BigDecimal(tmp[1]).doubleValue();
				double y = new BigDecimal(tmp[2]).doubleValue();
				//System.out.println(x + ", " + y);
				vals.add(new Point2D.Double(x,y));
				
			}
			
			this.shapeVertices = new Point2D.Double[vals.size()];
			
			for(int i = 0; i < vals.size(); i++){
				shapeVertices[i] = vals.get(i);
			}
			
			br.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
