
public class Test {
	public static void main(String[] args) {
		double FNs = testGenerateFNRecords();
		double TPs = 1 - FNs;
		double FPs = testGenerateFPRecords();
		double TNs = 1 - FPs;
		
		System.out.println("FNs = " + String.format( "%.2f", FNs )  + " TPs = " + String.format( "%.2f", TPs ) + " FPs = " + String.format( "%.2f", FPs ) + " TNs = " + String.format( "%.2f", TNs ));
	}
	
	public static void testPolicyRandomDataGeneration() {
		Policy p = new Policy("test.txt");
		p.printPolicy();
		System.out.println();
		p.printAttributes();
		System.out.println();
		p.generatePolicyData(500);
	}
	
	public static double testGenerateFNRecords() {
		System.out.println("Printing the policy: ************************");
		Policy p = new Policy("ExtractedTest.txt");
		p.printPolicy();
		System.out.println("End of policy: ************************");
		System.out.println();
		System.out.println("Printing False Negative Records");
		return p.getFalseNegative("permittedtest.txt");
	}
	
	public static double testGenerateFPRecords() {
		System.out.println("Printing the policy: ************************");
		Policy p = new Policy("ExtractedTest.txt");
		p.printPolicy();
		System.out.println("End of policy: ************************");
		System.out.println();
		System.out.println("Printing False Positive Records");
		return p.getFalsePositive("deniedtest.txt");
	}
}
