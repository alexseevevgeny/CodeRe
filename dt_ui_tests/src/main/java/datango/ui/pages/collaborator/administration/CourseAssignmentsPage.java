package datango.ui.pages.collaborator.administration;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.CourseAssignmentEditDetails;
import datango.utils.RandomUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.enums.AttributeValues.*;
import static datango.ui.enums.ElementAttribute.*;
import static datango.utils.RandomUtils.getRandomIntBetween;
import static datango.utils.RandomUtils.getRandomString;

/**
 * @author Sergey Kuzhel
 */
public class CourseAssignmentsPage extends BasePage {
    private static final String ADD_BUTTON = "courseassignments.page.add.button";
    private static final String ASSIGNMENTS_TABLE_CAPTIONS_COL = "courseassignments.page.captionscol.values";
    private static final String ASSIGNMENTS_TABLE_WEIGHT_COL = "courseassignments.page.weightcol.values";
    private static final String ASSIGNMENTS_TABLE_REQ_APPROVAL_COL = "courseassignments.page.reqapprovalcol.values";
    private static final String ASSIGNMENTS_TABLE_STATUS_COL = "courseassignments.page.recurringcol.values";
    private static final String ASSIGNMENTS_TABLE_ACTIVE_COL = "courseassignments.page.activecol.values";
    private static final String ASSIGNMENTS_TABLE_CHARGEABLE_COL = "courseassignments.page.chargeablecol.values";
    private static final String ASSIGNMENTS_TABLE_MANDATORY_COL = "courseassignments.page.mandatorycol.values";
    private static final String ASSIGNMENTS_TABLE_CERTIFICATE_COL = "courseassignments.page.certificatecol.values";
    private static final String ASSIGNMENTS_ADVANCED_SEARCH = "courseassignment.advanced.search.filter";
    private static final String ASSIGNMENTS_TABLE = "courseassignments.page.assignments.table";
    private static final String ASSIGNMENTS_TABLE_ROWS = "courseassignments.page.table.rows";
    private static final String ASSIGNMENTS_OPEN_FILTER_BUTTON = "courseassignments.page.myfilter.open.button";
    private static final String ASSIGNMENTS_FILTER_NAME_INPUT = "tasks.save.filter.name.input";
    private static final String ASSIGNMENTS_FILTER_SAVE_BUTTON = "tasks.save.filter.save";
    private static final String ASSIGNMENTS_FILTER_CLEAR_BUTTON = "courseassignments.filter.clear.button";
    private static final String QR_IMG = "courseassignments.page.popup.qrcode.img";
    private static final String QR_DIV = "courseassignment.page.popup.qrcode.div";
    private static final String COURSE_LINK = "courseassignments.page.popup.link";
    private static final String CAPTION_FILTER_INPUT = "courseassignments.caption.filter.input";
    private static final String WEIGHT_FILTER_INPUT = "courseassignments.weight.filter.input";
    private static final String COURSE_TYPE_FILTER_DROPDOWN = "courseassignments.coursetype.filter.dropdown";
    private static final String REQ_APPROVAL_FILTER_CHECKBOX = "courseassignments.reqapproval.filter.input";
    private static final String RECURRING_FILTER_CHECKBOX = "courseassignments.recurring.filter.input";
    private static final String CHARGEABLE_FILTER_CHECKBOX = "courseassignments.chargeable.filter.input";
    private static final String MANDATORY_FILTER_CHECKBOX = "courseassignments.mandatory.filter.input";
    private static final String ACTIVE_FILTER_CHECKBOX = "courseassignments.active.filter.input";
    private static final String CERTIFICATE_FILTER_CHECKBOX = "courseassignments.certificate.filter.input";
    private static final String COURSE_TYPE_FILTER_CHECKBOX = "courseassignments.coursetype.filter.checkbox";
    private static final String COLOR_FILTER_CHECKBOX = "courseassignments.color.filter.checkbox";
    private static final String COLOR_FILTER_LABEL = "courseassignments.color.filter.label";
    private static final String COURSE_TYPE_FILTER_LABEL = "courseassignments.coursetype.filter.label";
    private static final String COLOR_FILTER_DROPDOWN = "courseassignments.color.filter.dropdown";
    private static final String DESCRIPTION_FILTER_INPUT = "courseassignments.description.input";
    private static final String NOTIFICATION_NAME_INPUT = "courseassignments.notification.name.input";
    private static final String NOTIFICATION_TEXT_INPUT = "courseassignments.notification.text.input";
    private static final String NOTIFICATION_SAVE_BUTTON = "courseassignments.notification.save.button";
    private static final String NOTIFICATION_COPY_BUTTON = "courseassignments.notification.copy.button";
    private static final String NOTIFICATION_DELETE_BUTTON = "courseassignments.notification.delete.button";
    private static final String NOTIFICATION_EDIT_BUTTON = "courseassignments.notification.edit.button";
    private static final String NOTIFICATION_SELECT_TEMPLATE = "courseassignments.notification.template.select";
    private static final String NOTIFICATION_TEMPLATE_LIST = "courseassignments.notification.template.option";
    private static final String FILTER_MORE_BUTTON = "courseassignments.more.button";
    private static final String MY_FILTER_DROPDOWN_BUTTON = "courseassignments.myfilter.button";
    private static final String MY_FILTER_CUSTOM_LIST = "courseassignments.myfilter.list";
    private static final String SELECT_FILTER_LIST = "courseassignments.filter.list";
    private static final String SEARCH_BUTTON = "courseassignments.search.button";
    private static final String TRASH_BUTTON = "courseassignments.trash.button";
    private static final String DELETE_FILTER_CONFIRM = "dialog.apply.button";
    private static final String QR_CODE_LOCATOR = ".//*[@*='#icon-qr']";
    private static final String ICON_CHECKED = "i";
    private static final String CHECKBOX_ACTIVE = "input";
    private static final String PERMISSION_MSG = "courseassignments.page.perm.msg";

    @Getter
    private CourseAssignmentEditDetails details;

    public CourseAssignmentsPage(WebDriver driver) {
        super(driver);
        this.details = new CourseAssignmentEditDetails(driver);
    }

    public void clickAddButton() {
        welActions.click(ADD_BUTTON);
    }

    public List<String> getAssignmentsNames() {
        return welActions.getElementsText(ASSIGNMENTS_TABLE_CAPTIONS_COL);
    }

    public boolean isEntityDeleted(String deletedEntityName) {
        return welActions.getElementsText(ASSIGNMENTS_TABLE_CAPTIONS_COL).stream()
            .noneMatch(name -> name.contains(deletedEntityName));
    }

    public List<String> getWeigthList() {
        return welActions.getElementsText(ASSIGNMENTS_TABLE_WEIGHT_COL);
    }

    public void clickAssignment(String name) {
        driverManager.shortWaitForLoad();
        welActions.click(ASSIGNMENTS_TABLE, name, 3);
    }

    public void clickActivateAssignment(String name) {
        welActions.click(ASSIGNMENTS_TABLE, name, 10);
    }

    public void clickDeleteIcon(String name) {
        welActions.click(ASSIGNMENTS_TABLE, name, 17);
    }

    public void clickCopyAssignment(String name) {
        welActions.click(ASSIGNMENTS_TABLE, name, 20);
    }

    private String defAssignment(String entityName, String entityType, String waName, boolean viaSearch) {
        String name = getRandomString(8, ENGLISH_ALPHABET, true);
        clickAddButton();
        getDetails().clickSection(1);
        getDetails().inputCaption(name);
        getDetails().clickSection(3);
        getDetails().clickAddEntity();
        getDetails().selectEntityWorkarea(waName);
        getDetails().clickSelectionEntitySelect();
        getDetails().clickSection(4);
        getDetails().assignTo(entityName, entityType, viaSearch);
        return name;
    }

    private String defAssignment(String entityName, String entityType, boolean viaSearch) {
        String name = getRandomString(8, ENGLISH_ALPHABET, true);
        clickAddButton();
        getDetails().clickSection(1);
        getDetails().inputCaption(name);
        getDetails().clickSection(3);
        getDetails().clickAddEntity();
        getDetails().selectEntityWorkarea();
        getDetails().clickSelectionEntitySelect();
        getDetails().clickSection(4);
        getDetails().assignTo(entityName, entityType, viaSearch);
        return name;
    }

    private void saveAssignment() {
        getDetails().clickDetailsSaveButton();
        getDetails().clickDetailsCloseIcon();
    }

    public String addAssignment(String entityName, String entityType, String waName, boolean viaSearch) {
        driverManager.shortWaitForLoad();
        String name = defAssignment(entityName, entityType, waName, viaSearch);
        saveAssignment();
        return name;
    }

    public String addAssignment(String entityName, String entityType, boolean viaSearch) {
        driverManager.shortWaitForLoad();
        String name = defAssignment(entityName, entityType, viaSearch);
        saveAssignment();
        return name;
    }

    public String addMandatoryAssignment(String entityName, String entityType, String waName, boolean viaSearch) {
        String name = defAssignment(entityName, entityType, waName, viaSearch);
        getDetails().clickSection(1);
        getDetails().checkIsMandatory();
        saveAssignment();
        return name;
    }

    public String addMandatoryAssignment(String entityName, String entityType, boolean viaSearch) {
        String name = defAssignment(entityName, entityType, viaSearch);
        getDetails().clickSection(1);
        getDetails().checkIsMandatory();
        saveAssignment();
        return name;
    }

    public String addMandatoryAssignmentToApproval(String entityName, String entityType, boolean viaSearch) {
        String name = defAssignment(entityName, entityType, viaSearch);
        getDetails().clickSection(1);
        getDetails().checkIsMandatory();
        getDetails().checkIsReqApproval();
        saveAssignment();
        return name;
    }

    public String addMandatoryAssignmentToApproval(String entityName, String entityType, String waName, boolean viaSearch) {
        String name = defAssignment(entityName, entityType, waName, viaSearch);
        getDetails().clickSection(1);
        getDetails().checkIsMandatory();
        getDetails().checkIsReqApproval();
        saveAssignment();
        return name;
    }

    public String addAssignmentToApproval(String entityName, String entityType, boolean viaSearch) {
        String name = defAssignment(entityName, entityType, viaSearch);
        getDetails().clickSection(1);
        getDetails().checkIsReqApproval();
        saveAssignment();
        return name;
    }

    public String addAssignmentToApproval(String entityName, String entityType, String waName, boolean viaSearch) {
        String name = defAssignment(entityName, entityType, waName, viaSearch);
        getDetails().clickSection(1);
        getDetails().checkIsReqApproval();
        saveAssignment();
        return name;
    }

    public void clickQRIcon(String assignName){
        List<WebElement> rows = welActions.getElements(ASSIGNMENTS_TABLE_ROWS);
        for (WebElement row : rows){
            if (row.getText().contains(assignName)){
                driverManager.shortWaitForLoad();
                welActions.click(row.findElement(By.xpath(QR_CODE_LOCATOR)), null);
            }
        }
    }

    public boolean qrCodeIsVisible(){
        return welActions.isVisible(QR_IMG);
    }

    public boolean linkIsPresent(){
        return welActions.isPresent(COURSE_LINK);
    }

    public String getLinkpart(String assignName){
        driverManager.shortWaitForLoad();
        String str = null;
        List<WebElement> rowsName = welActions.getElements(ASSIGNMENTS_TABLE_ROWS);
        for (WebElement row : rowsName) {
            if (row.getText().contains(assignName)){
                str = row.getAttribute(DATA_ASSIGNMENT.getValue());
            }
        }
        return str;
    }

    public List<String> getCustomFilters() {
        welActions.click(MY_FILTER_DROPDOWN_BUTTON);
        List<WebElement> elems = welActions.getElements(MY_FILTER_CUSTOM_LIST);
        List<String> arr = new ArrayList<>();
        for (WebElement elem: elems) {
            arr.add(elem.getText());
        }
        return arr;
    }

    public boolean linkIsValid(String linkSuffix){
        return welActions.getElement(QR_DIV).getAttribute(TITLE.getValue()).endsWith(linkSuffix);
    }

    private void selectFilter(String filterNameId) {
        welActions.click(FILTER_MORE_BUTTON);
        List<WebElement> elems = welActions.getElements(SELECT_FILTER_LIST);
        for (WebElement elem: elems) {
            if (elem.getAttribute(ID.getValue()).equals(filterNameId)) {
                elem.click();
            }
        }
    }

    public void clickSearchButton() {
        welActions.click(SEARCH_BUTTON);
    }

    public void searchByCaption(String caption) {
        welActions.input(CAPTION_FILTER_INPUT, caption);
        welActions.click(SEARCH_BUTTON);
    }

    public void searchByWeight(String weight) {
        selectFilter(CA_OPTION_ID_WEIGHT.getValue());
        welActions.input(WEIGHT_FILTER_INPUT, weight);
        welActions.click(SEARCH_BUTTON);
    }

    public void searchByApproval() {
        selectFilter(CA_OPTION_ID_REQUIRE_APPROVAL.getValue());
        if (!welActions.isChecked(REQ_APPROVAL_FILTER_CHECKBOX)) {
            welActions.click(REQ_APPROVAL_FILTER_CHECKBOX);
        }
    }

    public void searchByRecurring() {
        selectFilter(CA_OPTION_ID_RECURRING.getValue());
        if (!welActions.isChecked(RECURRING_FILTER_CHECKBOX)) {
            welActions.click(RECURRING_FILTER_CHECKBOX);
        }
    }

    public void searchByChargeable() {
        selectFilter(CA_OPTION_ID_CHARGEABLE.getValue());
        if (!welActions.isChecked(CHARGEABLE_FILTER_CHECKBOX)) {
            welActions.click(CHARGEABLE_FILTER_CHECKBOX);
        }
    }

    public void searchByMandatory() {
        selectFilter(CA_OPTION_ID_MANDATORY.getValue());
        if (!welActions.isChecked(MANDATORY_FILTER_CHECKBOX)) {
            welActions.click(MANDATORY_FILTER_CHECKBOX);
        }
    }

    public void searchByActive() {
        selectFilter(CA_OPTION_ID_ACTIVE.getValue());
        if (welActions.isChecked(ACTIVE_FILTER_CHECKBOX)) {
            welActions.click(ACTIVE_FILTER_CHECKBOX);
        }
    }

    public void searchByCertificate() {
        selectFilter(CA_OPTION_ID_CERTIFICATE.getValue());
        if (!welActions.isChecked(CERTIFICATE_FILTER_CHECKBOX)) {
            welActions.click(CERTIFICATE_FILTER_CHECKBOX);
        }
    }

    public void searchByDescription(String searchText) {
        selectFilter(CA_OPTION_ID_DESCRIPTION.getValue());
        welActions.input(DESCRIPTION_FILTER_INPUT, searchText);
        welActions.click(SEARCH_BUTTON);
    }

    public String selectFilter(String filterOption, String filterSelector, String filterCheckboxInput, String filterLabel) {
        selectFilter(filterOption);
        welActions.click(filterSelector);
        List<WebElement> inputValue = welActions.getElements(filterCheckboxInput);
        List<String> labelText = welActions.getElementsText(filterLabel);
        int randomValue = getRandomIntBetween(1 , inputValue.size() - 1);
        inputValue.get(randomValue).click();

        return labelText.get(randomValue);
    }

    public String selectFilter(String filterSelector, String filterCheckboxInput, String filterLabel) {
        welActions.click(filterSelector);
        List<WebElement> inputValue = welActions.getElements(filterCheckboxInput);
        List<String> labelText = welActions.getElementsText(filterLabel);
        int randomValue = getRandomIntBetween(1 , inputValue.size() - 1);
        inputValue.get(randomValue).click();

        return labelText.get(randomValue);
    }

    public boolean isFilteredByCourseType() {
        String labelName = selectFilter(CA_OPTION_ID_COURSETYPE.getValue(), COURSE_TYPE_FILTER_DROPDOWN,
            COURSE_TYPE_FILTER_CHECKBOX, COURSE_TYPE_FILTER_LABEL);
        List<WebElement> filteredList = welActions.getElements(ASSIGNMENTS_TABLE_CAPTIONS_COL);
        for (WebElement elem: filteredList) {
            elem.click();
            if (!getDetails().getCourseType().toLowerCase().contains(labelName)) {
                return false;
            }
        }

        return true;
    }

    public boolean isFilteredByColor() {
        String labelName = selectFilter(COLOR_FILTER_DROPDOWN, COLOR_FILTER_CHECKBOX, COLOR_FILTER_LABEL);
        List<WebElement> filteredList = welActions.getElements(ASSIGNMENTS_TABLE_CAPTIONS_COL);
        for (WebElement elem: filteredList) {
            elem.click();
            if (!getDetails().getColor().contains(labelName)) {
                return false;
            }
        }

        return true;
    }

    public boolean isFilteredByDescription(String searchText) {
        searchByDescription(searchText);
        List<WebElement> filteredList = welActions.getElements(ASSIGNMENTS_TABLE_CAPTIONS_COL);
        for (WebElement elem: filteredList) {
            elem.click();
            if (!getDetails().getDescription().toLowerCase().contains(searchText)) {
                return false;
            }
        }

        return true;
    }

    public boolean isFilteredByCaption(String caption) {
        searchByCaption(caption);
        return getAssignmentsNames().stream().allMatch(s -> s.toLowerCase().contains(caption.toLowerCase()));
    }

    public boolean isFilteredByAdvancedSearch(String caption) {
        return getAssignmentsNames().stream().allMatch(s -> s.toLowerCase().contains(caption.toLowerCase()));
    }

    public boolean isFilteredByWeigth(String weigth) {
        return getWeigthList().stream().allMatch(s -> s.equals(weigth));
    }

    public boolean isFilteredByReqApproval() {
        List<WebElement> elems = welActions.getElements(ASSIGNMENTS_TABLE_REQ_APPROVAL_COL);
        for (WebElement elem: elems) {
            if (!welActions.isPresent(elem, ICON_CHECKED))
            return false;
        }
        return true;
    }

    public boolean isFilteredByRecurring() {
        String recurring = "Recurring";
        return welActions.getElementsText(ASSIGNMENTS_TABLE_STATUS_COL).stream()
            .allMatch(s -> s.equals(recurring));
    }

    public boolean isFilteredByChargeable() {
        List<WebElement> elems = welActions.getElements(ASSIGNMENTS_TABLE_CHARGEABLE_COL);
        for (WebElement elem: elems) {
            if (!welActions.isPresent(elem, ICON_CHECKED))
                return false;
        }
        return true;
    }

    public boolean isFilteredByMandatory() {
        List<WebElement> elems = welActions.getElements(ASSIGNMENTS_TABLE_MANDATORY_COL);
        for (WebElement elem: elems) {
            if (!welActions.isPresent(elem, ICON_CHECKED))
                return false;
        }
        return true;
    }

    public boolean isFilteredByCertificate() {
        List<WebElement> elems = welActions.getElements(ASSIGNMENTS_TABLE_CERTIFICATE_COL);
        for (WebElement elem: elems) {
            if (!welActions.isPresent(elem, ICON_CHECKED))
                return false;
        }
        return true;
    }

    public boolean isFilteredByActive() {
        List<WebElement> elems = welActions.getElements(ASSIGNMENTS_TABLE_ACTIVE_COL);
        for (WebElement elem: elems) {
            if (elem.findElement(By.xpath(CHECKBOX_ACTIVE)).isSelected())
                return false;
        }
        return true;
    }

    public void clickOpenFilterModal() {
        welActions.click(ASSIGNMENTS_OPEN_FILTER_BUTTON);
    }

    public void inputFilterName(String text) {
        welActions.input(ASSIGNMENTS_FILTER_NAME_INPUT, text);
    }

    public void clickSaveFilterButton() {
        welActions.click(ASSIGNMENTS_FILTER_SAVE_BUTTON);
    }

    public boolean saveOwnTitleFilter(String filterName) {
        return getCustomFilters().stream().anyMatch(s -> s.contains(filterName));
    }

    public void clickTrashButton() {
        welActions.click(TRASH_BUTTON);
    }

    public boolean updateCustomFilter(String caption, String filterName) {
        welActions.click(MY_FILTER_DROPDOWN_BUTTON);
        List<WebElement> elems = welActions.getElements(MY_FILTER_CUSTOM_LIST);
        int randFilter = RandomUtils.getRandomIntBetween(0, elems.size());
        elems.get(randFilter).click();
        searchByCaption(caption);
        welActions.click(SEARCH_BUTTON);
        welActions.click(ASSIGNMENTS_OPEN_FILTER_BUTTON);
        welActions.clearField(ASSIGNMENTS_FILTER_NAME_INPUT);
        inputFilterName(filterName);
        clickSaveFilterButton();
        return getCustomFilters().stream().anyMatch(s -> s.contains(filterName));
    }

    public void clickConfirmDeleteFilter() {
        welActions.click(DELETE_FILTER_CONFIRM);
    }

    public String deleteCustomFilter() {
        welActions.click(MY_FILTER_DROPDOWN_BUTTON);
        List<WebElement> elems = welActions.getElements(MY_FILTER_CUSTOM_LIST);
        int randFilter = RandomUtils.getRandomIntBetween(0, elems.size());
        String str = elems.get(randFilter).getText();
        elems.get(randFilter).click();
        clickTrashButton();
        clickConfirmDeleteFilter();
        return str;
    }

    public boolean isCustomFilterDeleted(String filterName) {
        return getCustomFilters().stream().anyMatch(s -> s.contains(filterName));
    }

    public boolean isFilteredByTwoFilters(String caption) {
        return  (isFilteredByCaption(caption) && isFilteredByCertificate());
    }

    public void clickAdvancedSearch() {
        welActions.click(ASSIGNMENTS_ADVANCED_SEARCH);
    }

    public boolean isFilterCleared() {
        List<String> beforeClear = getAssignmentsNames();
        welActions.click(ASSIGNMENTS_FILTER_CLEAR_BUTTON);
        List<String> afterClearFilter = getAssignmentsNames();
        return beforeClear.size() != afterClearFilter.size();
    }

    public void selectCustomFilter(String filterName) {
        List<WebElement> elems = welActions.getElements(MY_FILTER_CUSTOM_LIST);
        for (WebElement elem: elems) {
            if (elem.getText().equals(filterName)) {
                elem.click();
            }
        }
    }

    public boolean permissionMsgVisibility() {
        return welActions.getElementsText(PERMISSION_MSG).contains("You have no permission to see this information.");
    }

    public void clickOnEntity(String entityName) {
        welActions.getElements(ASSIGNMENTS_TABLE_CAPTIONS_COL).stream()
            .filter(element -> element.getText().equals(entityName))
            .forEach(WebElement::click);
    }

    public void inputNotifyEmailTemplateName(String name) {
        welActions.input(NOTIFICATION_NAME_INPUT, name);
    }

    public void inputNotifyEmailTemplateText(String text) {
        welActions.input(NOTIFICATION_TEXT_INPUT, text);
    }

    public String getNotificationText() {
        return welActions.getElement(NOTIFICATION_TEXT_INPUT).getText();
    }

    public void clickSaveNotifyButton() {
        welActions.click(NOTIFICATION_SAVE_BUTTON);
    }

    public boolean isNotifyCopyEnabled() {
        return welActions.isClickable(NOTIFICATION_COPY_BUTTON);
    }

    public boolean isNotifyDeleteEnabled() {
        return welActions.isClickable(NOTIFICATION_DELETE_BUTTON);
    }

    public boolean isNotifyEditPresent() {
        return welActions.isVisible(NOTIFICATION_EDIT_BUTTON) && welActions.isClickable(NOTIFICATION_EDIT_BUTTON);
    }

    public String getTemplateName() {
        return welActions.getSelectedValue(NOTIFICATION_SELECT_TEMPLATE);
    }

    public List<String> getTemplateNames() {
        return welActions.getElementsText(NOTIFICATION_TEMPLATE_LIST);
    }

    public void selectTemplate() {
        String templateName = getTemplateNames().stream()
            .filter(option -> option.contains("adt"))
            .findFirst().orElse(null);
        welActions.selectByText(NOTIFICATION_SELECT_TEMPLATE, templateName);
    }

    public void clickNotifyCopy() {
        welActions.click(NOTIFICATION_COPY_BUTTON);
    }

    public void clickNotifyDelete() {
        welActions.click(NOTIFICATION_DELETE_BUTTON);
    }
}
