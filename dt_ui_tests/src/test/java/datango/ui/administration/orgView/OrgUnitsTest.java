package datango.ui.administration.orgView;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.SPECIAL_CHARS;
import static datango.ui.data.InputValuesProvider.HTML_CODE;
import static datango.ui.data.InputValuesProvider.TEST_EDIT;
import static datango.ui.data.InputValuesProvider.TEST_EMPTY;
import static datango.ui.data.TestDataProvider.SERVICE_OU;
import static datango.ui.enums.AssertMessages.MSG_CODE_DELETED;
import static datango.ui.enums.AssertMessages.MSG_ENTITY_CREATED;
import static datango.ui.enums.AssertMessages.MSG_ENTITY_DELETED;
import static datango.ui.enums.AssertMessages.MSG_ENTITY_EDITED;
import static datango.ui.enums.AssertMessages.MSG_ENTITY_NOT_CREATED;
import static datango.ui.enums.AssertMessages.MSG_ENTITY_NOT_EDITED;
import static datango.ui.enums.AssertMessages.MSG_IS_ERROR_MESSAGE;
import static datango.ui.enums.FormFields.NAME;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import datango.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

/**
 * @author Sergey Kuzhel
 */
@Slf4j
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Organizational view/Organization units] automation test cases")
public class OrgUnitsTest extends BaseTest {
    private String ouName;
    private String regCode;
    private String ouNameEdit;
    private String filterName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoUserAdministration();
    }
    
    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        ouName = createName();
        filterName = RandomUtils.getRandomString(8, ENGLISH_ALPHABET, true);
        ouNameEdit = ouName + TEST_EDIT;
        regCode = createId();
        orgViewPage.clickOU(BASE_OU);
    }

    @TestCaseID(id = "4796")
    @Test(description = "Verify creating new Organizational Unit (incorrect input)", groups = "smoke-test")
    @TmsLink("4796")
    public void ouAddIncorrectTest() {
        orgViewPage.selectAddOU();
        orgViewPage.inputEntityOuAddName(TEST_EMPTY);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4797")
    @Test(description = "Verify creating new Organizational Unit (existing name)", groups = "smoke-test")
    @TmsLink("4797")
    public void ouAddExistingTest() {
        orgViewPage.addOU(ouName, regCode);
        List<String> before = orgViewPage.getOuNames();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        List<String> after = orgViewPage.getOuNames();
        asserts.assertListSizeEquality(before, after, MSG_ENTITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "4795")
    @Test(description = "Verify creating new Organizational Unit (correct input - all fields)", groups = "smoke-test")
    @TmsLink("4795")
    public void ouAddCorrectTest() {
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        List<String> ouNames = orgViewPage.getOuNames();
        assertTrue(ouNames.contains(ouName), MSG_ENTITY_CREATED.getValue());
    }

    @TestCaseID(id = "5377")
    @Test(description = "Verify creating new Organizational Unit (correct input - mandatory fields)", groups = "smoke-test")
    @TmsLink("5377")
    public void ouAddCorrectMandatoryOnlyTest() {
        orgViewPage.addOU(ouName, TEST_EMPTY);
        orgViewPage.clickOU(ouName);
        List<String> ouNames = orgViewPage.getOuNames();
        assertTrue(ouNames.contains(ouName), MSG_ENTITY_CREATED.getValue());
    }

    @TestCaseID(id = "4798")
    @Test(description = "Verify creating new Organizational Unit (existing Reg code)", groups = "smoke-test")
    @TmsLink("4798")
    public void ouAddExistingRegCodeTest() {
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(BASE_OU);
        List<String> before = orgViewPage.getOuNames();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        browserManager.reloadPage();
        orgViewPage.clickOU(BASE_OU);
        List<String> after = orgViewPage.getOuNames();
        asserts.assertListSizeEquality(before, after, MSG_ENTITY_NOT_CREATED.getValue());
    }

    @TestCaseID(id = "3900")
    @Test(description = "Verify edit Organizational Unit (existing Reg code)", groups = "smoke-test")
    @TmsLink("3900")
    public void ouAddEditIncorrectRegCodeTest() {
        regCode = getRandomString(4, SPECIAL_CHARS, false);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickOkButton();
        orgViewPage.addOU(ouName, createId());
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditRegCode(regCode);
        orgViewPage.clickOuSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        
    }

    @TestCaseID(id = "4799")
    @Test(description = "Verify creating new Organizational Unit (blank mandatory field)", groups = "smoke-test")
    @TmsLink("4799")
    public void ouAddBlankMandatoryTest() {
        orgViewPage.addOU(TEST_EMPTY, TEST_EMPTY);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4800")
    @Test(description = "Verify editing existing Organizational Unit (correct input)", groups = "smoke-test")
    @TmsLink("4800")
    public void ouEditCorrectTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditName(ouNameEdit);
        orgViewPage.clickOuSaveButton();
        assertTrue(orgViewPage.getOuNames().contains(ouNameEdit), MSG_ENTITY_EDITED.getValue());
    }

    @TestCaseID(id = "4801")
    @Test(description = "Verify editing existing Organizational Unit (incorrect input)", groups = "regression-test")
    @TmsLink("4801")
    public void ouEditIncorrectTest() {
        regCode = "";
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditName(ouNameEdit);
        orgViewPage.clickOuSaveButton();
        orgViewPage.clickOU(BASE_OU);
        List<String> names = orgViewPage.getOuNames().stream()
                .filter(n -> n.equals(ouName))
                .collect(Collectors.toList());
        assertTrue(names.isEmpty(), MSG_ENTITY_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4802")
    @Test(description = "Verify editing existing Organizational Unit (blank mandatory input)", groups = "regression-test")
    @TmsLink("4802")
    public void ouEditBlankMandatoryTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditName(TEST_EMPTY);
        orgViewPage.clickOuSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_ENTITY_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4803")
    @Test(description = "Verify editing existing Organizational Unit (existing name)", groups = "regression-test")
    @TmsLink("4803")
    public void ouEditExistingNameTest() {
        orgViewPage.clickOU(BASE_OU);
        regCode = "";
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditName(ouName);
        orgViewPage.clickOuSaveButton();
        assertTrue(!orgViewPage.getOuNames().contains(ouNameEdit), MSG_ENTITY_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4806")
    @Test(description = "Verify deleting Registration-Code for Organizational Unit" , groups = "regression-test")
    @TmsLink("4806")
    public void ouDeleteRegCodeTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditRegCode(TEST_EMPTY);
        orgViewPage.clickOuSaveButton();
        orgViewPage.clickOU(ouName);
        assertTrue(orgViewPage.getRegCodeValue().isEmpty(), MSG_CODE_DELETED.getValue());
    }

    @TestCaseID(id = "4811")
    @Test(description = "Verify deleting existing Organizational Unit" , groups = "regression-test")
    @TmsLink("4811")
    public void ouDeleteOUTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.clickOuDeleteButton();
        dialogBox.clickApplyButton();
        assertTrue(!orgViewPage.getOuNames().contains(ouName), MSG_ENTITY_DELETED.getValue());
    }

    @TestCaseID(id = "4805")
    @Test(description = "Verify adding / changing to incorrect registration code to OU" , groups = "regression-test")
    @TmsLink("4805")
    public void addIncorrectRegCodeOUTest() {
        String invRegCode = getRandomString(4, SPECIAL_CHARS, false);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, invRegCode);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
        dialogBox.clickApplyButton();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditRegCode(invRegCode);
        orgViewPage.clickOuSaveButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4807")
    @Test(description = "Verify adding / changing manager (OU responsible) of existing OU" , groups = "regression-test")
    @TmsLink("4807")
    public void addChangeManagerToOUTest() {
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(ouName);
        String userOne = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(ouName);
        String userTwo = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(ouName);
        orgViewPage.clickResponsibleOuButton();
        orgViewPage.clickDetailOU(ouName);
        orgViewPage.clickDetailUser(userOne);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(orgViewPage.responsibleName().contains(userOne));
        orgViewPage.clickResponsibleOuButton();
        orgViewPage.clickDetailOU(ouName);
        orgViewPage.clickDetailUser(userTwo);
        orgViewPage.clickDetailsSaveButton();
        assertTrue(orgViewPage.responsibleName().contains(userTwo));
    }

    @TestCaseID(id = "11215")
    @TCStep(step = 1)
    @Test(description = "Verify adding html code to input fields (Organizational View OU)" , groups = "regression-test")
    @TmsLink("11215")
    public void createOuWithHtmlCodeTest() {
        orgViewPage.addOU(HTML_CODE, regCode);
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TCStep(step = 2)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (by Name)", groups = "regression-test")
    @TmsLink("4863")
    public void searchByNameTest() {
        assertTrue(orgViewPage.isEntityPresentInFilteredByNameResult(ouName));
    }

    @TCStep(step = 3)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (by OU)", groups = "regression-test")
    @TmsLink("4863")
    public void searchByOUTest() {
        assertTrue(orgViewPage.isEntityPresentInFilteredByOuResult(SERVICE_OU));
    }

    @TCStep(step = 4)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (by Type)", groups = "regression-test")
    @TmsLink("4863")
    public void searchByTypeTest() {
        assertTrue(orgViewPage.isFilteredByType());
    }

    @TCStep(step = 5)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (by Active)", groups = "regression-test")
    @TmsLink("4863")
    public void searchByActiveTest() {
        assertTrue(orgViewPage.isFilteredByActive());
    }

    @TCStep(step = 5)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (by Active)", groups = "regression-test")
    @TmsLink("4863")
    public void searchByDeactiveTest() {
        assertFalse(orgViewPage.isFilteredByDeactive());
    }

    @TCStep(step = 6)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (Combine two filters)", groups = "regression-test")
    @TmsLink("4863")
    public void searchByTwoFiltersTest() {
        assertTrue(orgViewPage.isFilteredByTwoFilter(ouName));
    }

    @TCStep(step = 7)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (save my filter)", groups = "regression-test")
    @TmsLink("4863")
    public void saveMyFilterTest() {
        assertTrue(orgViewPage.isMyFilterSaved(SERVICE_OU, filterName));
    }

    @TCStep(step = 8)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (update my filter)", groups = "regression-test")
    @TmsLink("4863")
    public void updateMyFilterTest() {
        assertTrue(orgViewPage.updateCustomFilter(SERVICE_OU, filterName));
    }

    @TCStep(step = 10)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (delete my filter)", groups = "regression-test")
    @TmsLink("4863")
    public void deleteMyFilterTest() {
        assertTrue(orgViewPage.deleteCustomFilter());
    }

    @TCStep(step = 9)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (apply my filter)", groups = "regression-test")
    @TmsLink("4863")
    public void applyMyFilterTest() {
        assertTrue(orgViewPage.isMyFilterApply(ouName, filterName));
    }

    @TCStep(step = 11)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (switch to advanced search)", groups = "regression-test")
    @TmsLink("4863")
    public void switchToAdvancedSearchTest() {
        assertTrue(orgViewPage.isSwitchedToAdvancedSearch());
    }

    @TCStep(step = 12)
    @TestCaseID(id = "4863")
    @Test(description = "Verify searching/filtering functionality for Users and Groups (clear search filter result)", groups = "regression-test")
    @TmsLink("4863")
    public void clearSearchFilterTest() {
        orgViewPage.addOU(ouName, regCode);
        assertTrue(orgViewPage.isSearchResultCleared(ouName));
    }

    @TestCaseID(id = "4808")
    @Test(description = "Verify removing manager (OU responsible) from an existing OU", groups = "regression-test")
    @TmsLink("4808")
    public void removeManagerOUTest() {
        orgViewPage.clickOU(BASE_OU);
        String name = orgViewPage.addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue()).toLowerCase();
        orgViewPage.clickOU(BASE_OU);
        assertFalse(orgViewPage.isRemovedManagerOU(name));
    }

    @TestCaseID(id = "4804")
    @Test(description = "Verify editing existing Organizational Unit (existing Registration-Code)", groups = "regression-test")
    @TmsLink("4804")
    public void editToExistRegCodeTest() {
        String localOuName = createName();
        String localRegCode = createId();
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(localOuName, localRegCode);
        orgViewPage.clickOU(localOuName);
        orgViewPage.inputEntityAddRegCode(localRegCode);
        orgViewPage.clickCreateButton();
        assertTrue(dialogBox.isErrorMessage(), MSG_IS_ERROR_MESSAGE.getValue());
    }

    @TestCaseID(id = "4809")
    @Test(description = "Verify 'Move to OU' functionality for OUs", groups = "regression-test")
    @TmsLink("4809")
    public void verifyMoveOuTest() {
        String localOuName = createName();
        String localRegCode = createId();
        orgViewPage.addOU(ouName, regCode);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(localOuName, localRegCode);
        orgViewPage.clickOU(localOuName);
        orgViewPage.clickMoveOUButton();
        orgViewPage.inputSearchDetailsOu(ouName);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.clickOU(localOuName);
        assertTrue(orgViewPage.isOuMovedOn(ouName, localOuName));
    }

    @TestCaseID(id = "4812")
    @Test(description = "Verify 'Show sub-elements from the tree' checkbox")
    @TmsLink("4812")
    public void verifyShowSubElementsCheckboxTest() {
        orgViewPage.clickOU(ROOT);
        String pagValue = orgViewPage.getPaginationValues();
        orgViewPage.clickSubElementsCheckbox();
        assertTrue(!orgViewPage.getPaginationValues().equals(pagValue));
    }
}

