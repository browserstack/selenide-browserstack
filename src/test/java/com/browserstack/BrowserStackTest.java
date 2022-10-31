package com.browserstack;
import com.browserstack.local.Local;

import java.io.FileReader;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;

import com.codeborne.selenide.WebDriverRunner;


public class BrowserStackTest {
	public RemoteWebDriver driver;
	private Local l;
	public static String sessionId;

	@BeforeMethod(alwaysRun=true)
	@Parameters(value={"config", "environment"})
	public void setUp(String config_file, String environment) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + config_file));
		JSONObject envs = (JSONObject) config.get("environments");

		MutableCapabilities capabilities = new MutableCapabilities();

		Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
		Iterator it = envCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			capabilities.setCapability(pair.getKey().toString(), pair.getValue());
		}

		Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
		it = commonCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (capabilities.getCapability(pair.getKey().toString()) == null) {
				capabilities.setCapability(pair.getKey().toString(), pair.getValue());
			} else if (pair.getKey().toString().equalsIgnoreCase("bstack:options")) {
				HashMap bstackOptionsMap = (HashMap) pair.getValue();
				bstackOptionsMap.putAll((HashMap) capabilities.getCapability("bstack:options"));
				capabilities.setCapability(pair.getKey().toString(), bstackOptionsMap);
			}
		}

		String username = System.getenv("BROWSERSTACK_USERNAME");
		if (username == null) {
			username = (String) config.get("user");
		}

		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		if (accessKey == null) {
			accessKey = (String) config.get("key");
		}

		if (capabilities.getCapability("bstack:options") != null) {
			HashMap bstackOptionsMap = (HashMap) capabilities.getCapability("bstack:options");
			if ((bstackOptionsMap.get("local") != null && bstackOptionsMap.get("local").toString().equalsIgnoreCase("true")) || (capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local").toString().equalsIgnoreCase("true"))) {
				l = new Local();
				Map<String, String> options = new HashMap<String, String>();
				options.put("key", accessKey);
				l.start(options);
			}
			bstackOptionsMap.put("source", "selenide:sample-master:v1.0");
		}

		driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		sessionId = driver.getSessionId().toString();

		WebDriverRunner.setWebDriver(driver);
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown() throws Exception {
		WebDriverRunner.closeWebDriver();
		if (l != null) l.stop();
	}
}
