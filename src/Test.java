
public class Test {
	public static void main(String[] args) {
//		testPolicyRandomDataGeneration("HealthCareDatset2/healthCareRules", "HealthCareDatset2/Attribute.txt", "HealthCareDatset2/AttributeValue.txt");
		
//		testGenerateFN_FPRecords("UniversityDataset2/step2ExtractedRulesAfterFNRefine", "UniversityDataset2/DataSet2RulesPermitted.txt", "UniversityDataset2/DataSet2RulesDenied.txt");
		testGenerateFN_FPRecords("UniversityDataset3/step3ExtractedRulesAfterFNRefine", "UniversityDataset3/permittedtest.txt", "UniversityDataset3/deniedtest.txt", "UniversityDataset3/Attribute.txt", "UniversityDataset3/AttributeValue.txt");
		
//		refineBasedonFNs("UniversityDataset3/step2ExtractedRulesAfterFNRefine", "UniversityDataset3/step3FNExtractedRules", "UniversityDataset3/permittedtest.txt" , "UniversityDataset3/deniedtest.txt", "UniversityDataset3/Attribute.txt", "UniversityDataset3/AttributeValue.txt");
//		refineBasedonFNs("UniversityDataset2/step1ExtractedRules", "UniversityDataset2/step2FNExtractedRules", "UniversityDataset2/DataSet2RulesPermitted.txt" , "UniversityDataset2/DataSet2RulesDenied.txt");
		
//		refineBasedonFPs("UniversityDataset2/step2ExtractedRulesAfterFNRefine", "UniversityDataset2/step3FPExtractedRules", "UniversityDataset2/DataSet2RulesPermitted.txt" , "UniversityDataset2/DataSet2RulesDenied.txt");
	}

	public static void testPolicyRandomDataGeneration(String policyName, String attributeFileName, String attributeValueFileName) {
		Policy p = new Policy(policyName, attributeFileName, attributeValueFileName);
		p.printPolicy();
		System.out.println();
		p.printAttributes();
		System.out.println();
		p.generatePolicyData(1000);
	}

	public static void testGenerateFN_FPRecords(String extractedPolicyFileName, String permittedFileName, String deniedFileName, String attributeFileName, String attributeValueFileName) {
		System.out.println("Printing the policy: ************************");
		Policy p = new Policy(extractedPolicyFileName, attributeFileName, attributeValueFileName);
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
	
	public static void refineBasedonFNs(String extractedPolicyFileName, String FNPolicyFileName, String permittedFileName, String deniedFileName, String attributeFileName, String attributeValueFileName) {
		System.out.println("Printing extracted policy: ************************");
		Policy extractedPolicy = new Policy(extractedPolicyFileName, attributeFileName, attributeValueFileName);
		extractedPolicy.printPolicy();
		System.out.println();
		System.out.println("Printing FN policy: ************************");
		Policy FNPolicy = new Policy(FNPolicyFileName, attributeFileName, attributeValueFileName);
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
		
		extractedPolicy.printPolicyInFile(extractedPolicyFileName + "AfterFNRefine.txt");
	}
	
	public static void refineBasedonFPs(String extractedPolicyFileName, String FPPolicyFileName, String permittedFileName, String deniedFileName, String attributeFileName, String attributeValueFileName) {
		System.out.println("Printing extracted policy: ************************");
		Policy extractedPolicy = new Policy(extractedPolicyFileName, attributeFileName, attributeValueFileName);
		extractedPolicy.printPolicy();
		System.out.println();
		System.out.println("Printing FP policy: ************************");
		Policy FPPolicy = new Policy(FPPolicyFileName, attributeFileName, attributeValueFileName);
		FPPolicy.printPolicy();
		System.out.println();
		extractedPolicy.prunePolicyOnFPs(FPPolicy);
		
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
		
		extractedPolicy.printPolicyInFile(extractedPolicyFileName + "AfterFPRefine.txt");
	}
}
