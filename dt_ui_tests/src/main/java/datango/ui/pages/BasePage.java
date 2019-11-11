package datango.ui.pages;

import datango.ui.driver.BrowserManager;
import datango.ui.driver.DriverManager;
import datango.ui.helpers.WebElementActions;
import datango.utils.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 * @author Sergey Kuzhel
 */
@Slf4j
public abstract class BasePage {
    protected DriverManager driverManager;
    protected BrowserManager browserManager;
    protected WebElementActions welActions;

    public BasePage(WebDriver driver) {
        log.info("{} created", this.getClass().getName());
        this.driverManager = new DriverManager(driver);
        this.welActions = driverManager.getWebElementActions();
    }

    /**
     * Check is an element present on page
     * @param locator
     * @return boolean
     */
    protected boolean isElementPresentOnPage(String locator) {
        try {
            if (welActions.isPresent(locator)) {
                log.info("element {} is present on page", locator);
                return true;
            } else {
                log.info("element {} is not present on page", locator);
                return false;
            }
        } catch (TimeoutException e) {
            log.error("Element is not present", e);

            return false;
        }
    }

    protected boolean isElementVisibleOnPage(String locator) {
        WaitUtil.setWait(3);
        if (welActions.isVisible(locator)) {
            log.info("element {} is present on page", locator);
            return true;
        }
        log.info("element {} is not present on page", locator);

        return false;
    }
}
