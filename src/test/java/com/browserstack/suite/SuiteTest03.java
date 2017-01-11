package com.browserstack.suite;
import com.browserstack.BrowserStackTest;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class SuiteTest03 extends BrowserStackTest {

    @Test
    public void test() throws Exception {

        open("http://www.google.com");

        $(By.name("q")).setValue("BrowserStack Security").pressEnter();

        sleep(2000);

        Assert.assertEquals(title(), "BrowserStack Security - Google Search");

    }
}