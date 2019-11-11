package datango.ui.workarea;

import datango.BaseTest;
import datango.api.requests.Permission;
import datango.api.requests.User;
import datango.api.requests.WaReq;
import datango.api.utils.GetterCollector;
import datango.testrail.TestCaseID;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static datango.ui.data.InputValuesProvider.TEST_EDIT;
import static datango.ui.data.InputValuesProvider.TEST_EMPTY;
import static datango.ui.enums.AssertMessages.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.USER;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Workarea] automation test cases")
public class WorkareaTest extends BaseTest {
    private static final String VERSION = "1";
    private String storageName;
    private String entityName;
    private String folderName;
    private String groupName;
    private String projectName;
    private String bookName;
    private String slideName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        collaborator.gotoWorkareaAdministration();
        storageName = workareaAdmPage.addNewWorkarea(createName(), createId());
        mainMenu.clickWorkarea(storageName);
        if (!collaborator.getHomePage().isPageLoaded()) {
            mainMenu.clickWorkarea(storageName);
        }
    }

    @BeforeMethod
    public void setUpMethod() {
        browserManager.reloadPage();
        entityName = createName();
        folderName = entityName + " folder";
        groupName = entityName + " group";
        projectName = entityName + " project";
        bookName = entityName + " book";
        slideName = entityName + " book page";
        workareaPage.getStorageTree().clickContentTab();
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickFinishEditingAll();
    }

    @AfterClass
    public void tearDownClass() {
        WaReq waReq = new WaReq();
        User userReq = new User();
        Permission perReq = new Permission();
        GetterCollector get = new GetterCollector();
        String userId = get.getUserId(userReq.getSelfInfo()).getUserId();
        get.getWaIds(waReq.getWaList()).forEach(waId -> perReq.addDeletePermForUser(userId, waId));
        get.getWaIds(waReq.getWaList()).forEach(waReq::deleteWa);
    }

	@TestCaseID(id = "")
    @Test(description = "Verify workarea entity acquiring ", groups = "regression-test")
    @TmsLink("")
    public void verifyEditPossibilityTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        assertTrue(workareaPage.getStorageContent().isAddOptionActive(), MSG_ADD_CONTENT.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new group", groups = "regression-test", enabled = false)
    @TmsLink("4759")
    public void addWaGroupTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addGroup(groupName);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(groupName), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new child group", groups = "regression-test", enabled = false)
    @TmsLink("4750")
    public void addWaChildGroupTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addGroup(groupName);
        workareaPage.getStorageTree().clickGroup(groupName);
        String subFolder = createName();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addGroup(subFolder);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(subFolder), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new project", groups = "regression-test", enabled = false)
    @TmsLink("4750")
    public void addWaProjectTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addProject(entityName);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(entityName), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new book", groups = "regression-test", enabled = false)
    @TmsLink("4750")
    public void addWaBookTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addBook(bookName);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(bookName), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new book page", groups = "regression-test", enabled = false)
    @TmsLink("4750")
    public void addWaSlideTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addSlide(slideName);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(slideName), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify edit group", groups = "regression-test", enabled = false)
    @TmsLink("3898")
    public void editWaGroupTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().addGroup(groupName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickGroup(groupName);
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().clickOverviewTab();
        String newCaption = groupName + TEST_EDIT;
        workareaPage.getStorageContent().inputCaption(newCaption);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(newCaption), MSG_ENTITY_EDITED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify edit project", groups = "regression-test", enabled = false)
    @TmsLink("3898")
    public void editWaProjectTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addProject(projectName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().clickOverviewTab();
        String newCaption = projectName + TEST_EDIT;
        workareaPage.getStorageContent().inputCaption(newCaption);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(newCaption), MSG_ENTITY_EDITED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify edit book", groups = "regression-test", enabled = false)
    @TmsLink("3898")
    public void editWaBookTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addBook(bookName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickBook(bookName);
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().clickOverviewTab();
        String newCaption = bookName + TEST_EDIT;
        workareaPage.getStorageContent().inputCaption(newCaption);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(newCaption), MSG_ENTITY_EDITED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify edit book page", groups = "regression-test", enabled = false)
    @TmsLink("3898")
    public void editWaSlideTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addSlide(slideName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickSlide(slideName);
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().clickOverviewTab();
        String newCaption = slideName + TEST_EDIT;
        workareaPage.getStorageContent().inputCaption(newCaption);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(newCaption), MSG_ENTITY_EDITED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content (WT for parent relinquished)", groups = "regression-test", enabled = false)
    @TmsLink("3896")
    public void deleteReleasedEntityTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addSlide(slideName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickSlide(slideName);
        workareaPage.getStorageContent().clickDelete();
        assertTrue(dialogBox.isErrorMessage(), MSG_NOT_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content (WT for parent acquired)", groups = "regression-test", enabled = false)
    @TmsLink("4734")
    public void deleteAcquiredEntityTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addBook(bookName);
        workareaPage.getStorageTree().clickBook(bookName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        assertTrue(!workareaPage.getStorageTree().getEntityNames().contains(bookName), MSG_ENTITY_DELETED.getValue());
        workareaPage.getStorageTree().clickTrashTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(bookName), MSG_ENTITY_TRASH.getValue());

    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content from 'Trash' (single entity) ", groups = "regression-test", enabled = false)
    @TmsLink("4736")
    public void deleteFromTrashSingleTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addProject(projectName);
        workareaPage.getStorageContent().addBook(bookName);
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickDeleteFinally();
        dialogBox.clickApplyButton();
        assertTrue(!workareaPage.getStorageTree().getEntityNames().contains(projectName), MSG_ENTITY_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content from 'Trash' (Empty Trash)", groups = "regression-test", enabled = false)
    @TmsLink("4737")
    public void deleteAllFromTrashTest() {
        List<String> delnames = new ArrayList<>();
        delnames.add(projectName);
        delnames.add(bookName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addProject(projectName);
        workareaPage.getStorageContent().addBook(bookName);
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickBook(bookName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickEmptyTrash();
        dialogBox.clickApplyButton();
        delnames.removeAll(workareaPage.getStorageTree().getEntityNames());
        assertTrue(!delnames.isEmpty(), MSG_ENTITY_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify restoring content from 'Trash' ", groups = "regression-test", enabled = false)
    @TmsLink("4738")
    public void restoreFromTrashTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addProject(projectName);
        workareaPage.getStorageContent().addBook(bookName);
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickBook(bookName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickProject(projectName);
        workareaPage.getStorageContent().clickRestore();
        workareaPage.getStorageTree().clickUnreferencedTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(projectName), MSG_ENTITY_DELETED.getValue());
    }

	@TestCaseID(id = "3894")
    @Test(description = "Verify workflow tab edit", groups = "regression-test")
    @TmsLink("3894")
    public void verifyWorkflowTabEditTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickWorkflowTab();
        workareaPage.getStorageContent().selectWorkflowValues();
        workareaPage.getStorageContent().saveWorkflowTab();
        assertTrue(!workareaPage.getStorageContent().getWorkwlowValues().contains(TEST_EMPTY), MSG_ENTITY_EDITED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify courses tab edit", groups = "regression-test")
    @TmsLink("")
    public void verifyCourseTabEditTest() {
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(storageName, createId());
        mainMenu.clickWorkarea(storageName);
        collaborator.gotoWorkareaPage();
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickPublishAll();
        workareaPage.getStorageContent().clickCoursesTab();
        String course = courseAssignmentsPage.addMandatoryAssignment(learner, USER, storageName, false);
        collaborator.gotoCourseAssignments();
        assertTrue(courseAssignmentsPage.getAssignmentsNames().contains(course), MSG_ENTITY_EDITED.getValue());
        mainMenu.clickWorkarea(storageName);
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting objects after renaming others", groups = "regression-test", enabled = false)
    @TmsLink("12569")
    public void deleteObjectAfterRenameTest() {
        String editedGroupName = createName() + " group";
        String groupNameTwo = createName() + " group";
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addGroup(groupName);
        workareaPage.getStorageContent().addGroup(groupNameTwo);
        workareaPage.getStorageTree().clickGroup(groupName);
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().inputCaption(editedGroupName);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        workareaPage.getStorageTree().clickGroup(groupNameTwo);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(editedGroupName));
        workareaPage.getStorageTree().clickTrashTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(groupNameTwo));
    }

	@TestCaseID(id = "")
    @Test(description = "Verify the unpublished content is not displayed for View published (scenario)", groups = "regression-test", enabled = false)
    @TmsLink("13030")
    public void viewUnpublishedContentTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addGroup(groupName);
        workareaPage.getStorageTree().clickGroup(groupName);
        workareaPage.getStorageContent().clickPublishEntity();
        workareaPage.getStorageTree().clickWorkareaRoot();
        assertTrue(workareaPage.getStorageContent().getVersionStatus().contains("Unpublished"));
        workareaPage.getStorageContent().openVersionModal();
        workareaPage.getStorageContent().clickOnShowVersionButton(VERSION);
        assertTrue(workareaPage.getStorageContent().getVersionStatus().contains("Published"));
        workareaPage.getStorageContent().clickViewPublished();
        workareaPage.getStorageTree().clickWorkareaRoot();
        System.out.println(workareaPage.getStorageTree().getEntityNames());
        assertFalse(workareaPage.getStorageTree().getEntityNames().contains(storageName));
        workareaPage.getStorageTree().clickUnreferencedTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(groupName));
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new folder", groups = "regression-test")
    @TmsLink("")
    public void addNewFolderTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(folderName), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify edit folder", groups = "regression-test")
    @TmsLink("")
    public void editFolderTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickFolder(groupName);
        workareaPage.getStorageContent().clickStartEditingAll();
        workareaPage.getStorageContent().clickOverviewTab();
        String newCaption = folderName + TEST_EDIT;
        workareaPage.getStorageContent().inputCaption(newCaption);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(newCaption), MSG_ENTITY_EDITED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify add new child folder", groups = "regression-test")
    @TmsLink("")
    public void addChildFolderTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageTree().clickFolder(folderName);
        String subFolder = createName();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(subFolder);
        workareaPage.getStorageContent().clickFinishEditing();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(subFolder), MSG_ENTITY_CREATED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content (WT for parent relinquished)", groups = "regression-test")
    @TmsLink("")
    public void deleteReleasedFolderEntityTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageContent().clickFinishEditing();
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickDelete();
        assertTrue(dialogBox.isErrorMessage(), MSG_NOT_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content (WT for parent acquired)", groups = "regression-test")
    @TmsLink("")
    public void deleteAcquiredFolderEntityTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        assertTrue(!workareaPage.getStorageTree().getEntityNames().contains(folderName), MSG_ENTITY_DELETED.getValue());
        workareaPage.getStorageTree().clickTrashTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(folderName), MSG_ENTITY_TRASH.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content from 'Trash' (single entity) ", groups = "regression-test")
    @TmsLink("")
    public void deleteFromTrashSingleFolderTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickDeleteFinally();
        dialogBox.clickApplyButton();
        assertTrue(!workareaPage.getStorageTree().getEntityNames().contains(folderName), MSG_ENTITY_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting content from 'Trash' (Empty Trash)", groups = "regression-test")
    @TmsLink("")
    public void deleteAllEntitiesFromTrashTest() {
        List<String> delnames = new ArrayList<>();
        delnames.add(folderName);
        delnames.add(groupName);
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageContent().addFolder(groupName);
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickFolder(groupName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickEmptyTrash();
        dialogBox.clickApplyButton();
        delnames.removeAll(workareaPage.getStorageTree().getEntityNames());
        assertTrue(!delnames.isEmpty(), MSG_ENTITY_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify restoring content from 'Trash' ", groups = "regression-test")
    @TmsLink("")
    public void restoreFromTrashFolderTest() {
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        workareaPage.getStorageTree().clickTrashTab();
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickRestore();
        workareaPage.getStorageTree().selectRestoredEntity(storageName);
        workareaPage.getStorageTree().clickSelectRestoreButton();
        workareaPage.getStorageTree().clickContentTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(folderName), MSG_ENTITY_DELETED.getValue());
    }

	@TestCaseID(id = "")
    @Test(description = "Verify deleting objects after renaming others", groups = "regression-test")
    @TmsLink("")
    public void deleteObjectAfterRenameFolderTest() {
        String editedFoldeName = createName() + " folder";
        String folderNameTwo = createName() + " folder";
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageContent().addFolder(folderNameTwo);
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().inputCaption(editedFoldeName);
        workareaPage.getStorageContent().clickSaveOverView();
        workareaPage.getStorageContent().clickFinishEditingAll();
        workareaPage.getStorageTree().clickFolder(folderNameTwo);
        workareaPage.getStorageContent().clickDelete();
        dialogBox.clickApplyButton();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(editedFoldeName));
        workareaPage.getStorageTree().clickTrashTab();
        assertTrue(workareaPage.getStorageTree().getEntityNames().contains(folderNameTwo));
    }

	@TestCaseID(id = "")
    @Test(description = "Verify the unpublished content is not displayed for View published (scenario)", groups = "regression-test")
    @TmsLink("")
    public void viewUnpublishedContentFolderTest() {
        collaborator.gotoWorkareaAdministration();
        String unpublishWa = workareaAdmPage.addNewWorkarea(createName(), createId());
        mainMenu.clickWorkarea(unpublishWa);
        workareaPage.getStorageTree().clickContentTab();
        workareaPage.getStorageTree().clickWorkareaRoot();
        workareaPage.getStorageContent().clickStartEditing();
        workareaPage.getStorageContent().addFolder(folderName);
        workareaPage.getStorageTree().clickFolder(folderName);
        workareaPage.getStorageContent().clickPublishEntity();
        workareaPage.getStorageTree().clickWorkareaRoot();
        assertTrue(workareaPage.getStorageContent().getVersionStatus().contains("Unpublished"));
        workareaPage.getStorageContent().openVersionModal();
        workareaPage.getStorageContent().clickOnShowVersionButton(VERSION);
        assertTrue(workareaPage.getStorageContent().getVersionStatus().contains("Published"));
        workareaPage.getStorageContent().clickViewPublished();
        workareaPage.getStorageTree().clickWorkareaRoot();
        assertFalse(workareaPage.getStorageTree().getEntityNames().contains(storageName));
        workareaPage.getStorageTree().clickContentTab();
        assertFalse(workareaPage.getStorageTree().getEntityNames().contains(folderName));
    }
}
