## Cucumber-Java Skeleton

This is the simplest possible build script setup for Cucumber using Java.
There is nothing fancy like a webapp or browser testing. All this does is to show you how
to install and run Cucumber!

### Get the code

Git:

    git clone https://github.com/cucumber/cucumber-java-skeleton.git
    cd cucumber-java-skeleton

Subversion:

    svn checkout https://github.com/cucumber/cucumber-java-skeleton
    cd cucumber-java-skeleton

Or simply [download](https://github.com/cucumber/cucumber-java-skeleton/releases) a zip or tarball.

### Use Maven

Open a command window and run:

    mvn test

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `RunCukesTest`
class tells JUnit to kick off Cucumber.

### Use Ant

Open a command window and run:

    ant download
    ant runcukes

This runs Cucumber features using Cucumber's Command Line Interface (CLI) runner. Note that the `RunCukesTest` junit class is not used at all.
If you remove it (and the `cucumber-junit` jar dependency), it will run just the same.

#### Overriding options

The Cucumber runtime parses command line options to know what features to run, where the glue code lives, what formatters to use etc.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test.

Sometimes it can be useful to override these options without changing or recompiling the JUnit class. This can be done with the
`cucumber.options` system property. The general form is:

Using Maven:

    mvn -Dcucumber.options="..." test

Using Ant:

    JAVA_OPTIONS='-Dcucumber.options="..."' ant runcukes

Let's look at some things you can do with `cucumber.options`. Try this:

    -Dcucumber.options="--help"

That should list all the available options.

*IMPORTANT*

When you override options with `-Dcucumber.options`, you will completely override whatever options are hard-coded in
your `@CucumberOptions` or in the script calling `cucumber.api.cli.Main`. There is one exception to this rule, and that
is the `--format` option. This will not _override_, but _add_ a formatter. The reason for this is to make it easier
for 3rd party tools (such as Cucumber Pro) to automatically install new formatters by appending arguments to a `cucumber.properties`
file.

You can read more about how this works on the [Cucumber-JVM Formatter for Cucumber Pro](https://github.com/cucumber-ltd/cucumber-pro-jvm)
page.

#### Run a subset of Features or Scenarios

Specify a particular scenario by *line* (and use the pretty format)

    -Dcucumber.options="classpath:skeleton/belly.feature:4 --format pretty"

This works because Maven puts `./src/test/resources` on your `classpath`.
You can also specify files to run by filesystem path:

    -Dcucumber.options="src/test/resources/skeleton/belly.feature:4 --format pretty"

You can also specify what to run by *tag*:

    -Dcucumber.options="--tags @bar --format pretty"

#### Running only the scenarios that failed in the previous run

    -Dcucumber.options="@target/rerun.txt"

This works as long as you have the `rerun` formatter enabled.

#### Specify a different formatter:

For example a JUnit formatter:

    -Dcucumber.options="--format junit:target/cucumber-junit-report.xml"
