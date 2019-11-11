package datango.ui.pages.collaborator.administration;

import datango.ui.enums.ElementType;
import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.EntityTree;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static datango.ui.enums.ElementAttribute.ARIA_SELECTED;
import static datango.ui.pages.collaborator.pageElements.EntityTree.ENTITY_TREE;

/**
 * @author Sergey Kuzhel
 */
public class WorkareasTagsPage extends BasePage {

    private static final String WA_NAME_INPUT = "workareastags.page.name.input";
    private static final String WA_ID_INPUT = "workareastags.page.id.input";
    private static final String WA_ADD = "workareastags.page.add.button";
    private static final String WA_NAMES = "workareastags.page.namecol.values";
    private static final String WA_TABLE_VALUES = "workareastags.page.table.values";
    private static final String WA_ACTIVATE = "workareastags.page.activate.button";
    private static final String IDENTITY_NAMES = "workareastags.permissions.idencol.values";
    private static final String IDENTITIES_LIST = "workareastags.permissions.table";
    private static final String IDENTITY_OK_BUTTON = "workareastags.permissions.identity.ok.button";
    private static final String PERM_TABLE = "workareastags.permissions.table.values";
    private static final String PERM_ADD = "workareastags.permissions.add.button";
    private static final String PERM_CANCEL = "workareastags.permissions.cancel.button";
    private static final String PERM_CANCEL_ICON = "workareastags.permissions.cancel.icon";
    private static final String PERM_SAVE = "workareastags.permissions.save.button";
    private static final String PERM_RESET = "workareastags.permissions.reset.button";
    private static final String PERM_SELECT = "workareastags.permissions.permission.select";
    private static final String TAG_NAME_INPUT = "workareastags.permissions.tagname.input";
    private static final String TAG_DESCRIPTION = "workareastags.permissions.tagdescr.input";
    private static final String TAG_ADD_ICON = "workareastags.permissions.tagadd.icon";
    private static final String TAG_NAME = "workareastags.permissions.tagnamecol.values";
    private static final String TAG_SAVE_ICON = "workareastags.permissions.tagsave.icon";
    private static final String TAG_DEL_ICON = "workareastags.permissions.tagdelete.icon";
    private static final String PERMISSION_LIST = "#waPermissionTable > tbody > tr > td:nth-child(2) > select";
    private static final String SEARCH_INPUT = "workareastags.search.input";
    private static final String SEARCH_BUTTON = "workareastags.search.button";
    private static final String SEARCH_RESULT_LIST = "workareastags.search.result.list";

    EntityTree entityTree;
    public WorkareasTagsPage(WebDriver driver) {
        super(driver);
        this.entityTree = new EntityTree(driver);
    }

    /**
     * Input workarea name
     *
     * @param name
     */
    public void inputWorkareaName(String name) {
        welActions.input(WA_NAME_INPUT, name);
    }

    /**
     * Input workarea id
     *
     * @param id
     */
    public void inputWorkareaId(String id) {
        welActions.input(WA_ID_INPUT, id);
    }

    /**
     * Click add workarea button
     */
    public void clickAddButton() {
        welActions.click(WA_ADD);
    }

    /**
     * Get names of all workarea
     * @return list
     */
    public List<String> getWorkareaNames() {
        driverManager.shortWaitForLoad();
        return welActions.getElementsText(WA_NAMES);
    }

    /**
     * Get identity names
     * @return list
     */
    public List<String> getIdentityNames() {
        driverManager.shortWaitForLoad();
        return welActions.getElementsText(IDENTITY_NAMES);
    }

    /**
     * Click on specified workarea on the page
     * @param name
     */
    public void clickOnWorkarea(String name) {
       welActions.click(WA_TABLE_VALUES, name, 0);
    }

    public boolean isWorkareaActive(String name) {
       return welActions.isClickable(welActions.getElement(WA_TABLE_VALUES, name, 1));
    }

    /**
     * Delete workareas
     */
    public void clickDeleteWorkareaIcon(String name) {
        welActions.click(WA_TABLE_VALUES, name, 3);
    }

    /**
     * Click on activate on the workarea
     */
    public void clickActivateWorkarea() {
        welActions.getElements(WA_ACTIVATE)
        .get(0).click();

    }

    /**
     * Add permission from the identity treenode
     */
    public void clickAddPermission() {
        welActions.click(PERM_ADD);
    }

    /**
     * Cancel permission changes
     */
    public void clickCancelButton() {
        welActions.click(PERM_CANCEL);
    }

    /**
     * Cancel permission changes
     */
    public void clickCancelIcon() {
        welActions.click(PERM_CANCEL_ICON);
    }

    /**
     * Click save button on the permission tab
     */
    public void clickSaveButton() {
        welActions.click(PERM_SAVE);
    }

    /**
     * Click reset button on the permission tab
     */
    public void clickResetButton() {
        welActions.click(PERM_RESET);

    }

    public void clickDeletePermission(String name) {
        welActions.click(PERM_TABLE, name, 4);
    }

    public void addDeletePermission(String name) {
        List<WebElement> elems = welActions.getElements(IDENTITIES_LIST);
        for (WebElement elem: elems) {
            if (elem.getText().contains(name)) {
                 welActions.select(elem.findElement(By.cssSelector(PERMISSION_LIST)), null, "delete");
            }
        }
    }

    public void clickViewPermission(String name) {
        List<WebElement> elems = welActions.getElements(IDENTITIES_LIST);
        for (WebElement elem: elems) {
            if (elem.getText().contains(name)) {
                 welActions.select(elem.findElement(By.cssSelector(PERMISSION_LIST)), null, "readOnly");
            }
        }
    }

    /**
     * Select test user identity
     */
    public void selectIdentity(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, EntityTree.USER);
        welActions.click(IDENTITY_OK_BUTTON);
    }

    public void unselectExistIdentity(String name) {
        if ("true".equals(welActions.getElement(ENTITY_TREE, name, 1).getAttribute(ARIA_SELECTED.getValue()))){
            welActions.click(IDENTITY_OK_BUTTON);
        } else {
            entityTree.clickEntity(ENTITY_TREE, name, EntityTree.USER);
            welActions.click(IDENTITY_OK_BUTTON);
        }
    }

    public void skipSelectIdentity(String name) {
        welActions.input(SEARCH_INPUT, name);
        welActions.click(SEARCH_BUTTON);
        welActions.getElements(SEARCH_RESULT_LIST).stream()
            .filter(element -> element.getText().contains(name))
            .filter(element -> !"true".equals(element.getAttribute(ARIA_SELECTED.getValue())))
            .forEach(WebElement::click);
        welActions.click(IDENTITY_OK_BUTTON);
    }

    public void selectExistIdentity(String name) {
        if (getIdentityNames().contains(name)) {
            clickDeletePermission(name);
            clickAddPermission();
            if ("true".equals(welActions.getElement(ENTITY_TREE, name, 1).getAttribute(ARIA_SELECTED.getValue()))){
                welActions.click(IDENTITY_OK_BUTTON);
            } else {
                entityTree.clickEntity(ENTITY_TREE, name, EntityTree.USER);
                welActions.click(IDENTITY_OK_BUTTON);

            }
        }
    }

    /**
     * Select permission for delete
     */
    public void selectDeletePermission() {
        welActions.select(PERM_SELECT, "delete");
    }

    public void selectViewPermission() {
        welActions.select(PERM_SELECT, "readOnly");
    }

    /**
     * Input workarea tag name
     * @param name
     */
    public void inputTagName(String name) {
        welActions.input(TAG_NAME_INPUT, name);
    }

    /**
     * Input workarea tag description
     * @param descr
     */
    public void inputTagDescription(String descr) {
        welActions.input(TAG_DESCRIPTION, descr);
    }

    /**
     * Click tag add icon
     */
    public void clickAddTagIcon() {
        welActions.click(TAG_ADD_ICON);
    }

    /**
     * Get tag names
     */
    public List<String> getTagNames() {
        return welActions.getElementsText(TAG_NAME);
    }

    /**
     * Click on the specified tag
     */
    public void clickTag(String name) {
        welActions.getElements(TAG_NAME).stream()
                .filter(e -> e.getText().equals(name))
                .collect(Collectors.toList())
                .get(0).click();
    }

    public void clickSaveTagIcon() {
        welActions.click(TAG_SAVE_ICON);
    }

    public void clickDeleteWorkareaTag(String name) {
        int index = getTagNames().indexOf(name);
        welActions.getElements(TAG_DEL_ICON)
                .get(index)
                .click();
    }

    public void addWorkareaTag(String name, String descr) {
        inputTagName(name);
        inputTagDescription(descr);
        clickAddTagIcon();
    }

    public void editWorkareaTag(String name, String newName) {
        clickTag(name);
        inputTagName(newName);
        inputTagDescription(newName + "descr");
        clickSaveTagIcon();
    }

    public void resetWorkareaPermissions() {
        clickResetButton();
    }

    public void cancelWorkareaPermissions(ElementType buttonType) {
        switch (buttonType) {
            case BUTTON:
                clickCancelButton();
                break;
            case ICON:
                clickCancelIcon();
                break;
            default:
                clickCancelButton();
                break;
        }
    }

    public String addNewWorkarea(String name, String id) {
        inputWorkareaName(name);
        inputWorkareaId(id);
        clickAddButton();
        return name;
    }

    public void openWorkareaPermissions() {
        String name = getWorkareaNames().get(0);
        clickOnWorkarea(name);
    }

    public void addWorkareaPermissionsForUser(String name, boolean viaSearch) {
        if (!viaSearch) {
            clickAddPermission();
            selectIdentity(name);
        } else {
            clickAddPermission();
            skipSelectIdentity(name);
        }
    }

    public void addWorkareaPermissionsForUserIfSelected(String name) {
        clickAddPermission();
        skipSelectIdentity(name);
    }

    public void unremoveSelectedPermForUser(String name) {
        clickAddPermission();
        unselectExistIdentity(name);
    }

    public void deleteWorkarea(String name) {
        clickOnWorkarea(name);
        selectDeletePermission();
        clickSaveButton();
        clickDeleteWorkareaIcon(name);
    }

    public void viewWorkarea(String name) {
        clickOnWorkarea(name);
        selectViewPermission();
        clickSaveButton();
    }
}
