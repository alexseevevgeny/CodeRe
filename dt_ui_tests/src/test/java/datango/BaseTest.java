package datango;

import datango.api.requests.CourseAssignmentReq;
import datango.api.requests.Permission;
import datango.api.requests.User;
import datango.api.requests.WaReq;
import datango.api.utils.GetterCollector;
import datango.ui.data.TestDataProvider;
import datango.ui.driver.BrowserManager;
import datango.ui.driver.DriverFactory;
import datango.ui.enums.Browser;
import datango.ui.helpers.Asserts;
import datango.ui.pages.collaborator.*;
import datango.ui.pages.collaborator.administration.*;
import datango.utils.FilesUtil;
import datango.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.text.MessageFormat;

import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.enums.AssertMessages.MSG_ENTITY_EDITED;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Slf4j
@Listeners(TestListener.class)
public abstract class BaseTest {
    protected static final String BASE_OU = get().defOuName();
    protected static final String ROOT = get().defRootOuName();
    protected String learnerRole = get().defGroupName();
    protected String learner = get().defLogin();
    protected TestDataProvider testDataProvider;
    WebDriver driver;
    protected Collaborator collaborator;
    protected Asserts asserts;
    protected BrowserManager browserManager;
    protected MainMenu mainMenu;
    protected CourseAssignmentsPage courseAssignmentsPage;
    protected WorkflowProcessPage workflowPage;
    protected CoursesPage coursesPage;
    protected CourseApprovalPage courseApprovalPage;
    protected LearningReporterPage lReporterPage;
    protected WorkareasTagsPage workareaAdmPage;
    protected SettingsPage settingsPage;
    protected WorkareaPage workareaPage;
    protected UserPage orgViewPage;
    protected ServerSettingsPage serverSettingsPage;
    protected TasksPage tasksPage;
    protected PermissionsPage permissionsPage;
    protected DialogBox dialogBox;
    protected RegistrationCodes registrationCodes;
    protected FilesUtil filesUtil;

    public BaseTest() {
        baseInit();
        initPages();
        testDataProvider = new TestDataProvider();
        filesUtil = new FilesUtil();
    }

    @BeforeSuite(alwaysRun = true)
    public void setUpSuite(ITestContext ctx) {
        log.info("EXECUTING SUITE: {}", ctx.getSuite().getName());
        if (Boolean.valueOf(get().isCreateTestData())) {
            createTestData();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite(ITestContext ctx) {
        driver.quit();
        driver = null;
        log.info("FINISHING SUITE: " + ctx.getSuite().getName());
        log.info("DELETING TEST DATA");
        CourseAssignmentReq req = new CourseAssignmentReq();
        GetterCollector get = new GetterCollector();
        WaReq waReq = new WaReq();
        User userReq = new User();
        Permission perReq = new Permission();
        get.getAssignmentId(req.getCourseAssignmentList())
            .forEach(req::deleteAssignment);
        String userId = get.getUserId(userReq.getSelfInfo()).getUserId();
        get.getWaIds(waReq.getWaList()).forEach(waId -> log.info(MessageFormat.format("Add permissions: {0}", waId) , perReq.addDeletePermForUser(userId, waId).prettyPrint()));
        get.getWaIds(waReq.getWaList()).forEach(waId -> {
            log.info(MessageFormat.format("Delete test entity: {0}", waId));
            waReq.deleteWa(waId);
        });
        log.info("TEST DATA IS DELETED");
    }

    private void baseInit() {
        driver = DriverFactory.initDriver(Browser.CHROME);
        browserManager = new BrowserManager(driver);
        collaborator = new Collaborator(driver);
        asserts = new Asserts();
    }

    private void initPages() {
        mainMenu = collaborator.getMainMenu();
        dialogBox = new DialogBox(driver);
        workflowPage = new WorkflowProcessPage(driver);
        courseAssignmentsPage = new CourseAssignmentsPage(driver);
        coursesPage = new CoursesPage(driver);
        courseApprovalPage = new CourseApprovalPage(driver);
        lReporterPage = new LearningReporterPage(driver);
        workareaAdmPage = new WorkareasTagsPage(driver);
        workareaPage = new WorkareaPage(driver);
        tasksPage = new TasksPage(driver);
        orgViewPage = new UserPage(driver);
        registrationCodes = new RegistrationCodes(driver);
        settingsPage = new SettingsPage(driver);
        permissionsPage = new PermissionsPage(driver);
        serverSettingsPage = new ServerSettingsPage(driver);
    }

    protected String createName() {
        return RandomUtils.getRandomString(7, ENGLISH_ALPHABET, true);
    }

    protected String createId() {
        return RandomUtils.getRandomString(6, NUMERIC, false);
    }

    protected void activateOrgEntityTest(String name) {
        orgViewPage.activateDeactivateEntity(name);
        orgViewPage.searchByName(name);
        orgViewPage.activateDeactivateEntity(name);
        orgViewPage.searchByName(name);
        assertTrue(orgViewPage.isEntityAcivated(name)
                && !dialogBox.isOverlay(), MSG_ENTITY_EDITED.getValue());
    }

    protected void activateOrgEntityDropdownTest(String name) {
        orgViewPage.activateDeactivateEntity(name);
        orgViewPage.searchByName(name);
        orgViewPage.checkEntity(name);
        orgViewPage.clickMoreDropdown();
        orgViewPage.clickMoreActivate();
        orgViewPage.searchByName(name);
        assertTrue(orgViewPage.isEntityAcivated(name)
                && !dialogBox.isOverlay(), MSG_ENTITY_EDITED.getValue());
    }

    protected void deActivateOrgEntityTest(String name) {
        orgViewPage.activateDeactivateEntity(name);
        orgViewPage.searchByName(name);
        assertTrue(!orgViewPage.isEntityAcivated(name)
                && !dialogBox.isOverlay(), MSG_ENTITY_EDITED.getValue());
    }

    protected void deActivateOrgEntityDropdownTest(String name) {
        orgViewPage.checkEntity(name);
        orgViewPage.clickMoreDropdown();
        orgViewPage.clickMoreDeactivate();
        orgViewPage.searchByName(name);
        assertTrue(!orgViewPage.isEntityAcivated(name)
                && !dialogBox.isOverlay(), MSG_ENTITY_EDITED.getValue());
    }

    private void createTestData() {
        testDataProvider.createOU();
        testDataProvider.createUsers();
        testDataProvider.createRole();
        testDataProvider.addPermissionToUser();
        testDataProvider.createResponsibleUserForOU();
    }
}
