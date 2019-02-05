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
}
