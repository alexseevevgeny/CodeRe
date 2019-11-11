package datango.ui.administration.workarea;

import datango.BaseTest;
import datango.testrail.TestCaseID;
import datango.utils.RandomUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.enums.ElementType.BUTTON;
import static datango.ui.enums.FormFields.NAME;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workareas/Tags] automation test cases")
public class WorkareaPermissonsTest extends BaseTest {

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(RandomUtils.getRandomString(7, ENGLISH_ALPHABET, true),
                RandomUtils.getRandomString(4, NUMERIC, false));
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
    }

    @TestCaseID(id = "4907")
    @Test(description = "Verify adding permissions to a Workarea ", groups = "regression-test")
    @TmsLink("4907")
    public void addPermissionForWorkareaTest() {
        workareaAdmPage.openWorkareaPermissions();
        List<String> identityNames = workareaAdmPage.getIdentityNames();
        workareaAdmPage.addWorkareaPermissionsForUser(learner, false);
        asserts.assertListSizeInequality(workareaAdmPage.getIdentityNames(),identityNames, "permission for identity added");
        workareaAdmPage.cancelWorkareaPermissions(BUTTON);
    }

    @TestCaseID(id = "4908")
    @Test(description = "Verify removing permissions from a Workarea", groups = "regression-test")
    @TmsLink("4908")
    public void removePermissionForWorkareaTest() {
        workareaAdmPage.openWorkareaPermissions();
        List<String> identityNames = workareaAdmPage.getIdentityNames();
        workareaAdmPage.addWorkareaPermissionsForUser(learner, false);
        workareaAdmPage.clickDeletePermission(identityNames.get(0));
        asserts.assertListSizeEquality(workareaAdmPage.getIdentityNames(), identityNames,  "permission for identity removed");
        workareaAdmPage.cancelWorkareaPermissions(BUTTON);
    }

    @TestCaseID(id = "4909")
    @Test(description = "Verify 'Reset' button functionality", groups = "regression-test")
    @TmsLink("4909")
    public void resetPermissionForWorkareaTest() {
        workareaAdmPage.openWorkareaPermissions();
        List<String> identityNames = workareaAdmPage.getIdentityNames();
        workareaAdmPage.addWorkareaPermissionsForUser(learner, false);
        workareaAdmPage.resetWorkareaPermissions();
        dialogBox.clickApplyButton();
        asserts.assertListSizeEquality(workareaAdmPage.getIdentityNames(), identityNames, "permission for identity removed");
    }

    @TestCaseID(id = "22224")
    @Test(description = "Verify adding identity to Workarea permissions", groups = "regression-test")
    @TmsLink("22224")
    public void addIdentityForWorkareaTest() {
        collaborator.gotoUserAdministration();
        orgViewPage.clickOU(BASE_OU);
        String userName = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        orgViewPage.clickOU(BASE_OU);
        String userNameTwo = orgViewPage
            .addUser(testDataProvider.getRandomUserInfo()).get(NAME.getValue());
        collaborator.gotoWorkareaAdministration();
        String nameWa = RandomUtils.getRandomString(7, ENGLISH_ALPHABET, true);
        workareaAdmPage.addNewWorkarea(nameWa, RandomUtils.getRandomString(5, NUMERIC, false));
        workareaAdmPage.clickOnWorkarea(nameWa);
        List<String> identityNames = workareaAdmPage.getIdentityNames();
        workareaAdmPage.addWorkareaPermissionsForUser(userName, true);
        workareaAdmPage.addWorkareaPermissionsForUser(userNameTwo, true);
        asserts.assertListSizeInequality(workareaAdmPage.getIdentityNames(),identityNames, "permission for identity added");
        workareaAdmPage.cancelWorkareaPermissions(BUTTON);
    }
}
