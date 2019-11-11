package datango.ui.login;

import datango.BaseTest;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Login] automation test cases")
public class LogoutTest extends BaseTest {

    @TestCaseID(id = "4857")
    @Test(description = "Verification of Logout", groups = "smoke-test")
    @TmsLink("4857")
    public void logoutTest() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.performLogOut();
        assertTrue(collaborator.isLoginButtonPresent(), "Logout from collaborator successful");
    }
}
