package datango.ui.events;

import datango.ui.driver.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.Arrays;

@Slf4j
public class EventHandler implements WebDriverEventListener {
    private DriverManager driverManager;

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        log.info("Should navigate to: " + url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        log.info("Successful navigated to: " + url);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        driverManager = new DriverManager(driver);
        driverManager.getDriverWait().until(driver1 -> driverManager.getJs().executeScript("return document.readyState").equals("complete") && driverManager.getJs()
            .executeScript("return jQuery.active === 0").equals(true));
        if (by != null) {
            log.debug("Trying to find Element By: " + by);
        }
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        if (by != null) {
            log.debug("Found element By: " + by);
        }

    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        log.info("Trying to click on: " + element.toString());
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        log.info("Clicked on: " + element.toString());
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        if (keysToSend != null) {
            log.info("Value should be inputted: " + Arrays.toString(keysToSend));
        }
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        if (keysToSend != null) {
            log.info("Value has been inputted to element: " + element + "text: " + Arrays.toString(keysToSend));
        }
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        log.info("Script should be executed: " + script);
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        if (!script.isEmpty()) {
            log.info("Script fulfilled: " + script);
        }
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {
        //do nothing because not used
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
        //do nothing because not used
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        //do nothing because not used
    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
        log.info("Text in element: " + element.getText());
    }
}
