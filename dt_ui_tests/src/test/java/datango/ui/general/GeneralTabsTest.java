package datango.ui.general;

import datango.BaseTest;
import datango.config.AppConfigProvider;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static datango.ui.enums.FormFields.NAME;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("General availability test for Collaborator(Server) 3.2")
@Story("[Pages availability] automation test cases")
public class GeneralTabsTest extends BaseTest {
    private static final String ADMINISTRATION_TEXT = "Administration";
    private static final String WORKAREA_URL = AppConfigProvider.get().baseUrlApi() + "workarea.html";
    private static final String ADMIN_URL = AppConfigProvider.get().baseUrlApi() + "admin.html";
    private static final String TASK_URL = AppConfigProvider.get().baseUrlApi() + "task.html";
    private static final String REPORT_URL = AppConfigProvider.get().baseUrlApi() + "report.html";
    private static final String LEARNER_URL = AppConfigProvider.get().baseUrlApi() + "learner.html";
    private String userName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        userName = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());

    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        collaborator.performLogOut();
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 1)
    @Test(description = "Verify administration - Workarea tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void administrationWokAreaTabTest() {
        collaborator.gotoWorkareaAdministration();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains(ADMINISTRATION_TEXT));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 2)
    @Test(description = "Verify administration - Org view tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void administrationOrgViewTabTest() {
        collaborator.gotoUserAdministration();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains(ADMINISTRATION_TEXT));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 3)
    @Test(description = "Verify administration - Permissions tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void administrationPermissionsTabTest() {
        collaborator.gotoPermissionsAdministration();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains(ADMINISTRATION_TEXT));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 4)
    @Test(description = "Verify administration - Reg-codes tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void administrationRegCodesTabTest() {
        collaborator.gotoRegCodesAdministration();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains(ADMINISTRATION_TEXT));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 5)
    @Test(description = "Verify administration - Workflow properties tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void administrationWorkflowTabTest() {
        collaborator.gotoStatusAdministration();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains(ADMINISTRATION_TEXT));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 6)
    @Test(description = "Verify Courses assignments tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void coursesAssignmentTabTest() {
        collaborator.gotoCourseAssignments();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains(ADMINISTRATION_TEXT));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 7)
    @Test(description = "Verify Courses tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void coursesTabTest() {
        collaborator.gotoCoursesPage();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains("Learner"));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 8)
    @Test(description = "Verify Course approval tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void courseApprovalTabTest() {
        collaborator.gotoCourseApprovalPage();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains("Course Approval"));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 9)
    @Test(description = "Verify Workarea tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void workareaTabTest() {
        collaborator.gotoWorkareaPage();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains("Workarea"));
    }

    @TestCaseID(id = "4772")
    @Test(description = "Verify Settings tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4772")
    public void settingsTabTest() {
        collaborator.gotoSettingsPage();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains("Settings"));
    }

    @TestCaseID(id = "4619")
    @TCStep(step = 10)
    @Test(description = "Verify Learning reporter tab (page is loaded)", groups = "smoke-test")
    @TmsLink("4619")
    public void learningReporterTabTest() {
        collaborator.gotoLearningReporterPage();
        assertTrue(collaborator.getHomePage().isPageLoaded());
        assertTrue(browserManager.getTitle().contains("Reports"));
    }

    @TestCaseID(id = "4620")
    @Test(description = "Verify user can't access views he's not allowed to by typing its URL and warning messages are correct", groups = "regression-test")
    @TmsLink("4620")
    public void noPermissionToPagesTest() {
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userName);
        collaborator.getLoginPage().inputLoginPassword(userName);
        collaborator.getLoginPage().clickLoginBtn();
        browserManager.navigateTo(WORKAREA_URL);
        browserManager.reloadPage();
        browserManager.navigateTo(WORKAREA_URL);
        assertTrue(workareaPage.getStorageContent().permissionMsgVisibility());
        browserManager.navigateTo(ADMIN_URL);
        assertTrue(courseAssignmentsPage.permissionMsgVisibility());
        browserManager.navigateTo(TASK_URL);
        assertTrue(tasksPage.permissionMsgVisibility());
        browserManager.navigateTo(REPORT_URL);
        assertTrue(collaborator.getLearningReporterPage().permissionMsgVisibility());
        browserManager.navigateTo(LEARNER_URL);
        assertTrue(coursesPage.permissionMsgVisibility());
    }
}
