package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * @author Sergey Kuzhel
 */
public class SettingsPage extends BasePage {
    private static final String LASTNAME_INPUT = "settings.page.lastname.input";
    private static final String FIRSTNAME_INPUT = "settings.page.firstname.input";
    private static final String MIDDLENAMES_INPUT = "settings.page.middlename.input";
    private static final String EMAIL_INPUT = "settings.page.email.input";
    private static final String PHONE_INPUT = "settings.page.phone.input";
    private static final String EDIT_BUTTON = "settings.page.edit.button";
    private static final String SAVE_BUTTON = "settings.page.save.button";
    private static final String SETTINGS_FORM = "settings.page.form";
    private static final String SETTING_HEADER = "settings.page.header";

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public String getSettingHeader() {
        return welActions.getElement(SETTING_HEADER).getText();
    }

    public void inputLastName(String name) {
        welActions.input(LASTNAME_INPUT, name);
    }

    public void inputFirstName(String name) {
        welActions.input(FIRSTNAME_INPUT, name);
    }

    public void inputMiddleNames(String name) {
        welActions.input(MIDDLENAMES_INPUT, name);
    }

    public void inputEmailInput(String name) {
        welActions.input(EMAIL_INPUT, name);
    }

    public void inputPhoneInput(String name) {
        welActions.input(PHONE_INPUT, name);
    }

    public void clickEdit() {
        welActions.click(EDIT_BUTTON);
    }

    public void clickSaveButton(){welActions.click(SAVE_BUTTON);}

    public List<String> getSettingsValue(){
        return welActions.getElementsText(SETTINGS_FORM);
    }
}
