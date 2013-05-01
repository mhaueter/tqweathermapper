package data;

import java.util.ArrayList;

/**
 * This Class allows for explicit parsing of data
 * 
 * @author Michael Haueter
 *
 */

public class Parse {
	public static String[] parseMdfRecord(String s){
		char[] sarray = s.toCharArray();
		ArrayList<String> newStrings = new ArrayList<String>();
		newStrings.add("");
		boolean inquote = false;
		for(int i = 0; i < sarray.length; i++){
			if(sarray[i] == ','){
				newStrings.add("");
			}
			if((sarray[i] == '"') && (inquote == false)){
				inquote = true;
				continue;
			}
			if(inquote){
				if(sarray[i] == '"'){
					inquote = false;
					continue;
				}
				newStrings.set(newStrings.size() - 1, newStrings.get(newStrings.size() - 1).concat(Character.toString(sarray[i])));
			}
			if(sarray[i] == ' '){
				continue;
			}
			
			
			newStrings.set(newStrings.size() - 1, newStrings.get(newStrings.size() - 1).concat(Character.toString(sarray[i])));
			
		}
		
		String[] StringsArray = newStrings.toArray(new String[newStrings.size()]);
		
		
		
		
		return StringsArray;
	}
}
