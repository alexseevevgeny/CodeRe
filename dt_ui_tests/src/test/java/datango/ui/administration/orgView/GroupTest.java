package datango.ui.administration.orgView;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.utils.RandomUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.SPECIAL_CHARS;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.data.TestDataProvider.SERVICE_OU;
import static datango.ui.data.TestDataProvider.SERVICE_ROLE;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.enums.FormFields.NAME;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.*;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Organizational view/Group] automation test cases")
public class GroupTest extends BaseTest {
    private static final String BASE_OU = get().defOuName();
    private static final String GROUP = " group";
    private String groupName;
    private String regCode;
    private String groupNameEdit;
    private String groupForEdit;
    private String ouName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        groupForEdit = orgViewPage
                .addRole(createName(), createId());
        browserManager.reloadPage();
        ouName = createName();
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        groupName = createName() + GROUP;
        groupNameEdit = groupName + TEST_EDIT;
        regCode = createId();
        orgViewPage.clickOU(BASE_OU);
    }

    @TestCaseID(id = "4682")
    @Test(description = "Verify the creation of new permission group (correct input - all fields)", groups = "smoke-test")
    @TmsLink("4682")
    public void roleAddCorrectTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        List<String> groupNames = orgViewPage.getEntityNames();
        assertTrue(groupNames.contains(groupName), MSG_ENTITY_CREATED.getValue());
    }

    @TestCaseID(id = "5378")
    @Test(description = "Verify the creation of new permission role (correct input - mandatory fields)", groups = "smoke-test")
    @TmsLink("5378")
    public void roleAddCorrectMandatoryTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        List<String> groupNames = orgViewPage.getEntityNames();
        assertTrue(groupNames.contains(groupName), MSG_ENTITY_CREATED.getValue());
    }

    @TestCaseID(id = "4683")
    @Test(description = "Verify the creation of new permission group (incorrect input)", groups = "smoke-test")
    @TmsLink("4683")
    public void roleAddIncorrectTest() {
        groupName = TEST_SPACE;
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        assertTrue(!orgViewPage.getEntityNames().contains(groupName), MSG_ENTITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4684")
    @Test(description = "Verify the creation of new permission Group (blank mandatory field)", groups = "regression-test")
    @TmsLink("4684")
    public void roleAddBlankMandatoryTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(TEST_EMPTY);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4685")
    @Test(description = "Verify the creation of new permission Group (existing name)", groups = "regression-test")
    @TmsLink("4685")
    public void roleAddExistingNameTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.clickCreateButton();
        browserManager.reloadPage();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        int b = orgViewPage.getEntityNames().size();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        browserManager.reloadPage();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        assertTrue(b == orgViewPage.getEntityNames().size(), MSG_ENTITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4686")
    @Test(description = "Verify the creation of new permission Group (existing Registration-Code)", groups = "regression-test")
    @TmsLink("4686")
    public void roleAddExistingRegCodeTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        groupName = getRandomString(7, ENGLISH_ALPHABET, true);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        assertTrue(!orgViewPage.getEntityNames().contains(groupName), MSG_ENTITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4687")
    @Test(description = "Verify editing a Group (correct input)", groups = "regression-test")
    @TmsLink("4687")
    public void roleEditCorrectTest() {
        orgViewPage.addRole(groupName, regCode);
        String roleEdit = createName();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.inputDeatilsGroupName(roleEdit);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.clickDetailsCloseIcon();
        orgViewPage.searchByName(roleEdit);
        assertTrue(orgViewPage.getEntityNames().contains(roleEdit), MSG_ENTITY_EDITED.getValue());
    }

    @TestCaseID(id = "4688")
    @Test(description = "Verify editing a Group (incorrect input)", groups = "regression-test")
    @TmsLink("4688")
    public void roleEditIncorrectTest() {
        groupNameEdit = TEST_EMPTY;
        orgViewPage.searchByName(groupForEdit);
        orgViewPage.clickEntity(groupForEdit);
        orgViewPage.inputDeatilsGroupName(TEST_EMPTY);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4689")
    @Test(description = "Verify editing a Group (blank mandatory field)", groups = "regression-test")
    @TmsLink("4689")
    public void roleEditBlankMandatoryTest() {
        groupNameEdit = TEST_SPACE;
        orgViewPage.searchByName(groupForEdit);
        orgViewPage.clickEntity(groupForEdit);
        orgViewPage.inputDeatilsGroupName(groupNameEdit);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4690")
    @Test(description = "Verify editing a Group (existing name)", groups = "regression-test")
    @TmsLink("4690")
    public void roleEditExistingNameTest() {
        groupNameEdit = orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        String existing = orgViewPage.getEntityNames().stream()
                .filter(e -> !e.contains(groupName))
                .findFirst().get();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupForEdit);
        orgViewPage.clickEntity(groupForEdit);
        orgViewPage.inputDeatilsGroupName(existing);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4691")
    @Test(description = "Verify editing a Group (existing Registration-Code)", groups = "regression-test")
    @TmsLink("4691")
    public void roleEditExistingRegCodeTest() {
        orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupForEdit);
        orgViewPage.clickEntity(groupForEdit);
        orgViewPage.inputDeatilsRegCode(regCode);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4692")
    @Test(description = "Verify adding / changing to incorrect Registration-Code for a Group", groups = "regression-test")
    @TmsLink("4692")
    public void roleAddEditIncorrectRegCodeTest() {
        regCode = RandomUtils.getRandomString(4, SPECIAL_CHARS, false);
        orgViewPage.addRole(groupName, regCode);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupForEdit);
        orgViewPage.clickEntity(groupForEdit);
        orgViewPage.inputDeatilsRegCode(regCode);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4696")
    @Test(description = "Verify activating Group (from the list)", groups = "regression-test")
    @TmsLink("4696")
    public void roleActivateTest() {
        orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        activateOrgEntityTest(groupName);
    }

    @TestCaseID(id = "4697")
    @Test(description = "Verify deactivating Group (from the list)", groups = "regression-test")
    @TmsLink("4697")
    public void roleDeactivateTest() {
        orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        deActivateOrgEntityTest(groupName);
    }

    @TestCaseID(id = "4698")
    @Test(description = "Verify activating group(s) (from the 'More' dropdown list)", groups = "regression-test")
    @TmsLink("4698")
    public void roleActivateDropdownTest() {
        orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        activateOrgEntityDropdownTest(groupName);
    }

    @TestCaseID(id = "4699")
    @Test(description = "Verify deactivating group(s) (from the 'More' dropdown list)", groups = "regression-test")
    @TmsLink("4699")
    public void roleDeactivateDropdownTest() {
        orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.searchByName(groupName);
        deActivateOrgEntityDropdownTest(groupName);
    }

    @TestCaseID(id = "4702")
    @Test(description = "Verify adding users/groups to the group", groups = "regression-test")
    @TmsLink("4702")
    public void addUserAndGroupsToGroupTest() {
        List<String> entitiesList = new LinkedList<>();
        String groupOne = createName() + GROUP;
        String groupTwo = createName() + GROUP;
        String regCodeOne = createId();
        String regCodeTwo = createId();
        List<String> targetGroup = Arrays.asList(groupOne, groupTwo);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupOne, regCodeOne);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupTwo, regCodeTwo);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupName, regCode);
        orgViewPage.clickOU(BASE_OU);
        String user = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        entitiesList.add(groupOne);
        entitiesList.add(groupTwo);
        entitiesList.add(user);
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.clickToggleButton();
        orgViewPage.clickAdvancedSwitchButton();
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickEntity(user);
        orgViewPage.clickEntity(groupOne);
        orgViewPage.clickEntity(groupTwo);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(orgViewPage.getGhildEntities().containsAll(targetGroup), MSG_CHILD_ENTITIES_ADDED.getValue());
        assertTrue(orgViewPage.getGhildEntities().stream().anyMatch(s -> s.contains(user)), MSG_CHILD_ENTITIES_ADDED.getValue());
    }

    @TestCaseID(id = "4703")
    @Test(description = "Verify removing users/groups from the target group", groups = "regression-test")
    @TmsLink("4703")
    public void removeUserAndGroupsFromGroupTest() {
        List<String> entitiesList = new LinkedList<>();
        String groupOne = createName() + GROUP;
        String groupTwo = createName() + GROUP;
        String regCodeOne = createId();
        String regCodeTwo = createId();
        orgViewPage.addRole(groupOne, regCodeOne);
        orgViewPage.addRole(groupTwo, regCodeTwo);
        orgViewPage.addRole(groupName, regCode);
        String user = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        entitiesList.add(groupOne);
        entitiesList.add(groupTwo);
        entitiesList.add(user);
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.clickToggleButton();
        orgViewPage.clickAdvancedSwitchButton();
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickEntity(user);
        orgViewPage.clickEntity(groupOne);
        orgViewPage.clickEntity(groupTwo);
        orgViewPage.clickDetailsSaveButton();
        int size = orgViewPage.getGhildEntities().size();
        orgViewPage.clickToggleButton();
        orgViewPage.removeChildEntity(user);
        orgViewPage.clickDetailsSaveButton();
        assertNotEquals(size, orgViewPage.getGhildEntities().size(), MSG_CHILD_ENTITY_REMOVED.getValue());
    }

    @TestCaseID(id = "4704")
    @Test(description = "Verify 'Move to OU' functionality for groups ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4704")
    public void moveGroupToOUTest() {
        List<String> entitiesList = new LinkedList<>();
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.clickOU(BASE_OU);
        String groupOne = createName() + GROUP;
        String groupTwo = createName() + GROUP;
        String regCodeOne = createId();
        String regCodeTwo = createId();
        orgViewPage.addRole(groupOne, regCodeOne);
        orgViewPage.addRole(groupTwo, regCodeTwo);
        entitiesList.add(groupOne);
        entitiesList.add(groupTwo);
        orgViewPage.clickAdvancedSwitchButton();
        orgViewPage.advancedSearchInput(orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickAndCheckEntity(groupOne);
        orgViewPage.clickAndCheckEntity(groupTwo);
        orgViewPage.clickMoveToOU();
        orgViewPage.clickDetailOU(ouName);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.advancedSearchInput(
            orgViewPage.getAdvancedSearchByOU(BASE_OU) +
                orgViewPage.getAdvancedSearchByName(entitiesList));
        assertFalse(orgViewPage.getEntityNames().containsAll(entitiesList));
        orgViewPage.advancedSearchInput(
            orgViewPage.getAdvancedSearchByOU(ouName) +
                orgViewPage.getAdvancedSearchByName(entitiesList));
        assertFalse(orgViewPage.getEntityNames().containsAll(entitiesList));
    }

    @TestCaseID(id = "4706")
    @Test(description = "Verify 'Add to groups' functionality for groups ('More' dropdown menu)", groups = "regression-test")
    @TmsLink("4706")
    public void addGroupsToGroupsTest() {
        String groupOne = createName() + GROUP;
        String groupTwo = createName() + GROUP;
        List<String> targetGroup = Arrays.asList(groupOne, groupTwo);
        String groupThree = createName() + GROUP;
        String regCodeOne = createId();
        String regCodeTwo = createId();
        String regCodeThree = createId();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupOne, regCodeOne);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupTwo, regCodeTwo);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupThree, regCodeThree);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(groupName, regCode);
        List<String> entitiesList = new LinkedList<>();
        entitiesList.add(groupOne);
        entitiesList.add(groupTwo);
        orgViewPage.clickAdvancedSwitchButton();
        orgViewPage.advancedSearchInput(
            orgViewPage.getAdvancedSearchByOU(BASE_OU) +
            orgViewPage.getAdvancedSearchByName(entitiesList));
        orgViewPage.clickSearchButton();
        orgViewPage.clickAndCheckEntity(groupOne);
        orgViewPage.clickAndCheckEntity(groupTwo);
        orgViewPage.clickAddToGroup();
        orgViewPage.clickDetailGroup(groupThree);
        orgViewPage.clickDetailGroup(groupName);
        orgViewPage.clickDetailsSaveButton();
        browserManager.reloadPage();
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        assertTrue(orgViewPage.getGhildEntities().containsAll(targetGroup), MSG_CHILD_ENTITIES_ADDED.getValue());
        browserManager.reloadPage();
        orgViewPage.searchByName(groupThree);
        orgViewPage.clickEntity(groupThree);
        assertTrue(orgViewPage.getGhildEntities().containsAll(targetGroup), MSG_CHILD_ENTITIES_ADDED.getValue());
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 2)
    @Test(description = "Verify adding html code to input fields (Organizational View groups)", groups = "smoke-test")
    @TmsLink("11215")
    public void createGroupWithHtmlCodeTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(HTML_CODE);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4693")
    @Test(description = "Verify deleting Registration-Code for a Group", groups = "smoke-test")
    @TmsLink("4693")
    public void verifyDeleteRegCodeForGroupTest() {
        String blankRegCode = "";
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.inputDeatilsRegCode(blankRegCode);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        assertTrue(orgViewPage.getDetailsRegCode().isEmpty());

    }

    @TestCaseID(id = "4700")
    @Test(description = "Verify deleting a group (in 'Details' pane)", groups = "smoke-test")
    @TmsLink("4700")
    public void verifyDeleteGroupInDetailsTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.clickDetailsDeleteIcon();
        dialogBox.clickApplyButton();
        orgViewPage.searchByName(groupName);
        assertFalse(orgViewPage.getEntityNames().contains(groupName));
    }

    //TODO: need to investigate issue with close driver
    /*@TestCaseID(id = "4705")
    @Test(description = "Verify export to XLSX", groups = "smoke-test")
    @TmsLink("4705")
    public void verifyExportXlsxTest() {
       orgViewPage.searchByName(SERVICE_ROLE);
       orgViewPage.clickEntity(SERVICE_ROLE);
       orgViewPage.clickXlsxExportIcon(SERVICE_OU + ".xlsx");
       assertTrue(orgViewPage.isAllEntitiesPresentInFile(SERVICE_OU + ".xlsx"));
    }*/

    @TestCaseID(id = "4701")
    @Test(description = "Verify deleting a group (in 'More' dropdown)", groups = "regression-test")
    @TmsLink("4701")
    public void deleteGroupInMoreDropdownTest() {
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.searchByName("group");
        String groupName = orgViewPage.getEntityNames().get(0);
        orgViewPage.checkEntity(groupName);
        orgViewPage.clickMoreDropdown();
        orgViewPage.clickMoreDeleteGroup();
        dialogBox.clickApplyButton();
        orgViewPage.searchByName(groupName);
        assertFalse(orgViewPage.getEntityNames().contains(groupName));
    }

    @TestCaseID(id = "4694")
    @Test(description = "Verify checking user / Group checkbox", groups = "regression-test")
    @TmsLink("4694")
    public void verifyActiveCheckbox() {
        orgViewPage.clickOU(BASE_OU);
        Map<String, String> testUser = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo());
        String testUserName = testUser.get(NAME.getValue());
        orgViewPage.searchByName(testUserName);
        orgViewPage.checkEntity(testUserName);
        orgViewPage.clickMoreDropdown();
        assertTrue(orgViewPage.checkIsMoreDeleteGroupIsClickable());
        assertTrue(orgViewPage.checkIsMoreDeativateIsClickable());
        assertTrue(orgViewPage.checkIsMoreActivateIsClickable());
        assertTrue(orgViewPage.checkIsMoreAddToGroupIsClickable());
        assertTrue(orgViewPage.checkIsMoreRemoveFromGroupIsClickable());
        assertTrue(orgViewPage.checkIsMoreMoveToIsClickable());
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.searchByName(groupName);
        orgViewPage.checkEntity(groupName);
        orgViewPage.clickMoreDropdown();
        assertTrue(orgViewPage.checkIsMoreDeleteGroupIsClickable());
        assertTrue(orgViewPage.checkIsMoreDeativateIsClickable());
        assertTrue(orgViewPage.checkIsMoreActivateIsClickable());
        assertTrue(orgViewPage.checkIsMoreAddToGroupIsClickable());
        assertTrue(orgViewPage.checkIsMoreRemoveFromGroupIsClickable());
        assertTrue(orgViewPage.checkIsMoreMoveToIsClickable());
    }
}

