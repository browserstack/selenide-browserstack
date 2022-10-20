package com.browserstack;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.codeborne.selenide.WebDriverRunner;
import org.yaml.snakeyaml.Yaml;


public class BrowserStackTest {
    public RemoteWebDriver driver;
    public static String userName, accessKey;
    public static Map<String, Object> browserStackYamlMap;
    public static final String USER_DIR = "user.dir";

    public BrowserStackTest() {
        File file = new File(getUserDir() + "/browserstack.yml");
        this.browserStackYamlMap = convertYamlFileToMap(file, new HashMap<>());
        userName = System.getenv("BROWSERSTACK_USERNAME") != null ? System.getenv("BROWSERSTACK_USERNAME") : (String) browserStackYamlMap.get("userName");
        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY") != null ? System.getenv("BROWSERSTACK_ACCESS_KEY") : (String) browserStackYamlMap.get("accessKey");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, String> bstackOptions = new HashMap<>();
        bstackOptions.put("source", "selenide:sample-sdk:v1.0");
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(new URL(String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", userName, accessKey)), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
    }

    private String getUserDir() {
        return System.getProperty(USER_DIR);
    }

    private Map<String, Object> convertYamlFileToMap(File yamlFile, Map<String, Object> map) {
        try {
            InputStream inputStream = Files.newInputStream(yamlFile.toPath());
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);
            map.putAll(config);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Malformed browserstack.yml file - %s.", e));
        }
        return map;
    }
}
