# selenide-browserstack

[Selenide](http://selenide.org/) Integration with BrowserStack.

<a href="https://www.browserstack.com/automate">![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)</a>

<a href="http://selenide.org/"><img src ="http://selenide.org/images/selenide-logo-big.png" height = "110"></a>

## Setup

* Clone the repo
* Install dependencies `mvn compile`
* Update `browserstack.yml` files at the root directory with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

## Running your tests

- To run a parallel tests, run `mvn test -P sample-test`
- To run local tests, run `mvn test -P sample-local-test`
- To run a full suite of tests with Cross-browser Testing, run `mvn test -P suite`

 Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* To test on a different set of browsers, check out our [platform configurator](https://www.browserstack.com/automate/java#setting-os-and-browser)
* You can export the environment variables for the Username and Access Key of your BrowserStack account

  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
