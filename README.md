# Cucumber-Java Skeleton

This is the simplest possible build script setup for Cucumber using Java.
There is nothing fancy like a webapp or browser testing. All this does is to show
you how to install and run Cucumber!

There is a single feature file with one scenario. The scenario has three steps,
two of them pending. See if you can make them all pass!

## Get the code

Git:

    git clone https://github.com/cucumber/cucumber-java-skeleton.git
    cd cucumber-java-skeleton

Subversion:

    svn checkout https://github.com/cucumber/cucumber-java-skeleton/trunk cucumber-java-skeleton
    cd cucumber-java-skeleton

Or [download a zip](https://github.com/cucumber/cucumber-java-skeleton/archive/main.zip) file.

## Use Maven

Open a command window and run:

    ./mvnw test

This runs Cucumber features using Cucumber's JUnit Platform Engine. The `Suite`
annotation on the `RunCucumberTest` class tells JUnit to kick off Cucumber.

## Use Gradle

Open a command window and run:

     ./gradlew test --rerun-tasks --info

This runs Cucumber features using Cucumber's JUnit Platform Engine. The `Suite`
annotation on the `RunCucumberTest` class tells JUnit to kick off Cucumber.

## Overriding options

The Cucumber runtime uses configuration parameters to know what features to run,
where the glue code lives, what plugins to use etc. When using JUnit, these
configuration parameters are provided through the `@ConfigurationParameter`
annotation on your test.

For available parameters see: `io.cucumber.junit.platform.engine.Constants`

### Run a subset of Features or Scenarios

Specify a particular scenario by *line*

    @SelectClasspathResource(value = "io/cucumber/skeleton/belly.feature", column = 3)

In case you have multiple feature files or scenarios to run against repeat the
annotation.

You can also specify what to run by *tag*:

    @IncludeTags("zucchini")
