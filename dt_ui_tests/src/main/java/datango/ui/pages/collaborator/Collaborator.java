package datango.ui.pages.collaborator;

import datango.ui.data.CredentialsDataHelper;
import datango.ui.helpers.WebElementActions;
import datango.ui.pages.BasePage;
import datango.utils.WaitUtil;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Main Collaborator application flow
 *
 * @author Sergey Kuzhel
 */
@Getter
public class Collaborator {

    private MainMenu mainMenu;
    private LoginPage loginPage;
    private HomePage homePage;
    private LearningReporterPage learningReporterPage;


    public Collaborator(WebDriver driver) {
        this.mainMenu = new MainMenu(driver);
        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
        this.learningReporterPage = new LearningReporterPage(driver);
    }

    public Collaborator openLoginPage() {
        loginPage.open();
        return this;
    }

    public Collaborator performLogin(Map<String, String> creds) {
        loginPage.logIn(creds);
        return this;
    }

    public Collaborator performDefaultLogin() {
        loginPage.logIn(CredentialsDataHelper.getDefaultCredentials());
        return this;
    }

    public Collaborator performInactiveLogin() {
        loginPage.logIn(CredentialsDataHelper.getInactiveCredentials());
        return this;
    }

    public Collaborator performGuestLogin() {
        loginPage.guestLogIn();
        return this;
    }

    public Collaborator performLogOut() {
        homePage.clickLogOutButton();
        return this;
    }

    public Collaborator performLogOutWithMove() {
        homePage.clickLogOutButtonWithMove();
        return this;
    }

    public Collaborator gotoWorkareaAdministration() {
        mainMenu.clickAdministration();
        mainMenu.clickWorkareasAdm();
        return this;
    }

    public Collaborator gotoStatusAdministration() {
        mainMenu.clickAdministration();
        mainMenu.clickStatus();
        return this;
    }

    public Collaborator gotoPermissionsAdministration() {
        mainMenu.clickAdministration();
        mainMenu.clickPermissions();
        return this;
    }

    public Collaborator gotoUserAdministration() {
        mainMenu.clickAdministration();
        mainMenu.clickUser();
        return this;
    }

    public Collaborator gotoTasks() {
        mainMenu.clickTasks();
        return this;
    }

    public void gotoCourseAssignments() {
        mainMenu.clickAdministration();
        mainMenu.clickCourseAssignments();
    }

    public void gotoCoursesPage() {
        mainMenu.clickCourses();
    }

    public void gotoCourseApprovalPage() {
        mainMenu.clickCourseApproval();
    }

    public void gotoRegCodesAdministration() {
        mainMenu.clickAdministration();
        mainMenu.clickRegCodes();
    }

    public void gotoWorkareaPage() {
        mainMenu.clickWorkareas();
    }

    public void gotoSettingsPage() {
        mainMenu.clickSettings();
    }

    public void gotoServerSettingsAdministration() {
        mainMenu.clickAdministration();
        mainMenu.clickServerSettings();
    }

    public void gotoLearningReporterPage() {
        mainMenu.clickLearningReporter();
    }


    public void gotoLessonReports() {
        mainMenu.clickLearningReporter();
        learningReporterPage.clickLessonReport();
    }

    public void gotoUserReports() {
        mainMenu.clickLearningReporter();
        learningReporterPage.clickUserReport();
    }

    public void gotoTestReports() {
        mainMenu.clickLearningReporter();
        learningReporterPage.clickTestReport();
    }

    public void gotoRegistrationCodes(){
        mainMenu.clickAdministration();
        mainMenu.clickRegCodes();
    }

    public boolean isLogoutButtonPresent() {
        WaitUtil.setWait(1);
        return homePage.isLogoutButtonPresent();
    }

    public boolean isLoginButtonPresent() {
        return loginPage.isLoginButtonPresent();
    }

    public boolean isLoginErrorMessagePresent() {
        return loginPage.isErrorMessagePresent();
    }

}
