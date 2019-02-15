
public class Test {
	public static void main(String[] args) {
//		testGenerateFN_FPRecords("ExtractedTest.txt");
		refineBasedonFNs("ExtractedTest.txt", "FNTest.txt");
	}

	public static void testPolicyRandomDataGeneration() {
		Policy p = new Policy("test.txt");
		p.printPolicy();
		System.out.println();
		p.printAttributes();
		System.out.println();
		p.generatePolicyData(500);
	}

	public static void testGenerateFN_FPRecords(String extractedPolicyFileName) {
		System.out.println("Printing the policy: ************************");
		Policy p = new Policy(extractedPolicyFileName);
		p.printPolicy();
		System.out.println("End of policy: ************************");
		System.out.println();
		System.out.println("Printing False Negative Records");
		double FNs = p.getFalseNegative("permittedtest.txt");
		double TPs = 1 - FNs;

		System.out.println("Printing False Positive Records");
		double FPs = p.getFalsePositive("deniedtest.txt");
		double TNs = 1 - FPs;

		System.out.println("FNs = " + String.format( "%.2f", FNs )  + " TPs = " 
				+ String.format( "%.2f", TPs ) + " FPs = " + String.format( "%.2f", FPs )
				+ " TNs = " + String.format( "%.2f", TNs ));

		double accuracy = (TPs + TNs) * 1.0 / (TPs + FPs + TNs + FNs);
		double recall = (TPs * 1.0) / (TPs + FNs);
		double precision = (TPs * 1.0) / (TPs + FPs);
		Double fMeasure = (2 * precision * recall) / (precision + recall);
		System.out.println("fMeasure = " + fMeasure);
	}
	
	public static void refineBasedonFNs(String extractedPolicyFileName, String FNPolicyFileName) {
		System.out.println("Printing extracted policy: ************************");
		Policy extractedPolicy = new Policy(extractedPolicyFileName);
		extractedPolicy.printPolicy();
		System.out.println();
		System.out.println("Printing FN policy: ************************");
		Policy FNPolicy = new Policy(FNPolicyFileName);
		extractedPolicy.printPolicy();
		System.out.println();
		extractedPolicy.prunePolicyOnFNs(FNPolicy);
		
		System.out.println("Printing extracted policy after refinement: ************************");
		extractedPolicy.printPolicy();
		
		System.out.println();
		System.out.println("Printing False Negative Records");
		double FNs = extractedPolicy.getFalseNegative("permittedtest.txt");
		double TPs = 1 - FNs;

		System.out.println("Printing False Positive Records");
		double FPs = extractedPolicy.getFalsePositive("deniedtest.txt");
		double TNs = 1 - FPs;

		System.out.println("FNs = " + String.format( "%.2f", FNs )  + " TPs = " 
				+ String.format( "%.2f", TPs ) + " FPs = " + String.format( "%.2f", FPs )
				+ " TNs = " + String.format( "%.2f", TNs ));

		double accuracy = (TPs + TNs) * 1.0 / (TPs + FPs + TNs + FNs);
		double recall = (TPs * 1.0) / (TPs + FNs);
		double precision = (TPs * 1.0) / (TPs + FPs);
		Double fMeasure = (2 * precision * recall) / (precision + recall);
		System.out.println("fMeasure = " + fMeasure);
	}
}
