# This make file helps the maintainers keep the skeleton up to date.
SHELL := /bin/bash

default:
	./mvnw test
	./gradlew test --rerun-tasks --info
.PHONY: default

.ONESHELL:
update-cucumber-version:
	set -e
	./mvnw versions:update-properties -DincludeProperties="cucumber.version"
	export NEW_VERSION=$$(mvn org.apache.maven.plugins:maven-help-plugin:evaluate -Dexpression=cucumber.version -q -DforceStdout 2> /dev/null)
	sed -i "s/cucumberVersion = '.*'/cucumberVersion = '$$NEW_VERSION'/g" build.gradle
	./mvnw test
	./gradlew test --rerun-tasks --info
	git commit -am "Upgrade to Cucumber-JVM v$$NEW_VERSION"
	git tag "v$$NEW_VERSION"
.PHONY: update-cucumber-version
