package com.browserstack;
import com.browserstack.local.Local;

import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.*;
import org.testng.annotations.Parameters;

import com.codeborne.selenide.WebDriverRunner;


public class BrowserStackTest {
    public RemoteWebDriver driver;
    private Local l;
    private static JSONObject config;
    private static Map<String, Object> commonCapabilities;
    public static String username;
    public static String accessKey;
    public static String sessionId;

    @BeforeSuite(alwaysRun = true)
    @Parameters(value = { "config"})
    public void beforeSuite(String config_file) throws Exception {
        JSONParser parser = new JSONParser();
        config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + config_file));
        commonCapabilities = (Map<String, Object>) config.get("capabilities");
        
        username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }
        try {
            if ((commonCapabilities.get("browserstack.local") != null &&
            commonCapabilities.get("browserstack.local").toString().equalsIgnoreCase("true") && (l == null))) {
                l = new Local();
                Map<String, String> options = new HashMap<String, String>();
                options.put("key", accessKey);
                l.start(options);
            }
        } catch (Exception e) {
            System.out.println("Error while start local - " + e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters(value = { "config", "environment" })
    public void setUp(String config_file, String environment) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + config_file));
        JSONObject envs = (JSONObject) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserstack.source","selenide:sample-master:v1.0");

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities= (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        sessionId = driver.getSessionId().toString();

        WebDriverRunner.setWebDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {
        if (l != null) l.stop();
    }
}
