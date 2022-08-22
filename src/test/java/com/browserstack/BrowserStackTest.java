package com.browserstack;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.codeborne.selenide.WebDriverRunner;


public class BrowserStackTest {
    public RemoteWebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
			MutableCapabilities capabilities = new MutableCapabilities();
			driver = new RemoteWebDriver(new URL("https://hub-cloud.browserstack.com/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebDriverRunner.setWebDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
			WebDriverRunner.closeWebDriver();
    }
}
