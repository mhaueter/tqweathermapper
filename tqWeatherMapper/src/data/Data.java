package data;

import java.awt.geom.Point2D;
//@SuppressWarnings("unused")
public abstract class Data {

	

	protected String[] sitenames; // Required info
	protected String[] siteids; // Required info
	protected String[] parameters;
	protected double[] lats; // Required info
	protected double[] lons; // Required info
	protected double[] elevs; // Required info
	protected String data[][]; // TODO need better encapsulator of data.
	protected String timestamp;
	

	
	//These Methods are for a Single Site Data
	public abstract double getLat(String sitename);
	public abstract double getLon(String sitename);
	public abstract Point2D.Double getLocation(String sitename);
	public abstract double getDatum(String sitename, String param);
	public abstract String getSiteID(String sitename);
	
	//These Methods get all sites;
	public abstract double[] getData(String param);
	public abstract String[] getSites();
	public abstract Point2D.Double[] getLocations();
	public abstract String[] getSiteIDs();
	
	
	//These Methods deal are required for the importing of data
	public abstract void importData();
	
}
