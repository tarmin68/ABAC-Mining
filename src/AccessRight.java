import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AccessRight {

	HashMap<String, String> attributes = new HashMap<>();
	
	public AccessRight(Policy p) {
		for(int i = 0; i < p.attributes.size(); i++) {
			String attrName = p.attributes.get(i);
			int size = p.attributeValues.get(attrName).size();
			int randomNum = ThreadLocalRandom.current().nextInt(0, size);
			attributes.put(attrName, p.attributeValues.get(attrName).get(randomNum));
		}
	}
	
	public void printAccessRight() {
		String accessRightString = "";
		for(String attrName : attributes.keySet()) {
			accessRightString += attrName + " " + attributes.get(attrName) + ",";
		}
		System.out.println(accessRightString);
	}
}
