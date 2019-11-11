package datango.ui.administration.workflow;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.NEW_FOLDER;
import static datango.utils.RandomUtils.*;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workflow process/Milestones] automation test cases")
@Slf4j
public class MilestonesTest extends BaseTest {

    private String milestoneName;
    private String milestoneForEdit;
    private String milestoneDescription;
    private String targetDate = getRandomDateInFuture().toString();
    private String waName = createName();
    private String taskName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoStatusAdministration();
    }

    @BeforeMethod
    public void setUpMethod() {
        milestoneName = createName();
        milestoneDescription = TEST_DESCRIPTION;
        browserManager.reloadPage();
        collaborator.gotoStatusAdministration();
    }

    @AfterClass
    public void tearDownClass() {
        browserManager.reloadPage();
        List<String> names = workflowPage.getMilestoneNames();
        for (String name : names) {
            if (name.contains(AUTOTESTS_PREFIX)) {
                try {
                    workflowPage.clickMilestoneDeleteIcon(name);
                    dialogBox.clickApplyButton();
                } finally {
                    browserManager.reloadPage();
                }
            }
        }
    }

    @TestCaseID(id = "4779")
    @Test(description = "Verify adding new Milestone (correct input) ", groups = "smoke-test")
    @TmsLink("4779")
    public void milestoneAddTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        assertTrue(workflowPage.getMilestoneNames().contains(milestoneName), MSG_MILESTONE_CREATED.getValue());
    }

    @TestCaseID(id = "4780")
    @Test(description = "Verify adding new Milestone (incorrect input) ", groups = "smoke-test")
    @TmsLink("4780")
    public void milestoneAddIncorrectTest() {
        milestoneName = " ";
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(!workflowPage.getStatusNames().contains(""), MSG_MILESTONE_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4782")
    @Test(description = "Verify adding new milestone (blank mandatory input) ", groups = "smoke-test")
    @TmsLink("4782")
    public void milestoneAddBlankMandatoryTest() {
        workflowPage.addNewMilestone(targetDate, TEST_EMPTY, milestoneDescription);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4781")
    @Test(description = "Verify adding new milestone (existing name input) ", groups = "smoke-test")
    @TmsLink("4781")
    public void milestoneAddExistingTest() {
        milestoneName = createName();
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        int beforeQty = workflowPage.getMilestoneNames().size();
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getMilestoneNames().size() != (beforeQty + 1)), MSG_MILESTONE_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4783")
    @Test(description = "Verify editing Milestone (correct input) ", groups = "smoke-test")
    @TmsLink("4783")
    public void milestoneEditCorrectTest() {
        String editName = milestoneName + TEST_EDIT;
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestone(milestoneName);
        workflowPage.inputMilestoneName(editName);
        workflowPage.clickMilestoneEditIcon();
        assertTrue(workflowPage.getMilestoneNames().contains(editName), MSG_MILESTONE_EDITED.getValue());
    }

    @TestCaseID(id = "4784")
    @Test(description = "Verify editing Milestone (incorrect input)", groups = "regression-test")
    @TmsLink("4784")
    public void milestoneEditIncorrectTest() {
        String editName = TEST_SPACE;
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestone(milestoneName);
        workflowPage.inputMilestoneName(editName);
        workflowPage.clickMilestoneEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(!workflowPage.getMilestoneNames().contains(editName), MSG_MILESTONE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4785")
    @Test(description = "Verify editing Milestone (existing name)", groups = "regression-test")
    @TmsLink("4785")
    public void milestoneEditExistingTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        int beforeQty = workflowPage.getMilestoneNames().size();
        String existing = workflowPage.getMilestoneNames().stream()
                .filter(e -> !e.contains(milestoneName))
                .findFirst().get();
        workflowPage.clickMilestone(milestoneName);
        workflowPage.inputMilestoneName(existing);
        workflowPage.clickMilestoneEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(workflowPage.getMilestoneNames().size() != (beforeQty + 1), MSG_MILESTONE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4786")
    @Test(description = "Verify editing Milestone  (clear mandatory field) ", groups = "regression-test")
    @TmsLink("4786")
    public void EditClearMandatoryTest() {
        milestoneForEdit = workflowPage.addNewMilestone(targetDate, milestoneName + TEST_EDIT, milestoneDescription);
        workflowPage.clickMilestone(milestoneForEdit);
        String editName = TEST_EMPTY;
        workflowPage.inputMilestoneName(editName);
        workflowPage.clickMilestoneEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(!workflowPage.getMilestoneNames().contains(editName), MSG_MILESTONE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4787")
    @Test(description = "Verify activating Milestone (from the list)", groups = "regression-test")
    @TmsLink("4787")
    public void milestoneActivateFromListTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestoneActivate(milestoneName);
        workflowPage.clickSaveMilestone();
        browserManager.reloadPage();
        assertTrue(workflowPage.isMilestoneActivated(milestoneName), MSG_IS_ACTIVATED.getValue());
    }

    @TestCaseID(id = "4789")
    @Test(description = "Verify deactivating Milestone (from the list)", groups = "regression-test")
    @TmsLink("4789")
    public void milestoneDeactivateFromListTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestoneActivate(milestoneName);
        workflowPage.clickSaveMilestone();
        browserManager.reloadPage();
        workflowPage.clickMilestoneActivate(milestoneName);
        assertTrue(!workflowPage.isMilestoneActivated(milestoneName), MSG_IS_ACTIVATED.getValue());
    }

    @TestCaseID(id = "4788")
    @Test(description = "Verify activating Milestone (from the 'edit' view)", groups = "regression-test")
    @TmsLink("4788")
    public void milestoneActivateFromEditTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestone(milestoneName);
        workflowPage.clickMilestoneActivateModal();
        workflowPage.clickMilestoneEditIcon();
        browserManager.reloadPage();
        assertTrue(workflowPage.isMilestoneActivated(milestoneName), MSG_IS_ACTIVATED.getValue());
    }

    @TestCaseID(id = "4790")
    @Test(description = "Verify deactivating Status (from the 'edit' view)", groups = "regression-test")
    @TmsLink("4790")
    public void milestoneDeactivateFromEditTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestone(milestoneName);
        workflowPage.clickMilestoneActivateModal();
        workflowPage.clickMilestoneEditIcon();
        workflowPage.clickMilestone(milestoneName);
        workflowPage.clickMilestoneActivateModal();
        workflowPage.clickMilestoneEditIcon();
        browserManager.reloadPage();
        assertTrue(!workflowPage.isMilestoneActivated(milestoneName), MSG_IS_DEACTIVATED.getValue());
    }

    @TestCaseID(id = "4791")
    @Test(description = "Verify deleting unassigned Milestone (single)", groups = "regression-test")
    @TmsLink("4791")
    public void milestoneDeleteSingleTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestoneDeleteIcon(milestoneName);
        dialogBox.clickApplyButton();
        assertTrue(!workflowPage.getMilestoneNames().contains(milestoneName), MSG_MILESTONE_DELETED.getValue());
    }

    @TestCaseID(id = "4792")
    @Test(description = "Verify deleting unassigned Milestone (multiple)", groups = "regression-test")
    @TmsLink("4792")
    public void milestoneDeleteMultipleTest() {
        List<String> names = new ArrayList<>();
        names.add(workflowPage.addNewMilestone(targetDate, createName(), milestoneDescription));
        names.add(workflowPage.addNewMilestone(targetDate, createName(), milestoneDescription));
        int initialNamesQty = workflowPage.getMilestoneNames().size();
        for (String name : names) {
            workflowPage.clickMilestoneCheck(name);
        }
        workflowPage.clickMilestoneDeleteAllIcon();
        dialogBox.clickApplyButton();
        assertTrue((initialNamesQty > workflowPage.getMilestoneNames().size()), MSG_MILESTONE_DELETED.getValue());
    }

    @TestCaseID(id = "9170")
    @Test(description = "Verify renaming Milestone to the same name but with lower/uppercase letters", groups = "regression-test")
    @TmsLink("9170")
    public void milestoneEditNameToUpperLowerTest() {
        String editName = StringUtils.swapCase(milestoneName);
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestone(milestoneName);
        workflowPage.inputMilestoneName(editName);
        workflowPage.clickMilestoneEditIcon();
        assertTrue(workflowPage.getMilestoneNames().contains(editName), MSG_MILESTONE_EDITED.getValue());
    }

    @TestCaseID(id = "4793")
    @Test(description = "Verify deleting single assigned Milestone", groups = "regression-test")
    @TmsLink("4793")
    public void deleteSingleAssignMilestoneTest() {
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.clickMilestoneActivate(milestoneName);
        workflowPage.clickSaveMilestone();
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectTargetMilestone(milestoneName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.clickMilestoneDeleteIcon(milestoneName);
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        deleteMilestoneWithTask(taskName, milestoneName);
    }

    @TestCaseID(id = "4794")
    @Test(description = "Verify deleting multiple Milestones if one of them is assigned", groups = "regression-test")
    @TmsLink("4794")
    public void deleteMultipleAssignMilestoneTest() {
        String milestoneNameOne = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewMilestone(targetDate, milestoneName, milestoneDescription);
        workflowPage.addNewMilestone(targetDate, milestoneNameOne, milestoneDescription);
        workflowPage.clickMilestoneActivate(milestoneName);
        workflowPage.clickMilestoneActivate(milestoneNameOne);
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectTargetMilestone(milestoneName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.multipleSelectionMilestones();
        workflowPage.clickMilestoneDeleteAllIcon();
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        deleteMilestoneWithTask(taskName, milestoneName);
    }

    private void deleteMilestoneWithTask(String taskName, String milestoneName) {
        dialogBox.clickOkButton();
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectBlankForAllDetails();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.clickMilestoneDeleteIcon(milestoneName);
        dialogBox.clickOkButton();
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 4)
    @Test(description = "Verify adding html code to input fields (Milestone)", groups = "smoke-test")
    @TmsLink("11215")
    public void milestoneAddWithHtmlCodeNameTest() {
        workflowPage.addNewMilestone(targetDate, HTML_CODE, milestoneDescription);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }
}
