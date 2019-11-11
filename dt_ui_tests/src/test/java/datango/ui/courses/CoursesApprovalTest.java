package datango.ui.courses;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static datango.ui.enums.AssertMessages.MSG_COURSE_PRESENT;
import static datango.ui.pages.collaborator.pageElements.EntityTree.USER;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Courses approval] automation test cases")
public class CoursesApprovalTest extends BaseTest {
    private String assignment;
    private String waName = createName();

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

    @TestCaseID(id = "4729")
    @TCStep(step = 1)
    @Test(description = "Verify 'Mandatory Course approval' availability ", groups = "regression-test")
    @TmsLink("4729")
    public void availabilityMandatoryCourseApprovalTest() {
        collaborator.gotoCourseAssignments();
        assignment = courseAssignmentsPage.addMandatoryAssignmentToApproval(learner, USER, waName, false);
        collaborator.gotoCoursesPage();
        coursesPage.clickMandatoryCoursesTab();
        coursesPage.clickApplyMandatoryCourse(assignment);
        collaborator.gotoCourseApprovalPage();
        courseApprovalPage.clickMandatoryTab();
        assertTrue(courseApprovalPage.isMandatoryCoursePresent(assignment), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4729")
    @TCStep(step = 2)
    @Test(description = "Verify 'Optional Course approval' availability ", groups = "regression-test")
    @TmsLink("4729")
    public void availabilityOptionalCourseApprovalTest() {
        collaborator.gotoCourseAssignments();
        assignment = courseAssignmentsPage.addAssignmentToApproval(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.clickApplyOptionalCourse(assignment);
        collaborator.gotoCourseApprovalPage();
        courseApprovalPage.clickOptionalTab();
        assertTrue(courseApprovalPage.isOptionalCoursePresent(assignment), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4730")
    @TCStep(step = 1)
    @Test(description = "Verify approving a course", groups = "regression-test")
    @TmsLink("4730")
    public void approvingMandatoryCourseTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        collaborator.gotoCourseAssignments();
        assignment = courseAssignmentsPage.addMandatoryAssignmentToApproval(learner, USER, waName,true);
        collaborator.gotoCoursesPage();
        coursesPage.clickMandatoryCoursesTab();
        coursesPage.clickApplyMandatoryCourse(assignment);
        collaborator.gotoCourseApprovalPage();
        courseApprovalPage.clickMandatoryTab();
        courseApprovalPage.clickApproveMandatory(assignment);
        assertTrue(courseApprovalPage.isMandatoryCoursePresent(assignment), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4731")
    @TCStep(step = 1)
    @Test(description = "Verify rejecting a course", groups = "regression-test")
    @TmsLink("4731")
    public void rejectingMandatoryCourseTest() {
        collaborator.gotoCourseAssignments();
        assignment = courseAssignmentsPage.addMandatoryAssignmentToApproval(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickMandatoryCoursesTab();
        coursesPage.clickApplyMandatoryCourse(assignment);
        collaborator.gotoCourseApprovalPage();
        courseApprovalPage.clickMandatoryTab();
        courseApprovalPage.clickRejectMandatory(assignment);
        assertTrue(courseApprovalPage.isMandatoryCoursePresent(assignment), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4730")
    @TCStep(step = 2)
    @Test(description = "Verify approving a optional course", groups = "regression-test")
    @TmsLink("4730")
    public void approvingOptionalCourseTest() {
        collaborator.gotoCourseAssignments();
        assignment = courseAssignmentsPage.addAssignmentToApproval(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.clickApplyOptionalCourse(assignment);
        collaborator.gotoCourseApprovalPage();
        courseApprovalPage.clickOptionalTab();
        courseApprovalPage.clickApproveOptinal(assignment);
        assertTrue(courseApprovalPage.isOptionalCoursePresent(assignment), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4731")
    @TCStep(step = 2)
    @Test(description = "Verify rejecting a optional course", groups = "regression-test")
    @TmsLink("4731")
    public void rejectingOptionalCourseTest() {
        collaborator.gotoCourseAssignments();
        assignment = courseAssignmentsPage.addAssignmentToApproval(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.clickApplyOptionalCourse(assignment);
        collaborator.gotoCourseApprovalPage();
        courseApprovalPage.clickOptionalTab();
        courseApprovalPage.clickRejectOptional(assignment);
        assertTrue(courseApprovalPage.isOptionalCoursePresent(assignment), MSG_COURSE_PRESENT.getValue());
    }
}
