package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import datango.utils.WaitUtil;
import org.openqa.selenium.WebDriver;

/**
 * @author Sergey Kuzhel
 */
public class HomePage extends BasePage {
    private static final String LOGOUT_BUTTON = "menu.header.logout.link";
    private static final String PAGE_BODY = "page.element.body";


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLogoutButtonPresent() {
        return super.isElementPresentOnPage(LOGOUT_BUTTON);
    }

    public boolean isPageLoaded() {
        WaitUtil.setWait(1);
        return super.isElementPresentOnPage(PAGE_BODY);
    }

    public void clickLogOutButton() {
        driverManager.getWebElementActions().click(LOGOUT_BUTTON);
    }

    public void clickLogOutButtonWithMove() {
        driverManager.getWebElementActions().moveToElement(LOGOUT_BUTTON);
        driverManager.getWebElementActions().click(LOGOUT_BUTTON);
    }

}
