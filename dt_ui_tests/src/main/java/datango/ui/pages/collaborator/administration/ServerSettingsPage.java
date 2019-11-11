package datango.ui.pages.collaborator.administration;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.EntityTree;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServerSettingsPage extends BasePage {
    private static final String MISCELLANEOUS_DROPDOWN = "serversettings.dropdown.list";
    private static final String SESSION_TIMEOUT_INPUT = "serversettings.timeout.session.input";
    private static final String SAVE_BUTTON = "serversettings.save.button";

    private EntityTree entityTree;
    private WebDriverWait wait;

    public ServerSettingsPage(WebDriver driver) {
        super(driver);
        this.entityTree = new EntityTree(driver);
    }

    public void clickMiscellaneousBtn() {
        welActions.click(MISCELLANEOUS_DROPDOWN);
    }

    public void clickMiscellaneousBtn(String sessionTime) {
        welActions.input(SESSION_TIMEOUT_INPUT, sessionTime);
    }

    public void clickSaveBtn() {
        welActions.click(SAVE_BUTTON);
    }

}
