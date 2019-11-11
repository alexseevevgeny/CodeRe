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

import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.data.InputValuesProvider.TEST_DESCRIPTION;
import static datango.ui.enums.AssertMessages.MSG_COURSE_PRESENT;
import static datango.ui.pages.collaborator.pageElements.EntityTree.GROUP;
import static datango.ui.pages.collaborator.pageElements.EntityTree.USER;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Courses] automation test cases")
public class CoursesTest extends BaseTest {
    private String learner = get().defLogin();
    private String waName = createName();
    private String assignmentName;
    String assignment;

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
        assignmentName = getRandomString(8, ENGLISH_ALPHABET, true);
    }

    @TestCaseID(id ="4708")
    @Test(description = "Verify availability of Mandatory courses ", groups = "regression-test")
    @TmsLink("4708")
    public void availabilityMandatoryCoursesTest() {
        collaborator.gotoCourseAssignments();
        String assignmentLocal = courseAssignmentsPage.addMandatoryAssignment(learner, USER, waName, false);
        collaborator.gotoCoursesPage();
        coursesPage.clickMandatoryCoursesTab();
        assertTrue(coursesPage.getMandatoryCourses().contains(assignmentLocal), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4709")
    @Test(description = "Verify availability of Optional courses ", groups = "regression-test")
    @TmsLink("4709")
    public void availabilityOptionalCoursesTest() {
        collaborator.gotoCourseAssignments();
        String assignmentLocal = courseAssignmentsPage.addAssignment(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentLocal), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4710")
    @Test(description = "Verify availability of Past courses ", groups = "regression-test")
    @TmsLink("4710")
    public void availabilityPastCoursesTest() {
        collaborator.gotoCourseAssignments();
        collaborator.gotoCoursesPage();
        coursesPage.clickPastCoursesTab();
        assertTrue(coursesPage.checkAvailabilityPastCourses(), MSG_COURSE_PRESENT.getValue());
    }

    @TestCaseID(id = "4713")
    @Test(description = "Verify applying to the course that needs approval", groups = "regression-test")
    @TmsLink("4713")
    public void applyCourseNeedsApproval() {
        collaborator.gotoCourseAssignments();
        String assignmentLocal =  courseAssignmentsPage.addAssignmentToApproval(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.clickApplyOptionalCourse(assignmentLocal);
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 1)
    @Test(description = "Verify searching/filtering functionality for Courses (by caption)", groups = "regression-test")
    @TmsLink("4714")
    public void searchByCaptionTest() {
        collaborator.gotoCourseAssignments();
        String userWithoutApprove = courseAssignmentsPage.addAssignment(learner, USER, waName, true);
        collaborator.gotoCourseAssignments();
        assignment =  courseAssignmentsPage.addAssignmentToApproval(learner, USER, waName, true);
        collaborator.gotoCourseAssignments();
        String userMandatory = courseAssignmentsPage.addMandatoryAssignment(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.inputCaption(userWithoutApprove);
        assertTrue(coursesPage.getOptionalCourses().contains(userWithoutApprove));
        coursesPage.inputCaption(assignment);
        assertTrue(coursesPage.getOptionalApprovalCourses().contains(assignment));
        coursesPage.clickMandatoryCoursesTab();
        coursesPage.inputCaption(userMandatory);
        assertTrue(coursesPage.getMandatoryCourses().contains(userMandatory));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 2)
    @Test(description = "Verify searching/filtering functionality for Courses (by description)", groups = "regression-test")
    @TmsLink("4714")
    public void searchByDescriptionTest() {
        String descr = assignmentName + TEST_DESCRIPTION;
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickDetailsDescription();
        courseAssignmentsPage.getDetails().inputCourseDescription(descr);
        courseAssignmentsPage.getDetails().clickOkModal();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.inputDescription(descr);
        assertTrue(coursesPage.getOptionalCourses().size() == 1);
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 3)
    @Test(description = "Verify searching/filtering functionality for Courses (by weight)", groups = "regression-test")
    @TmsLink("4714")
    public void searchByWeightTest() {
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        String weight = courseAssignmentsPage.getDetails().inputWeight();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.inputWeight(weight);
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 4)
    @Test(description = "Verify searching/filtering functionality for Courses (by color)", groups = "regression-test")
    @TmsLink("4714")
    public void searchByColorTest() {
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, USER, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();

        courseAssignmentsPage.searchByCaption(assignmentName);
        courseAssignmentsPage.clickAssignment(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().clickColorButton();
        courseAssignmentsPage.getDetails().selectColor();
        courseAssignmentsPage.getDetails().checkIsCertificate();
        String color = courseAssignmentsPage.getDetails().getColor().substring(1);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.clickColorFilter();
        coursesPage.selectColor(color);
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 5)
    @Test(description = "Verify searching/filtering functionality for Courses (by chargeable)", groups = "regression-test")
    @TmsLink("4714")
    public void searchByChargeableTest() {
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().checkIsChargeable();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.selectChargeableFilter();
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 6)
    @Test(description = "Verify searching/filtering functionality for Courses (Switch to advanced search)", groups = "regression-test")
    @TmsLink("4714")
    public void switchToAdvancedTest() {
        collaborator.gotoCoursesPage();
        assertTrue(coursesPage.isSwitchedToAdvancedSearch());
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 7)
    @Test(description = "Verify searching/filtering functionality for Courses (by clear search (after any filter applied)", groups = "regression-test")
    @TmsLink("4714")
    public void clearFilterTest() {
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.addAssignment(learner, USER, waName, true);
        collaborator.gotoCourseAssignments();
        String assignmentLocal =  courseAssignmentsPage.addAssignment(learner, USER, waName, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.addAssignment(learner, USER, waName, true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.inputCaption(assignmentLocal);
        int afterSearch = coursesPage.getOptionalCourses().size();
        coursesPage.clickClearFilter();
        int aflerClearSize = coursesPage.getOptionalCourses().size();
        assertTrue(aflerClearSize != afterSearch);
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 8)
    @Test(description = "Verify searching/filtering functionality for Courses (by combine two filter)", groups = "regression-test")
    @TmsLink("4714")
    public void searchByCombineTwoFilterTest() {
        String assignmentLocal = getRandomString(8, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, USER, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(assignmentName);
        courseAssignmentsPage.clickAssignment(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().clickColorButton();
        courseAssignmentsPage.getDetails().selectColor();
        courseAssignmentsPage.getDetails().checkIsCertificate();
        String color = courseAssignmentsPage.getDetails().getColor().substring(1);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentLocal);
        courseAssignmentsPage.getDetails().checkIsChargeable();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.clickColorFilter();
        coursesPage.selectColor(color);
        coursesPage.selectChargeableFilter();
        assertFalse(coursesPage.getOptionalCourses().contains(assignmentName));
        assertFalse(coursesPage.getOptionalCourses().contains(assignmentLocal));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 9)
    @Test(description = "Verify searching/filtering functionality for Courses (Save a filter)", groups = "regression-test")
    @TmsLink("4714")
    public void saveFilterTest() {
        String filterName = getRandomString(8, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        String weight = courseAssignmentsPage.getDetails().inputWeight();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.saveMyFilter(weight, filterName);
        assertTrue(coursesPage.getMyFiltersText().contains(filterName));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 10)
    @Test(description = "Verify searching/filtering functionality for Courses (Update the filter)", groups = "regression-test")
    @TmsLink("4714")
    public void updateFilterTest() {
        String filterName = getRandomString(8, ENGLISH_ALPHABET, true);
        String weight = "105";
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.updateCustomFilter(weight, filterName));
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 11)
    @Test(description = "Verify searching/filtering functionality for Courses (Apply the filter)", groups = "regression-test")
    @TmsLink("4714")
    public void applyFilterTest() {
        String filterName = getRandomString(8, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        String weight = courseAssignmentsPage.getDetails().inputWeight();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.saveMyFilter(weight, filterName);
        int beforeSearch = coursesPage.getOptionalCourses().size();
        browserManager.reloadPage();
        coursesPage.clickMyFilter();
        coursesPage.selectCustomFilter(filterName);
        coursesPage.clickOptionCoursesTab();
        int afterSearch = coursesPage.getOptionalCourses().size();
        assertTrue(beforeSearch == afterSearch);
    }

    @TestCaseID(id = "4714")
    @TCStep(step = 12)
    @Test(description = "Verify searching/filtering functionality for Courses (Apply the filter)", groups = "regression-test")
    @TmsLink("4714")
    public void deleteFilterTest() {
        String filterName = getRandomString(8, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        String weight = courseAssignmentsPage.getDetails().inputWeight();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learner, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.saveMyFilter(weight, filterName);
        coursesPage.clickMyFilter();
        coursesPage.selectCustomFilter(filterName);
        coursesPage.clickDeleteFilter();
        dialogBox.clickApplyButton();
        coursesPage.clickMyFilter();
        assertFalse(coursesPage.getMyFiltersText().contains(filterName));
    }
}
