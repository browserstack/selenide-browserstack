package com.browserstack;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;

import org.testng.annotations.Test;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.*;

public class LocalTest extends BrowserStackTest {

	@Test
	public void test() throws Exception {

		open("http://bs-local.com:45691/check");

		String pageText = $("body").text();

		Assert.assertEquals(pageText, "Up and running");
	}
}
