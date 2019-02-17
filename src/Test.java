
public class Test {
	public static void main(String[] args) {
//		testPolicyRandomDataGeneration("UniversityDataset2/DataSet2Rules");
//		testGenerateFN_FPRecords("UniversityDataset2/step1ExtractedRules", "UniversityDataset2/DataSet2RulesPermitted.txt", "UniversityDataset2/DataSet2RulesDenied.txt");
//		refineBasedonFNs("UniversityDataset3/step1ExtractedRules", "UniversityDataset3/step2FNExtractedRules");
//		refineBasedonFPs("ExtractedTest2", "FP extracted rule");
	}

	public static void testPolicyRandomDataGeneration(String policyName) {
		Policy p = new Policy(policyName);
		p.printPolicy();
		System.out.println();
		p.printAttributes();
		System.out.println();
		p.generatePolicyData(1000);
	}

	public static void testGenerateFN_FPRecords(String extractedPolicyFileName, String permittedFileName, String deniedFileName) {
		System.out.println("Printing the policy: ************************");
		Policy p = new Policy(extractedPolicyFileName);
		p.printPolicy();
		System.out.println("End of policy: ************************");
		System.out.println();
		System.out.println("Printing False Negative Records");
		double FNs = p.getFalseNegative(permittedFileName);
		double TPs = 1 - FNs;

		System.out.println("Printing False Positive Records");
		double FPs = p.getFalsePositive(deniedFileName);
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
	
	public static void refineBasedonFNs(String extractedPolicyFileName, String FNPolicyFileName, String permittedFileName, String deniedFileName) {
		System.out.println("Printing extracted policy: ************************");
		Policy extractedPolicy = new Policy(extractedPolicyFileName);
		extractedPolicy.printPolicy();
		System.out.println();
		System.out.println("Printing FN policy: ************************");
		Policy FNPolicy = new Policy(FNPolicyFileName);
		FNPolicy.printPolicy();
		System.out.println();
		extractedPolicy.prunePolicyOnFNs(FNPolicy);
		
		System.out.println("Printing extracted policy after refinement: ************************");
		extractedPolicy.printPolicy();
		
		System.out.println();
		System.out.println("Printing False Negative Records");
		double FNs = extractedPolicy.getFalseNegative(permittedFileName);
		double TPs = 1 - FNs;

		System.out.println("Printing False Positive Records");
		double FPs = extractedPolicy.getFalsePositive(deniedFileName);
		double TNs = 1 - FPs;

		System.out.println("FNs = " + String.format( "%.2f", FNs )  + " TPs = " 
				+ String.format( "%.2f", TPs ) + " FPs = " + String.format( "%.2f", FPs )
				+ " TNs = " + String.format( "%.2f", TNs ));

		double accuracy = (TPs + TNs) * 1.0 / (TPs + FPs + TNs + FNs);
		double recall = (TPs * 1.0) / (TPs + FNs);
		double precision = (TPs * 1.0) / (TPs + FPs);
		Double fMeasure = (2 * precision * recall) / (precision + recall);
		System.out.println("fMeasure = " + fMeasure);
		
		extractedPolicy.printPolicyInFile("TestTest.txt");
	}
	
	public static void refineBasedonFPs(String extractedPolicyFileName, String FPPolicyFileName) {
		System.out.println("Printing extracted policy: ************************");
		Policy extractedPolicy = new Policy(extractedPolicyFileName);
		extractedPolicy.printPolicy();
		System.out.println();
		System.out.println("Printing FP policy: ************************");
		Policy FPPolicy = new Policy(FPPolicyFileName);
		FPPolicy.printPolicy();
		System.out.println();
		extractedPolicy.prunePolicyOnFPs(FPPolicy);
		
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
		
		extractedPolicy.printPolicyInFile("TestTest.txt");
	}
}
