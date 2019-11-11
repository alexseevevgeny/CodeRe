package datango.ui.administration.registrationCodes;

import datango.BaseTest;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static datango.ui.enums.AssertMessages.MSG_REGCODE_IS_EDITED;
import static datango.ui.enums.AssertMessages.MSG_REGCODE_IS_PRESENT;
import static org.testng.Assert.assertTrue;

@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Registration codes] automation test cases")
public class RegistrationCodeTest extends BaseTest {
    private String ouName;
    private String regCodeOU;
    private String regCode;
    private String regCodeGroup;
    private String roleGroup;

    @BeforeClass
    public void setUpClass(){
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        ouName = createName();
        regCodeOU = createId();
        regCodeGroup = createId();
        roleGroup = createName() + " group";
        regCode = regCodeOU + " - " + regCodeGroup;
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        collaborator.gotoUserAdministration();
    }

    @TestCaseID(id = "4880")
    @Test(description = "Verify Registration-Codes mapping (OU - role)" ,groups = "smoke-test")
    @TmsLink("4880")
    public void verifyRegCodesTest(){
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addOU(ouName, regCodeOU);
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.addRole(roleGroup, regCodeGroup);
        collaborator.gotoRegistrationCodes();
        assertTrue(registrationCodes.getRegCodeByOuName(ouName).contains(regCode), MSG_REGCODE_IS_PRESENT.getValue());
    }

    @TestCaseID(id = "4881")
    @Test(description = "Verify Registration-Codes are changed when OU and role is edited" ,groups = "smoke-test", dependsOnMethods = "verifyRegCodesTest")
    @TmsLink("4881")
    public void verifyRegCodesEditTest(){
        String editRegCodeOu = createId();
        String editRegCodeRole = createId();
        orgViewPage.searchByName(roleGroup);
        orgViewPage.clickEntity(roleGroup);
        orgViewPage.inputDeatilsRegCode(editRegCodeRole);
        orgViewPage.clickDetailsSaveButton();
        orgViewPage.clickDetailsCloseIcon();
        orgViewPage.clickOU(BASE_OU);
        orgViewPage.clickOU(ouName);
        orgViewPage.inputEntityEditRegCode(editRegCodeOu);
        orgViewPage.clickOuSaveButton();
        collaborator.gotoRegistrationCodes();
        assertTrue(registrationCodes.getRegCodeByOuName(ouName)
            .contains(editRegCodeOu + " - " + editRegCodeRole), MSG_REGCODE_IS_EDITED.getValue());
    }
}
