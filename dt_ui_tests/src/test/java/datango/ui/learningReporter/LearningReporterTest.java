package datango.ui.learningReporter;

/**
 * @author Sergey Kuzhel
 */

import datango.BaseTest;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static datango.config.AppConfigProvider.get;
import static datango.ui.enums.AssertMessages.MSG_REPORT_GENERATED;
import static datango.utils.DateUtils.getCurentDateInLastMonth;
import static datango.utils.DateUtils.getCurrentDate;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Learning reporter] automation test cases")
public class LearningReporterTest extends BaseTest {
    private String userForReport = get().defLogin();
    private String waName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoWorkareaAdministration();
        waName = workareaAdmPage.addNewWorkarea(createName(), createId());
        mainMenu.clickWorkarea(waName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickPublishAll();
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
    }

    @TestCaseID(id = "4627")
    @Test(description = "Verify 'Lesson Reports' ", groups = "regression-test")
    @TmsLink("4627")
    public void verifyLessonReportsTest() {
        collaborator.gotoLessonReports();
        lReporterPage.checkIsTimePeriod();
        lReporterPage.setTimePeriod(getCurentDateInLastMonth().toString(), getCurrentDate().toString());
        lReporterPage.clickLessonReport();
        lReporterPage.selectWorkarea(waName);
        lReporterPage.clickSelect();
        assertTrue(collaborator.getHomePage().isPageLoaded(), MSG_REPORT_GENERATED.getValue());
    }

    @TestCaseID(id = "4625")
    @Test(description = "Verify 'User Reports' ", groups = "regression-test")
    @TmsLink("4625")
    public void verifyUserReportsTest() {
        collaborator.gotoUserReports();
        lReporterPage.checkIsTimePeriod();
        lReporterPage.setTimePeriod(getCurentDateInLastMonth().toString(), getCurrentDate().toString());
        lReporterPage.clickUserReport();
        lReporterPage.selectUser(userForReport);
        lReporterPage.clickSelect();
        assertTrue(collaborator.getHomePage().isPageLoaded(), MSG_REPORT_GENERATED.getValue());
    }

    @Test(description = "Verify 'Test Reports' ", groups = "regression-test")
    @TmsLink("")
    public void verifyTestReportsTest() {
        collaborator.gotoTestReports();
        lReporterPage.checkIsTimePeriod();
        lReporterPage.setTimePeriod(getCurentDateInLastMonth().toString(), getCurrentDate().toString());
        lReporterPage.clickTestReport();
        lReporterPage.selectWorkarea(waName);
        lReporterPage.clickSelect();
        assertTrue(collaborator.getHomePage().isPageLoaded(), MSG_REPORT_GENERATED.getValue());
    }

}
