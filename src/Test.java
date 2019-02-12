
public class Test {
	public static void main(String[] args) {
		Policy p = new Policy("test.txt");
		p.printPolicy();
		System.out.println();
		p.printAttributes();
		System.out.println();
		p.generatePolicyData(500);
	}
}
