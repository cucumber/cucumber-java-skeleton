# Cucumber-Java Skeleton

[![Build Status](https://travis-ci.org/cucumber/cucumber-java-skeleton.svg?branch=main)](https://travis-ci.org/cucumber/cucumber-java-skeleton)

This is the simplest possible build script setup for Cucumber using Java.
There is nothing fancy like a webapp or browser testing. All this does is to show you how
to install and run Cucumber!

There is a single feature file with one scenario. The scenario has three steps, two of them pending. See if you can make them all pass!

## Get the code

Git:

    git clone https://github.com/cucumber/cucumber-java-skeleton.git
    cd cucumber-java-skeleton

Subversion:

    svn checkout https://github.com/cucumber/cucumber-java-skeleton/trunk cucumber-java-skeleton
    cd cucumber-java-skeleton

Or simply [download a zip](https://github.com/cucumber/cucumber-java-skeleton/archive/main.zip) file.

## Use Maven

Open a command window and run:

    ./mvnw test

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the 
`RunCucumberTest` class tells JUnit to kick off Cucumber.

## Use Gradle

Open a command window and run:

     ./gradlew test --rerun-tasks --info

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the
`RunCucumberTest` class tells JUnit to kick off Cucumber.

## Overriding options

The Cucumber runtime parses command line options to know what features to run, where the glue code lives, what plugins to use etc.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test.

Sometimes it can be useful to override these options without changing or recompiling the JUnit class. This can be done with the
`cucumber.options` system property. The general form is:

Using Maven:

    mvn -Dcucumber.features="..." -Dcucumber.glue="..." test

Using Gradle:

    gradlew -Dcucumber.features="..." -Dcucumber.glue="..." test

For available options and overriding rules, please consult the following Maven command:

    mvn exec:java \
      -Dexec.classpathScope=test \
      -Dexec.mainClass=io.cucumber.core.cli.Main \
      -Dexec.args="--help"

### Run a subset of Features or Scenarios

Specify a particular scenario by *line*

    -Dcucumber.features="classpath:skeleton/belly.feature:4"

This works because Maven puts `./src/test/resources` on your `classpath`.
You can also specify files to run by filesystem path:

    -Dcucumber.features="src/test/resources/skeleton/belly.feature:4"

In case you have many feature files or scenarios to run against, separate them with commas `,`

    -Dcucumber.features="src/test/resources/skeleton/belly.feature:4, src/test/resources/skeleton/stomach.feature"

You can also specify what to run by *tag*:

    -Dcucumber.filter.tags="@bar"

### Running only the scenarios that failed in the previous run

    -Dcucumber.features="@target/rerun.txt"

This works as long as you have the `rerun` formatter enabled.

### Specify a different formatter:

For example a JUnit formatter:

    -Dcucumber.plugin="junit:target/cucumber-junit-report.xml"
