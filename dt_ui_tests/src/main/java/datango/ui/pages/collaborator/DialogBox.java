package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * @author Sergey Kuzhel
 */
public class DialogBox extends BasePage {
    private static final String APPLY_BUTTON = "dialog.apply.button";
    private static final String OK_BUTTON = "dialog.ok.button";
    private static final String DIALOG_WINDOW = "dialog.message.window";
    private static final String OVERLAY_WIDGET = "overlay.widget";

    public DialogBox(WebDriver driver) {
        super(driver);
    }

    public void clickApplyButton() {
        driverManager.getWebElementActions().click(APPLY_BUTTON);
    }

    public void clickOkButton() {
        driverManager.getWebElementActions().click(OK_BUTTON);
    }

    public boolean isErrorMessage() {
        return welActions.isClickable(DIALOG_WINDOW);
    }

    public boolean isOverlay() {
        return welActions.isPresent(OVERLAY_WIDGET);
    }
}
