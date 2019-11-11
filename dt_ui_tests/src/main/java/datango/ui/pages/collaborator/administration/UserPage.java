
package datango.ui.pages.collaborator.administration;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.EntityTree;
import datango.utils.RandomUtils;
import datango.utils.WaitUtil;
import datango.utils.XlsReaderUtil;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.data.InputValuesProvider.TEST_EMPTY;
import static datango.ui.enums.AttributeValues.DETAIL_GROUP_CLASS;
import static datango.ui.enums.ElementAttribute.*;
import static datango.ui.enums.FormFields.*;
import static datango.ui.pages.collaborator.pageElements.EntityTree.*;
import static datango.utils.RandomUtils.getRandomIntBetween;


/**
 * @author Sergey Kuzhel
 */
public class UserPage extends BasePage {
    private static final String OPTION_OU = "Ou";
    private static final String OPTION_USER = "User";
    private static final String OPTION_GROUP = "Group";

    private static final String OU_TABLE = "user.page.outable.values";
    private static final String OU_TABLE_NAME_COL = "user.outable.namecol.values";
    private static final String OU_TABLE_TYPE_COL = "user.outable.typecol.values";
    private static final String OU_TABLE_ACTIVE_COL = "user.outable.activecol.values";
    private static final String ADD_OPTION_SELECT = "user.page.add.select";
    private static final String SEARCH_NAME_INPUT = "user.page.search.name.input";
    private static final String OU_ADD_NAME_INPUT = "user.ou.addname.input";
    private static final String OU_ADD_REG_CODE_INPUT = "user.ou.addregcode.input";
    private static final String CREATE_BUTTON = "user.page.create.button";
    private static final String EDIT_NAME_INPUT = "user.page.editname.input";
    private static final String EDIT_REG_CODE_INPUT = "user.page.editregcode.input";
    private static final String OU_SAVE_BUTTON = "user.page.save.button";
    private static final String OU_DELETE_BUTTON = "user.page.delete.button";
    private static final String ROLE_ADD_NAME_INPUT = "user.role.addname.input";
    private static final String ROLE_ADD_REGCODE_INPUT = "user.role.addregcode.input";
    private static final String DETAILS_TABLE = "user.details.table.values";
    private static final String DETAILS_GROUPNAME_INPUT = "user.details.groupname.input";
    private static final String DETAILS_GROUP_BUTTON = "user.details.group.button";
    private static final String DETAILS_LOGIN_INPUT = "user.details.login.input";
    private static final String DETAILS_LAST_NAME = "user.details.uselastname.input";
    private static final String DETAILS_USERNAME_INPUT = "user.details.username.input";
    private static final String DETAILS_GROUP_MEMBER_LIST = "user.details.groupmember.list";
    private static final String DETAILS_MIDDLENAME_INPUT = "user.details.middlename.input";
    private static final String DETAILS_REGCODE_INPUT = "user.details.regcode.input";
    private static final String DETAILS_DELETE_ICON = "user.details.delete.icon";
    private static final String DETAILS_EXPORT_ICON = "user.details.group.export.icon";
    private static final String DETAILS_SAVE_BUTTON = "user.details.save.button";
    private static final String DETAILS_CLEAR_BUTTON = "user.details.clear.button";
    private static final String DETAILS_CLOSE_ICON = "user.details.close.icon";
    private static final String DETAILS_ACTIVATE_INPUT = "user.details.activate.input";
    private static final String DROPDOWN_MORE = "user.page.more.button";
    private static final String DROPDOWN_MORE_ACTIVATE = "user.more.activate.link";
    private static final String DROPDOWN_MORE_DEACTIVATE = "user.more.deactivate.link";
    private static final String DROPDOWN_MORE_DELETE_GROUP = "user.more.delete.group.link";
    private static final String USER_ADD_LOGIN_INPUT = "user.user.addlogin.input";
    private static final String USER_ADD_NAME_INPUT = "user.user.addname.input";
    private static final String USER_ADD_LNAME_INPUT = "user.user.addlastname.input";
    private static final String USER_ADD_EMAIL_INPUT = "user.user.addemail.input";
    private static final String USER_ADD_PASS_INPUT = "user.user.addpass.input";
    private static final String DETAIL_EDIT_PASS_INPUT = "user.user.newpass.input";
    private static final String DETAIL_CONF_PASS_INPUT = "user.user.confpass.input";
    private static final String DETAIL_LANGUAGE_SELECT = "user.user.interface.selector";
    private static final String OU_MORE_DROPDOWN_BUTTON = "user.more.dropdown.button";
    private static final String TOGGLE_BUTTON = "user.toggle.addremove.button";
    private static final String MOVE_TO_OU_BUTTON = "user.movetoou.button";
    private static final String ADD_TO_GROUP_BUTTON = "user.addtogroup.button";
    private static final String REMOVE_FROM_GROUP_BUTTON = "user.removefromgroup.button";
    private static final String OU_TREE_LIST = "user.details.ou.tree";
    private static final String USER_DETAIL_SEARCH_BUTTON = "user.details.search.button";
    private static final String DETAIL_USER_OU_LABEL = "user.details.ou.manager.label.name";
    private static final String DETAIL_REMOVE_SEARCH_BUTTON = "user.details.search.remove.button";
    private static final String DETAIL_SEARCH_INPUT = "user.details.search.input";
    private static final String DETAIL_SEARCH_OU_BUTTON = "user.details.searchOU.button";
    private static final String DETAIL_SEARCH_OUMANAGER_SEARCH = "user.details.searchOU.input";
    private static final String DETAIL_SEARCH_REMOVE_INPUT = "user.details.searchtoremove.input";
    private static final String DETAIL_GROUP_ENTITIES_LIST = "user.details.group.related.entities.list";
    private static final String DETAIL_CHECKBOX_SELECT = "user.details.checkbox";
    private static final String DETAIL_CHECKBOX_SELECT_PARENT = "user.details.checkbox.parent";
    private static final String DETAIL_GROUP_HEADER = "user.details.header";
    private static final String DETAILS_TABLE_CHILD_ENTITIES = "user.details.group.table.list";
    private static final String SELECT_OU_RESPONSIBLE_BUTTON = "user.select.ou.manager.button";
    private static final String ENTITY_ADD_FORM = "user.add.entity.form";
    private static final String ADVANCED_SEARCH_SWITCH_BUTTON = "user.advanced.search.button";
    private static final String ADVANCED_SEARCH_INPUT = "user.advanced.search.input";
    private static final String SEARCH_BUTTON = "user.search.button";
    private static final String SEARCH_BY_NAME_INPUT = "user.search.name.input";
    private static final String SEARCH_BY_OU_INPUT = "user.search.ou.input";
    private static final String FILTER_DROPDOWN_LIST = "user.search.dropdown.filter.list";
    private static final String FILTER_TYPE_OPTIONS = "user.search.optional.tab.type";
    private static final String FILTER_ACTIVE_OPTIONS = "user.search.optional.tab.active";
    private static final String FILTER_ACTIVE_CHECKBOX = "user.outable.activecol.checkbox";
    private static final String TYPE_DROPDOWN = "user.search.select.type";
    private static final String TYPE_CHECKBOX_INPUT = "user.search.select.type.checkbox";
    private static final String TYPE_LABEL = "user.search.select.type.label";
    private static final String INPUT_FILTER_NAME = "user.modal.filter.name.input";
    private static final String SAVE_FILTER_BUTTON = "user.save.filter.button";
    private static final String SAVE_MODAL_BUTTON = "user.save.filter.ok";
    private static final String MY_FILTER_DROPDOWN = "user.myfilter.dropdown";
    private static final String MY_FILTER_LIST= "user.myfilter.list";
    private static final String TRASH_BUTTON= "user.trash.button";
    private static final String DELETE_FILTER_CONFIRM = "dialog.apply.button";
    private static final String SWITCH_TO_ADVANCED_SEARCH_BUTTON = "user.advanced.search.link";
    private static final String CLEAR_SEARCH_RESULT_BUTTON = "user.clear.filter.button";
    private static final String MOVE_OU_BUTTON = "user.page.moveou.button";
    private static final String DETAILS_SEARCH_OU_INPUT = "user.details.search.outomove";
    private static final String DETAILS_SEARCH_OU_BUTTON = "user.details.search.outomove.button";
    private static final String PAGINATION_VALUES = "user.pagination.value";
    private static final String SUB_ELEMENTS_CHECKBOX = "user.sub.element.checkbox";

    private EntityTree entityTree;
    private WebDriverWait wait;

    @Getter
    private XlsReaderUtil readXls;

    public UserPage(WebDriver driver) {
        super(driver);
        this.entityTree = new EntityTree(driver);
    }

    public void selectInterfaceLanguage(String language) {
        welActions.selectByText(DETAIL_LANGUAGE_SELECT, language);
    }

    public void selectAddOU() {
        welActions.select(ADD_OPTION_SELECT, OPTION_OU);
    }

    public void selectAddRole() {
        welActions.select(ADD_OPTION_SELECT, OPTION_GROUP);
    }

    public void selectAddUser() {
        welActions.select(ADD_OPTION_SELECT, OPTION_USER);
    }

    public void inputEntityOuAddName(String name) {
        welActions.input(OU_ADD_NAME_INPUT, name);
    }

    public void inputEntityRoleAddName(String name) {
        welActions.input(ROLE_ADD_NAME_INPUT, name);
    }

    public void inputEntityRoleAddRegCode(String name) {
        welActions.input(ROLE_ADD_REGCODE_INPUT, name);
    }

    public void inputEntityAddRegCode(String name) {
        welActions.input(OU_ADD_REG_CODE_INPUT, name);
    }

    public void clickResponsibleOuButton() {
        welActions.click(SELECT_OU_RESPONSIBLE_BUTTON);
    }

    public String responsibleName() {
        return welActions.getElement(SELECT_OU_RESPONSIBLE_BUTTON).getText();
    }

    public void inputEntityEditName(String name) {
        welActions.input(EDIT_NAME_INPUT, name);
    }

    public void inputEntityEditRegCode(String name) {
        welActions.input(EDIT_REG_CODE_INPUT, name);
    }

    public void inputDeatilsGroupName(String name) {
        welActions.input(DETAILS_GROUPNAME_INPUT, name);
    }

    public void inputDeatilsRegCode(String code) {
        welActions.input(DETAILS_REGCODE_INPUT, code);
    }

    public String getDetailsRegCode() {
        return welActions.getElement(DETAILS_REGCODE_INPUT).getText();
    }

    public void inputDetailsLogin(String login) {
        welActions.input(DETAILS_LOGIN_INPUT, login);
    }

    public void inputDetailsLastName(String login) {
        welActions.input(DETAILS_LAST_NAME, login);
    }

    public void inputDetailsUserName(String name) {
        welActions.input(DETAILS_USERNAME_INPUT, name);
    }

    public void inputDetailsMiddleName(String name) {
        welActions.input(DETAILS_MIDDLENAME_INPUT, name);
    }

    public void inputEntityUserAddLogin(String login) {
        welActions.input(USER_ADD_LOGIN_INPUT, login);
    }

    public void inputEntityUserAddName(String name) {
        welActions.input(USER_ADD_NAME_INPUT, name);
    }

    public void inputEntityUserAddLastName(String name) {
        welActions.input(USER_ADD_LNAME_INPUT, name);
    }

    public void inputEntityUserAddEmail(String email) {
        welActions.input(USER_ADD_EMAIL_INPUT, email);
    }

    public void inputEntityUserAddPassword(String pass) {
        welActions.input(USER_ADD_PASS_INPUT, pass);
    }

    public void clickOU(String name) {
        driverManager.shortWaitForLoad();
        entityTree.clickEntity(ENTITY_TREE, name, OU);
    }

    public void clickDetailOU(String name) {
        driverManager.shortWaitForLoad();
        entityTree.clickEntity(OU_TREE_LIST, name, OU);
    }

    public void clickDetailGroup(String name) {
        driverManager.shortWaitForLoad();
        entityTree.clickEntity(OU_TREE_LIST, name, GROUP);
    }

    public void clickDetailUser(String name) {
        driverManager.shortWaitForLoad();
        entityTree.clickEntity(OU_TREE_LIST, name, USER);
    }

    public void addUserToGroup(String groupName) {
        welActions.input(DETAIL_SEARCH_INPUT, groupName);
        welActions.click(USER_DETAIL_SEARCH_BUTTON);
        welActions.click(DETAIL_CHECKBOX_SELECT);
        clickDetailsSaveButton();
    }

    public void removeUserFromGroup(String groupName) {
        welActions.input(DETAIL_SEARCH_REMOVE_INPUT, groupName);
        welActions.click(DETAIL_REMOVE_SEARCH_BUTTON);
        if ("false".equals(welActions.getElement(DETAIL_CHECKBOX_SELECT_PARENT).getAttribute(ARIA_SELECTED.getValue()))){
            welActions.click(DETAIL_CHECKBOX_SELECT);
        }
        clickDetailsSaveButton();
    }

    public void clickDetailGroupButton() {
        if (!welActions.getElement(DETAIL_GROUP_HEADER).getAttribute(CLASS.getValue()).equals(DETAIL_GROUP_CLASS.getValue())){
            welActions.click(DETAILS_GROUP_BUTTON);
        }
    }

    public void inputSearchName(String name) {
        welActions.input(SEARCH_NAME_INPUT, name);
    }

    public void clickCreateButton() {
        welActions.click(CREATE_BUTTON);
    }

    public void clickOuSaveButton() {
        welActions.click(OU_SAVE_BUTTON);
    }

    public void clickOuDeleteButton() {
        welActions.click(OU_DELETE_BUTTON);
    }

    public void clickDetailsSaveButton() {
        welActions.click(DETAILS_SAVE_BUTTON);
    }

    public void clickDetailsClearButton() {
        welActions.click(DETAILS_CLEAR_BUTTON);
    }

    public void clickEntity(String name) {
        welActions.click(OU_TABLE, name, 0);
    }

    public List<String> getGroups() {
        List<WebElement> groupListElems = welActions.getElements(DETAILS_GROUP_MEMBER_LIST);
        List<String> groupList = new ArrayList<>();
        for (WebElement elem: groupListElems){
            groupList.add(elem.getText());
        }
        return groupList;
    }

    public List<String> getGhildEntities() {
        List<WebElement> elems = welActions.getElements(DETAILS_TABLE_CHILD_ENTITIES);
        List<String> childList = new ArrayList<>();
        for (WebElement elem: elems){
            childList.add(elem.getText());
        }
        return childList;
    }

    public void removeChildEntity(String entityName) {
        List<WebElement> elems = welActions.getElements(DETAILS_TABLE_CHILD_ENTITIES);
        for (WebElement elem: elems){
            if (elem.getText().contains(entityName)) {
                elem.click();
            }
        }
    }

    public void clickMoreDropdown() {
        welActions.click(DROPDOWN_MORE);
    }

    public void clickMoreActivate() {
        welActions.click(DROPDOWN_MORE_ACTIVATE);
    }

    public void clickMoreDeactivate() {
        welActions.click(DROPDOWN_MORE_DEACTIVATE);
    }

    public void clickMoreDeleteGroup() {
        welActions.click(DROPDOWN_MORE_DELETE_GROUP);
    }

    public boolean checkIsMoreDeleteGroupIsClickable() {
        return welActions.isClickable(DROPDOWN_MORE_DELETE_GROUP);
    }

    public boolean checkIsMoreDeativateIsClickable() {
        return welActions.isClickable(DROPDOWN_MORE_DEACTIVATE);
    }

    public boolean checkIsMoreActivateIsClickable() {
        return welActions.isClickable(DROPDOWN_MORE_ACTIVATE);
    }

    public boolean checkIsMoreAddToGroupIsClickable() {
        return welActions.isClickable(ADD_TO_GROUP_BUTTON);
    }

    public boolean checkIsMoreRemoveFromGroupIsClickable() {
        return welActions.isClickable(REMOVE_FROM_GROUP_BUTTON);
    }

    public boolean checkIsMoreMoveToIsClickable() {
        return welActions.isClickable(MOVE_TO_OU_BUTTON);
    }

    public void clickDetailsDeleteIcon() {
        welActions.click(DETAILS_DELETE_ICON);
    }

    public void clickDetailsCloseIcon() {
        welActions.click(DETAILS_CLOSE_ICON);
    }

    public void clickDetailsActivate() {
        welActions.click(DETAILS_ACTIVATE_INPUT);
    }

    public void checkEntity(String name) {
        driverManager.shortWaitForLoad();
        welActions.click(OU_TABLE, name, -1);
    }

    public void activateDeactivateEntity(String name) {
        welActions.click(OU_TABLE, name, 1);
    }

    public boolean isEntityAcivated(String name) {
        return welActions.getElement(OU_TABLE, name, 1).isSelected();
    }

    public List<String> getOuNames() {
        return welActions.getElementsText(ENTITY_TREE);
    }

    public List<String> getEntityNames() {
        return welActions.getElementsText(OU_TABLE_NAME_COL);
    }

    public List<String> getTypes() {
        return welActions.getElementsText(OU_TABLE_TYPE_COL);
    }

    public List<WebElement> getActive() {
        return welActions.getElements(OU_TABLE_ACTIVE_COL);
    }

    public String getRegCodeValue() {
        String name = TEST_EMPTY;
        List<String> names = welActions.getElementsText(EDIT_REG_CODE_INPUT);
        if (!names.isEmpty()) {
            name = names.get(0);
        }
        return name;
    }

    public String addOU(String name, String regcode) {
        selectAddOU();
        inputEntityOuAddName(name);
        inputEntityAddRegCode(regcode);
        clickCreateButton();
        return name;
    }

    public String addRole(String name, String regCode) {
        selectAddRole();
        inputEntityRoleAddName(name);
        inputEntityRoleAddRegCode(regCode);
        clickCreateButton();
        return name;
    }

    public Map<String, String> addUser(Map<String, String> vals) {
        selectAddUser();
        inputEntityUserAddLogin(vals.get(LOGIN.getValue()));
        inputEntityUserAddName(vals.get(NAME.getValue()));
        inputEntityUserAddLastName(vals.get(LAST_NAME.getValue()));
        inputEntityUserAddEmail(vals.get(EMAIL.getValue()));
        inputEntityUserAddPassword(vals.get(PASSWORD.getValue()));
        clickCreateButton();
        return vals;
    }

    public List<String> getDetailsValues() {
        return welActions.getElements(DETAILS_TABLE).stream()
                .map(d -> d.getAttribute(VALUE.getValue()))
                .collect(Collectors.toList());
    }

    public Map<String, String> getDetails() {
        int offset = 2;
        int step = 2;
        HashMap<String, String> details = new HashMap<>();
        List<WebElement> wels = welActions.getElements(DETAILS_TABLE);
        for (int i = 0; i < wels.size(); i++) {
            if (i == (wels.size() - 1)) {
                break;
            }
            details.put(wels.get(i).getText(), wels.get(i + offset).getAttribute(VALUE.getValue()));
            i = i + step;
        }
        return details;
    }

    public void clickAndCheckEntity(String name) {
        driverManager.shortWaitForLoad();
        clickEntity(name);
        checkEntity(name);
    }

    public void inputNewPass(String pass) {
        welActions.input(DETAIL_EDIT_PASS_INPUT, pass);
        welActions.input(DETAIL_CONF_PASS_INPUT, pass);
    }

    public void clickMoreButton() {
        welActions.click(OU_MORE_DROPDOWN_BUTTON);
    }

    public void clickMoveToOU() {
        clickMoreButton();
        welActions.click(MOVE_TO_OU_BUTTON);
    }

    public void clickAddToGroup() {
        clickMoreButton();
        welActions.click(ADD_TO_GROUP_BUTTON);
    }

    public void clickRemoveFromGroup() {
        clickMoreButton();
        welActions.click(REMOVE_FROM_GROUP_BUTTON);
    }

    public void clickToggleButton() {
        welActions.click(TOGGLE_BUTTON);
    }

    public void clickAdvancedSwitchButton() {
        welActions.click(ADVANCED_SEARCH_SWITCH_BUTTON);
    }

    public void clickSearchButton() {
        welActions.click(SEARCH_BUTTON);
    }

    public void advancedSearchInput(String query) {
        welActions.input(ADVANCED_SEARCH_INPUT, query);
    }

    public void searchByName(String searchKey) {
        welActions.input(SEARCH_BY_NAME_INPUT, searchKey);
        clickSearchButton();
    }

    public void searchByOU(String searchKey) {
        welActions.input(SEARCH_BY_OU_INPUT, searchKey);
        clickSearchButton();
    }

    public String getAdvancedSearchByName(List<String> args) {
        return args.stream()
            .map(arg -> String.format("OU_name = \"%s\"", arg))
            .collect(Collectors.joining(" OR "));
    }

    public String getAdvancedSearchByOU(String ou) {
        return String.format("parentOuName = \"%s\" AND ", ou);
    }

    public boolean isEntityPresentInFilteredByNameResult(String searchKey) {
        searchByName(searchKey);
        return getEntityNames().stream().allMatch(name -> name.contains(searchKey));
    }

    public boolean isEntityPresentInFilteredByOuResult(String searchKey) {
        searchByOU(searchKey);
        return getEntityNames().size() == 3;
    }

    public String selectFilter(String filterSelector, String filterCheckboxInput, String filterLabel) {
        welActions.click(filterSelector);
        List<WebElement> inputValue = welActions.getElements(filterCheckboxInput);
        List<String> labelText = welActions.getElementsText(filterLabel);
        int randomValue = getRandomIntBetween(1 , inputValue.size() - 1);
        inputValue.get(randomValue).click();

        return labelText.get(randomValue);
    }

    public boolean isFilteredByType() {
        welActions.click(FILTER_DROPDOWN_LIST);
        welActions.click(FILTER_TYPE_OPTIONS);
        String typeName = selectFilter(TYPE_DROPDOWN, TYPE_CHECKBOX_INPUT, TYPE_LABEL);

        return getTypes().stream().allMatch(types -> types.contains(typeName));
    }

    public boolean isFilteredByActive() {
        welActions.click(FILTER_DROPDOWN_LIST);
        welActions.click(FILTER_ACTIVE_OPTIONS);
        return getActive().stream().allMatch(WebElement::isSelected);
    }

    public boolean isFilteredByDeactive() {
        welActions.click(FILTER_DROPDOWN_LIST);
        welActions.click(FILTER_ACTIVE_OPTIONS);
        welActions.click(FILTER_ACTIVE_CHECKBOX);
        return getActive().stream().allMatch(WebElement::isSelected);
    }

    public boolean isFilteredByTwoFilter(String searchKey) {
        welActions.input(SEARCH_BY_NAME_INPUT, searchKey);
        welActions.click(FILTER_DROPDOWN_LIST);
        welActions.click(FILTER_TYPE_OPTIONS);
        String typeName = selectFilter(TYPE_DROPDOWN, TYPE_CHECKBOX_INPUT, TYPE_LABEL);

        return getEntityNames().stream().allMatch(name -> name.contains(searchKey))
            && getTypes().stream().allMatch(types -> types.contains(typeName));
    }

    public void clickSaveFilterButton() {
        WaitUtil.setWait(1);
        welActions.click(SAVE_FILTER_BUTTON);
    }

    public void clickSaveModalButton() {
        welActions.click(SAVE_MODAL_BUTTON);
    }

    public void inputFilterName(String text) {
        welActions.input(INPUT_FILTER_NAME, text);
    }

    public void clickMyFilter() {
        welActions.click(MY_FILTER_DROPDOWN);
    }

    public List<String> getMyFiltersText() {
        return welActions.getElementsText(MY_FILTER_LIST);
    }

    public void saveMyFilter(String searchKey, String filterName) {
        searchByName(searchKey);
        clickSaveFilterButton();
        inputFilterName(filterName);
        clickSaveModalButton();
        clickMyFilter();
    }

    public boolean isMyFilterSaved(String searchKey, String filterName) {
        saveMyFilter(searchKey, filterName);
        return getMyFiltersText().stream().anyMatch(s -> s.contains(filterName));
    }

    public boolean updateCustomFilter(String searchKey, String filterName) {
        String updSearchKey = RandomUtils.getRandomString(8, ENGLISH_ALPHABET, true);
        String updFilterName = RandomUtils.getRandomString(8, ENGLISH_ALPHABET, true);
        saveMyFilter(searchKey, filterName);

        int randFilter = RandomUtils.getRandomIntBetween(0, welActions.getElements(MY_FILTER_LIST).size());
        welActions.getElements(MY_FILTER_LIST).get(randFilter).click();
        saveMyFilter(updSearchKey, updFilterName);
        return getMyFiltersText().stream().anyMatch(s -> s.contains(updFilterName));
    }

    public boolean deleteCustomFilter() {
        String searchKey = RandomUtils.getRandomString(8, ENGLISH_ALPHABET, true);
        String filterName = RandomUtils.getRandomString(8, ENGLISH_ALPHABET, true);
        saveMyFilter(searchKey, filterName);

        int randFilter = RandomUtils.getRandomIntBetween(0, welActions.getElements(MY_FILTER_LIST).size() - 1);
        clickMyFilter();
        welActions.getElements(MY_FILTER_LIST).get(randFilter).click();
        clickTrashFilterButton();
        clickConfirmDeleteFilter();

        return getMyFiltersText().stream().noneMatch(s -> s.contains(filterName));
    }

    public void clickTrashFilterButton() {
        welActions.click(TRASH_BUTTON);
    }

    public void clickConfirmDeleteFilter() {
        welActions.click(DELETE_FILTER_CONFIRM);
    }

    public void clickSwitchToAdvancedSearch() {
        welActions.click(SWITCH_TO_ADVANCED_SEARCH_BUTTON);
    }

    public boolean isMyFilterApply(String searchKey, String filterName) {
        saveMyFilter(searchKey, filterName);
        int filterPos = welActions.getElementsText(MY_FILTER_LIST).indexOf(filterName);
        welActions.getElements(MY_FILTER_LIST).get(filterPos).click();
        return getEntityNames().stream().allMatch(searchName -> searchKey.contains(searchKey));
    }

    public boolean isSwitchedToAdvancedSearch() {
        clickSwitchToAdvancedSearch();
        return welActions.isPresent(ADVANCED_SEARCH_INPUT);
    }

    public boolean isSearchResultCleared(String searchKey) {
        searchByName(searchKey);
        int beforeSearch = getEntityNames().size();
        clickClearFilterButton();
        WaitUtil.setWait(1);
        return getEntityNames().size() != beforeSearch;
    }

    public void clickClearFilterButton() {
        welActions.click(CLEAR_SEARCH_RESULT_BUTTON);
    }

    public void clickMoveOUButton() {
        welActions.click(MOVE_OU_BUTTON);
    }

    public boolean isRemovedManagerOU(String userName) {
        clickResponsibleOuButton();
        clickDetailUser(userName);
        clickDetailsSaveButton();
        clickResponsibleOuButton();
        clickDetailsClearButton();

        return responsibleName().contains(userName);
    }

    public void clickOuRespSearchButton() {
        welActions.click(DETAIL_SEARCH_OU_BUTTON);
    }

    public void searchOuManager(String name) {
        welActions.input(DETAIL_SEARCH_OUMANAGER_SEARCH, name);
    }

    public void selectUserAsOuManager(String name) {
        entityTree.clickEntity(DETAIL_CHECKBOX_SELECT_PARENT, name);
        clickDetailsSaveButton();
    }

    public String getOuManagerName() {
        return welActions.getElement(DETAIL_USER_OU_LABEL).getText();
    }

    public void clickXlsxExportIcon(String name) {
        welActions.click(DETAILS_EXPORT_ICON);

    }

    public List<String> getEntityList() {
        return welActions.getElementsText(DETAIL_GROUP_ENTITIES_LIST);
    }

    public boolean isAllEntitiesPresentInFile(String name) {
        clickXlsxExportIcon(name);
        welActions.fileUploadedWaiter(name);
        return readXls.readXlsFile(name).containsAll(getEntityList());
    }

    public void inputSearchDetailsOu(String name) {
        welActions.input(DETAILS_SEARCH_OU_INPUT, name);
        welActions.click(DETAILS_SEARCH_OU_BUTTON);
        entityTree.clickEntity(DETAIL_CHECKBOX_SELECT_PARENT, name);
    }

    public boolean isOuMovedOn(String parentName, String childName) {
        try {
            String resultXpath = String.format(".//span[contains(text(),'%s')]/parent::span/..//span[contains(text(),'%s')]", parentName, childName);
            driverManager.getDriver().findElement(By.xpath(resultXpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getPaginationValues() {
        return welActions.getElement(PAGINATION_VALUES).getText();
    }

    public void clickSubElementsCheckbox() {
        welActions.click(SUB_ELEMENTS_CHECKBOX);
    }
}
