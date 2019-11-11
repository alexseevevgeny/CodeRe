package datango.ui.driver;

import datango.config.AppConfigProvider;
import datango.utils.WaitUtil;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

/**
 * @author Sergey Kuzhel
 */
public class BrowserManager {

    private static final String URL = AppConfigProvider.get().baseUrl();
    private WebDriver driver;
    private DriverManager driverManager;

    public BrowserManager(WebDriver driver) {
        this.driver = driver;
        this.driverManager = new DriverManager(driver);
    }

    public void closeTab() {
        driver.close();
    }

    public void reloadPage() {
        driver.navigate().refresh();
    }

    public void navigateTo(String urlPart) {
        getCookies();
        setCookies();
        driver.navigate().to(urlPart);
        WaitUtil.setWait(2);
    }

    public Cookie getCookies() {
        return driver.manage().getCookieNamed("JSESSIONID");
    }

    public void setCookies() {
        driver.manage().addCookie(getCookies());
    }

    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    public String getTitle() {
       return driver.getTitle();
    }

    public void switchToOldTab() {
        try {
            String oldTab = driver.getWindowHandle();
            driver.switchTo().window(oldTab);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        System.out.println(driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    public void openNewTab() {
        driver.getWindowHandle();
        driverManager.getJs().executeScript(String.format("window.open(\"%s\");", URL));
    }

    public void switchToAnotherTab(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber));
    }

}
