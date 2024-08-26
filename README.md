# selenide-browserstack

[Selenide](http://selenide.org/) Integration with BrowserStack.

<a href="https://www.browserstack.com/automate">![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)</a>

<a href="http://selenide.org/"><img src ="http://selenide.org/images/selenide-logo-big.png" height = "110"></a>

## Using Maven

### Setup

* Clone the repo
* Install dependencies `mvn compile`
* Update `browserstack.yml` files at the root directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

- To run a parallel tests, run `mvn test -P sample-test`
- To run local tests, run `mvn test -P sample-local-test`
- To run a full suite of tests with Cross-browser Testing, run `mvn test -P suite`

 Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

## Using Gradle

### Prerequisites
- If using Gradle, Java v9+ is required.

### Setup

- Clone the repository
- Install dependencies `gradle build`
- Update `browserstack.yml` files at the root directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Run sample build

- To run the test suite having cross-platform with parallelization, run `gradle sampleTest`
- To run local tests, run `gradle sampleLocalTest`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

This repository uses the BrowserStack SDK to run tests on BrowserStack. Follow the steps below to install the SDK in your test suite and run tests on BrowserStack:

* Following are the changes required in `gradle.build` -
  * Add `compileOnly 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies
  * Fetch Artifact Information and add `jvmArgs` property in tasks *SampleTest* and *SampleLocalTest* :
  ```
  def browserstackSDKArtifact = configurations.compileClasspath.resolvedConfiguration.resolvedArtifacts.find { it.name == 'browserstack-java-sdk' }
  
  task sampleTest(type: Test) {
    useTestNG() {
      dependsOn cleanTest
      useDefaultListeners = true
      suites "config/sample.testng.xml"
      jvmArgs "-javaagent:${browserstackSDKArtifact.file}"
    }
  }
  ```

* Install dependencies `gradle build`

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* To test on a different set of browsers, check out our [platform configurator](https://www.browserstack.com/automate/java#setting-os-and-browser)
* You can export the environment variables for the Username and Access Key of your BrowserStack account

  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
