import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sun.javafx.collections.MappingChange.Map;

public class Rule {
	HashMap<String, String> posAttrFil = new HashMap<>();
	HashMap<String, String> negAttrFil = new HashMap<>();
	HashMap<String, String> posRelCond = new HashMap<>();
	HashMap<String, String> negRelCond = new HashMap<>();

	public void addAttributeFilters(boolean positive, String attrName, String attrValue) {
		if(positive) {
			posAttrFil.put(attrName, attrValue);
		}
		else {
			negAttrFil.put(attrName, attrValue);
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
			ruleString += attrName + "=" + posAttrFil.get(attrName) + ",";
		}
		for(String attrName : negAttrFil.keySet()) {
			ruleString += attrName + "!=" + negAttrFil.get(attrName) + ",";
		}
		for(String firstAttr : posRelCond.keySet()) {
			ruleString += firstAttr + "==" + posRelCond.get(firstAttr) + ",";
		}
		for(String firstAttr : negRelCond.keySet()) {
			ruleString += firstAttr + "!==" + negRelCond.get(firstAttr) + ",";
		}

		if(ruleString.length() > 0) {
			ruleString = ruleString.substring(0, ruleString.length() - 1);
		}
		return ruleString;
	}

	public boolean checkRule(AccessRequest ar) {
		for(String attrName : posAttrFil.keySet()) {
			if(ar.attributes.containsKey(attrName)) {
				if(!posAttrFil.get(attrName).equals(ar.attributes.get(attrName))) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		for(String attrName : negAttrFil.keySet()) {
			if(ar.attributes.containsKey(attrName)) {
				if(negAttrFil.get(attrName).equals(ar.attributes.get(attrName))) {
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

	public double calcSimilarity(Rule r) {
		int unionCount = 0;
		int intersectionCount = 0;

		for(String attrName : this.posAttrFil.keySet()) {
			if(r.posAttrFil.keySet().contains(attrName) && this.posAttrFil.get(attrName).equals(r.posAttrFil.get(attrName))) {
				intersectionCount++;
			}
			else {
				unionCount++;
			}
		}

		for(String attrName : this.negAttrFil.keySet()) {
			if(r.negAttrFil.keySet().contains(attrName) && this.negAttrFil.get(attrName).equals(r.negAttrFil.get(attrName))) {
				intersectionCount++;
			}
			else {
				unionCount++;
			}
		}

		for(String attrName : this.posRelCond.keySet()) {
			if(r.posRelCond.keySet().contains(attrName) && this.posRelCond.get(attrName).equals(r.posRelCond.get(attrName))) {
				intersectionCount++;
			}
			else {
				unionCount++;
			}
		}

		for(String attrName : this.negRelCond.keySet()) {
			if(r.negRelCond.keySet().contains(attrName) && this.negRelCond.get(attrName).equals(r.negRelCond.get(attrName))) {
				intersectionCount++;
			}
			else {
				unionCount++;
			}
		}

		unionCount += intersectionCount;

		return intersectionCount / (unionCount * 1.0);
	}

	public void FNprune(Rule r) {
		Iterator<Entry<String, String>> posAttrFilIter = this.posAttrFil.entrySet().iterator();
		while (posAttrFilIter.hasNext()) {
			Entry<String,String> entry = posAttrFilIter.next();
			if(!r.posAttrFil.containsKey(entry.getKey()) || (r.posAttrFil.containsKey(entry.getKey()) && !r.posAttrFil.get(entry.getKey()).equals(entry.getValue()))){
				posAttrFilIter.remove();
			}
		}

		Iterator<Entry<String, String>> negAttrFilIter = this.negAttrFil.entrySet().iterator();
		while (negAttrFilIter.hasNext()) {
			Entry<String,String> entry = negAttrFilIter.next();
			if(!r.negAttrFil.containsKey(entry.getKey()) || (r.negAttrFil.containsKey(entry.getKey()) && r.negAttrFil.get(entry.getKey()).equals(entry.getValue()))){
				negAttrFilIter.remove();
			}
		}

		Iterator<Entry<String, String>> posRelCondIter = this.posRelCond.entrySet().iterator();
		while (posRelCondIter.hasNext()) {
			Entry<String,String> entry = posRelCondIter.next();
			if(!r.posRelCond.containsKey(entry.getKey()) || (r.posRelCond.containsKey(entry.getKey()) && r.posRelCond.get(entry.getKey()).equals(entry.getValue()))){
				posRelCondIter.remove();
			}
		}

		Iterator<Entry<String, String>> negRelCondIter = this.negRelCond.entrySet().iterator();
		while (negRelCondIter.hasNext()) {
			Entry<String,String> entry = negRelCondIter.next();
			if(!r.negRelCond.containsKey(entry.getKey()) || (r.negRelCond.containsKey(entry.getKey()) && r.negRelCond.get(entry.getKey()).equals(entry.getValue()))){
				negRelCondIter.remove();
			}
		}
	}

	public void FPprune(Rule r) {
		Iterator<Entry<String, String>> posAttrFilIter = r.posAttrFil.entrySet().iterator();
		while (posAttrFilIter.hasNext()) {
			Entry<String,String> entry = posAttrFilIter.next();
			if(this.posAttrFil.containsKey(entry.getKey()) && this.posAttrFil.get(entry.getKey()).equals(entry.getValue())){
				posAttrFilIter.remove();
			}
		}
		this.posAttrFil.putAll(r.posAttrFil);
		
		Iterator<Entry<String, String>> negAttrFilIter = r.negAttrFil.entrySet().iterator();
		while (negAttrFilIter.hasNext()) {
			Entry<String,String> entry = negAttrFilIter.next();
			if(this.negAttrFil.containsKey(entry.getKey()) && this.negAttrFil.get(entry.getKey()).equals(entry.getValue())){
				negAttrFilIter.remove();
			}
		}
		this.negAttrFil.putAll(r.negAttrFil);
		
		Iterator<Entry<String, String>> posRelCondIter = r.posRelCond.entrySet().iterator();
		while (posRelCondIter.hasNext()) {
			Entry<String,String> entry = posRelCondIter.next();
			if(this.posRelCond.containsKey(entry.getKey()) && this.posRelCond.get(entry.getKey()).equals(entry.getValue())){
				posRelCondIter.remove();
			}
		}
		this.posRelCond.putAll(r.posRelCond);
		
		Iterator<Entry<String, String>> negRelCondIter = r.negRelCond.entrySet().iterator();
		while (negRelCondIter.hasNext()) {
			Entry<String,String> entry = negRelCondIter.next();
			if(this.negRelCond.containsKey(entry.getKey()) && this.negRelCond.get(entry.getKey()).equals(entry.getValue())){
				negRelCondIter.remove();
			}
		}
		this.negRelCond.putAll(r.negRelCond);
	}
}
