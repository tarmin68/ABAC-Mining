import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Policy {
	String rulesFileName;
	ArrayList<Rule> rules = new ArrayList<>();
	Set<String> attributes = new HashSet<String>(); 
	Map<String, Set<String>> attributeValues = new HashMap<>();

	public Policy(String rulesFileName) {
		this.rulesFileName = rulesFileName;
		rules = Parser.ruleParser(rulesFileName);
	}

	public ArrayList<String> getAttributes(){
		ArrayList<String> attributes = new ArrayList<>();
		for(int i = 0; i < rules.size(); i++) {
			for(String attrName : rules.get(i).posAttrFil.keySet()) {
				attributes.add(attrName);
			}
			for(String attrName : rules.get(i).negAttrFil.keySet()) {
				attributes.add(attrName);
			}
			for(String attrName : rules.get(i).posRelCond.keySet()) {
				attributes.add(attrName);
				attributes.add(rules.get(i).posRelCond.get(attrName));
			}
			for(String attrName : rules.get(i).negRelCond.keySet()) {
				attributes.add(attrName);
				attributes.add(rules.get(i).negRelCond.get(attrName));
			}
		}

		return attributes;
	}

	public Map<String, Set<String>> getAttributeValues(){
		Map<String, Set<String>> attributeValues = new HashMap<>();

		for(int i = 0; i < rules.size(); i++) {
			for(String attrName : rules.get(i).posAttrFil.keySet()) {
				if(attributeValues.containsKey(attrName)) {
					Set<String> values = attributeValues.get(attrName);
					values.addAll(rules.get(i).posAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
				else {
					Set<String> values = new HashSet<>();
					values.addAll(rules.get(i).posAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
			}
			for(String attrName : rules.get(i).negAttrFil.keySet()) {
				if(attributeValues.containsKey(attrName)) {
					Set<String> values = attributeValues.get(attrName);
					values.addAll(rules.get(i).negAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
				else {
					Set<String> values = new HashSet<>();
					values.addAll(rules.get(i).negAttrFil.get(attrName));
					attributeValues.put(attrName, values);
				}
			}
		}

		return attributeValues;
	}
	
	public void generatePolicyData() throws Exception {
	}
}
