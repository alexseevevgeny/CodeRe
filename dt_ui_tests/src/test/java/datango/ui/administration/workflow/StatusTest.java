package datango.ui.administration.workflow;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.HTML_CODE;
import static datango.ui.data.InputValuesProvider.TEST_SPACE;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.NEW_FOLDER;
import static datango.utils.RandomUtils.AUTOTESTS_PREFIX;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workflow process/Status] automation test cases")
public class StatusTest extends BaseTest {

    private String statusName;
    private String statusOrder;
    private String statusForEdit;
    private String taskName;
    private String waName = createName();

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoStatusAdministration();
        statusForEdit  = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewStatus(statusForEdit, getRandomString(2, NUMERIC, false));
    }

    @BeforeMethod
    public void setUpMethod() {
        statusName = getRandomString(7, ENGLISH_ALPHABET, true);
        statusOrder = getRandomString(2, NUMERIC, false);
        browserManager.reloadPage();
        collaborator.gotoStatusAdministration();
    }

    @AfterClass
    public void tearDownClass() {
        browserManager.reloadPage();
        List<String> names = workflowPage.getStatusNames();
        for (String name : names) {
            if (name.contains(AUTOTESTS_PREFIX)) {
                try {
                    workflowPage.clickStatusDelete(name);
                    dialogBox.clickApplyButton();
                } finally {
                    browserManager.reloadPage();
                }
            }
        }
    }

    @TestCaseID(id = "4821")
    @Test(description = "Verify adding new Status (correct input) ", groups = "smoke-test")
    @TmsLink("4821")
    public void statusAddTest() {
        workflowPage.addNewStatus(statusName, statusOrder);
        assertTrue(workflowPage.getStatusNames().contains(statusName), MSG_STATUS_CREATED.getValue());
    }

    @TestCaseID(id = "4831")
    @Test(description = "Verify activating Status (from the list)", groups = "regression-test")
    @TmsLink("4831")
    public void statusActivateFromListTest() {
        workflowPage.clickStatusActivate(statusForEdit);
    }

    @TestCaseID(id = "4833")
    @Test(description = "Verify deactivating Status (from the list)", groups = "regression-test")
    @TmsLink("4833")
    public void statusDeactivateFromListTest() {
        workflowPage.clickStatusActivate(statusForEdit);
        workflowPage.clickStatusActivate(statusForEdit);
    }

    @TestCaseID(id = "4832")
    @Test(description = "Verify activating Status (from the 'edit' view)", groups = "regression-test")
    @TmsLink("4832")
    public void statusActivateFromEditTest() {
        workflowPage.clickStatus(statusForEdit);
        workflowPage.clickStatusEditActivate();
        workflowPage.clickStatusEditIcon();
    }

    @TestCaseID(id = "4834")
    @Test(description = "Verify deactivating Status (from the 'edit' view)", groups = "regression-test")
    @TmsLink("4834")
    public void statusDeactivateFromEditTest() {
        workflowPage.clickStatus(statusForEdit);
        workflowPage.clickStatusEditActivate();
        workflowPage.clickStatusEditIcon();
        workflowPage.clickStatus(statusForEdit);
        workflowPage.clickStatusEditActivate();
        workflowPage.clickStatusEditIcon();
    }

    @TestCaseID(id = "4822")
    @Test(description = "Verify adding new Status (incorrect name)", groups = "smoke-test")
    @TmsLink("4822")
    public void statusAddIncorrectTest() {
        statusName = " ";
        workflowPage.addNewStatus(statusName, statusOrder);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(!workflowPage.getStatusNames().contains(""), MSG_STATUS_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4825")
    @Test(description = "Verify adding new Status (blank mandatory field)", groups = "regression-test")
    @TmsLink("4825")
    public void statusAddBlankMandatoryTest() {
        statusName = "";
        int beforeQty = workflowPage.getStatusNames().size();
        workflowPage.addNewStatus(statusName, statusOrder);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        assertTrue(workflowPage.getStatusNames().size() == beforeQty, MSG_STATUS_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4824")
    @Test(description = "Verify adding new Status (existing name)", groups = "regression-test")
    @TmsLink("4824")
    public void statusAddExistingTest() {
        workflowPage.addNewStatus(statusName, statusOrder);
        int beforeQty = workflowPage.getStatusNames().size();
        workflowPage.addNewStatus(statusName, statusOrder);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getStatusNames().size() != (beforeQty + 1)), MSG_STATUS_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4828")
    @Test(description = "Verify editing Status (existing name)", groups = "regression-test")
    @TmsLink("4828")
    public void statusEditExistingTest() {
        workflowPage.addNewStatus(statusName, statusOrder);
        int beforeQty = workflowPage.getStatusNames().size();
        workflowPage.clickStatus(statusName);
        String existing = workflowPage.getStatusNames().stream()
                .filter(e -> !e.contains(statusName))
                .findFirst().get();
        workflowPage.inputStatusName(existing);
        workflowPage.clickStatusEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getStatusNames().size() != (beforeQty + 1)), MSG_STATUS_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4835")
    @Test(description = "Verify deleting unassigned Status (single) ", groups = "smoke-test")
    @TmsLink("4835")
    public void statusDeleteSingleTest() {
        workflowPage.addNewStatus(statusName, statusOrder);
        workflowPage.clickStatusCheck(statusName);
        workflowPage.clickStatusDelete(statusName);
        dialogBox.clickApplyButton();
        assertTrue(!workflowPage.getStatusNames().contains(statusName), MSG_STATUS_DELETED.getValue());
    }

    @TestCaseID(id = "4826")
    @Test(description = "Verify editing Status (correct input) ", groups = "smoke-test")
    @TmsLink("4826")
    public void statusEditCorrectTest() {
        String editName = statusName + " edit";
        workflowPage.addNewStatus(statusName, statusOrder);
        workflowPage.clickStatus(statusName);
        workflowPage.inputStatusName(editName);
        workflowPage.clickStatusEditIcon();
        assertTrue(workflowPage.getStatusNames().contains(editName), MSG_STATUS_EDITED.getValue());
    }

    @TestCaseID(id = "4827")
    @Test(description = "Verify editing Status (incorrect name)", groups = "regression-test")
    @TmsLink("4827")
    public void statusEditIncorrectTest() {
        String editName = " ";
        workflowPage.clickStatus(statusForEdit);
        workflowPage.inputStatusName(editName);
        workflowPage.clickStatusEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(!workflowPage.getStatusNames().contains(editName), MSG_STATUS_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4830")
    @Test(description = "Verify editing Status (clear mandatory field)", groups = "regression-test")
    @TmsLink("4830")
    public void statusEditEmptyMandatoryTest() {
        String editName = "";
        workflowPage.clickStatus(statusForEdit);
        workflowPage.inputStatusName(editName);
        workflowPage.clickStatusEditIcon();
        assertTrue(!workflowPage.getStatusNames().contains(editName), MSG_TYPE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4836")
    @Test(description = "Verify deleting unassigned Status (multiple)", groups = "regression-test")
    @TmsLink("4836")
    public void statusDeleteMultiplyTest() {
        List<String> names = new ArrayList<>();
        names.add(workflowPage.addNewStatus(createName(), statusOrder));
        names.add(workflowPage.addNewStatus(createName(), statusOrder));
        int b = workflowPage.getStatusNames().size();
        for (String name : names) {
            workflowPage.clickStatusCheck(name);
        }
        workflowPage.clickStatusDeleteAllIcon();
        dialogBox.clickApplyButton();
        assertTrue((b > workflowPage.getStatusNames().size()), MSG_TYPE_DELETED.getValue());
    }

    @TestCaseID(id = "4823")
    @Test(description = "Verify adding new Status (incorrect order) ", groups = "smoke-test")
    @TmsLink("4823")
    public void statusAddIncorrectOrderTest() {
        statusOrder = "-1";
        workflowPage.addNewStatus(statusName, statusOrder);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue(!workflowPage.getStatusOrders().contains(statusOrder), MSG_STATUS_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4829")
    @Test(description = "Verify editing Status (incorrect order)", groups = "regression-test")
    @TmsLink("4829")
    public void statusEditIncorrectOrderTest() {
        workflowPage.clickStatus(statusForEdit);
        workflowPage.inputStatusName(statusName);
        workflowPage.inputStatusOrder("256");
        workflowPage.clickStatusEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        assertTrue(!workflowPage.getStatusOrders().contains(TEST_SPACE), MSG_STATUS_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "9168")
    @Test(description = "Verify renaming Status to the same name but with lower/uppercase letters", groups = "regression-test")
    @TmsLink("9168")
    public void statusEditNameToUpperLowerTest() {
        workflowPage.addNewStatus(statusName, statusOrder);
        String convertedStatus = StringUtils.swapCase(statusName);
        workflowPage.clickStatus(statusName);
        workflowPage.inputStatusName(convertedStatus);
        workflowPage.clickStatusEditIcon();
        assertTrue(workflowPage.getStatusNames().contains(convertedStatus), MSG_STATUS_EDITED.getValue());
    }

    @TestCaseID(id = "4837")
    @Test(description = "Verify deleting single assigned Status", groups = "regression-test")
    @TmsLink("4837")
    public void deleteSingleAssignStatusTest() {
        workflowPage.addNewStatus(statusName, statusOrder);
        workflowPage.clickStatusActivate(statusName);
        workflowPage.clickSaveStatus();
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectTargetStatus(statusName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.clickStatusDelete(statusName);
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        deleteStatusWithTask(taskName, statusName);
    }

    @TestCaseID(id = "4838")
    @Test(description = "Verify deleting multiple Statuses if one of them is assigned", groups = "regression-test")
    @TmsLink("4838")
    public void deleteMultipleAssignStatusTest() {
        String statusNameOne = getRandomString(7, ENGLISH_ALPHABET, true);
        String statusNameTwo = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewStatus(statusNameOne, statusOrder);
        workflowPage.addNewStatus(statusNameTwo, statusOrder);
        workflowPage.addNewStatus(statusName, statusOrder);
        workflowPage.clickStatusActivate(statusName);
        workflowPage.clickSaveStatus();
        workflowPage.clickStatusActivate(statusNameOne);
        workflowPage.clickSaveStatus();
        workflowPage.clickStatusActivate(statusNameTwo);
        workflowPage.clickSaveStatus();
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectTargetStatus(statusName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.multipleSelectionStatus();
        workflowPage.clickStatusDeleteAllIcon();
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        deleteStatusWithTask(taskName, statusName);
    }


    private void deleteStatusWithTask(String taskName, String statusName) {
        dialogBox.clickOkButton();
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectBlankForAllDetails();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.clickStatusDelete(statusName);
        dialogBox.clickOkButton();
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 6)
    @Test(description = "Verify adding html code to input fields (Status)", groups = "smoke-test")
    @TmsLink("11215")
    public void statusAddWithHtmlCodeNameTest() {
        workflowPage.addNewStatus(HTML_CODE, statusOrder);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }
}
