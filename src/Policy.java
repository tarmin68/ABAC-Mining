import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Policy {
	String rulesFileName;
	String permittedDataFileName;
	String deniedDataFileName;
	ArrayList<Rule> rules = new ArrayList<>();
	ArrayList<String> attributes = new ArrayList<String>(); 
	Map<String, ArrayList<String>> attributeValues = new HashMap<>();

	public Policy(String rulesFileName) {
		this.rulesFileName = rulesFileName;
		permittedDataFileName = "permitted" + rulesFileName;
		deniedDataFileName = "denied" + rulesFileName;
		rules = Parser.ruleParser(rulesFileName);
		getAttributesFromRules();
		getAttributeValuesFromRules();
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
		
		for(int i = 0; i < size; i++) {
			AccessRight ar = new AccessRight(this);
			if(checkDecision(ar)) {
				String arString = "";
				for(String attrName : attributes) {
					arString += ar.attributes.get(attrName) + ",";
				}
				permittedAR.append(arString.substring(0,arString.length() - 1) + "\n");
			}
			else {
				String arString = "";
				for(String attrName : attributes) {
					arString += ar.attributes.get(attrName) + ",";
				}
				deniedAR.append(arString.substring(0,arString.length() - 1) + "\n");
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

	public boolean checkDecision(AccessRight ar){
		boolean decision = false;
		for(Rule r: rules) {
			if(r.checkRule(ar)) {
				decision = true;
			}
		}
		return decision;
	}
	
	public static void writeInFile(StringBuilder data, String fileName){
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
}
