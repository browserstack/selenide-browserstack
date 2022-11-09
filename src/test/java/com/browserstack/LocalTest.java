package com.browserstack;

import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.Selenide.open;
import org.testng.Assert;

public class LocalTest extends BrowserStackTest {

	@Test
	public void test() throws Exception {

		open("http://bs-local.com:45454/");

		String pageTitle = title();

		Assert.assertEquals(pageTitle, "BrowserStack Local");
	}
}
