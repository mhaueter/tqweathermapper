package data;

import java.awt.geom.Point2D.Double;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	public void importData(){
		if(paramInfo){
			importParamInfo();
		}
		importSiteInfo();
		importDataFile();
	}
	
	
	
	public void importParamInfo(){
		
	}
	
	public void importSiteInfo(){
		
	}
	
	public void importDataFile(){
		
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

	
	//***************************************************//
	//*********** XML Parsing Methods *******************//
	// These Methods were provided by Eric Bruno
	
	
	private Node getNode(String tagname, NodeList nodes){
		for(int x = 0; x < nodes.getLength(); x++){
			Node node = nodes.item(x);
			if(node.getNodeName().equalsIgnoreCase(tagname)){
				return node;
			}
		}
		return null;
	}
	
	private String getNodeValue(Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int x = 0; x< childNodes.getLength(); x++){
			Node data = childNodes.item(x);
			if ( data.getNodeType() == Node.TEXT_NODE)
				return data.getNodeValue();
		}
		return "";
	}
	
	private String getNodeValue(String tagName, NodeList nodes){
		for (int x = 0; x < nodes.getLength(); x++){
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)){
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++){
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.TEXT_NODE)
						return data.getNodeValue();
				}
			}
		}
		return "";
	}
	
	private String getNodeAttr(String attrName, Node node){
		NamedNodeMap attrs = node.getAttributes();
		for(int y = 0; y < attrs.getLength(); y++){
			Node attr = attrs.item(y);
			if (attr.getNodeName().equalsIgnoreCase(attrName)){
				return attr.getNodeValue();
			}
		}
		return "";
	}
	
	private String getNodeAttr(String tagName, String attrName, NodeList nodes){
		for(int x = 0; x < nodes.getLength(); x++){
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)){
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++){
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.ATTRIBUTE_NODE){
						if (data.getNodeName().equalsIgnoreCase(attrName)){
							return data.getNodeValue();
						}
					}
				}
			}
		}
		return "";
	}
	
}
