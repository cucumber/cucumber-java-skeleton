package skeleton;

import gherkin.formatter.StepPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import cucumber.api.CucumberOptions;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

public class Pickle extends ParentRunner<FeatureRunner> {
	private final JUnitReporter jUnitReporter;
	private final List<FeatureRunner> children = new ArrayList<FeatureRunner>();
	private final Runtime runtime;

	/**
	 * Constructor called by JUnit. This class was created as a surrogate for
	 * the Cucumber class. When running the cucumber classes with the junit
	 * runner inside a one-jar, the-one jar class is unable to find the
	 * JavaBackend class. This constructor sends the test runner class (this
	 * class) to the super test runner. It explicitly loads the backend class
	 * and uses it for all the cucumber functions
	 * 
	 * @param clazz
	 *            the class with the @RunWith annotation.
	 * @throws java.io.IOException
	 *             if there is a problem
	 * @throws org.junit.runners.model.InitializationError
	 *             if there is another problem
	 */

	public Pickle(Class testClazz) throws InitializationError, IOException {
		super(testClazz);

		Assertions.assertNoCucumberAnnotatedMethods(testClazz);

		// String current = System.getProperty("user.dir");
		// System.out.println("Pickle Current working directory in Java : " +
		// current);

		// do this because the one jar class loader doesn't seem to be able to
		// find the step definitions
		
		Class stepdefClazz = RunCuke.class;
		ClassLoader classLoader = stepdefClazz.getClassLoader();
		
		System.out.println("ClassLoader " + classLoader);
		
		ResourceLoader resourceLoader = new MultiLoader(classLoader);

		// Do this because the one jar doesn't seem to be able to find the
		// Javabackend
		Collection<cucumber.runtime.java.JavaBackend> backends = new HashSet<cucumber.runtime.java.JavaBackend>();
		backends.add(new cucumber.runtime.java.JavaBackend(resourceLoader));

		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(
				stepdefClazz, new Class[] { CucumberOptions.class });
		RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

		System.out.print("Run Time Options Glue " + runtimeOptions.getGlue()
				+ "\n");

		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader,
				classLoader);

		runtime = new Runtime(resourceLoader, classLoader, backends,
				runtimeOptions);

		jUnitReporter = new JUnitReporter(runtimeOptions.reporter(classLoader),
				runtimeOptions.formatter(classLoader),
				runtimeOptions.isStrict());
		addChildren(runtimeOptions.cucumberFeatures(resourceLoader));
	}

	@Override
	public List<FeatureRunner> getChildren() {
		return children;
	}

	@Override
	protected Description describeChild(FeatureRunner child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(FeatureRunner child, RunNotifier notifier) {
		System.out.println("\n\n" + child.getDescription());
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Child " +
		// child.getName() + " " + child.getClass() + " " + child.getTestClass()
		// + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		String current = System.getProperty("user.dir");
		System.out.println("Pickle Current working directory in Java : "
				+ current);
		child.run(notifier);
	}

	public void run(RunNotifier notifier) {
		super.run(notifier);
		jUnitReporter.done();
		jUnitReporter.close();
		StepPrinter stepPrinter = new StepPrinter();
//		stepPrinter.writeStep(this.runtime,"%s");
	}

	private void addChildren(List<CucumberFeature> cucumberFeatures)
			throws InitializationError {
		for (CucumberFeature cucumberFeature : cucumberFeatures) {
			children.add(new FeatureRunner(cucumberFeature, runtime,
					jUnitReporter));
		}
	}

}
