package object;

import java.awt.Polygon;

public class WindBarb {
	final private int length = 15;
	private Polygon barb;
	
	WindBarb(double windspeed, double winddir){
		for(int i = 0; i < length; i++){
			barb.addPoint(0, i);
		}
		double knots = windspeed;
		int pennants = 0;
		int flags = 0;
		if(knots > 4){
			while(knots > 4){
				if(knots - 50 > 0){
					//for(int i = 0; )
					knots -= 50;
					pennants++;
				}
				else if(knots - 10 > 0){
					//TODO draw long line
					knots -= 10;
					flags++;
				}
				else if(knots - 5 > 0){
					//TODO draw short line
					knots -= 5;
				}
			}			
		}
		else {
			// TODO Draw double circle
		}
	}

}
