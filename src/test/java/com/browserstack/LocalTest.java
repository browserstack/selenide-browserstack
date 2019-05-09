package com.browserstack;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;

import org.testng.annotations.Test;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.*;
import java.util.regex.Pattern;

public class LocalTest extends BrowserStackTest {

	@Test
	public void test() throws Exception {

		open("http://www.google.com");

		$(By.name("q")).setValue("BrowserStack").pressEnter();

		sleep(2000);

		Assert.assertTrue(Pattern.matches("BrowserStack -.*", title()));

	}
}