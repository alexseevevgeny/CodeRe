package datango.ui.driver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;

public final class InitOptions {
    private static ChromeOptions optionsChrome = new ChromeOptions();
    private static FirefoxOptions optionsFirefox = new FirefoxOptions();
    private static InternetExplorerOptions optionsInternetExplorer = new InternetExplorerOptions();

    private InitOptions() {
    }

    public static ChromeOptions defaultChromeOptions() {
        optionsChrome.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        //optionsChrome.addArguments("--headless");
        //optionsChrome.addArguments("--window-size=1920,1080");
        /*HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "downloadedFiles");
        optionsChrome.setExperimentalOption("prefs", prefs);*/

        optionsChrome.addArguments("--start-maximized");
        optionsChrome.addArguments("--no-sandbox");
        optionsChrome.addArguments("--disable-gpu");
        optionsChrome.addArguments("--incognito");
        optionsChrome.addArguments("--disable-popup-blocking");
        optionsChrome.addArguments("--disable-extensions");
        optionsChrome.addArguments("--disable-infobars");

        return optionsChrome;
    }

    public static FirefoxOptions defaultFirefoxOptions() {
        optionsFirefox.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        optionsFirefox.addPreference("browser.private.browsing.autostart", true);

        return optionsFirefox;
    }

    public static InternetExplorerOptions defaultInternetExplorerOptions() {
        //stub
        return optionsInternetExplorer;
    }
}