# Cucumber-Java Skeleton

This is the simplest possible setup for Cucumber using Java. There is nothing
fancy like a webapp or browser testing. All this does is to show you how to
set up and run Cucumber!

There is a single feature file with one scenario. The scenario has three steps,
two of them pending. See if you can make them all pass!

To write assertions the project comes with [AssertJ](https://assertj.github.io/doc/#assertj-core-assertions-guide)
included. 

## Get the code

Git:

    git clone https://github.com/cucumber/cucumber-java-skeleton.git
    cd cucumber-java-skeleton

Subversion:

    svn checkout https://github.com/cucumber/cucumber-java-skeleton/trunk cucumber-java-skeleton
    cd cucumber-java-skeleton

Or [download a zip](https://github.com/cucumber/cucumber-java-skeleton/archive/main.zip) file.

## Run the tests
### Use Maven

Open a command window and run:

    cd maven
    ./mvnw test

This runs Cucumber features using Cucumber's JUnit Platform Engine. The `Suite`
annotation on the `RunCucumberTest` class tells JUnit to kick off Cucumber.

### Use Gradle

Open a command window and run:

    cd gradle
    ./gradlew test --rerun-tasks --info

This runs Cucumber features using Cucumber's JUnit Platform Engine. The `Suite`
annotation on the `RunCucumberTest` class tells JUnit to kick off Cucumber.

## Configuration 

The [Cucumber JUnit Platform Engine](https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-junit-platform-engine) uses configuration parameters to know what features to run,
where the glue code lives, what plugins to use, etc. When using JUnit, these
configuration parameters are provided through the `@ConfigurationParameter`
annotation on your test.

For available parameters see: `io.cucumber.junit.platform.engine.Constants`

## Run a subset of Features or Scenarios

Specify a particular scenario by *line*

    @SelectClasspathResource(value = "io/cucumber/skeleton/belly.feature", line = 3)

In case you have multiple feature files or scenarios to run against repeat the
annotation.

You can also specify what to run by *tag*. 

First add a tag to a scenario:

```feature
Feature: Belly

  @Zucchini
  Scenario: a few cukes
```

Then add an annotation to `RunCucumberTest`.

```java
@IncludeTags("Zucchini")
```

When using Maven, tags can be selected from the CLI using the `groups` and `excludedGroups` parameters. These take a
[JUnit5 Tag Expression](https://junit.org/junit5/docs/current/user-guide/#running-tests-tag-expressions). 
Note: When using JUnit, the `@` is not part of the tag.

```
mvn verify -DexcludedGroups="Haricots" -Dgroups="Zucchini | Gherkin"
```

### Running a single scenario or feature

Maven and Gradle do not (yet) support selecting single features or scenarios
with JUnit selectors. As a work around the `cucumber.features` property can be
used. Because this property will cause Cucumber to ignore any other selectors
from JUnit it is prudent to only execute the Cucumber engine.

#### With Maven

To select the scenario on line 3 of the `belly.feature` file use:

```
./mvnw test -Dsurefire.includeJUnit5Engines=cucumber -Dcucumber.features=src/test/resources/io/cucumber/skeleton/belly.feature:3 
```

Note: Add `-Dcucumber.plugin=pretty` to get a more detailed output during test execution.

#### With Gradle

TODO: (I don't know how to do this. Feel free to send a pull request. ;))
