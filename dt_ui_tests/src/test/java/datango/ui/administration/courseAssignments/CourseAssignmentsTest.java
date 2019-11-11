package datango.ui.administration.courseAssignments;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.data.TestDataProvider.SERVICE_OU;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.enums.FormFields.NAME;
import static datango.ui.pages.collaborator.pageElements.EntityTree.*;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Course assignments] automation test cases")
public class CourseAssignmentsTest extends BaseTest {
    private String assignmentName;
    private String assignmentForEdit;
    private String filterName;
    private String waName;
    private String waId;
    private String ouName;
    private String regCode;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        waName = getRandomString(7, ENGLISH_ALPHABET, true);
        waId = getRandomString(4, NUMERIC, false);
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, waId);
        mainMenu.clickWorkarea(waName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickPublishAll();
        collaborator.gotoCourseAssignments();
        assignmentForEdit = courseAssignmentsPage.addAssignment(learnerRole, GROUP, waName,true);
        regCode = createId();
        ouName = createName();
    }

    @BeforeMethod
    public void setUpMethod() {
        assignmentName = getRandomString(8, ENGLISH_ALPHABET, true);
        browserManager.reloadPage();
    }

    @TestCaseID(id = "4883")
    @Test(description = "Verify adding a new course assignment (correct input)", groups = "smoke-test")
    @TmsLink("4883")
    public void courseAssignmentAddTest() {
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, false);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        assertTrue(courseAssignmentsPage.getAssignmentsNames().contains(assignmentName), MSG_ASSIGNMENT_CREATED.getValue());
    }

    @TestCaseID(id = "4884")
    @Test(description = "Verify adding a new course assignment (blank mandatory input)", groups = "smoke-test")
    @TmsLink("4884")
    public void courseAssignmentBlankMandatoryAddTest() {
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_ASSIGNMENT_NOT_CREATED.getValue());
        dialogBox.clickOkButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        dialogBox.clickApplyButton();
    }

    @TestCaseID(id = "4885")
    @Test(description = "Verify editing a course assignment (correct input) ", groups = "regression-test")
    @TmsLink("4885")
    public void courseAssignmentEditTest() {
        String editName = assignmentForEdit + TEST_EDIT;
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickAssignment(assignmentForEdit);
        courseAssignmentsPage.getDetails().inputCaption(editName);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(editName);
        assertTrue(courseAssignmentsPage.getAssignmentsNames().contains(editName), MSG_ASSIGNMENT_EDITED.getValue());
    }

    @TestCaseID(id = "4891")
    @Test(description = "Verify changing Type of course assignment", groups = "regression-test")
    @TmsLink("4891")
    public void courseAssignmentEditTypeTest() {
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickAssignment(assignmentForEdit);
        courseAssignmentsPage.getDetails().clickSection(1);
        String exp = courseAssignmentsPage.getDetails().selectCourseType();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        browserManager.reloadPage();
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickAssignment(assignmentForEdit);
        String act = courseAssignmentsPage.getDetails().getCourseType();
        assertTrue(exp.equals(act), MSG_ASSIGNMENT_EDITED.getValue());

    }

    @TestCaseID(id = "4894")
    @Test(description = "Verify copying a course assignment", groups = "regression-test")
    @TmsLink("4894")
    public void courseAssignmentCopyTest() {
        String name = assignmentForEdit + "copy";
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickCopyAssignment(assignmentForEdit);
        courseAssignmentsPage.getDetails().inputCaption(name);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(name);
        assertTrue(courseAssignmentsPage.getAssignmentsNames().contains(name), MSG_PERMISSION_ADDED.getValue());

    }

    @TestCaseID(id = "4886")
    @Test(description = "Verify editing a course assignment (blank mandatory fields)", groups = "regression-test")
    @TmsLink("4886")
    public void courseAssignmentEditEmptyMandatoryTest() {
        String editName = "";
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickAssignment(assignmentForEdit);
        courseAssignmentsPage.getDetails().inputCaption(editName);
        courseAssignmentsPage.getDetails().checkIsChargeable();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_ASSIGNMENT_NOT_EDITED.getValue());
        dialogBox.clickOkButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        dialogBox.clickApplyButton();

    }

    @TestCaseID(id = "")
    @Test(description = "Verify editing a new course assignment (incorrect input)", groups = "regression-test")
    @TmsLink("")
    public void courseAssignmentEditIncorrectTest() {
        String assignmentEdit = courseAssignmentsPage.addAssignment(learnerRole, GROUP, waName, true);
        String editName = TEST_SPACE;
        browserManager.reloadPage();
        courseAssignmentsPage.searchByCaption(assignmentEdit);
        courseAssignmentsPage.clickAssignment(assignmentEdit);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(editName);
        courseAssignmentsPage.getDetails().checkIsChargeable();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_ASSIGNMENT_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4887")
    @Test(description ="Verify deactivating a course assignment (in the list)", groups = "regression-test")
    @TmsLink("4887")
    public void courseAssignmentDeactivateTest() {
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickActivateAssignment(assignmentForEdit);
        dialogBox.clickApplyButton();
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickActivateAssignment(assignmentForEdit);
    }

    @TestCaseID(id = "4888")
    @Test(description ="Verify deactivating a course assignment (in the list)", groups = "regression-test")
    @TmsLink("4888")
    public void courseAssignmentActivateTest() {
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickActivateAssignment(assignmentForEdit);
        dialogBox.clickApplyButton();
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickActivateAssignment(assignmentForEdit);
    }

    @TestCaseID(id = "4889")
    @Test(description ="Verify deactivating a course assignment (in the 'Details' view)", groups = "regression-test")
    @TmsLink("4889")
    public void courseAssignmentDeactivateDetailsTest() {
        courseAssignmentsPage.searchByCaption(assignmentForEdit);
        courseAssignmentsPage.clickAssignment(assignmentForEdit);
        courseAssignmentsPage.getDetails().checkIsActive();
        courseAssignmentsPage.getDetails().checkIsActive();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
    }

    @TestCaseID(id = "4890")
    @Test(description ="Verify activating a course assignment (in the 'Details' view)", groups = "regression-test")
    @TmsLink("4890")
    public void courseAssignmentActivateDetailsTest() {
        courseAssignmentsPage.clickAssignment(assignmentForEdit);
        courseAssignmentsPage.getDetails().checkIsActive();
        courseAssignmentsPage.getDetails().checkIsActive();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
    }

    @TestCaseID(id = "4893")
    @Test(description ="Verify deleting of course assignment", groups = "regression-test")
    @TmsLink("4893")
    public void deleteCourseAssignmentTest() {
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(assignmentName);
        courseAssignmentsPage.clickDeleteIcon(assignmentName);
        dialogBox.clickApplyButton();
        assertTrue(courseAssignmentsPage.isEntityDeleted(assignmentName), MSG_ASSIGNMENT_DELETED.getValue());
    }

    @TestCaseID(id = "4895")
    @Test(description = "Verify course description is visible", groups = "regression-test")
    @TmsLink("4895")
    public void descripPopupIsVisibleTest(){
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        browserManager.reloadPage();
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignment);
        courseAssignmentsPage.getDetails().clickDetailsDescription();
        courseAssignmentsPage.getDetails().inputCourseDescription(assignment + TEST_DESCRIPTION);
        courseAssignmentsPage.getDetails().clickOkModal();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        browserManager.reloadPage();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        coursesPage.courseOpenPopup(assignment);
        assertTrue(coursesPage.popupIsPresent(), MSG_POPUP_PRESENT.getValue());
    }

    @TestCaseID(id = "4896")
    @Test(description = "Verify course description is NOT visible", groups = "regression-test")
    @TmsLink("4896")
    public void descripPopupIsNotVisibleTest(){
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertFalse(coursesPage.descrIconNotVisible(assignment), MSG_DESCRIPTION_ICON_NOT_PRESENT.getValue());
    }

    @TestCaseID(id = "5975")
    @Test(description = "Verify sharing a course assignment", groups = "regression-test")
    @TmsLink("5975")
    public void sharingCourseAssignTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        browserManager.reloadPage();
        String linkSuffix = courseAssignmentsPage.getLinkpart(assignment);
        courseAssignmentsPage.clickQRIcon(assignment);
        assertTrue(courseAssignmentsPage.qrCodeIsVisible(), MSG_COURSE_QR_CODE_IS_DISPLAYED.getValue());
        assertTrue(courseAssignmentsPage.linkIsPresent(), MSG_COURSE_LINK_IS_PRESENT.getValue());
        assertTrue(courseAssignmentsPage.linkIsValid(linkSuffix), MSG_COURSE_LINK_IS_VALID.getValue());
    }

    @TestCaseID(id = "5979")
    @Test(description = "Verify a tooltip appears when pointing at object selected for the course step", groups = "regression-test")
    @TmsLink("5979")
    public void tooltipDisplayTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        browserManager.reloadPage();
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(3);
        assertTrue(courseAssignmentsPage.getDetails().getTooltipTitle().matches("[a-zA-Z]{4}[:][\\s][0-9]{4,}"),
            MSG_COURSE_STEP_WORKAREA_TOOLTIP_PRESENT.getValue());
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 9)
    @Test(description = "Verify adding html code to input fields (Course assignments)", groups = "smoke-test")
    @TmsLink("11215")
    public void courseAssignmentAddWithHtmlCodeName() {
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(HTML_CODE);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Caption')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByCaptionTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        assignment = assignment.substring(0,4);
        assertTrue(courseAssignmentsPage.isFilteredByCaption(assignment));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Weight')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByWeightTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        String weight = courseAssignmentsPage.getDetails().inputWeight();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByWeight(weight);
        assertTrue(courseAssignmentsPage.isFilteredByWeigth(weight));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Requires approval')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByRequiredApproveTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsReqApproval();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByApproval();
        assertTrue(courseAssignmentsPage.isFilteredByReqApproval());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Requires approval')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByRecurringTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(2);
        courseAssignmentsPage.getDetails().clickReccuringButton();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        dialogBox.clickOkButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByRecurring();
        assertTrue(courseAssignmentsPage.isFilteredByRecurring());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Chargeable')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByChargeableTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsChargeable();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByChargeable();
        assertTrue(courseAssignmentsPage.isFilteredByChargeable());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Mandatory')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByMandatoryTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsMandatory();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByMandatory();
        assertTrue(courseAssignmentsPage.isFilteredByMandatory());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Active')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByActiveTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsActive();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByActive();
        assertTrue(courseAssignmentsPage.isFilteredByActive());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Certificate')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByCertificateTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsCertificate();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCertificate();
        assertTrue(courseAssignmentsPage.isFilteredByCertificate());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Course Type')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByCourseTypeTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().selectCourseTypeByDigital();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        assertTrue(courseAssignmentsPage.isFilteredByCourseType());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Description')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByDescriptionTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().clickDetailsDescription();
        courseAssignmentsPage.getDetails().inputCourseDescription(assignment + TEST_DESCRIPTION);
        courseAssignmentsPage.getDetails().clickOkModal();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        assertTrue(courseAssignmentsPage.isFilteredByDescription(assignment.substring(0,3).toLowerCase()));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Filter by 'Color')", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilteredByColorTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().clickColorButton();
        courseAssignmentsPage.getDetails().selectColor();
        courseAssignmentsPage.getDetails().selectCourseTypeByDigital();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        assertTrue(courseAssignmentsPage.isFilteredByColor());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Save a filter)", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentSaveFilterTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(overLapTitle);
        courseAssignmentsPage.clickOpenFilterModal();
        courseAssignmentsPage.inputFilterName(filterName);
        courseAssignmentsPage.clickSaveFilterButton();
        assertTrue(courseAssignmentsPage.saveOwnTitleFilter(filterName));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Update a filter)", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentUpdateFilterTest() {
        String overLapTitle = "adt";
        String updatedTitle = getRandomString(5, ENGLISH_ALPHABET, true);
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        String updatedFilter = getRandomString(6, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(overLapTitle);
        courseAssignmentsPage.clickOpenFilterModal();
        courseAssignmentsPage.inputFilterName(filterName);
        courseAssignmentsPage.clickSaveFilterButton();
        assertTrue(courseAssignmentsPage.updateCustomFilter(updatedTitle, updatedFilter));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Delete a filter)", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentDeleteFilterTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(overLapTitle);
        courseAssignmentsPage.clickOpenFilterModal();
        courseAssignmentsPage.inputFilterName(filterName);
        courseAssignmentsPage.clickSaveFilterButton();
        String deleteFilter = courseAssignmentsPage.deleteCustomFilter();
        assertFalse(courseAssignmentsPage.isCustomFilterDeleted(deleteFilter));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Combine two or more filters together)", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentFilterByTwoFiltersTest() {
        String overLapTitle = "adt";
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsCertificate();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(overLapTitle);
        courseAssignmentsPage.searchByCertificate();
        assertTrue(courseAssignmentsPage.isFilteredByTwoFilters(overLapTitle));
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Clear search (after any filter applied))", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentClearFilterTest() {
        String overLapTitle = "adt";
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsCertificate();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(overLapTitle);
        courseAssignmentsPage.searchByCertificate();
        assertTrue(courseAssignmentsPage.isFilterCleared());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Apply the filter)", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentApplyFilterTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().checkIsCertificate();
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        courseAssignmentsPage.searchByCaption(overLapTitle);
        courseAssignmentsPage.searchByCertificate();
        courseAssignmentsPage.clickOpenFilterModal();
        courseAssignmentsPage.inputFilterName(filterName);
        courseAssignmentsPage.clickSaveFilterButton();
        courseAssignmentsPage.selectCustomFilter(filterName);
        assertTrue(courseAssignmentsPage.isFilterCleared());
    }

    @TestCaseID(id = "4902")
    @Test(description = "Verify searching/filtering functionality for Course assignments (Switch to advanced search)", groups = "regression-test")
    @TmsLink("4902")
    public void assignmentAdvancedSearchTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(learner, USER, waName,true);
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAdvancedSearch();
        courseAssignmentsPage.clickSearchButton();
        assertTrue(courseAssignmentsPage.isFilteredByAdvancedSearch(assignment));
    }

    @TestCaseID(id = "16864")
    @Test(description = "Verify if custom email template could be saved")
    @TmsLink("16864")
    public void verifyEmailTemplateSaveTest() {
        String templateName = getRandomString(7, ENGLISH_ALPHABET, true);
        String templateText = getRandomString(7, ENGLISH_ALPHABET, true) + templateName;
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickNotifyUserAfterCourseAssignment();
        courseAssignmentsPage.getDetails().clickPersonalizedNotifyUserCA();
        courseAssignmentsPage.getDetails().clickPersonalizedButtonNotifyUserCA();
        courseAssignmentsPage.inputNotifyEmailTemplateName(templateName);
        courseAssignmentsPage.inputNotifyEmailTemplateText(templateText);
        courseAssignmentsPage.clickSaveNotifyButton();
        assertTrue(courseAssignmentsPage.isNotifyCopyEnabled());
        assertTrue(courseAssignmentsPage.isNotifyDeleteEnabled());
        assertTrue(courseAssignmentsPage.isNotifyEditPresent());
        assertTrue(courseAssignmentsPage.getTemplateName().equals(templateName));
    }

    @TestCaseID(id = "16865")
    @Test(description = "Verify if custom email template could be copied", dependsOnMethods = "verifyEmailTemplateSaveTest")
    @TmsLink("16865")
    public void verifyEmailTemplateCopyTest() {
        collaborator.gotoCourseAssignments();
        String templateName = getRandomString(7, ENGLISH_ALPHABET, true);
        String templateText = getRandomString(7, ENGLISH_ALPHABET, true) + templateName;
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickNotifyUserAfterCourseAssignment();
        courseAssignmentsPage.getDetails().clickPersonalizedNotifyUserCA();
        courseAssignmentsPage.getDetails().clickPersonalizedButtonNotifyUserCA();
        courseAssignmentsPage.selectTemplate();
        String text = courseAssignmentsPage.getNotificationText();
        courseAssignmentsPage.clickNotifyCopy();
        assertTrue(text.equals(courseAssignmentsPage.getNotificationText()));
        courseAssignmentsPage.inputNotifyEmailTemplateName(templateName);
        courseAssignmentsPage.inputNotifyEmailTemplateText(templateText);
        courseAssignmentsPage.clickSaveNotifyButton();
        assertTrue(courseAssignmentsPage.isNotifyCopyEnabled());
        assertTrue(courseAssignmentsPage.isNotifyDeleteEnabled());
        assertTrue(courseAssignmentsPage.isNotifyEditPresent());
        assertTrue(courseAssignmentsPage.getTemplateName().equals(templateName));
    }

    @TestCaseID(id = "16866")
    @Test(description = "Verify if custom email template could be deleted", dependsOnMethods = "verifyEmailTemplateCopyTest")
    @TmsLink("16866")
    public void verifyEmailTemplateDeleteTest() {
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickNotifyUserAfterCourseAssignment();
        courseAssignmentsPage.getDetails().clickPersonalizedNotifyUserCA();
        courseAssignmentsPage.getDetails().clickPersonalizedButtonNotifyUserCA();
        List<String> templateList = courseAssignmentsPage.getTemplateNames();
        courseAssignmentsPage.selectTemplate();
        courseAssignmentsPage.clickNotifyDelete();
        dialogBox.clickApplyButton();
        assertTrue(!courseAssignmentsPage.getTemplateNames().containsAll(templateList));
    }

    @TestCaseID(id = "16847")
    @Test(description = "Verify if an OU can be removed from a course assignment")
    @TmsLink("16847")
    public void ouRemoveFromCourseTest() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(SERVICE_OU, OU, waName,true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignment));
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDeleteIcon(SERVICE_OU);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(!coursesPage.getOptionalCourses().contains(assignment));
    }

    @TestCaseID(id = "16846")
    @Test(description = "Verify if an OU can be added to a course assignment")
    @TmsLink("16846")
    public void addOuToCourseAssignment() {
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(SERVICE_OU, OU, waName,true);
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignment));
    }

    @TestCaseID(id = "16844")
    @Test(description = "Verify if an OU can be added to a course assignment")
    @TmsLink("16844")
    public void addGroupToCourseAssignment() {
        String groupName = createName() + " group";
        String regCode = createId();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.searchByName(userOne);
        orgViewPage.clickToggleButton();
        orgViewPage.clickEntity(userOne);
        orgViewPage.clickDetailsSaveButton();
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userOne);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(groupName, GROUP, waName,true);
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignment));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "16845")
    @Test(description = "Verify if a group can be removed from a course assignment")
    @TmsLink("16845")
    public void removeGroupFromCourseAssignment() {
        String groupName = createName() + " group";
        String regCode = createId();
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddRole();
        orgViewPage.inputEntityRoleAddName(groupName);
        orgViewPage.inputEntityRoleAddRegCode(regCode);
        orgViewPage.clickCreateButton();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.searchByName(groupName);
        orgViewPage.clickEntity(groupName);
        orgViewPage.searchByName(userOne);
        orgViewPage.clickToggleButton();
        orgViewPage.clickEntity(userOne);
        orgViewPage.clickDetailsSaveButton();
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userOne);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(groupName, GROUP, waName,true);
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignment));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDeleteIcon(groupName);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(!coursesPage.getOptionalCourses().contains(assignment));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "16842")
    @Test(description = "Verify if multiple learners can be added to a course assignment at once")
    @TmsLink("16842")
    public void addMultiplyLearnersToCourseAssignment() {
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(BASE_OU);
        String userTwo = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(userOne, USER, true);
        courseAssignmentsPage.getDetails().assignTo(userTwo, USER, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userOne);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userTwo);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userTwo);
        collaborator.getLoginPage().inputLoginPassword(userTwo);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "16843")
    @Test(description = "Verify if a learner can be removed from a course assignment")
    @TmsLink("16843")
    public void removeMultiplyLearnersToCourseAssignment() {
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(BASE_OU);
        String userTwo = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.clickAddButton();
        courseAssignmentsPage.getDetails().clickSection(1);
        courseAssignmentsPage.getDetails().inputCaption(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(3);
        courseAssignmentsPage.getDetails().clickAddEntity();
        courseAssignmentsPage.getDetails().selectEntityWorkarea(waName);
        courseAssignmentsPage.getDetails().clickSelectionEntitySelect();
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(userOne, USER, true);
        courseAssignmentsPage.getDetails().assignTo(userTwo, USER, true);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userOne);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userTwo);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userTwo);
        collaborator.getLoginPage().inputLoginPassword(userTwo);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignmentName));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.searchByCaption(assignmentName);
        courseAssignmentsPage.clickAssignment(assignmentName);
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDeleteIcon(userOne);
        courseAssignmentsPage.getDetails().clickDeleteIcon(userTwo);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(!coursesPage.getOptionalCourses().contains(assignmentName));
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userTwo);
        collaborator.getLoginPage().inputLoginPassword(userTwo);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(!coursesPage.getOptionalCourses().contains(assignmentName));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }

    @TestCaseID(id = "16841")
    @Test(description = "Verify if an additional learner can be added to a course assignment")
    @TmsLink("16841")
    public void addSingleLearnersToCourseAssignment() {
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        collaborator.gotoCourseAssignments();
        String assignment = courseAssignmentsPage.addAssignment(userOne, USER, waName,true);
        collaborator.gotoPermissionsAdministration();
        permissionsPage.searchEntity(userOne);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(coursesPage.getOptionalCourses().contains(assignment));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
        collaborator.gotoCourseAssignments();
        courseAssignmentsPage.searchByCaption(assignment);
        courseAssignmentsPage.clickAssignment(assignment);
        courseAssignmentsPage.getDetails().clickSection(4);
        courseAssignmentsPage.getDetails().assignTo(learnerRole, GROUP, true);
        courseAssignmentsPage.getDetails().clickDeleteIcon(userOne);
        courseAssignmentsPage.getDetails().clickDetailsSaveButton();
        courseAssignmentsPage.getDetails().clickDetailsCloseIcon();
        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userOne);
        collaborator.getLoginPage().inputLoginPassword(userOne);
        collaborator.getLoginPage().clickLoginBtn();
        collaborator.gotoCoursesPage();
        coursesPage.clickOptionCoursesTab();
        assertTrue(!coursesPage.getOptionalCourses().contains(assignment));
        collaborator.performLogOut();
        collaborator.performDefaultLogin();
    }
}
