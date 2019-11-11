package datango.ui.administration.permissions;

import datango.BaseTest;
import datango.testrail.TestCaseID;
import datango.utils.RandomUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static datango.config.AppConfigProvider.get;
import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.ENTITY_TREE;
import static datango.ui.pages.collaborator.pageElements.EntityTree.GROUP;
import static datango.utils.RandomUtils.getRandomIntBetween;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Permissions] automation test cases")
public class PermissionsTest extends BaseTest {
    private static final String testGroup = get().defGroupName();
    private String searchKey;
    private List<String> expectedList = new ArrayList<>();
    private List<String> actualList = new ArrayList<>();

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoPermissionsAdministration();

    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        collaborator.gotoPermissionsAdministration();
    }

    @TestCaseID(id = "4621")
    @Test(description = "Verify adding permission(s) to entity", groups = "smoke-test")
    @TmsLink("4621")
    public void permissionsAddToEntityTest() {
        permissionsPage.getEntityTree().clickEntity(ENTITY_TREE, testGroup, GROUP);
        permissionsPage.clickEditPermissions();
        permissionsPage.checkPermissions(getRandomIntBetween(1,9));
        permissionsPage.clickSave();
        permissionsPage.clickEditPermissions();
        List<String> checked = permissionsPage.checkPermissions(4);
        permissionsPage.clickSave();
        List<String> current = permissionsPage.getCurrentPermissions();
        checked.removeAll(current);
        assertTrue(checked.isEmpty(), MSG_PERMISSION_ADDED.getValue());
    }

    @TestCaseID(id = "4622")
    @Test(description = "Verify removing permission(s) from entity", groups = "smoke-test")
    @TmsLink("4622")
    public void permissionsRemoveFromEntityTest() {
        permissionsPage.searchEntity(testGroup);
        permissionsPage.clickEditPermissions();
        permissionsPage.checkPermissions(getRandomIntBetween(1,5));
        permissionsPage.clickSave();
        browserManager.reloadPage();
        permissionsPage.searchEntity(testGroup);
        permissionsPage.clickEditPermissions();
        permissionsPage.unCheckPermissions(getRandomIntBetween(1,5));
        permissionsPage.clickSave();
        assertTrue(!permissionsPage.getCurrentPermissions().isEmpty(), MSG_PERMISSION_REMOVED.getValue());
    }

    @TestCaseID(id = "4623")
    @Test(description = "Verify that inherited permission(s) can't be removed from an entity", groups = "regression-test")
    @TmsLink("4623")
    public void permissionsInheritanceTest() {
        permissionsPage.searchEntity(testGroup);
        permissionsPage.clickEditPermissions();
        permissionsPage.clearPermissions();
        permissionsPage.clickSave();
        assertTrue(!permissionsPage.getCurrentPermissions().isEmpty(), MSG_ENTITY_NOT_EDITED.getValue());
    }

    @TestCaseID(id = "4624")
    @Test(description = "Verify searching functionality for Permissions", groups = "regression-test")
    @TmsLink("4624")
    public void permissionOverlappingSearchTest() {
        searchKey = "adt";
        permissionsPage.inputSearchKey(searchKey);
        permissionsPage.clickSearchButton();
        assertTrue(permissionsPage.isAllResultContainsSearchKey(searchKey), MSG_PERMISSIONS_FILTERED_BY_SEARCH_KEY.getValue());
    }

    @TestCaseID(id = "4624")
    @Test(description = "Verify searching functionality for Permissions", groups = "regression-test")
    @TmsLink("4624")
    public void permissionClearSearchTest() {
        permissionsPage.clickExpander();
        expectedList = permissionsPage.initialEntitiesList();
        searchKey = RandomUtils.getRandomString(1, ENGLISH_ALPHABET, true);
        permissionsPage.inputSearchKey(searchKey);
        permissionsPage.clickSearchButton();
        permissionsPage.clickClearSearchField();
        assertTrue(permissionsPage.getSearchFieldInputValue().isEmpty());
        actualList = expectedList = permissionsPage.initialEntitiesList();
        assertTrue(actualList.containsAll(expectedList), MSG_SEARCH_FIELD_IS_EMPTY.getValue());
    }
}