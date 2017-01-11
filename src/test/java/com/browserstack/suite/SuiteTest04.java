package com.browserstack.suite;
import com.browserstack.BrowserStackTest;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class SuiteTest04 extends BrowserStackTest {

    @Test
    public void test() throws Exception {

        open("http://www.google.com");

        $(By.name("q")).setValue("BrowserStack Local").pressEnter();

        sleep(2000);

        Assert.assertEquals(title(), "BrowserStack Local - Google Search");

    }
}