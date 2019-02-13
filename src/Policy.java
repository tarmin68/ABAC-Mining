import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Policy {
	String rulesFileName;
	String attributeFileName;
	String attributeValueFileName;
	String permittedDataFileName;
	String deniedDataFileName;
	ArrayList<Rule> rules = new ArrayList<>();
	ArrayList<String> attributes = new ArrayList<String>(); 
	Map<String, ArrayList<String>> attributeValues = new HashMap<>();

	public Policy(String rulesFileName) {
		this.rulesFileName = rulesFileName;
		attributeFileName = "attribute.txt";
		attributeValueFileName = "attributevalue.txt";
		permittedDataFileName = "permitted" + rulesFileName;
		deniedDataFileName = "denied" + rulesFileName;
		rules = Parser.ruleParser(rulesFileName);
		attributes = Parser.attributeParser(attributeFileName);
		attributeValues = Parser.attributeValueParser(attributeValueFileName);

		//		getAttributesFromRules();
		//		getAttributeValuesFromRules();
	}

	public void getAttributesFromRules(){
		for(int i = 0; i < rules.size(); i++) {
			for(String attrName : rules.get(i).posAttrFil.keySet()) {
				if(!attributes.contains(attrName)) {
					attributes.add(attrName);
				}
			}
			for(String attrName : rules.get(i).negAttrFil.keySet()) {
				if(!attributes.contains(attrName)) {
					attributes.add(attrName);
				}
			}
			for(String firstAttr : rules.get(i).posRelCond.keySet()) {
				if(!attributes.contains(firstAttr)) {
					attributes.add(firstAttr);
				}
				String secondAttr = rules.get(i).posRelCond.get(firstAttr);
				if(!attributes.contains(secondAttr)) {
					attributes.add(secondAttr);
				}
			}
			for(String firstAttr : rules.get(i).negRelCond.keySet()) {
				if(!attributes.contains(firstAttr)) {
					attributes.add(firstAttr);
				}
				String secondAttr = rules.get(i).negRelCond.get(firstAttr);
				if(!attributes.contains(secondAttr)) {
					attributes.add(secondAttr);
				}
			}
		}
	}

	public void getAttributeValuesFromRules(){
		for(int i = 0; i < rules.size(); i++) {
			for(String attrName : rules.get(i).posAttrFil.keySet()) {
				if(attributeValues.containsKey(attrName)) {
					ArrayList<String> values = attributeValues.get(attrName);
					values.addAll(rules.get(i).posAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
				else {
					ArrayList<String> values = new ArrayList<String>();
					values.addAll(rules.get(i).posAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
			}
			for(String attrName : rules.get(i).negAttrFil.keySet()) {
				if(attributeValues.containsKey(attrName)) {
					ArrayList<String> values = attributeValues.get(attrName);
					values.addAll(rules.get(i).negAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
				else {
					ArrayList<String> values = new ArrayList<>();
					values.addAll(rules.get(i).negAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
			}
		}
	}

	public void generatePolicyData(int size) {
		StringBuilder permittedAR = new StringBuilder();
		StringBuilder deniedAR = new StringBuilder();

		String attrString = "";
		for(String attrName : attributes) {
			attrString += attrName + ",";
		}
		permittedAR.append(attrString + "\n");
		deniedAR.append(attrString + "\n");

		int[] ruleCounts = new int[rules.size()];
		int totalPermitCount = 0;
		int totalDenyCount = 0;
		while(totalPermitCount < rules.size() * size) {
			AccessRequest ar = new AccessRequest(this);
			int decision = checkDecision(ar);
			if(decision != -1) {
				ruleCounts[decision]++;
				if(ruleCounts[decision] <= size) {
					totalPermitCount++;
					System.out.println(decision + " " + ruleCounts[decision]);
					String arString = "";
					for(String attrName : attributes) {
						arString += ar.attributes.get(attrName) + ",";
					}
					permittedAR.append(arString.substring(0,arString.length() - 1) + "\n");
				}
			}
			else {
				if(totalDenyCount < rules.size() * size){
					totalDenyCount++;
					String arString = "";
					for(String attrName : attributes) {
						arString += ar.attributes.get(attrName) + ",";
					}
					deniedAR.append(arString.substring(0,arString.length() - 1) + "\n");
				}
			}
		}
		writeInFile(permittedAR, permittedDataFileName);
		writeInFile(deniedAR, deniedDataFileName);
	}

	public void printPolicy() {
		for(Rule r : rules) {
			System.out.println(r.getRuleString());
		}
	}

	public void printAttributes() {
		for(String attr : attributes) {
			System.out.print(attr + " ");
		}
	}

	public Integer checkDecision(AccessRequest ar){
		Integer decision = -1;
		for(int i = 0; i < rules.size(); i++) {
			if(rules.get(i).checkRule(ar)) {
				decision = i;
			}
		}
		return decision;
	}

	public void writeInFile(StringBuilder data, String fileName){
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(data);
			fileWriter.close();
		} catch (Exception e) {
			System.out.println("Error in FileWriter !!!");
			e.printStackTrace();
		}
	}

	public double getFalseNegative(String permittedFileName) {
		StringBuilder falseNegatives = new StringBuilder();

		String attrString = "";
		for(String attrName : attributes) {
			attrString += attrName + ",";
		}
		falseNegatives.append(attrString + "\n");

		String line = null;
		int total = 0;
		int FNs = 0;

		try {
			FileReader fileReader = new FileReader(permittedFileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				total++;
				AccessRequest ar = new AccessRequest(this, line);
				int decision = checkDecision(ar);
				if(decision == -1) {
					FNs++;
					ar.printAccessRequest();
					String arString = "";
					for(String attrName : attributes) {
						arString += ar.attributes.get(attrName) + ",";
					}
					falseNegatives.append(arString.substring(0,arString.length() - 1) + "\n");
				}
			}
			writeInFile(falseNegatives, "falseNegatives.txt");
			bufferedReader.close();  
		}
		catch(Exception ex) {
			System.out.println("Unable to open file '" + permittedFileName + "'");
			System.out.println(ex);
		}
		return FNs / (total * 1.0);
	}

	public double getFalsePositive(String deniedFileName) {
		StringBuilder falsePositives = new StringBuilder();

		String attrString = "";
		for(String attrName : attributes) {
			attrString += attrName + ",";
		}
		falsePositives.append(attrString + "\n");

		String line = null;
		int total = 0;
		int FPs = 0;

		try {
			FileReader fileReader = new FileReader(deniedFileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				total++;
				AccessRequest ar = new AccessRequest(this, line);
				int decision = checkDecision(ar);
				if(decision != -1) {
					FPs++;
					ar.printAccessRequest();
					String arString = "";
					for(String attrName : attributes) {
						arString += ar.attributes.get(attrName) + ",";
					}
					falsePositives.append(arString.substring(0,arString.length() - 1) + "\n");
				}
			}
			writeInFile(falsePositives, "falsePositives.txt");
			bufferedReader.close();  
		}
		catch(Exception ex) {
			System.out.println("Unable to open file '" + deniedFileName + "'");
			System.out.println(ex);
		}
		
		return FPs / (total * 1.0);
	}
}
