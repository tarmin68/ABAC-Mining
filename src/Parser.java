import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
}
