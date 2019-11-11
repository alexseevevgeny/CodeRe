package datango.ui.pages.collaborator;

import datango.config.AppConfigProvider;
import datango.ui.pages.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static datango.ui.enums.FormFields.LOGIN;
import static datango.ui.enums.FormFields.PASSWORD;

/**
 * @author Sergey Kuzhel
 */
@Slf4j
public class LoginPage extends BasePage {
    private static final String USERNAME_INPUT = "login.page.username.input";
    private static final String PASS_INPUT = "login.page.password.input";
    private static final String LOGIN_BUTTON = "login.page.login.button";
    private static final String DATANGO_LINK = "login.page.datango.link";
    private static final String ERR_DIALOG = "login.page.loginerror.dialog";
    private static final String GUEST_LINK = "login.page.guest.link";
    private static final String SELF_REG_LINK = "login.page.selfreg.link";
    private static final String REGISTER_BUTTON = "login.selfreg.register.button";
    private static final String REG_CODE1 = "login.selfreg.regcode1.input";
    private static final String REG_CODE2 = "login.selfreg.regcode2.input";
    private static final String REG_USER_NAME_INPUT = "login.selfreg.username.input";
    private static final String REG_FIRST_NAME_INPUT = "login.selfreg.firstname.input";
    private static final String REG_LAST_NAME_INPUT = "login.selfreg.lastname.input";
    private static final String REG_MIDDLE_NAME_INPUT = "login.selfreg.middlename.input";
    private static final String REG_EMAIL_INPUT = "login.selfreg.email.input";
    private static final String REG_PASS_INPUT = "login.selfreg.pass.input";
    private static final String REG_PASS_RE_INPUT = "login.selfreg.passre.input";
    private static final String ERR_DIALOG_OK_BUTTON = "login.page.loginerror.ok.button";
    private static final String WELCOME_NAME = "menu.user.welcome.span";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open Login page
     */
    public LoginPage open() {
        driverManager.getDriver().get(AppConfigProvider.get().baseUrl());
     log.info("open login page");
     return this;
     }

     /**
     * Perform login with credentials
     */
    public LoginPage logIn(Map<String, String> creds) {
        welActions.input(USERNAME_INPUT, creds.get(LOGIN.getValue()));
        welActions.input(PASS_INPUT, creds.get(PASSWORD.getValue()));
        welActions.click(LOGIN_BUTTON);
        driverManager.waitForLoad();
        return this;
    }

    public void clickSelfRegistration() {
        if (welActions.isClickable(SELF_REG_LINK)
                && !welActions.isClickable(REGISTER_BUTTON)) {
            welActions.click(SELF_REG_LINK);
        }
    }

    public void inputUsername(String userName) {
        welActions.input(USERNAME_INPUT, userName);
    }

    public void inputPass(String pass) {
        welActions.input(PASS_INPUT, pass);
    }

    public void clickLogInButton() {
        welActions.click(LOGIN_BUTTON);
    }

    public void clickErrorOkButton() {
        welActions.click(ERR_DIALOG_OK_BUTTON);
    }

    public void clickRegButton() {
        welActions.click(REGISTER_BUTTON);
    }

    /**
     * Perform login as guest
     */
    public LoginPage guestLogIn() {
        driverManager.waitForLoad();
        welActions.click(GUEST_LINK);
        return this;
    }

    /**
     * Get datango link
     *
     * @return
     */
    public String getDatangoUrl() {
        driverManager.waitForLoad();
        return welActions.getElementsText(DATANGO_LINK).get(0);
    }

    public void inputRegUserName(String name) {
        welActions.input(REG_USER_NAME_INPUT, name);
    }

    public void inputRegCode1(String code) {
        welActions.input(REG_CODE1, code);
    }

    public void inputRegCode2(String code) {
        welActions.input(REG_CODE2, code);
    }

    public void inputLastName(String name) {
        welActions.input(REG_LAST_NAME_INPUT, name);
    }

    public void inputFirstName(String name) {
        welActions.input(REG_FIRST_NAME_INPUT, name);
    }

    public void inputMiddleName(String name) {
        welActions.input(REG_MIDDLE_NAME_INPUT, name);
    }

    public void inputEmail(String email) {
        welActions.input(REG_EMAIL_INPUT, email);
    }

    public void inputPassword(String pass) {
        welActions.input(REG_PASS_INPUT, pass);
    }

    public void inputRePassword(String pass) {
        welActions.input(REG_PASS_RE_INPUT, pass);
    }

    public void inputLoginUserName(String userName) {
        welActions.input(USERNAME_INPUT, userName);
    }

    public void inputLoginPassword(String pass) {
        welActions.input(PASS_INPUT, pass);
    }

    public void clickLoginBtn() {
        welActions.click(LOGIN_BUTTON);
    }

    /**
     * Check is login button present
     */
    public boolean isLoginButtonPresent() {
        return isElementPresentOnPage(LOGIN_BUTTON);
    }

    public boolean isErrorMessagePresent() {
        return isElementPresentOnPage(ERR_DIALOG);
    }

    public boolean isGuestLinkPresent() {
        return welActions.isClickable(GUEST_LINK);
    }

    public boolean isSelfRegLinkPresent() {
        return welActions.isClickable(SELF_REG_LINK);
    }

    public String getUserName() {
        return welActions.getElement(WELCOME_NAME).getText();
    }
}
