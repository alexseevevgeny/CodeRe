package datango.ui.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datango.ui.enums.Browser;
import datango.ui.events.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static datango.ui.enums.Browser.*;
import static datango.utils.OSValidateUtil.isUnix;
import static datango.utils.OSValidateUtil.isWindows;

/**
 * Instantiation of needed driver
 *
 * @author Sergey Kuzhel
 */
@Slf4j
public class DriverFactory {
    private static WebDriver driver;
    private static EventFiringWebDriver eventDriver;
    private static SessionId sesId;
    private static EventHandler eventHandler = new EventHandler();

    private DriverFactory() {
        //DriverFactory should never be instantiated
    }

    public static WebDriver initDriver(Browser browserName) {
        if (eventDriver == null) {
            createDriver(browserName);
            return eventDriver;
        } else {
            return eventDriver;
        }
    }

    private static void createDriver(Browser browserName) {
        if (browserName.equals(FIREFOX)) {
            if (isWindows()) {
                System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
            } else if (isUnix()) {
                System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
            }
            driver = new FirefoxDriver(InitOptions.defaultFirefoxOptions());
            eventDriver = new EventFiringWebDriver(driver);
            eventDriver.register(eventHandler);

            log.info("Firefox browser instantiated");
        } else if (browserName.equals(CHROME)) {
            if (isWindows()) {
                System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
            } else if (isUnix()) {
                System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            }
            ChromeDriverService driverService = ChromeDriverService.createDefaultService();
            driver = new ChromeDriver(driverService, InitOptions.defaultChromeOptions());
            sesId = ((ChromeDriver) driver).getSessionId();
            eventDriver = new EventFiringWebDriver(driver);
            eventDriver.register(eventHandler);
            allowHeadlessDownload(driverService);
            log.info("Chrome browser instantiated");
        } else if (browserName.equals(IE)) {
            if (isWindows()) {
                System.setProperty("webdriver.ie.driver", "src/main/resources/driver/IEDriverServer.exe");
            }
            driver = new InternetExplorerDriver();
            eventDriver = new EventFiringWebDriver(driver);
            eventDriver.register(eventHandler);
            log.info("IE browser instantiated");
        }
        if (driver == null) {
            throw new NullPointerException("Driver not instantiated");
        }
    }

    private static void allowHeadlessDownload(ChromeDriverService driverService) {
        Map<String, Object> commandParams = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String command = null;

        try {
            commandParams.put("cmd", "Page.setDownloadBehavior");
            params.put("behavior", "allow");
            params.put("downloadPath", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "downloadedFiles");
            commandParams.put("params", params);
            ObjectMapper objectMapper = new ObjectMapper();
            command = objectMapper.writeValueAsString(commandParams);
        } catch (JsonProcessingException e) {
            log.info("JsonProcessingException: ", e);
        }
        String u = driverService.getUrl().toString() + "/session/" + sesId + "/chromium/send_command";
        HttpPost request = new HttpPost(u);
        request.addHeader("content-type", "application/json");
        try {
            request.setEntity(new StringEntity(command));
            httpClient.execute(request);
        } catch (IOException e) {
            log.info("I/O Exception: ", e);
        }
    }
}
