package object;

public class Format {
	private String format;
	
	public Format(String format){
		this.format = format;
	}
	
	public String getFormat(){
		return format;
	}
	public static Format ascii = new Format("ascii");
	public static Format mshp = new Format("mshp");
}


