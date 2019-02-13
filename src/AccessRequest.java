import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AccessRequest {

	HashMap<String, String> attributes = new HashMap<>();
	
	public AccessRequest(Policy p) {
		for(int i = 0; i < p.attributes.size(); i++) {
			String attrName = p.attributes.get(i);
			int size = p.attributeValues.get(attrName).size();
			int randomNum = ThreadLocalRandom.current().nextInt(0, size);
			attributes.put(attrName, p.attributeValues.get(attrName).get(randomNum));
		}
	}
	
	public AccessRequest(Policy p, String line) {
		String[] parts = line.split(",");
		for(int i = 0; i < p.attributes.size(); i++) {
			String attrName = p.attributes.get(i);
			attributes.put(attrName, parts[i]);
		}
	}
	
	public void printAccessRequest() {
		String accessRightString = "";
		for(String attrName : attributes.keySet()) {
			accessRightString += attrName + " " + attributes.get(attrName) + ",";
		}
		System.out.println(accessRightString);
	}
}
