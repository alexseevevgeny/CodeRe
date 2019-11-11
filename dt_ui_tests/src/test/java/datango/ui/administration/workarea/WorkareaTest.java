package datango.ui.administration.workarea;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.testrail.TestCaseIds;
import datango.ui.data.InputValuesProvider;
import datango.utils.WaitUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.HTML_CODE;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.GROUP;
import static datango.utils.AssertResultMsg.assertFalse;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workareas/Tags] automation test cases")
public class WorkareaTest extends BaseTest {
    private static final String DEF_WA = "main";
    private static final String SERVICE_NAME = "adtadmin (adtadmin adtadmin)";
    private String assignmentName;
    private String waName;
    private String waId;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoWorkareaAdministration();
        assignmentName = getRandomString(8, ENGLISH_ALPHABET, true);
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
    }

    @TestCaseID(id = "4903")
    @Test(description = "Verify creating of a new Workarea (correct input)", groups = "smoke-test")
    @TmsLink("4903")
    public void addWorkareaCorrectTest() {
        waName = getRandomString(7, ENGLISH_ALPHABET, true);
        waId = getRandomString(4, NUMERIC, false);
        workareaAdmPage.addNewWorkarea(waName, waId);
        assertTrue(workareaAdmPage.getWorkareaNames().contains(waName), MSG_WA_CREATED.getValue());

    }

    @TestCaseID(id = "4905")
    @Test(description = "Verify creating of a new Workarea (existing id)", groups = "regression-test")
    @TmsLink("4905")
    public void addWorkareaExistingNameIdTest() {
        String waNameLocal = createName();
        String waIdLocal = createId();
        for (int i = 0; i < 2; i++) {
            workareaAdmPage.addNewWorkarea(waNameLocal, waIdLocal);
        }
        assertTrue(dialogBox.isErrorMessage(), MSG_WA_NOT_CREATED.getValue());
        dialogBox.clickApplyButton();
    }

    @TestCaseID(id = "4904")
    @Test(description = "Verify creating of a new Workarea (incorrect input)",
            dataProviderClass = InputValuesProvider.class, dataProvider = "incorrectWaNames", groups = "regression-test")
    @TmsLink("4904")
    public void addWorkareaIncorrectTest(String waName) {
        String waIdLocal = getRandomString(4, NUMERIC, false);
        workareaAdmPage.addNewWorkarea(waName, waIdLocal);
        assertTrue(!workareaAdmPage.getWorkareaNames().contains(waName), MSG_WA_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4906")
    @Test(description = "Verify creating of a new Workarea (blank mandatory input)", groups = "regression-test")
    @TmsLink("4906")
    public void addWorkareaBlankMandatoryTest() {
        String waIdLocal = getRandomString(4, NUMERIC, false);
        String waEmpty = workareaAdmPage.addNewWorkarea(" ", waIdLocal);
        dialogBox.clickApplyButton();
        assertTrue(!workareaAdmPage.getWorkareaNames().contains(waEmpty), MSG_WA_NOT_CREATED.getValue());
    }

    @TestCaseIds({"4912", "4910"})
    @Test(description = "Verify activation/deactivation of a Workarea)", groups = "regression-test")
    @TmsLink("4912")
    @TmsLink("4910")
    public void deactivateWorkAreaTest() {
        workareaAdmPage.clickActivateWorkarea();
        workareaAdmPage.clickActivateWorkarea();
    }

    @TestCaseID(id = "4913")
    @Test(description = "Verify deleting of a Workarea )", groups = "regression-test")
    @TmsLink("4913")
    public void deleteWorkAreaTest() {
        String waNameLocal = createName();
        workareaAdmPage.addNewWorkarea(waNameLocal, createId());
        workareaAdmPage.clickOnWorkarea(waNameLocal);
        workareaAdmPage.selectDeletePermission();
        workareaAdmPage.clickSaveButton();
        workareaAdmPage.clickDeleteWorkareaIcon(waNameLocal);
        dialogBox.clickApplyButton();
        assertTrue(!workareaAdmPage.getWorkareaNames().contains(waNameLocal), MSG_WA_DELETED.getValue());
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 8)
    @Test(description = "Verify adding html code to input fields (Workarea)", groups = "smoke-test")
    @TmsLink("11215")
    public void addWorkareaWithHtmlCodeNameTest() {
        String waIdLocal = getRandomString(5, NUMERIC, false);
        workareaAdmPage.addNewWorkarea(HTML_CODE, waIdLocal);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4911")
    @Test(description = "Verify editing Workarea without permission", groups = "regression-test")
    @TmsLink("4911")
    public void editWorkareaWithoutPermissionTest() {
        String waNameLocal = createName();
        workareaAdmPage.addNewWorkarea(waNameLocal, createId());
        workareaAdmPage.clickOnWorkarea(waNameLocal);
        workareaAdmPage.clickViewPermission(get().defServiceOuName());
        workareaAdmPage.clickSaveButton();
        WaitUtil.setWait(1);
        assertFalse(workareaAdmPage.isWorkareaActive(waNameLocal));
    }

    @TestCaseID(id = "9173")
    @Test(description = "Verify deleting of the initial (default) Workarea", groups = "regression-test")
    @TmsLink("9173")
    public void deleteInitialWATest() {
        workareaAdmPage.clickOnWorkarea(DEF_WA);
        workareaAdmPage.addWorkareaPermissionsForUserIfSelected("adtadmin");
        workareaAdmPage.selectExistIdentity("adtadmin");
        workareaAdmPage.addDeletePermission("adtadmin");
        workareaAdmPage.clickSaveButton();
        workareaAdmPage.clickDeleteWorkareaIcon(DEF_WA);
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage());
        dialogBox.clickOkButton();
    }

    @TestCaseID(id = "4915")
    @Test(description = "Verify creating new Workarea with id equal to recently deleted Workarea with several permissions", groups = "regression-test")
    @TmsLink("4915")
    public void createNewWaWithRemovedWaiIdTest() {
        String waNameLocal = createName();
        String waIdLocal = createId();
        String waNameTwo = createName();
        workareaAdmPage.addNewWorkarea(waNameLocal, waIdLocal);
        workareaAdmPage.clickOnWorkarea(waNameLocal);
        workareaAdmPage.addWorkareaPermissionsForUser(SERVICE_NAME, false);
        workareaAdmPage.addDeletePermission(SERVICE_NAME);
        int identitiesCount = workareaAdmPage.getIdentityNames().size();
        workareaAdmPage.selectDeletePermission();
        workareaAdmPage.clickSaveButton();
        workareaAdmPage.clickDeleteWorkareaIcon(waNameLocal);
        dialogBox.clickApplyButton();
        workareaAdmPage.addNewWorkarea(waNameTwo, waIdLocal);
        workareaAdmPage.clickOnWorkarea(waNameTwo);
        assertNotEquals(workareaAdmPage.getIdentityNames().size(), identitiesCount);
    }

    @TestCaseID(id = "4914")
    @Test(description = "Verify deleting of a Workarea while there are related courses", groups = "regression-test")
    @TmsLink("4914")
    public void deleteWaWithRelatedCourses() {
        String waNameLocal = createName();
        String waIdLocal = createId();
        workareaAdmPage.addNewWorkarea(waNameLocal, waIdLocal);
        workareaAdmPage.clickOnWorkarea(waNameLocal);
        workareaAdmPage.addWorkareaPermissionsForUser(SERVICE_NAME, true);
        workareaAdmPage.addDeletePermission(SERVICE_NAME);
        workareaAdmPage.clickSaveButton();
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waNameLocal);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.clickDeleteWorkareaIcon(waNameLocal);
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }


}
