package skeleton;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Belly {
	
	public static void main(String[] args) {

		// Use this as Debug cucumber command line arguments
		// -Dcucumber.options"--features --tags @rocket"
		// Use this to hard code arguments here
		// System.setProperty("cucumber.options", "features --tags @ipv6");

		System.out.println("cucumber options = "
				+ System.getProperty("cucumber.options"));

		RunCuke runCuke = new RunCuke();
		
		List<Class> testCases = new ArrayList<Class>();

		// Add test cases
		testCases.add(RunCuke.class);

		for (Class testCase : testCases) {
			runTestCase(testCase);
		}
		
		return;
	}

	/** Runs the test case **/
	private static void runTestCase(Class testCase) {
		System.out.println("In runTestcase" + testCase.toString());
		JUnitCore runit = new org.junit.runner.JUnitCore();
		Result result = runit.runClasses(testCase);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		return;
	}
    
    public void eat(int cukes) {
       System.out.println("Yum Yum !");
    }
}
