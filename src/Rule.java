import java.util.ArrayList;
import java.util.HashMap;

public class Rule {
	HashMap<String, ArrayList<String>> posAttrFil = new HashMap<>();
	HashMap<String, ArrayList<String>> negAttrFil = new HashMap<>();
	HashMap<String, String> posRelCond = new HashMap<>();
	HashMap<String, String> negRelCond = new HashMap<>();

	public void addAttributeFilters(boolean positive, String attrName, String attrValue) {
		if(positive) {
			if(posAttrFil.containsKey(attrName)) {
				ArrayList<String> attrValues = posAttrFil.get(attrName);
				attrValues.add(attrValue);
				posAttrFil.put(attrName, attrValues);
			}
			else {
				ArrayList<String> attrValues = new ArrayList<>();
				attrValues.add(attrValue);
				posAttrFil.put(attrName, attrValues);
			}
		}
		else {
			if(negAttrFil.containsKey(attrName)) {
				ArrayList<String> attrValues = negAttrFil.get(attrName);
				attrValues.add(attrValue);
				negAttrFil.put(attrName, attrValues);
			}
			else {
				ArrayList<String> attrValues = new ArrayList<>();
				attrValues.add(attrValue);
				negAttrFil.put(attrName, attrValues);
			}
		}
	}

	public void addRelationCondition(boolean positive, String firstAttr, String secAttr){
		if(positive) {
			posRelCond.put(firstAttr, secAttr);
		}
		else {
			negRelCond.put(firstAttr, secAttr);
		}
	}

	public String getRuleString() {
		String ruleString = "";
		for(String attrName : posAttrFil.keySet()) {
			for(String attValue : posAttrFil.get(attrName)) {
				ruleString += attrName + "=" + attValue + ",";
			}
		}
		for(String attrName : negAttrFil.keySet()) {
			for(String attValue : negAttrFil.get(attrName)) {
				ruleString += attrName + "!=" + attValue + ",";
			}
		}
		for(String firstAttr : posRelCond.keySet()) {
			ruleString += firstAttr + "==" + posRelCond.get(firstAttr) + ",";
		}
		for(String firstAttr : negRelCond.keySet()) {
			ruleString += firstAttr + "!==" + negRelCond.get(firstAttr) + ",";
		}

		ruleString = ruleString.substring(0, ruleString.length() - 1);
		return ruleString;
	}

	public boolean checkRule(AccessRight ar) {
		for(String attrName : posAttrFil.keySet()) {
			if(ar.attributes.containsKey(attrName)) {
				if(!posAttrFil.get(attrName).contains(ar.attributes.get(attrName))) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		for(String attrName : negAttrFil.keySet()) {
			if(ar.attributes.containsKey(attrName)) {
				if(negAttrFil.get(attrName).contains(ar.attributes.get(attrName))) {
					return false;
				}
			}
		}
		for(String firstAttr : posRelCond.keySet()) {
			String secondAttr = posRelCond.get(firstAttr);
			if(ar.attributes.containsKey(firstAttr) && ar.attributes.containsKey(secondAttr)) {
				if(!ar.attributes.get(firstAttr).equals(ar.attributes.get(secondAttr))){
					return false;
				}
			}
			else {
				return false;
			}
		}
		for(String firstAttr : negRelCond.keySet()) {
			String secondAttr = negRelCond.get(firstAttr);
			if(ar.attributes.containsKey(firstAttr) && ar.attributes.containsKey(secondAttr)) {
				if(ar.attributes.get(firstAttr).equals(ar.attributes.get(secondAttr))){
					return false;
				}
			}
		}
		return true;
	}
}
