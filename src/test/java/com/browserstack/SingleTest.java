package com.browserstack;

import org.openqa.selenium.By;

import org.testng.annotations.Test;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.*;

public class SingleTest extends BrowserStackTest {

	@Test
	public void test() throws Exception {

		open("http://www.google.com");

		$(By.name("q")).setValue("BrowserStack").pressEnter();

		sleep(2000);

		Assert.assertEquals(title(), "BrowserStack - Google Search");

	}

}