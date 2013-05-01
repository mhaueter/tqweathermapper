package data;

import java.awt.geom.Point2D;
//@SuppressWarnings("unused")
abstract class Data {

	

	protected String[] sitenames; // Required info
	protected String[] siteids; // Required info
	protected String[] parameters;
	protected double[] lats; // Required info
	protected double[] lons; // Required info
	protected double[] elevs; // Required info
	protected double data[][];
	protected String timestamp;
	

	
	//These Methods are for a Single Site Data
	abstract double getLat(String sitename);
	abstract double getLon(String sitename);
	abstract Point2D.Double getLocation(String sitename);
	abstract double getDatum(String sitename, String param);
	abstract String getSiteID(String sitename);
	
	//These Methods get all sites;
	abstract double[] getData(String param);
	abstract String[] getSites();
	abstract Point2D.Double[] getLocations();
	abstract String[] getSiteIDs();
	
	
	//These Methods deal are required for the importing of data
	abstract void importData();
	
}
