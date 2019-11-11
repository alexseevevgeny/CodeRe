package datango.ui.login;

import datango.BaseTest;
import datango.config.AppConfigProvider;
import datango.testrail.TestCaseID;
import datango.testrail.TestCaseIds;
import datango.ui.data.CredentialsDataHelper;
import datango.utils.WaitUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.data.TestDataProvider.ROOT_OU;
import static datango.ui.data.TestDataProvider.SERVICE_USER;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.enums.FormFields.LOGIN;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Login] automation test cases")
public class LoginTest extends BaseTest {
    String userName;

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod() {
        browserManager.reloadPage();
        collaborator.openLoginPage();
    }

    @TestCaseID(id = "4846")
    @Test(description = "Verification of valid login (user is active)", groups = "smoke-test")
    @TmsLink("4846")
    public void loginTest() {
        collaborator.performDefaultLogin();
        assertTrue(collaborator.isLogoutButtonPresent(), MSG_LOGIN_SUCCESSFUL.getValue());
    }

    @TestCaseID(id = "4847")
    @Test(description = "Verification of valid login (user inactive)", groups = "smoke-test")
    @TmsLink("4847")
    public void loginInactiveTest() {
        collaborator.performInactiveLogin();
        assertTrue(collaborator.isLoginErrorMessagePresent(), MSG_LOGIN_FAILED.getValue());
    }

    @TestCaseID(id = "4848")
    @Test(description = "Verification of login as a guest", groups = "regression-test")
    @TmsLink("4848")
    public void loginAsGuestTest() {
        collaborator.performGuestLogin();
        assertTrue(collaborator.isLogoutButtonPresent(), MSG_LOGIN_SUCCESSFUL.getValue());
    }


    @TestCaseIds({"4843", "4844", "4845"})
    @TmsLink("4843")
    @TmsLink("4844")
    @TmsLink("4845")
    @Test(description = "Verification of invalid login", groups = "regression-test",
            dataProviderClass = CredentialsDataHelper.class, dataProvider = "invalidCredentials")
    public void invalidLoginTest(Map<String, String> creds) {
        collaborator.performLogin(creds);
        if ("".equals(creds.get(LOGIN.getValue()))) {
            assertTrue(collaborator.isLoginButtonPresent(), MSG_LOGIN_FAILED.getValue());
        } else {
            assertTrue(collaborator.isLoginErrorMessagePresent(), MSG_LOGIN_FAILED.getValue());
        }

    }

    @TestCaseID(id = "4858")
    @Test(description = "Verification of www.datango.com link on the login page ", groups = "regression-test")
    @TmsLink("4858")
    public void verifyDatangoLinkTest() {
        Assert.assertEquals(collaborator.getLoginPage().getDatangoUrl(), "www.datango.de");
    }

    @TestCaseID(id = "4850")
    @Test(description = "Verification of Self-Registration (blank mandatory fields)",  groups = "regression-test")
    @TmsLink("4850")
    public void verifySelfRegBlankMandatoryTest() {
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().clickRegButton();
        assertTrue(!collaborator.isLogoutButtonPresent(), MSG_LOGIN_FAILED.getValue());
    }

    @TestCaseID(id = "4853")
    @Test(description = "Verification of Self-Registration (incorrect input)",  groups = "regression-test")
    @TmsLink("4853")
    public void verifySelfRegIncorrectTest() {
        userName = " ";
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().inputRegUserName(userName);
        collaborator.getLoginPage().inputRegCode1(TEST_REG_CODE);
        collaborator.getLoginPage().inputRegCode2(TEST_REG_CODE);
        collaborator.getLoginPage().inputLastName(userName + TEST_LAST_NAME);
        collaborator.getLoginPage().inputFirstName(userName);
        collaborator.getLoginPage().inputMiddleName(userName + TEST_MIDDLE_NAME);
        collaborator.getLoginPage().inputEmail(TEST_EMAIL);
        collaborator.getLoginPage().inputPassword(userName);
        collaborator.getLoginPage().inputRePassword(userName);
        collaborator.getLoginPage().clickRegButton();
        assertTrue(!collaborator.isLogoutButtonPresent(), MSG_LOGIN_FAILED.getValue());
    }

    @TestCaseID(id = "4852")
    @Test(description = "Verification of Self-Registration (incorrect reg codes)",  groups = "regression-test")
    @TmsLink("4852")
    public void verifySelfRegIncorrectCodesTest() {
        userName = getRandomString(7, ENGLISH_ALPHABET, true);
        String code = getRandomString(2, NUMERIC, false);
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().inputRegUserName(userName);
        collaborator.getLoginPage().inputRegCode1(TEST_REG_CODE + code);
        collaborator.getLoginPage().inputRegCode2(TEST_REG_CODE + code);
        collaborator.getLoginPage().inputLastName(userName + TEST_LAST_NAME);
        collaborator.getLoginPage().inputFirstName(userName);
        collaborator.getLoginPage().inputMiddleName(userName + TEST_MIDDLE_NAME);
        collaborator.getLoginPage().inputEmail(TEST_EMAIL);
        collaborator.getLoginPage().inputPassword(userName);
        collaborator.getLoginPage().inputRePassword(userName);
        collaborator.getLoginPage().clickRegButton();
        assertTrue(collaborator.isLoginErrorMessagePresent(), MSG_LOGIN_FAILED.getValue());
    }

    @TestCaseID(id = "4851")
    @Test(description = "Verification of Self-Registration (existing user name)", groups = "regression-test")
    @TmsLink("4851")
    public void verifySelfRegExistingUserTest() {
        userName = AppConfigProvider.get().defLogin();
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().inputRegUserName(userName);
        collaborator.getLoginPage().inputRegCode1(TEST_REG_CODE);
        collaborator.getLoginPage().inputRegCode2(TEST_REG_CODE);
        collaborator.getLoginPage().inputLastName(userName + TEST_LAST_NAME);
        collaborator.getLoginPage().inputFirstName(userName);
        collaborator.getLoginPage().inputMiddleName(userName + TEST_MIDDLE_NAME);
        collaborator.getLoginPage().inputEmail(TEST_EMAIL);
        collaborator.getLoginPage().inputPassword(userName);
        collaborator.getLoginPage().inputRePassword(userName);
        collaborator.getLoginPage().clickRegButton();
        assertTrue(collaborator.isLoginErrorMessagePresent(), MSG_LOGIN_FAILED.getValue());
    }

    @TestCaseID(id = "4849")
    @Test(description = "Verification of Self-Registration (correct input)", groups = "regression-test")
    @TmsLink("4849")
    public void verifySelfRegTest() {
        userName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().clickSelfRegistration();
        collaborator.getLoginPage().inputRegUserName(userName);
        collaborator.getLoginPage().inputRegCode1(TEST_REG_CODE);
        collaborator.getLoginPage().inputRegCode2(TEST_REG_CODE);
        collaborator.getLoginPage().inputLastName(userName + TEST_LAST_NAME);
        collaborator.getLoginPage().inputFirstName(userName);
        collaborator.getLoginPage().inputMiddleName(userName + TEST_MIDDLE_NAME);
        collaborator.getLoginPage().inputEmail(TEST_EMAIL);
        collaborator.getLoginPage().inputPassword(userName);
        collaborator.getLoginPage().inputRePassword(userName);
        collaborator.getLoginPage().clickRegButton();
        WaitUtil.setWait(10);
        assertTrue(collaborator.isLogoutButtonPresent(), MSG_LOGIN_SUCCESSFUL.getValue());
        collaborator.performLogOut();
    }

    @TestCaseID(id = "4860")
    @Test(description = "Verify user deactivating after multiple wrong password entries", groups = "regression-test")
    @TmsLink("4860")
    public void verifyDeactivateAccountTest() {
        userName = getRandomString(7, ENGLISH_ALPHABET, true);
        String wrongPass = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.performDefaultLogin();
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        collaborator.performLogOut();
        for (int i = 0; i < 6; i++) {
            collaborator.getLoginPage().inputLoginUserName(userName);
            collaborator.getLoginPage().inputLoginPassword(wrongPass);
            collaborator.getLoginPage().clickLoginBtn();
            dialogBox.clickOkButton();
        }
        collaborator.getLoginPage().inputLoginUserName(userName);
        collaborator.getLoginPage().inputLoginPassword(userName);
        collaborator.getLoginPage().clickLoginBtn();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4859")
    @Test(description = "Verification of session timeout", groups = "regression-test")
    @TmsLink("4859")
    public void verifySessionTimeout() {
        collaborator.performDefaultLogin();
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
        collaborator.gotoServerSettingsAdministration();
        serverSettingsPage.clickMiscellaneousBtn();
        serverSettingsPage.clickMiscellaneousBtn("60");
        serverSettingsPage.clickSaveBtn();
        browserManager.getCookies();
        browserManager.setCookies();
        WaitUtil.setWait(65);
        collaborator.gotoPermissionsAdministration();
        assertTrue(collaborator.isLoginButtonPresent());
        collaborator.performDefaultLogin();
        collaborator.gotoServerSettingsAdministration();
        serverSettingsPage.clickMiscellaneousBtn();
        serverSettingsPage.clickMiscellaneousBtn("3600");
        serverSettingsPage.clickSaveBtn();
    }

    @TestCaseID(id = "4856")
    @Test(description = "Verification of user logout when logged in with 2 different users", groups = "regression-test")
    @TmsLink("4856")
    public void verifyDisableSelfRegistrationTest() {
        userName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        browserManager.openNewTab();
        browserManager.switchToAnotherTab(1);
        collaborator.getLoginPage().inputLoginUserName(userName);
        collaborator.getLoginPage().inputLoginPassword(userName);
        collaborator.getLoginPage().clickLoginBtn();
        browserManager.switchToAnotherTab(0);
        collaborator.performLogOut();
        assertTrue(collaborator.isLoginButtonPresent());
        browserManager.switchToAnotherTab(1);
        browserManager.reloadPage();
        assertTrue(collaborator.isLoginButtonPresent());
        browserManager.closeTab();
        browserManager.switchToAnotherTab(0);
    }

    @TestCaseID(id = "4841")
    @Test(description = "Verify appearance of login page ('guest' user is active, Self-Registration is possible)", groups = "regression-test")
    @TmsLink("4841")
    public void verifySelfRegPossibleGuestActiveTest() {
        assertTrue(collaborator.getLoginPage().isGuestLinkPresent());
        assertTrue(collaborator.getLoginPage().isSelfRegLinkPresent());
    }

    @TestCaseID(id = "4842")
    @Test(description = "Verify appearance of login page ('guest' user is inactive, Self-Registration is possible)", groups = "regression-test")
    @TmsLink("4842")
    public void verifySelfRegPossibleGuestInactiveTest() {
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(ROOT_OU);
        orgViewPage.activateDeactivateEntity("guest");
        collaborator.performLogOut();
        assertFalse(collaborator.getLoginPage().isGuestLinkPresent());
        assertTrue(collaborator.getLoginPage().isSelfRegLinkPresent());
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(ROOT_OU);
        orgViewPage.activateDeactivateEntity("guest");
        collaborator.performLogOut();
    }

    @TestCaseID(id = "4854")
    @Test(description = "Verification of user switch when logged in with 2 different users (same browser)", groups = "regression-test")
    @TmsLink("4854")
    public void verifyLoginByTwoUserSameBrowserTest() {
        userName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        browserManager.openNewTab();
        browserManager.switchToAnotherTab(1);
        collaborator.getLoginPage().inputLoginUserName(userName);
        collaborator.getLoginPage().inputLoginPassword(userName);
        collaborator.getLoginPage().clickLoginBtn();
        browserManager.reloadPage();
        browserManager.switchToAnotherTab(0);
        browserManager.reloadPage();
        assertTrue(collaborator.getLoginPage().getUserName().contains(SERVICE_USER));
        browserManager.switchToAnotherTab(1);
        assertTrue(collaborator.getLoginPage().getUserName().contains(SERVICE_USER));
        browserManager.closeTab();
        browserManager.switchToAnotherTab(0);
    }
}

