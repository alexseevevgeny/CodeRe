package datango.ui.administration.workflow;

import datango.BaseTest;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.utils.ElemRemoverUtil;
import datango.utils.RandomUtils;
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
import static datango.ui.data.InputValuesProvider.*;
import static datango.ui.enums.AssertMessages.*;
import static datango.utils.RandomUtils.AUTOTESTS_PREFIX;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workflow process/Priorities] automation test cases")
public class PrioritiesTest extends BaseTest {
    private String priorityName;
    private String priorityDescr;
    private String priorityWeight;
    private String waName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoWorkareaAdministration();
        waName = workareaAdmPage.addNewWorkarea(createName(), createId());
        collaborator.gotoStatusAdministration();
    }

    @BeforeMethod
    public void setUpMethod() {
        priorityName = getRandomString(7, ENGLISH_ALPHABET, true);
        priorityWeight = getRandomString(2, NUMERIC, false);
        priorityDescr = TEST_DESCRIPTION;
        browserManager.reloadPage();
        collaborator.gotoStatusAdministration();
    }

    @AfterClass
    public void tearDownClass() {
        browserManager.reloadPage();
        List<String> names = workflowPage.getPriorityNames();
        for (String name : names) {
            if (name.contains(AUTOTESTS_PREFIX)) {
                try {
                    workflowPage.clickPriorityDelete(name);
                    dialogBox.clickApplyButton();
                } finally {
                    browserManager.reloadPage();
                }
            }
        }
    }

    @TestCaseID(id = "4646")
    @Test(description = "Verify adding new Priority (correct input) ", groups = "smoke-test")
    @TmsLink("4646")
    public void priorityAddTest() {
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        assertTrue(workflowPage.getPriorityNames().contains(priorityName), MSG_PRIORITY_CREATED.getValue());
    }

    @TestCaseID(id = "4647")
    @Test(description = "Verify adding new Priority (incorrect input) ", groups = "smoke-test")
    @TmsLink("4647")
    public void priorityAddIncorrectTest() {
        String priorityNameLocal = " ";
        int beforeQty = workflowPage.getPriorityNames().size();
        workflowPage.addNewPriority(priorityNameLocal, priorityWeight, priorityDescr);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getPriorityNames().size() != (beforeQty + 1)), MSG_PRIORITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4649")
    @Test(description = "Verify adding new Priority (blank mandatory field)", groups = "smoke-test")
    @TmsLink("4649")
    public void priorityAddBlankMandatoryTest() {
        String priorityNameLocal = "";
        int beforeQty = workflowPage.getPriorityNames().size();
        workflowPage.addNewPriority(priorityNameLocal, priorityWeight, priorityDescr);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getPriorityNames().size() != (beforeQty + 1)), MSG_PRIORITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4648")
    @Test(description = "Verify adding new Priority (existing name)", groups = "smoke-test")
    @TmsLink("4648")
    public void priorityAddExistingTest() {
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        int beforeQty = workflowPage.getPriorityNames().size();
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        assertTrue((workflowPage.getPriorityNames().size() != (beforeQty + 1)), MSG_PRIORITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4654")
    @Test(description = "Verify deleting unassigned Priority (single) ", groups = "smoke-test")
    @TmsLink("4654")
    public void priorityDeleteSingleTest() {
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        workflowPage.clickPriorityCheck(priorityName);
        workflowPage.clickPriorityDelete(priorityName);
        dialogBox.clickApplyButton();
        assertTrue(!workflowPage.getPriorityNames().contains(priorityName), MSG_PRIORITY_DELETED.getValue());
    }

    @TestCaseID(id = "4650")
    @Test(description = "Verify editing Priority (correct input) ", groups = "smoke-test")
    @TmsLink("4650")
    public void priorityEditCorrectTest() {
        String editName = priorityName + " edit";
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        workflowPage.clickPriority(priorityName);
        workflowPage.inputPriorityName(editName);
        workflowPage.clickPriorityEditIcon();
        assertTrue(workflowPage.getPriorityNames().contains(editName), MSG_PRIORITY_EDITED.getValue());
    }

    @TestCaseID(id = "4651")
    @Test(description = "Verify editing Priority (incorrect input)", groups = "smoke-test")
    @TmsLink("4651")
    public void priorityEditIncorrectTest(){
        String priorityNameLocal = RandomUtils.getRandomListElement(workflowPage.getPriorityNames());
        workflowPage.clickPriority(priorityNameLocal);
        workflowPage.inputPriorityName(TEST_SPACE);
        workflowPage.clickPriorityEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        workflowPage.clickPriority(priorityNameLocal);
        workflowPage.inputPriorityWeight(TEST_SPACE);
        workflowPage.clickPriorityEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4652")
    @Test(description = "Verify editing Priority (existing name)", groups = "smoke-test")
    @TmsLink("4652")
    public void priorityEditToExistingTest(){
        String priorityName = RandomUtils.getRandomListElement(workflowPage.getPriorityNames());
        List<String> priorityNameArr = ElemRemoverUtil.getListWithOutElem(workflowPage.getPriorityNames() ,priorityName);
        String existPriorityName = RandomUtils.getRandomListElement(priorityNameArr);
        workflowPage.clickPriority(existPriorityName);
        workflowPage.inputPriorityName(priorityName);
        workflowPage.clickPriorityEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4653")
    @Test(description = "Verify editing Priority (clear mandatory field)", groups = "smoke-test")
    @TmsLink("4653")
    public void priorityEditMandatoryFieldTest(){
        String priorityNameLocal = RandomUtils.getRandomListElement(workflowPage.getPriorityNames());
        workflowPage.clickPriority(priorityNameLocal);
        workflowPage.clearPriorityName();
        workflowPage.clickPriorityEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        workflowPage.clickPriority(priorityNameLocal);
        workflowPage.clearPriorityOrder();
        workflowPage.clickPriorityEditIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4655")
    @Test(description = "Verify deleting unassigned Priorities (multiple)", groups = "smoke_test")
    @TmsLink("4655")
    public void priorityDeleteUnassignedTest(){
        String priorityNameOne = getRandomString(7, ENGLISH_ALPHABET, true);
        String priorityNameTwo = getRandomString(7, ENGLISH_ALPHABET, true);
        List<String> removedPriority = new ArrayList<>();
        removedPriority.add(priorityNameOne);
        removedPriority.add(priorityNameTwo);
        workflowPage.addNewPriority(priorityNameOne, priorityWeight, priorityDescr);
        workflowPage.addNewPriority(priorityNameTwo, priorityWeight, priorityDescr);
        workflowPage.clickPriorityCheck(priorityNameOne);
        workflowPage.clickPriorityCheck(priorityNameTwo);
        workflowPage.clickPriorityDeleteAllIcon();
        dialogBox.clickOkButton();
        assertFalse(workflowPage.getPriorityNames().contains(removedPriority), MSG_PRIORITY_DELETED.getValue());

    }

    @TestCaseID(id = "4656")
    @Test(description = "Verify deleting single assigned Priority", groups = "smoke_test")
    @TmsLink("4656")
    public void priorityDeleteSingleAssignedTest(){
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        mainMenu.clickWorkarea(waName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickWorkflowTab();
        workareaPage.getStorageContent().selectPriorityFromList(priorityName);
        workareaPage.getStorageContent().saveWorkflowTab();
        collaborator.gotoStatusAdministration();
        workflowPage.clickPriority(priorityName);
        workflowPage.clickPriorityDelete(priorityName);
        assertTrue(dialogBox.isErrorMessage(), MSG_PRIORITY_NOT_DELETED.getValue());
    }

    @TestCaseID(id = "4657")
    @Test(description = "Verify deleting multiple Priorities if one of them is assigned", groups = "smoke_test")
    @TmsLink("4657")
    public void priorityDeleteMultipleAssigned(){
        String unassPriority = getRandomString(7, ENGLISH_ALPHABET, true);
        workflowPage.addNewPriority(priorityName, priorityWeight, priorityDescr);
        workflowPage.addNewPriority(unassPriority, priorityWeight, priorityDescr);
        mainMenu.clickWorkarea(waName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickWorkflowTab();
        workareaPage.getStorageContent().selectPriorityFromList(priorityName);
        workareaPage.getStorageContent().saveWorkflowTab();
        collaborator.gotoStatusAdministration();
        workflowPage.clickPriorityCheck(priorityName);
        workflowPage.clickPriorityCheck(unassPriority);
        workflowPage.clickPriorityDeleteAllIcon();
        assertTrue(dialogBox.isErrorMessage(), MSG_PRIORITY_NOT_DELETED.getValue());
    }

    @TestCaseID(id = "9171")
    @Test(description = "Verify renaming Priority to the same name but with lower/uppercase letters", groups = "regression-test")
    @TmsLink("9171")
    public void priorityEditNameToUpperLowerTest() {
        String priorityNameLocal = RandomUtils.getRandomListElement(workflowPage.getPriorityNames());
        List<String> priorityNameArr = ElemRemoverUtil.getListWithOutElem(workflowPage.getPriorityNames() ,priorityNameLocal);
        String existPriorityName = RandomUtils.getRandomListElement(priorityNameArr);
        workflowPage.clickPriority(existPriorityName);
        existPriorityName = StringUtils.swapCase(existPriorityName);
        workflowPage.inputPriorityName(existPriorityName);
        workflowPage.clickPriorityEditIcon();
        assertTrue(workflowPage.getPriorityNames().contains(existPriorityName), MSG_PRIORITY_EDITED.getValue());
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 5)
    @Test(description = "Verify adding html code to input fields (Priority)", groups = "smoke-test")
    @TmsLink("11215")
    public void priorityAddWithHtmlCodeNameTest() {
        workflowPage.addNewPriority(HTML_CODE, priorityWeight, priorityDescr);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }
}
