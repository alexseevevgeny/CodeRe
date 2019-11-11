package datango.ui.settings;

import datango.BaseTest;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.enums.AssertMessages.MSG_IS_ERROR_MESSAGE;
import static datango.ui.enums.AssertMessages.MSG_SETTINGS_EDITED;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Settings] automation test cases")
public class SettingsTest extends BaseTest {

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoSettingsPage();
    }

    @BeforeMethod
    public void setUpMethod(){
        browserManager.reloadPage();

    }

    @AfterClass
    public void tearDownClass() {
        browserManager.reloadPage();
        settingsPage.clickEdit();
        settingsPage.inputFirstName("adtadmin");
        settingsPage.inputLastName("adtadmin");
        settingsPage.inputMiddleNames(TEST_EMPTY);
        settingsPage.inputEmailInput(TEST_EMAIL);
        settingsPage.inputPhoneInput(TEST_PHONE_NUMBER);
        settingsPage.clickSaveButton();
    }

    @TestCaseID(id = "4773")
    @Test(description = "Verify editing user's details (correct input)", groups = "smoke-test")
    @TmsLink("4773")
    public void settingsEditUserCorrectTest(){
        List<String> inputtedArr = new ArrayList<>();
        inputtedArr.add(TEST_FIRST_NAME);
        inputtedArr.add(TEST_LAST_NAME);
        inputtedArr.add(TEST_MIDDLE_NAME);
        inputtedArr.add(TEST_EMAIL);
        inputtedArr.add(TEST_PHONE_NUMBER);
        settingsPage.clickEdit();
        settingsPage.inputFirstName(TEST_FIRST_NAME);
        settingsPage.inputLastName(TEST_LAST_NAME);
        settingsPage.inputMiddleNames(TEST_MIDDLE_NAME);
        settingsPage.inputEmailInput(TEST_EMAIL);
        settingsPage.inputPhoneInput(TEST_PHONE_NUMBER);
        settingsPage.clickSaveButton();
        assertTrue(settingsPage.getSettingsValue().containsAll(inputtedArr),
            MSG_SETTINGS_EDITED.getValue());

    }

    @TestCaseID(id = "4774")
    @Test(description = "Verify editing user's details (incorrect input)", groups = "smoke-test")
    @TmsLink("4774")
    public void settingsEditUserIncorrectTest(){
        settingsPage.clickEdit();
        settingsPage.inputFirstName(TEST_SPACE);
        settingsPage.inputLastName(TEST_LAST_NAME);
        settingsPage.inputMiddleNames(TEST_MIDDLE_NAME);
        settingsPage.inputEmailInput(TEST_EMAIL);
        settingsPage.inputPhoneInput(TEST_PHONE_NUMBER);
        settingsPage.clickSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        settingsPage.inputFirstName(TEST_FIRST_NAME);
        settingsPage.inputLastName(TEST_SPACE);
        settingsPage.inputMiddleNames(TEST_MIDDLE_NAME);
        settingsPage.inputEmailInput(TEST_EMAIL);
        settingsPage.inputPhoneInput(TEST_PHONE_NUMBER);
        settingsPage.clickSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4775")
    @Test(description = "Verify editing user's details (blank mandatory fields)", groups = "smoke-test")
    @TmsLink("4775")
    public void settingsEditUserMandatoryTest(){
        settingsPage.clickEdit();
        settingsPage.inputFirstName(TEST_SPACE);
        settingsPage.inputLastName(TEST_LAST_NAME);
        settingsPage.clickSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        settingsPage.inputFirstName(TEST_FIRST_NAME);
        settingsPage.inputLastName(TEST_SPACE);
        settingsPage.clickSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

}
