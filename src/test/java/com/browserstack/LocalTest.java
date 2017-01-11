package com.browserstack;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LocalTest extends BrowserStackTest {

	@Test
	public void test() throws Exception {

		open("http://bs-local.com:45691/check");

		$("body").shouldHave(text("Up and running"));

	}
}