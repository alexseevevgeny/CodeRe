package datango.ui.administration.orgView;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.enums.FormFields.NAME;
import static datango.ui.enums.FormFields.PASSWORD;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.*;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Organizational view/User] automation test cases")
public class UserTest extends BaseTest {
    private static final String GROUP = " group";
    private static final String BASE_OU = get().defOuName();
    private String ouName;
    private String regCode;
    private String userName;
    private String userNameEdit;
    private String userForEdit;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        userForEdit = orgViewPage
                .addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
    }

    @BeforeMethod
    public void setUpMethod() {
        ouName = createName();
        regCode = createId();
        browserManager.reloadPage();
        userName = getRandomString(6, ENGLISH_ALPHABET, true).toLowerCase();
        userNameEdit = userName + TEST_EDIT;
        collaborator.gotoUserAdministration();
    }

    @TestCaseID(id = "4759")
    @Test(description = "Verify adding a new user (correct input)", groups = "smoke-test")
    @TmsLink("4759")
    public void userAddCorrectTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(userName);
        List<String> userNames = orgViewPage.getEntityNames();
        assertTrue(userNames.size() < 2, MSG_ENTITY_CREATED.getValue());
    }

    @TestCaseID(id = "4761")
    @Test(description = "Verify adding a new user (incorrect input)", groups = "smoke-test")
    @TmsLink("4761")
    public void userAddInorrectTest() {
        userName = TEST_SPACE;
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4760")
    @Test(description = "Verify adding a new user (existing 'Login')", groups = "regression-test")
    @TmsLink("4760")
    public void userAddExistingLoginTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userForEdit);
        orgViewPage.inputEntityUserAddName(userForEdit);
        orgViewPage.inputEntityUserAddLastName(userForEdit + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4762")
    @Test(description = "Verify adding a new user (blank mandatory)", groups = "regression-test")
    @TmsLink("4762")
    public void userAddBlankMandatoryTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddName(TEST_EMPTY);
        orgViewPage.inputEntityUserAddLogin(TEST_EMPTY);
        orgViewPage.inputEntityUserAddLastName(TEST_EMPTY);
        orgViewPage.inputEntityUserAddEmail(TEST_EMPTY);
        orgViewPage.inputEntityUserAddPassword(TEST_EMPTY);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4763")
    @Test(description = "Verify editing user details (add or change)", groups = "regression-test")
    @TmsLink("4763")
    public void userEditCorrectChangeTest() {
        String mn = TEST_MIDDLE_NAME + userForEdit;
        orgViewPage.searchByName(userForEdit);
        orgViewPage.clickEntity(userForEdit);
        orgViewPage.inputDetailsUserName(userNameEdit);
        orgViewPage.inputDetailsMiddleName(mn);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.searchByName(userForEdit);
        orgViewPage.clickEntity(userForEdit);
        assertTrue(orgViewPage.getDetailsValues().contains(userNameEdit), MSG_ENTITY_EDITED.getValue());
    }

    @TestCaseID(id = "4766")
    @Test(description = "Verify editing user details (clear mandatory fields)", groups = "regression-test")
    @TmsLink("4766")
    public void userEditEmptyMandatoryTest() {
        String login = "     ";
        orgViewPage.searchByName(userForEdit);
        orgViewPage.clickEntity(userForEdit);
        orgViewPage.inputDetailsLogin(login);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        orgViewPage.clickDetailsCloseIcon();
        dialogBox.clickApplyButton();
        orgViewPage.searchByName(userForEdit);
        orgViewPage.clickEntity(userForEdit);
        orgViewPage.getDetails();
        assertTrue(!orgViewPage.getDetailsValues().contains(login), MSG_ENTITY_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4765")
    @Test(description = "Verify editing user details (existing login, name)", groups = "regression-test")
    @TmsLink("4765")
    public void userEditExistingLoginTest() {
        orgViewPage.clickOU(BASE_OU);
        String login = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue()).toLowerCase();
        orgViewPage.searchByName(userForEdit);
        orgViewPage.clickEntity(userForEdit);
        orgViewPage.inputDetailsLogin(login);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        orgViewPage.clickDetailsCloseIcon();
        orgViewPage.searchByName(userForEdit);
        orgViewPage.clickEntity(userForEdit);
        orgViewPage.getDetails();
        assertTrue(!orgViewPage.getDetailsValues().contains(login), MSG_ENTITY_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4754")
    @Test(description = "Verify the activate functionality for users ('active' checkbox)", groups = "regression-test")
    @TmsLink("4754")
    public void userActivateCheckboxTest() {
        orgViewPage.clickOU(BASE_OU);
        userName = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(userName);
        activateOrgEntityTest(userName);
    }

    @TestCaseID(id = "4757")
    @Test(description = "Verify the deactivate functionality for users ('active' checkbox)", groups = "regression-test")
    @TmsLink("4757")
    public void usersDeactivateCheckboxTest() {
        orgViewPage.clickOU(BASE_OU);
        userName = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(userName);
        deActivateOrgEntityTest(userName);
    }

    @TestCaseID(id = "4753")
    @Test(description = "Verify the activate functionality for users ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4753")
    public void userActivateMenuTest() {
        orgViewPage.clickOU(BASE_OU);
        userName = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(userName);
        activateOrgEntityDropdownTest(userName);
    }

    @TestCaseID(id = "4756")
    @Test(description = "Verify the deactivate functionality for users ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4756")
    public void usersDeactivateMenuTest() {
        orgViewPage.clickOU(BASE_OU);
        userName = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(userName);
        deActivateOrgEntityDropdownTest(userName);
    }

    @TestCaseID(id = "4755")
    @Test(description = "Verify the activate functionality for users ('Details' pane)", groups = "regression-test")
    @TmsLink("4755")
    public void userActivateDetailsTest() {
        orgViewPage.clickOU(BASE_OU);
        userName = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(userName);
        orgViewPage.clickEntity(userName);
        orgViewPage.clickDetailsActivate();
        orgViewPage.clickDetailsSaveButton();
        browserManager.reloadPage();
        orgViewPage.searchByName(userName);
        orgViewPage.clickEntity(userName);
        orgViewPage.clickDetailsActivate();
        orgViewPage.clickDetailsSaveButton();
        browserManager.reloadPage();
        orgViewPage.searchByName(userName);
        assertTrue(orgViewPage.isEntityAcivated(userName)
                && !dialogBox.isOverlay(), MSG_ENTITY_EDITED.getValue());
    }

    @TestCaseID(id = "9163")
    @Test(description = "Verify changing Password for a user (incorrect input)", groups = "regression-test")
    @TmsLink("9163")
    public void changeToIncorrectUserPassTest() {
        orgViewPage.clickOU(BASE_OU);
        Map<String, String> testUser = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo());
        String testUserName = testUser.get(NAME.getValue());
        orgViewPage.searchByName(testUserName);
        orgViewPage.clickEntity(testUserName);
        orgViewPage.inputNewPass(TEST_SPACE);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_INVALID_PASSWORD.getValue());
    }

    @TestCaseID(id = "4769")
    @Test(description = "Verify 'Move to OU' functionality for users ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4769")
    public void moveUserToOuTest() {
        List<String> entitiesList = new LinkedList<>();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(BASE_OU);
        String userTwo = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        entitiesList.add(userOne);
        entitiesList.add(userTwo);
        orgViewPage.clickAdvancedSwitchButton();
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickAndCheckEntity(userOne);
        orgViewPage.clickAndCheckEntity(userTwo);
        orgViewPage.clickMoveToOU();
        orgViewPage.clickDetailOU(ouName);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.advancedSearchInput(
            orgViewPage.getAdvancedSearchByOU(BASE_OU) +
                orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        assertFalse(orgViewPage.getEntityNames().containsAll(entitiesList), MSG_REMOVED_USER_TO_OU.getValue());
        orgViewPage.advancedSearchInput(
            orgViewPage.getAdvancedSearchByOU(ouName) +
                orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        assertEquals(orgViewPage.getEntityNames().size(), 2, MSG_MOVE_USER_TO_OU.getValue());
    }

    @TestCaseID(id = "4770")
    @Test(description = "Verify 'Add to groups' functionality for users ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4770")
    public void addUsersToGroupTest() {
        List<String> entitiesList = new LinkedList<>();
        String groupOne = createName() + GROUP;
        String groupTwo = createName() + GROUP;
        List<String> targetGroup = Arrays.asList(groupOne, groupTwo);
        String regCodeOne = createId();
        String regCodeTwo = createId();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupOne);
        orgViewPage.inputEntityRoleAddRegCode(regCodeOne);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupTwo);
        orgViewPage.inputEntityRoleAddRegCode(regCodeTwo);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(BASE_OU);
        String userTwo = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        entitiesList.add(userOne);
        entitiesList.add(userTwo);
        orgViewPage.clickAdvancedSwitchButton();
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickAndCheckEntity(userOne);
        orgViewPage.clickAndCheckEntity(userTwo);
        orgViewPage.clickAddToGroup();
        orgViewPage.addUserToGroup(groupOne);
        orgViewPage.clickAddToGroup();
        orgViewPage.addUserToGroup(groupTwo);
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickEntity(userOne);
        orgViewPage.clickDetailGroupButton();
        assertTrue(orgViewPage.getGroups().containsAll(targetGroup), MSG_GROUP_ADDED_TO_USER.getValue());
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickEntity(userTwo);
        orgViewPage.clickDetailGroupButton();
        assertTrue(orgViewPage.getGroups().containsAll(targetGroup), MSG_GROUP_ADDED_TO_USER.getValue());
    }

    @TestCaseID(id = "4771")
    @Test(description = "Verify 'Remove from groups' functionality for users ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4771")
    public void removeUserFromGroupTest() {
        String group = createName() + GROUP;
        String regsCode = createId();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(group);
        orgViewPage.inputEntityRoleAddRegCode(regsCode);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(userOne);
        orgViewPage.checkEntity(userOne);
        orgViewPage.clickAddToGroup();
        orgViewPage.addUserToGroup(group);
        orgViewPage.searchByName(userOne);
        orgViewPage.checkEntity(userOne);
        orgViewPage.clickEntity(userOne);
        orgViewPage.clickRemoveFromGroup();
        orgViewPage.removeUserFromGroup(group);
        orgViewPage.searchByName(userOne);
        orgViewPage.clickEntity(userOne);
        orgViewPage.clickEntity(userOne);
        orgViewPage.clickDetailGroupButton();
        assertFalse(orgViewPage.getGroups().contains(group), MSG_USERS_REMOVED.getValue());
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 3)
    @Test(description = "Verify adding html code to input fields (Organizational View users)", groups = "smoke-test")
    @TmsLink("11215")
    public void createUserWithHtmlCodeTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(HTML_CODE);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4758")
    @Test(description = "Verify the deactivate functionality for users ('Details' pane)")
    @TmsLink("4758")
    public void deactivateFunctionalityForUserTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        orgViewPage.searchByName(userName);
        orgViewPage.clickEntity(userName);
        orgViewPage.clickDetailsActivate();
        orgViewPage.clickDetailsSaveButton();
        browserManager.reloadPage();
        orgViewPage.searchByName(userName);
        assertFalse(orgViewPage.getActive().stream().allMatch(WebElement::isSelected));
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userName);
        collaborator.getLoginPage().inputLoginPassword(userName);
        collaborator.getLoginPage().clickLoginBtn();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "4764")
    @Test(description = "Verify editing user details (incorrect input)")
    @TmsLink("4764")
    public void verifyEditIncorrectDetailsInputTest() {
        String invalidValue = "";
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();
        orgViewPage.searchByName(userName);
        orgViewPage.clickEntity(userName);
        orgViewPage.inputDetailsLogin(invalidValue);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        orgViewPage.searchByName(userName);
        orgViewPage.clickEntity(userName);
        orgViewPage.inputDetailsUserName(invalidValue);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        orgViewPage.searchByName(userName);
        orgViewPage.clickEntity(userName);
        orgViewPage.inputDetailsLastName(invalidValue);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
    }

    @TestCaseID(id = "4767")
    @Test(description = "Verify changing Password for a user (correct input)", groups = "regression-test")
    @TmsLink("4767")
    public void changeInterfaceLanguageTest() {
        orgViewPage.clickOU(BASE_OU);
        Map<String, String> testUser = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo());
        String testUserName = testUser.get(NAME.getValue());
        String userPassBefore = testUser.get(PASSWORD.getValue());
        orgViewPage.searchByName(testUserName);
        orgViewPage.clickEntity(testUserName);
        orgViewPage.selectInterfaceLanguage(DEUTSCH);
        orgViewPage.clickDetailsSaveButton();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputUsername(testUserName);
        collaborator.getLoginPage().inputPass(userPassBefore);
        collaborator.getLoginPage().clickLogInButton();
        assertEquals(settingsPage.getSettingHeader().toLowerCase(), GERMAN_TEXT, MSG_LANGUAGE_CHANGED.getValue());
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "4768")
    @Test(description = "Verify changing Password for a user (correct input)", groups = "regression-test")
    @TmsLink("4768")
    public void changeUserPassTest() {
        orgViewPage.clickOU(BASE_OU);
        String updatedPass = getRandomString(7, ENGLISH_ALPHABET, true).toLowerCase();
        Map<String, String> testUser = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo());
        String testUserName = testUser.get(NAME.getValue());
        String userPassBefore = testUser.get(PASSWORD.getValue());
        orgViewPage.searchByName(testUserName);
        orgViewPage.clickEntity(testUserName);
        orgViewPage.inputNewPass(updatedPass);
        orgViewPage.clickDetailsSaveButton();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputUsername(testUserName);
        collaborator.getLoginPage().inputPass(userPassBefore);
        collaborator.getLoginPage().clickLogInButton();
        assertTrue(collaborator.isLoginErrorMessagePresent(), MSG_LOGIN_FAILED.getValue());
        collaborator.getLoginPage().clickErrorOkButton();
        collaborator.getLoginPage().inputUsername(testUserName);
        collaborator.getLoginPage().inputPass(updatedPass);
        collaborator.getLoginPage().clickLogInButton();
        assertTrue(collaborator.isLogoutButtonPresent(), MSG_LOGIN_SUCCESSFUL.getValue());
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "23605")
    @Test(description = "Verify the OU manager of user in the Details pane", groups = "regression-test")
    @TmsLink("23605")
    public void verifyOuManagerTest() {
        orgViewPage.clickOU(BASE_OU);
        Map<String, String> testUser = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo());
        Map<String, String> testUserTwo = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo());
        String testUserName = testUser.get(NAME.getValue());
        String testUserTwoName = testUserTwo.get(NAME.getValue());
        orgViewPage.clickResponsibleOuButton();
        orgViewPage.searchOuManager(testUserName);
        orgViewPage.clickOuRespSearchButton();
        orgViewPage.selectUserAsOuManager(testUserName);
        orgViewPage.clickOuSaveButton();
        orgViewPage.searchByName(testUserTwoName);
        orgViewPage.clickEntity(testUserTwoName);
        assertTrue(orgViewPage.getOuManagerName().contains(testUserName));
    }
}
