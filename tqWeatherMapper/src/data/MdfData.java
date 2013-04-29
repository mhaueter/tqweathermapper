package data;

import java.awt.geom.Point2D.Double;
import java.io.IOException;
import java.util.ArrayList;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

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
		try {
			importSiteInfo();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		importDataFile();
	}
	
	
	
	public void importParamInfo(){
		
	}
	
	public void importSiteInfo() throws SAXException, IOException{
		DOMParser parser = new DOMParser();
		parser.parse(this.siteInfoFile);
		Document doc = parser.getDocument();
		
		// Root XML node
		NodeList root = doc.getChildNodes();
		
		Node stin = getNode("siteinfo", root);
		
		Node table = getNode("table", stin.getChildNodes());
		
		NodeList list = table.getChildNodes();
		
		
		ArrayList<String> field_ids = new ArrayList<String>();
		ArrayList<String> field_types = new ArrayList<String>();
		ArrayList<String> sites = new ArrayList<String>();
		for(int i = 0; i < list.getLength(); i ++){
			if(list.item(i).getNodeName().equalsIgnoreCase("fieldref")){
				field_ids.add(getNodeAttr("id", list.item(i)));
				field_types.add(getNodeAttr("type", list.item(i)));
			}
			if(list.item(i).getNodeName().equalsIgnoreCase("record")){
				sites.add(list.item(i).getNodeValue());
			}
		}
		
		field_ids.trimToSize();
		field_types.trimToSize();
		sites.trimToSize();
		int siteIdIdx; // = field_ids.indexOf();
		int siteNameIdx;
		int latIdx;
		int lonIdx;
		
		this.siteids = new String[sites.size()];
		this.sitenames = new String[sites.size()];
		this.lats = new double[sites.size()];
		this.lons = new double[sites.size()];
		
		
//		Node item = getNode("fieldref", list);
//		list.item()
//		
//		while(item.getNodeName().equalsIgnoreCase("fieldref")){
//			
//		}
//		
//		while(item.getNodeName().equalsIgnoreCase("record")){
//			
//		}
		
		
	}
	
	public void importDataFile(){
		
	}


	@Override
	double getLat(String sitename) {
		//TODO Auto-generated method stub
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
	// These Methods were provided by Eric Bruno         //
	
	
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
