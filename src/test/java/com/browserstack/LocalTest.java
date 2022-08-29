package com.browserstack;

import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import org.testng.Assert;

public class LocalTest extends BrowserStackTest {

	@Test
	public void test() throws Exception {

		open("http://bs-local.com:45691/check");

		String pageText = $("body").text();

		Assert.assertEquals(pageText, "Up and running");
	}
}
