package com.jalasoft.automation.core.selenium;

import com.jalasoft.automation.core.config.WebDriverConfig;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class WebDriverManager {
    private static WebDriverManager instance = null;
    private Logger log = Logger.getLogger(getClass());
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private WebDriverConfig webDriverConfig;
    private long implicitWaitTime;
    private long explicitWaitTime;
    private long waitSleepTime;
    private String browser;

    public static WebDriverManager getInstance() {
        if (instance == null || instance.webDriver == null) { //TODO why the or
            instance = new WebDriverManager();
        }
        return instance;
    }

    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return this.webDriverWait;
    }

    public void quitDriver() {
        try {
            log.info("Quit Driver");
            this.webDriver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        webDriver = null;
    }

    public void initializeWebDriver(WebDriverConfig webDriverConfig) throws MalformedURLException {
        log.info("Initializing web driver");
        this.webDriverConfig = webDriverConfig;
        this.implicitWaitTime = this.webDriverConfig.getImplicitWaitTime();
        this.explicitWaitTime = this.webDriverConfig.getExplicitWaitTime();
        this.waitSleepTime = this.webDriverConfig.getWaitSleepTime();
        if (webDriverConfig.getDriverType().equalsIgnoreCase("remote")) {
            this.setupRemoteDriver(webDriverConfig);
        } else {
            this.setupBrowserDriver(webDriverConfig);
        }
        this.setupDriverTimeouts();
    }

    private void setupDriverTimeouts() {
        log.info(String.format("initializing driver timeouts implicit wait time:{%d}, explicit wait time:{%d} ", this.implicitWaitTime, this.explicitWaitTime));
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(this.implicitWaitTime, TimeUnit.SECONDS);
        webDriverWait = new WebDriverWait(webDriver, this.explicitWaitTime, this.waitSleepTime);
    }

    public void setupRemoteDriver(WebDriverConfig webDriverConfig) throws MalformedURLException {
        log.info("setup remote driver");
        DesiredCapabilities caps = this.getDesiredCapabilities(webDriverConfig);
        Map<String, String> settings = webDriverConfig.getRemoteDriverSettings();
        String userName = settings.get("remoteUser");
        String automateKey = settings.get("remoteAutomateKey");
        String urls = String.format("http://%s:%s@hub.browserstack.com/wd/hub", userName, automateKey);
        this.webDriver = new RemoteWebDriver(new URL(urls), caps);
    }

    private DesiredCapabilities getDesiredCapabilities(WebDriverConfig webDriverConfig) {
        return new DesiredCapabilities(webDriverConfig.getDesiredCapabilities());
    }

    public void setupBrowserDriver(WebDriverConfig webDriverConfig) {
        this.browser = webDriverConfig.getBrowser();
        log.info(String.format("setup driver for browser:{%s}", this.browser));
        DesiredCapabilities capabilities = this.getDesiredCapabilities(webDriverConfig);
        switch (this.browser.toLowerCase()) {
            case "firefox":
                webDriver = new FirefoxDriver(capabilities);
                break;
            case "chrome":
                ChromeDriverManager.getInstance().setup();
                webDriver = new ChromeDriver(capabilities);
                break;
            case "ie":
                webDriver = new InternetExplorerDriver(capabilities);
                System.setProperty("webdriver.ie.webDriver", "drivers/IEDriverServer.exe");
                break;
            case "safari":
                webDriver = new SafariDriver(capabilities);
                break;
            default:
                log.error("It is not possible to setup the driver for browser " + this.browser);
                break;
        }
    }
}