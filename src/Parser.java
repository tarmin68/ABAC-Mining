import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {
	
	public static ArrayList<Rule> ruleParser(String fileName){
			ArrayList<Rule> rules = new ArrayList<>();
			String line = null;

			try {
				FileReader fileReader = new FileReader(fileName);

				BufferedReader bufferedReader = new BufferedReader(fileReader);

				while((line = bufferedReader.readLine()) != null) {
					Rule newRule = new Rule();
					String[] elements = line.split(",");
					for(int i = 0; i < elements.length; i++) {
						if(elements[i].contains("!==")) {
							String[] parts = elements[i].split("!==");
							newRule.addRelationCondition(false, parts[0], parts[1]);
						}
						else if(elements[i].contains("==")) {
							String[] parts = elements[i].split("==");
							newRule.addRelationCondition(true, parts[0], parts[1]);
						}
						else if(elements[i].contains("!=")) {
							String[] parts = elements[i].split("!=");
							newRule.addAttributeFilters(false, parts[0], parts[1]);
						}
						else {
							String[] parts = elements[i].split("=");
							newRule.addAttributeFilters(true, parts[0], parts[1]);
						}
					}
					rules.add(newRule);
				} 
				bufferedReader.close();         
			}
			catch(Exception ex) {
				System.out.println("Unable to open file '" + fileName + "'");
				System.out.println(ex);
			}

			return rules;
		}
	
	public static ArrayList<String> attributeParser(String fileName){
		ArrayList<String> attributes = new ArrayList<>();
		
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				String[] attrs = line.split(",");
				for(int i = 0; i < attrs.length; i++) {
					attributes.add(attrs[i]);
				}
			}
			bufferedReader.close();  
		}
		catch(Exception ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			System.out.println(ex);
		}
		
		return attributes;
	}
	
	public static Map<String, ArrayList<String>> attributeValueParser(String fileName){
		Map<String, ArrayList<String>> attributeValues = new HashMap<>();
		
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(" ");
				String[] attrVals = parts[1].split(",");
				ArrayList<String> values = new ArrayList<>();
				for(int i = 0; i < attrVals.length; i++) {
					values.add(attrVals[i]);
				}
				attributeValues.put(parts[0], values);
			}
			bufferedReader.close();  
		}
		catch(Exception ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			System.out.println(ex);
		}
		
		return attributeValues;
	}
}
