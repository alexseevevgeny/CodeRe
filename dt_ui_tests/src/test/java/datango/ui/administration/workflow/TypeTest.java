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
import static datango.ui.data.InputValuesProvider.HTML_CODE;
import static datango.ui.data.InputValuesProvider.TEST_DESCRIPTION;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.NEW_FOLDER;
import static datango.utils.RandomUtils.AUTOTESTS_PREFIX;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workflow process/Type] automation test cases")
public class TypeTest extends BaseTest {

    String typeName;
    String typeDescription;
    String typeForEdit;
    private String taskName;
    private String waName = createName();

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoWorkareaAdministration();
        waName = workareaAdmPage.addNewWorkarea(createName(), createId());
        collaborator.gotoStatusAdministration();
        typeForEdit = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewType(typeForEdit, TEST_DESCRIPTION);
    }

    @BeforeMethod
    public void setUpMethod() {
        typeName = getRandomString(7, ENGLISH_ALPHABET, true);
        typeDescription =  "type" + TEST_DESCRIPTION;
        browserManager.reloadPage();
        collaborator.gotoStatusAdministration();
    }

    @AfterClass
    public void tearDownClass() {
        browserManager.reloadPage();
        List<String> names = workflowPage.getTypeNames();
        for (String name : names) {
            if (name.contains(AUTOTESTS_PREFIX)) {
                try {
                    workflowPage.clickTypeDelete(name);
                    dialogBox.clickApplyButton();
                } finally {
                    browserManager.reloadPage();
                }
            }
        }
    }

    @TestCaseID(id = "4717")
    @Test(description = "Verify adding new Type (correct input) ", groups = "smoke-test")
    @TmsLink("4717")
    public void typeAddTest() {
        workflowPage.addNewType(typeName, typeDescription);
        assertTrue(workflowPage.getTypeNames().contains(typeName), MSG_TYPE_CREATED.getValue());
    }

    @TestCaseID(id = "4719")
    @Test(description = "Verify adding new Type (existing name)", groups = "regression-test")
    @TmsLink("4719")
    public void typeAddExistingTest() {
        typeName = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewType(typeName, typeDescription);
        int beforeQty = workflowPage.getTypeNames().size();
        workflowPage.addNewType(typeName, typeDescription);
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getTypeNames().size() != (beforeQty + 1)), MSG_TYPE_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4718")
    @Test(description = "Verify adding new Type (incorrect input) ", groups = "smoke-test")
    @TmsLink("4718")
    public void typeAddIncorrectTest() {
        typeName = " ";
        int beforeQty = workflowPage.getTypeNames().size();
        workflowPage.addNewType(typeName, typeDescription);
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getTypeNames().size() != (beforeQty + 1)), MSG_TYPE_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4720")
    @Test(description = "Verify adding new Type (blank mandatory field)", groups = "regression-test")
    @TmsLink("4720")
    public void typeAddBlankMandatoryTest() {
        typeName = "";
        int beforeQty = workflowPage.getTypeNames().size();
        workflowPage.addNewType(typeName, typeDescription);
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getTypeNames().size() != (beforeQty + 1)), MSG_TYPE_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4725")
    @Test(description = "Verify deleting unassigned Type (single)", groups = "regression-test")
    @TmsLink("4725")
    public void typeDeleteSingleTest() {
        workflowPage.addNewType(typeName, typeDescription);
        workflowPage.clickTypeCheck(typeName);
        workflowPage.clickTypeDelete(typeName);
        dialogBox.clickApplyButton();
        assertTrue(!workflowPage.getTypeNames().contains(typeName), MSG_TYPE_DELETED.getValue());
    }

    @TestCaseID(id = "4721")
    @Test(description = "Verify editing Types (correct input)", groups = "regression-test")
    @TmsLink("4721")
    public void typeEditCorrectTest() {
        String editName = typeName + " edit";
        workflowPage.addNewType(typeName, typeDescription);
        workflowPage.clickType(typeName);
        workflowPage.inputTypeName(editName);
        workflowPage.clickTypeEditIcon();
        assertTrue(workflowPage.getTypeNames().contains(editName), MSG_TYPE_EDITED.getValue());
    }

    @TestCaseID(id = "4722")
    @Test(description = "Verify editing Type (incorrect input)", groups = "regression-test")
    @TmsLink("4722")
    public void typeEditIncorrectTest() {
        String editName = " ";
        workflowPage.clickType(typeForEdit);
        workflowPage.inputTypeName(editName);
        workflowPage.clickTypeEditIcon();
        assertTrue(!workflowPage.getTypeNames().contains(editName), MSG_TYPE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4724")
    @Test(description = "Verify editing Type (clear mandatory field)", groups = "regression-test")
    @TmsLink("4724")
    public void typeEditEmptyMandatoryTest() {
        String editName = "";
        workflowPage.clickType(typeForEdit);
        workflowPage.inputTypeName(editName);
        workflowPage.clickTypeEditIcon();
        assertTrue(!workflowPage.getTypeNames().contains(editName), MSG_TYPE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4726")
    @Test(description = "Verify deleting unassigned Types (multiple)", groups = "regression-test")
    @TmsLink("4726")
    public void typeDeleteMultipleTest() {
        List<String> names = new ArrayList<>();
        names.add(workflowPage.addNewType(createName(), TEST_DESCRIPTION));
        names.add(workflowPage.addNewType(createName(), TEST_DESCRIPTION));
        int b = workflowPage.getTypeNames().size();
        for (String name : names) {
                workflowPage.clickTypeCheck(name);
        }
        workflowPage.clickTypeDeleteAllIcon();
        dialogBox.clickApplyButton();
        assertTrue((b > workflowPage.getTypeNames().size()), MSG_TYPE_DELETED.getValue());
    }

    @TestCaseID(id = "4723")
    @Test(description = "Verify editing Type (existing name)", groups = "regression-test")
    @TmsLink("4723")
    public void typeEditExistingTest() {
        workflowPage.addNewType(typeName, typeDescription);
        int beforeQty = workflowPage.getTypeNames().size();
        workflowPage.clickType(typeName);
        String existing = workflowPage.getTypeNames().stream()
                .filter(e -> !e.contains(typeName))
                .findFirst().get();
        workflowPage.inputTypeName(existing);
        workflowPage.clickTypeEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getTypeNames().size() != (beforeQty + 1)), MSG_TYPE_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "9169")
    @Test(description = "Verify renaming Type to the same name but with lower/uppercase letters", groups = "regression-test")
    @TmsLink("9169")
    public void typeEditNameToUpperLowerTest() {
        workflowPage.addNewType(typeName, typeDescription);
        workflowPage.clickType(typeName);
        String editName = StringUtils.swapCase(typeName);
        workflowPage.inputTypeName(editName);
        workflowPage.clickTypeEditIcon();
        assertTrue(workflowPage.getTypeNames().contains(editName), MSG_TYPE_EDITED.getValue());
    }

    @TestCaseID(id = "4727")
    @TCStep(step = 1)
    @Test(description = "Verify deleting single assigned Type", groups = "regression-test")
    @TmsLink("4727")
    public void deleteSingleAssignTypeTest() {
        workflowPage.addNewType(typeName, typeDescription);
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectTargetType(typeName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.clickTypeDelete(typeName);
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        deleteTypeWithTask(taskName, typeName);
    }

    @TestCaseID(id = "4727")
    @TCStep(step = 2)
    @Test(description = "Verify deleting multiple assigned Type", groups = "regression-test")
    @TmsLink("4727")
    public void deleteMultipleAssignTypeTest() {
        String typeNameOne = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewType(typeName, typeDescription);
        workflowPage.addNewType(typeNameOne, typeDescription);
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName, createId());
        mainMenu.clickWorkarea(waName);
        taskName = workareaPage.addNewEntity(NEW_FOLDER);
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectTargetType(typeName);
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.multipleSelectionType();
        workflowPage.clickTypeDeleteAllIcon();
        dialogBox.clickApplyButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        deleteTypeWithTask(taskName, typeName);
    }

    private void deleteTypeWithTask(String taskName, String typeName) {
        dialogBox.clickOkButton();
        collaborator.gotoTasks();
        tasksPage.searchForTask(taskName);
        tasksPage.clickTask(taskName);
        tasksPage.getDetails().selectBlankForAllDetails();
        tasksPage.getDetails().clickSaveButton();
        tasksPage.getDetails().uncheckEmailToWatcher();
        tasksPage.getDetails().clickOkSaveModal();
        collaborator.gotoStatusAdministration();
        workflowPage.clickTypeDelete(typeName);
        dialogBox.clickOkButton();
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 7)
    @Test(description = "Verify adding html code to input fields (Type)", groups = "smoke-test")
    @TmsLink("11215")
    public void statusAddWithHtmlCodeNameTest() {
        workflowPage.addNewType(HTML_CODE, typeDescription);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4728")
    @Test(description = "Verify deleting multiple Types if one of them is assigned", groups = "regression-test")
    @TmsLink("4728")
    public void deleteMultiplyTypesTest() {
        String unassType = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewType(typeName, typeDescription);
        workflowPage.addNewType(unassType, typeDescription);
        mainMenu.clickWorkarea(waName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickWorkflowTab();
        workareaPage.getStorageContent().selectTypeFromList(typeName);
        workareaPage.getStorageContent().saveWorkflowTab();
        collaborator.gotoStatusAdministration();
        workflowPage.clickTypeCheck(typeName);
        workflowPage.clickTypeCheck(unassType);
        workflowPage.clickTypeDeleteAllIcon();
        assertTrue(dialogBox.isErrorMessage());

    }
}
