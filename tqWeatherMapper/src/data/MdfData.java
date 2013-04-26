package data;

import java.awt.geom.Point2D.Double;

public class MdfData extends Data{

	String dataFile;
	String siteInfoFile;
	boolean paramInfo;
	String paramInfoFile;
	//TODO create parameter info variables
	
	
	public MdfData(String dataFile, String siteInfoFile){
		this.dataFile = dataFile;
		this.siteInfoFile = siteInfoFile;
		
		
			
	}
	
	public MdfData(String dataFile, String siteInfoFile, String paramInfoFile){
		this.dataFile = dataFile;
		this.siteInfoFile = siteInfoFile;
		this.paramInfoFile = paramInfoFile;
		this.paramInfo = true;
		
	}
	
	


	@Override
	double getLat(String sitename) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	double getLon(String sitename) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	Double getLocation(String sitename) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	double getDatum(String sitename, String param) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	String getSiteID(String sitename) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	double[] getData(String param) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	String[] getSites() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	Double[] getLocations() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	String[] getSiteIDs() {
		// TODO Auto-generated method stub
		return null;
	}

}
