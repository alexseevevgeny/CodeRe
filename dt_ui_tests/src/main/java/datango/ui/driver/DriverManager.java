package datango.ui.driver;

import datango.config.AppConfigProvider;
import datango.ui.helpers.WebElementActions;
import datango.utils.AllureReportUtil;
import datango.utils.WaitUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Manages WebDriver
 * @author Sergey Kuzhel
 */
@Slf4j
public class DriverManager {

    private static final int DEFAULT_IMPL_WAIT_SEC = AppConfigProvider.get().defImplWait();

    @Getter
    private WebDriver driver;

    @Getter
    private WebDriverWait driverWait;

    @Getter
    private WebElementActions webElementActions;

    @Getter
    private JavascriptExecutor js;

    public DriverManager(WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(driver, DEFAULT_IMPL_WAIT_SEC);
        this.webElementActions = new WebElementActions(driver, driverWait);
        this.js = (JavascriptExecutor) driver;
        log.debug("Driver manager created");
    }

    public void waitForLoad() {
        WaitUtil.setWait(AppConfigProvider.get().defExplWait());
    }

    public void shortWaitForLoad() {
        WaitUtil.setWait(AppConfigProvider.get().defElLoadWait());
    }

    public void increaseWait(int times) {
        for (int i = 0; i < times; i++) {
            shortWaitForLoad();
        }
    }

    public static void takeScreenshot(WebDriver driver) {
        if (driver == null) {
            return;
        }
        byte[] bytes = ((EventFiringWebDriver) driver).getScreenshotAs(OutputType.BYTES);
        AllureReportUtil.attachPng("SCREENSHOT ", bytes);
    }

}
