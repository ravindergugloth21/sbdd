package automation.utilities;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Hook {

    public Utils utils;
    public WebDriver webDriver;

    public String LOG4J_FILE = FileUtils.getCurrentDir() + FileUtils.getFileSeparator() + "log4j.properties";
    public Logger log = Logger.getLogger("Hook");
    public Pojo gettersSetters = Pojo.getInstance();
    public Properties properties = ReadPropertyFile.getInstance().getProperties();
    public static String DRIVER_PATH;

    // Windows
    public static final String CHROME_DRIVER = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "./src/test/resources/browser_drivers/chromedriver.exe";

    @Before(order = 1)
    public void launchBrowser(Scenario scenario) {
        PropertyConfigurator.configure(LOG4J_FILE);
        String os = FileUtils.OSDetector();
        String browser = System.getProperty("runnerType");
        if (browser == null)
            browser = properties.getProperty("runnerType");
        else
            throw new FrameworkException("Browser should not be empty");
        gettersSetters.setBrowserName(browser);
        System.out.println(browser);
        switch (os) {
            case "Windows":
                if (browser.contains("chrome"))
                    DRIVER_PATH = CHROME_DRIVER_PATH;

                break;


            default:
                System.out.println("None");

        }

        if (webDriver == null || ((RemoteWebDriver) webDriver).getSessionId() == null) {
            switch (browser) {

                case "chrome":
                default:
                    System.setProperty(CHROME_DRIVER, DRIVER_PATH);
                    HashMap<String, Object> plugin = new HashMap<String, Object>();
                    plugin.put("plugins.plugins_disabled", new String[]{
                            "Chrome PDF Viewer"
                    });
                    plugin.put("enabled", false);
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("profile.default_content_settings.popups", 0);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("plugins.always_open_pdf_externally", true);
                    // To disable the save of credentials
                    ChromeOptions options = new ChromeOptions();
                    Map<String, Object> prefs2 = new HashMap<String, Object>();
                    prefs.put("credentials_enable_service", false);
                    options.setExperimentalOption("prefs", prefs);
                    webDriver = new ChromeDriver(options);
                    break;
            }
        }

        utils = Utils.getInstance();
        gettersSetters.setScenarioName(scenario.getName());
        gettersSetters.setBrowserName(browser);
        webDriver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
        utils.intialize(webDriver);

        SerenityWebdriverManager.inThisTestThread().setCurrentDriverTo(webDriver);
        if (!(browser.contains("mobile")))
            webDriver.manage().window().maximize();
        String environment = System.getProperty("target.server");
        if (environment == null) {
            environment = properties.getProperty("target.server");
        } else {
            throw new FrameworkException("Environment should not be empty");
        }
        webDriver.get(environment);
        log.info("Navigated to " + environment);
        gettersSetters.setEnvironmeent(environment);
    }

    @After
    public void destoryDriver(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                scenario.embed(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES), "image/png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (webDriver != null) {
            System.out.println(Serenity.getWebdriverManager().getCurrentDriver());
            webDriver.quit();
            Serenity.getWebdriverManager().closeDriver();
        }
    }


}
