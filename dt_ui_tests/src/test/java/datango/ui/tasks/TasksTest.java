package datango.ui.tasks;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.utils.RandomUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.*;
import static datango.utils.RandomUtils.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Tasks] automation test cases")
public class TasksTest extends BaseTest {
    private String taskName;
    private String statusName;
    private String statusOrder;
    private String taskNameEdit;
    private String targetDate;
    private String description;
    private String textComment;
    private String titleComment;
    private String workareaName;
    private String filterName;
    private String userName;
    private String waName = createName();
    private String milestoneName;
    private String milestoneDescription;
    private String targetMilestoneDate;
    private String priorityName;
    private String priorityDescr;
    private String priorityWeight;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoStatusAdministration();
        milestoneName = createName();
        statusName = getRandomString(7, ENGLISH_ALPHABET, true);
        statusOrder = getRandomString(2, NUMERIC, false);
        priorityName = getRandomString(7, ENGLISH_ALPHABET, true);
        priorityWeight = getRandomString(2, NUMERIC, false);
        priorityDescr = TEST_DESCRIPTION;
        milestoneDescription = TEST_DESCRIPTION;
        targetMilestoneDate = getRandomDateInFuture().toString();
        workflowPage.addNewMilestone(targetMilestoneDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestoneActivate(milestoneName);
        workflowPage.clickSaveMilestone();
        browserManager.reloadPage();
        workflowPage.addNewStatus(statusName, statusOrder);
        workflowPage.clickStatusActivate(statusName);
        browserManager.reloadPage();
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        collaborator.gotoTasks();
        userName = getRandomString(6, ENGLISH_ALPHABET, true).toLowerCase();
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        description = getRandomString(4, ENGLISH_ALPHABET, true) + TEST_DESCRIPTION;
        titleComment = getRandomString(5, ENGLISH_ALPHABET, true);
        textComment = getRandomString(4, ENGLISH_ALPHABET, true) + getRandomString(8, ENGLISH_ALPHABET, true);
        targetDate = getRandomDateInFuture().toString();
    }

    @AfterClass
    public void tearDownClass() {
        filesUtil.deleteFiles();
    }

    @TestCaseID(id = "4871")
    @Test(description = "Verify all tasks appear in the list (one task for each object for each workarea)", groups = "smoke-test")
    @TmsLink("4871")
    public void tasksAppearInTheListTest() {
        assertTrue(!tasksPage.getTaskTitles().isEmpty(), MSG_TASKS_PRESENT.getValue());
    }

    @TestCaseID(id = "4873")
    @Test(description = "Verify creating a task when adding new object (WA, Folder, Lesson, Presentation)", groups = "regression-test")
    @TmsLink("4873")
    public void tasksCreatedVerifyTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        assertTrue(tasksPage.getTaskTitles().contains(taskName), MSG_TASKS_PRESENT.getValue());
    }

    @TestCaseID(id = "4874")
    @Test(description = "Verify editing a task ", groups = "regression-test")
    @TmsLink("4874")
    public void tasksEditTest() {
        List<String> names = tasksPage.getTaskTitles();
        int taskIndex = getRandomIntBetween(0, (names.size() - 1));
        String name = names.get(taskIndex);
        taskNameEdit = TEST_EDIT + name;
        tasksPage.clickTask(name);
        tasksPage.getDetails().inputTitle(taskNameEdit);
        tasksPage.getDetails().selectPriority();
        tasksPage.getDetails().selectStatus();
        tasksPage.getDetails().selectType();
        tasksPage.getDetails().clickSaveButton();
        assertTrue(tasksPage.getDetails().isSaveComment(), MSG_TASKS_PRESENT.getValue());
    }

    @TestCaseID(id = "4875")
    @Test(description = "Verify removing task's parameters (set to blank)", groups = "regression-test")
    @TmsLink("4875")
    public void tasksRemoveParamTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectPriority();
        tasksPage.getDetails().selectStatus();
        tasksPage.getDetails().selectType();
        tasksPage.getDetails().selectMilestone();
        tasksPage.getDetails().selectAndReturnAssignTo();
        tasksPage.getDetails().inputDescription(description);
        tasksPage.getDetails().inputTargetDate(targetDate);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.getDetails().clearDescription();
        tasksPage.getDetails().clearTargetDate();
        tasksPage.getDetails().selectBlankForAllDetails();
        tasksPage.getDetails().clickWatcherButton();
        tasksPage.getDetails().removeWatchers();
        tasksPage.getDetails().clickRemove();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.getDetails().clickSaveButton();
        browserManager.reloadPage();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().getDetailsTargetDateValue().isEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
        assertTrue(tasksPage.getDetails().getDetailsDescriptionValue().isEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
        assertTrue(tasksPage.getDetails().getDetailsMilestoneValue().isEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
        assertTrue(tasksPage.getDetails().getDetailsPriorityValue().isEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
        assertTrue(tasksPage.getDetails().getDetailsStatusValue().isEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
        assertTrue(tasksPage.getDetails().getDetailsTypeValue().isEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
        assertFalse(tasksPage.getDetails().isWatcherListEmpty(), MSG_DETAILS_IS_EMPTY.getValue());
    }

    @TestCaseID(id = "5380")
    @Test(description = "Verify adding comments", groups = "regression-test")
    @TmsLink("5380")
    public void tasksAddNewCommentTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().inputTitleAndTextComment(titleComment, textComment);
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        browserManager.reloadPage();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isChangeAddedToEventLog(titleComment, textComment), MSG_TITLE_AND_TEXT_PRESENT.getValue());
    }

    @TestCaseID(id = "5379")
    @Test(description = "Verify icon and hover message are shown in the log when making changes to the workflow", groups = "regression-test")
    @TmsLink("5379")
    public void tasksEventLogChangesTest() {
        int rand = RandomUtils.getRandomIntBetween(0, tasksPage.getTaskTitles().size() - 1);
        String nameTask = tasksPage.getTaskTitles().get(rand);
        tasksPage.clickTask(nameTask);
        String assignName = tasksPage.getDetails().selectAndReturnAssignTo();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().inputTextComment(textComment);
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        browserManager.reloadPage();
        tasksPage.clickTask(nameTask);
        assertTrue(tasksPage.getDetails().isChangeAddedToEventLog(assignName, textComment), MSG_ASSIGNTO_PRESENT.getValue());
        String pageType = tasksPage.getDetails().selectType();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().inputTextComment(textComment);
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        browserManager.reloadPage();
        tasksPage.clickTask(nameTask);
        assertTrue(tasksPage.getDetails().isChangeAddedToEventLog(pageType, textComment), MSG_TYPE_PRESENT.getValue());
    }

    @TestCaseID(id = "4878")
    @Test(description = "Verify deleting a task when deleting related object (WA, Folder, Lesson, Presentation)", groups = "regression-test")
    @TmsLink("4878")
    public void tasksDeleteTest() {
        workareaName = createName();
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(workareaName, createId());
        mainMenu.clickWorkarea(workareaName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageTree().clickFolder(taskName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickFolder(taskName);
        workareaPage.getStorageContent().clickDeleteFinally();
        dialogBox.clickApplyButton();
        collaborator.gotoTasks();
        assertFalse(tasksPage.isProjectTaskDeleted(taskName));
    }

    @TestCaseID(id = "4877")
    @TCStep(step = 1)
    @Test(description = "Verify logging type changes", groups = "regression-test")
    @TmsLink("4877")
    public void tasksLogTypeChangesTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String type = tasksPage.getDetails().selectType();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.clickTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isTasksChangeLogged(type), MSG_TYPE_CHANGE_LOGGED.getValue());
    }

    @TestCaseID(id = "4877")
    @TCStep(step = 2)
    @Test(description = "Verify logging status changes", groups = "regression-test")
    @TmsLink("4877")
    public void tasksLogStatusChangesTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String status = tasksPage.getDetails().selectStatus();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.clickTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isTasksChangeLogged(status), MSG_STATUS_CHANGE_LOGGED.getValue());
    }

    @TestCaseID(id = "4877")
    @TCStep(step = 3)
    @Test(description = "Verify logging priority changes", groups = "regression-test")
    @TmsLink("4877")
    public void tasksLogPriorityChangesTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String priority = tasksPage.getDetails().selectPriority();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.clickTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isTasksChangeLogged(priority), MSG_PRIORITY_CHANGE_LOGGED.getValue());
    }

    @TestCaseID(id = "4877")
    @TCStep(step = 4)
    @Test(description = "Verify logging milestine changes", groups = "regression-test")
    @TmsLink("4877")
    public void tasksLogMilestoneChangesTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String milestone = tasksPage.getDetails().selectMilestone();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.clickTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isTasksChangeLogged(milestone), MSG_MILESTONE_CHANGE_LOGGED.getValue());
    }

    @TestCaseID(id = "4877")
    @TCStep(step = 5)
    @Test(description = "Verify logging Assigned to changes", groups = "regression-test")
    @TmsLink("4877")
    public void tasksLogAssignedToChangesTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String assigned = tasksPage.getDetails().selectAndReturnAssignTo();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.clickTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isTasksChangeLogged(assigned), MSG_ASSIGNED_TO_CHANGE_LOGGED.getValue());
    }

    @TestCaseID(id = "4877")
    @TCStep(step = 6)
    @Test(description = "Verify logging target date changes", groups = "regression-test")
    @TmsLink("4877")
    public void tasksLogTargetDateChangesTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().inputTargetDate(targetDate);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.clickTask(taskName);
        tasksPage.clickTask(taskName);
        assertTrue(tasksPage.getDetails().isTasksChangeLogged(targetDate), MSG_TARGET_DATE_CHANGE_LOGGED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterMilestoneTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectMilestone();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        assertTrue(tasksPage.isFilteredByMilestone(), MSG_MILESTONE_IS_FILTERED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterPriorityTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectPriority();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        browserManager.reloadPage();
        assertTrue(tasksPage.isFilteredByPriority(), MSG_PRIORITY_IS_FILTERED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterTypeTest() {
        assertTrue(tasksPage.isFilteredByType(), MSG_TYPE_IS_FILTERED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterStatusTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectStatus();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        assertTrue(tasksPage.isFilteredByStatus(), MSG_STATUS_IS_FILTERED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterTitleTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        assertTrue(tasksPage.isFilteredByTitle(taskName), MSG_TITLE_IS_FILTERED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterAssignedTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String assigne = tasksPage.getDetails().selectAndReturnAssignTo();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.cleanSearchTaskField();
        assertTrue(tasksPage.isFilteredByAssignedTo(assigne), MSG_ASSIGNED_IS_FILTERED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterDateBeforeTargetTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().inputTargetDate(targetDate);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.cleanSearchTaskField();
        assertTrue(tasksPage.isFilteredByTargetDateBefore(targetDate), MSG_TARGET_DATE_IS_LESS.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterDateAfterTargetTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().inputTargetDate(targetDate);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        tasksPage.cleanSearchTaskField();
        assertTrue(tasksPage.isFilteredByTargetDateAfter(targetDate), MSG_TARGET_DATE_IS_GREATER.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterCreatedDateTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        assertTrue(tasksPage.isFilteredByCreatedDate(), MSG_FILTERED_BY_CREATED_DATE.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterByMeTest() {
        tasksPage.searchCreatedByMe();
        assertTrue(tasksPage.isFilteredByMe(), MSG_TASKS_FILTERED_BY_CREATOR.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterByDescriptionTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().inputTargetDate(targetDate);
        tasksPage.getDetails().inputDescription(description);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        String overlap = "adt";
        tasksPage.searchByDescription(overlap);
        assertTrue(tasksPage.isFilteredByDescription(overlap), MSG_TASKS_FILTERED_BY_DESCRIPTION.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksFilterByTwoFiltersTest() {
        String overLapTitle = "adt";
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        String assigne = tasksPage.getDetails().selectAndReturnAssignTo();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        browserManager.reloadPage();
        assertTrue(tasksPage.isFilteredByTwoFilters(overLapTitle, assigne), MSG_FILTERED_BY_TWO_FILTERS.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksSaveCustomFilterTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(5, ENGLISH_ALPHABET, true);
        collaborator.gotoTasks();
        tasksPage.searchForTask(overLapTitle);
        tasksPage.clickOpenFilterModal();
        tasksPage.inputFilterName(filterName);
        tasksPage.clickSaveFilterButton();
        assertTrue(tasksPage.saveOwnTitleFilter(filterName), MSG_FILTER_IS_SAVED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksUpdateCustomFilterTest() {
        String overLapTitle = "adt";
        String updatedTitle = getRandomString(5, ENGLISH_ALPHABET, true);
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        String updatedFilter = getRandomString(6, ENGLISH_ALPHABET, true);
        collaborator.gotoTasks();
        tasksPage.searchForTask(overLapTitle);
        tasksPage.clickOpenFilterModal();
        tasksPage.inputFilterName(filterName);
        tasksPage.clickSaveFilterButton();
        assertTrue(tasksPage.updateCustomFilter(updatedTitle, updatedFilter), MSG_FILTER_IS_UPDATED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksDeleteCustomFilterTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoTasks();
        tasksPage.searchForTask(overLapTitle);
        tasksPage.clickOpenFilterModal();
        tasksPage.inputFilterName(filterName);
        tasksPage.clickSaveFilterButton();
        String deleteFilter = tasksPage.deleteCustomFilter();
        assertFalse(tasksPage.isCustomFilterDeleted(deleteFilter), MSG_FILTER_IS_UPDATED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksAdvancedFormulaAppearedTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoTasks();
        tasksPage.searchForTask(overLapTitle);
        tasksPage.clickToAdvancedSearch();
        assertFalse(tasksPage.isAdvancedSearchFormulaAppeared(), MSG_ADVANCED_FORMULA_IS_DISPLAYED.getValue());
    }

    @TestCaseID(id = "4879")
    @Test(description = "Verify searching/filtering functionality for Tasks", groups = "regression-test")
    @TmsLink("4879")
    public void tasksClearFiltersTest() {
        String overLapTitle = "adt";
        filterName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoTasks();
        tasksPage.searchForTask(overLapTitle);
        assertFalse(tasksPage.isFilterCleared(), MSG_ADVANCED_FORMULA_IS_DISPLAYED.getValue());
    }

    @TestCaseID(id = "4873")
    @Test(description = "Verify adding html code to input fields (Task)", groups = "regression-test", enabled = false)
    @TmsLink("4873")
    public void tasksCreatedWithHtmlCodeNameTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(HTML_CODE, NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(StringUtils.substring(taskName, 0, 6));
        assertTrue(tasksPage.getTaskTitles().contains(taskName), MSG_TASKS_PRESENT.getValue());
    }

    @TestCaseID(id = "4872")
    @Test(description = "Verify no tasks appear in the list (no permissions)", groups = "regression-test")
    @TmsLink("4872")
    public void noAccessToTaskPageTest() {
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.selectAddUser();
        orgViewPage.inputEntityUserAddLogin(userName);
        orgViewPage.inputEntityUserAddName(userName);
        orgViewPage.inputEntityUserAddLastName(userName + TEST_LAST_NAME);
        orgViewPage.inputEntityUserAddEmail(TEST_EMAIL);
        orgViewPage.inputEntityUserAddPassword(userName);
        orgViewPage.clickCreateButton();

        collaborator.gotoPermissionsAdministration();
        permissionsPage.getEntityTree().clickEntity(ENTITY_TREE, userName, USER);
        permissionsPage.clickEditPermissions();
        permissionsPage.clickAddAllPermission();
        permissionsPage.clickSave();

        collaborator.performLogOut();
        collaborator.getLoginPage().inputLoginUserName(userName);
        collaborator.getLoginPage().inputLoginPassword(userName);
        collaborator.getLoginPage().clickLoginBtn();

        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());

        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickPublishAll();


        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        int taskCountBefore = tasksPage.getTaskTitles().size();

        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.clickOnWorkarea(waName);
        List<String> identityNames = workareaAdmPage.getIdentityNames();
        for (String identityName : identityNames) {
            workareaAdmPage.clickDeletePermission(identityName);
        }
        workareaAdmPage.clickSaveButton();
        collaborator.gotoTasks();

        tasksPage.searchForTask(taskName);
        int taskCountAfter = tasksPage.getTaskTitles().size();

        collaborator.performLogOut();
        collaborator.performDefaultLogin();
        collaborator.gotoTasks();

        assertFalse(taskCountAfter == taskCountBefore, MSG_TASKS_PRESENT.getValue());
    }

    @TestCaseID(id = "4876")
    @TCStep(step = 1)
    @Test(description = "Verify XLS", groups = "regression-test")
    @TmsLink("4876")
    public void tasksVerifyXLSFileTest() {
        String nameXls = RandomUtils.getRandomString(7, ENGLISH_ALPHABET, true) + ".xls";
        collaborator.gotoTasks();
        assertTrue(tasksPage.isAllDataExportedXls(nameXls));
        assertTrue(tasksPage.isAllTitlesPresent(3, nameXls));
        assertTrue(tasksPage.isNumberOfEntitiesEquals(nameXls));

    }

    @TestCaseID(id = "4876")
    @TCStep(step = 2)
    @Test(description = "Verify CSV Export", groups = "regression-test")
    @TmsLink("4876")
    public void tasksVerifyCsvFileTest() {
        String nameCsv = RandomUtils.getRandomString(7, ENGLISH_ALPHABET, true) + ".csv";
        collaborator.gotoTasks();
        assertTrue(tasksPage.isAllDataExportedCsv(nameCsv));
        assertTrue(tasksPage.isNumberOfEntitiesEqualsCsv(nameCsv));
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 11)
    @Test(description = "Verify that user can't add html code to input fields (Tasks)", groups = "regression-test")
    @TmsLink("11215")
    public void verifyCreateTaskWithHtmlTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(HTML_CODE, NEW_FOLDER);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

}
