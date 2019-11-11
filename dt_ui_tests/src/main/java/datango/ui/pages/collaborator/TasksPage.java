package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.TaskEditDetails;
import datango.utils.CsvReaderUtil;
import datango.utils.DateUtils;
import datango.utils.RandomUtils;
import datango.utils.XlsReaderUtil;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.util.Strings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static datango.ui.enums.AttributeValues.*;
import static datango.ui.enums.ElementAttribute.*;
import static datango.utils.RandomUtils.getRandomIntBetween;

/**
 * @author Sergey Kuzhel
 */
public class TasksPage extends BasePage {
    private static final String TASKS_TABLE = "tasks.page.tasks.table";
    private static final String TASKS_TABLE_TITLECOL = "tasks.table.titlecol.values";
    private static final String TASKS_SEARCH_TITLE = "tasks.search.title.input";
    private static final String TASKS_SEARCH_BUTTON = "tasks.search.search.button";
    private static final String TASKS_FILTER_DROPDOWN = "tasks.details.filter.dropdown";
    private static final String TASKS_FILTER_LIST = "tasks.details.filter.list";
    private static final String TASKS_MILESTONE_COLUMN = "tasks.milestone.column";
    private static final String TASKS_PRIORITY_COLUMN = "tasks.priority.column";
    private static final String TASKS_MILESTONE_CHECKBOX_INPUT = "tasks.milestone.checkbox.input";
    private static final String TASKS_MILESTONE_FILTER = "tasks.milestone.filter";
    private static final String TASKS_PRIORITY_CHECKBOX_INPUT = "tasks.priority.checkbox.input";
    private static final String TASKS_PRIORITY_FILTER = "tasks.priority.filter";
    private static final String TASKS_TYPE_CHECKBOX_INPUT = "tasks.type.checkbox.input";
    private static final String TASKS_TYPE_FILTER = "tasks.type.filter";
    private static final String TASKS_TYPE_COLUMN = "tasks.type.column";
    private static final String TASKS_STATUS_CHECKBOX_INPUT = "tasks.status.checkbox.input";
    private static final String TASKS_STATUS_FILTER = "tasks.status.filter";
    private static final String TASKS_STATUS_COLUMN = "tasks.status.column";
    private static final String TASKS_ASSIGNED_COLUMN = "tasks.assigned.column";
    private static final String TASKS_ASSIGNED_TITLE = "tasks.assigned.input";
    private static final String TASKS_TARGET_DATE = "tasks.targetdate.input";
    private static final String TASKS_TARGET_DATE_CLOSE = "tasks.targetdate.close.icon";
    private static final String TASKS_TARGET_DATE_COLUMN = "tasks.targetdate.column";
    private static final String LESS_TARGET_DATE_LESS = "tasks.targetdate.less.button";
    private static final String LESS_TARGET_DATE_BUTTON = "tasks.targetdate.button";
    private static final String LESS_CREATED_DATE_BUTTON = "tasks.created.button";
    private static final String TASKS_CREATED_TITLE = "tasks.created.input";
    private static final String TASKS_CREATED_COLUMN = "tasks.created.column";
    private static final String TASKS_CREATOR_INFO = "tasks.creator.info";
    private static final String TASKS_CREATOR_CHECKBOX = "tasks.creator.checkbox";
    private static final String TASKS_DESCRIPTION_INPUT = "tasks.description.input";
    private static final String TASKS_OPEN_FILTER_BUTTON = "tasks.save.filter.button";
    private static final String TASKS_SAVE_FILTER_NAME = "tasks.save.filter.name.input";
    private static final String TASKS_FILTER_SAVE = "tasks.save.filter.save";
    private static final String TASKS_MY_FILTER_DROPDOWN = "tasks.myfilter.dropdown";
    private static final String TASKS_MY_FILTER_LIST = "tasks.myfilter.list";
    private static final String TASKS_MY_FILTER_TRASH_BUTTON = "tasks.myfilter.trash.button";
    private static final String TASKS_MY_FILTER_CONFIRM_DELETE = "tasks.myfilter.delete.confirm.button";
    private static final String TASKS_ADVANCED_SEARCH = "tasks.advanced.search.button";
    private static final String TASKS_ADVANCED_INPUT_SEARCH = "tasks.advanced.input.search";
    private static final String TASKS_FILTER_CLEAR_BUTTON = "tasks.advanced.clear.button";
    private static final String TASKS_XLS_BUTTON = "tasks.xls.export.button";
    private static final String TASKS_CSV_BUTTON = "tasks.csv.export.button";
    private static final String TASKS_PERMISSION_MSG = "tasks.permission.info.msg";
    private static final String TASKS_COLUMN_VALUES = "tasks.columns.values";
    private static final String TASKS_COLUMN_ATTRIBUTE = "tasks.columns.attribute";
    private static final String TASKS_TITLES = "tasks.columns.titles";
    private static final String TASKS_DETAILS_WORKAREA_NAME = "tasks.details.workarea.name";
    private static final String TASKS_PAGINATION_VALUE = "tasks.pagination.values";
    private static final String SEARCH_STRING = "SearchString";

    @Getter
    private TaskEditDetails details;
    private XlsReaderUtil readXls;
    private CsvReaderUtil readCsv;

    public TasksPage(WebDriver driver) {
        super(driver);
        this.details = new TaskEditDetails(driver);
        this.readXls = new XlsReaderUtil();
        this.readCsv = new CsvReaderUtil();
    }

    public List<String> getTaskTitles() {
        return welActions.getElementsText(TASKS_TABLE_TITLECOL);
    }

    public void clickTask(String name) {
        welActions.click(TASKS_TABLE, name, 0);
    }

    public void clickOpenFilterModal() {
        welActions.click(TASKS_OPEN_FILTER_BUTTON);
    }

    public void inputFilterName(String name) {
        welActions.input(TASKS_SAVE_FILTER_NAME, name);
    }

    public void clickSaveFilterButton() {
        welActions.click(TASKS_FILTER_SAVE);
    }

    public void clickTrashButton() {
        welActions.click(TASKS_MY_FILTER_TRASH_BUTTON);
    }

    public void clickConfirmDeleteFilter() {
        welActions.click(TASKS_MY_FILTER_CONFIRM_DELETE);
    }

    public void searchForTask(String name) {
        welActions.input(TASKS_SEARCH_TITLE, name);
        welActions.click(TASKS_SEARCH_BUTTON);
    }

    public void cleanSearchTaskField() {
        welActions.clearField(TASKS_SEARCH_TITLE);
    }

    public void searchForAssignedTo(String name) {
        selectFilter(TASK_OPTION_ID_ASSIGNEE.getValue());
        welActions.input(TASKS_ASSIGNED_TITLE, name);
        welActions.click(TASKS_SEARCH_BUTTON);
    }

    public void searchForCreated(String date) {
        welActions.input(TASKS_CREATED_TITLE, date);
        welActions.click(TASKS_SEARCH_BUTTON);
    }

    public void searchForTargetDate(String date) {
        welActions.input(TASKS_TARGET_DATE, date);
        welActions.click(TASKS_SEARCH_BUTTON);
    }

    public void searchCreatedByMe() {
        if (!welActions.isChecked(TASKS_CREATOR_CHECKBOX)) {
            welActions.click(TASKS_CREATOR_CHECKBOX);
        }
    }

    public void searchByDescription(String description) {
        selectFilter(TASK_OPTION_ID_DESCRIPTION.getValue());
        welActions.input(TASKS_DESCRIPTION_INPUT, description);
        welActions.click(TASKS_SEARCH_BUTTON);
    }

    public void searchByTwoFilters(String title, String assignTo) {
        selectFilter(TASK_OPTION_ID_ASSIGNEE.getValue());
        welActions.input(TASKS_ASSIGNED_TITLE, assignTo);
        welActions.input(TASKS_SEARCH_TITLE, title);
        welActions.click(TASKS_SEARCH_BUTTON);
    }

    public boolean isProjectTaskDeleted(String name){
        searchForTask(name);
        return getTaskTitles().contains(name);
    }

    public void clickToAdvancedSearch() {
        welActions.click(TASKS_ADVANCED_SEARCH);
    }

    public void doubleClickTask(String name) {
        welActions.doubleClick(TASKS_TABLE, name, 0);
    }

    private void selectFilter(String filterNameId) {
        welActions.click(TASKS_FILTER_DROPDOWN);
        List<WebElement> elems = welActions.getElements(TASKS_FILTER_LIST);
        for (WebElement elem: elems) {
            if (elem.getAttribute(ID.getValue()).equals(filterNameId)) {
                elem.click();
            }
        }
    }

    public List<String> getTaskMilestones() {
        return welActions.getElementsText(TASKS_MILESTONE_COLUMN);
    }

    public List<String> getTaskPriority() {
        return welActions.getElementsText(TASKS_PRIORITY_COLUMN);
    }

    public List<String> getTaskType() {
        return welActions.getElementsText(TASKS_TYPE_COLUMN);
    }

    public List<String> getTaskStatus() {
        return welActions.getElementsText(TASKS_STATUS_COLUMN);
    }

    public List<String> getTaskAssigned() {
        return welActions.getElementsText(TASKS_ASSIGNED_COLUMN);
    }

    public List<String> getTaskCreated() {
        return welActions.getElementsText(TASKS_CREATED_COLUMN);
    }

    public List<String> getTaskTargetDate() {
        return welActions.getElementsText(TASKS_TARGET_DATE_COLUMN);
    }

    public List<String> getCustomFilters() {
        welActions.click(TASKS_MY_FILTER_DROPDOWN);
        List<WebElement> elems = welActions.getElements(TASKS_MY_FILTER_LIST);
        List<String> arr = new ArrayList<>();
        for (WebElement elem: elems) {
            arr.add(elem.getText());
        }
        return arr;
    }

    public void selectFilter(String filterOption, String filterSelector, String filterCheckboxInput) {
        selectFilter(filterOption);
        welActions.click(filterSelector);
        List<WebElement> milestoneInput = welActions.getElements(filterCheckboxInput);
        int randomValue = getRandomIntBetween(1 , milestoneInput.size() - 1);
        milestoneInput.get(randomValue).click();
    }

    public boolean isFilteredByMilestone() {
        selectFilter(TASK_OPTION_ID_MILESTONE.getValue(), TASKS_MILESTONE_FILTER, TASKS_MILESTONE_CHECKBOX_INPUT);
        return getTaskMilestones().stream().distinct().count() <= 1;
    }

    public boolean isFilteredByPriority() {
        selectFilter(TASK_OPTION_ID_PRIORITY.getValue(), TASKS_PRIORITY_FILTER, TASKS_PRIORITY_CHECKBOX_INPUT);
        return getTaskPriority().stream().distinct().count() <= 1;
    }

    public boolean isFilteredByStatus() {
        selectFilter(TASK_OPTION_ID_STATUS.getValue(), TASKS_STATUS_FILTER, TASKS_STATUS_CHECKBOX_INPUT);
        return getTaskStatus().stream().distinct().count() <= 1;
    }

    public boolean isFilteredByType() {
        selectFilter(TASK_OPTION_ID_TYPE.getValue(), TASKS_TYPE_FILTER, TASKS_TYPE_CHECKBOX_INPUT);
        return getTaskType().stream().distinct().count() <= 1;
    }

    public boolean isFilteredByTitle(String taskName) {
        searchForTask(taskName);
        return getTaskTitles().stream().distinct().count() <= 1;
    }

    public boolean isFilteredByAssignedTo(String assignedTo) {
        searchForAssignedTo(assignedTo);
        return getTaskAssigned().stream().distinct().count() <= 1;
    }

    public boolean isFilteredByTargetDateBefore(String date) {
        searchForTargetDate(date);
        if (welActions.getElement(LESS_TARGET_DATE_LESS).getAttribute(XLINK_HREF.getValue()).equals(ICON_LESS.getValue())) {
            welActions.click(LESS_TARGET_DATE_BUTTON);
        }
        LocalDate targetDate = LocalDate.parse(date);
        return getTaskCreated().stream()
            .map(LocalDate::parse)
            .noneMatch(dates -> dates.isAfter(targetDate));
    }

    public boolean isFilteredByTargetDateAfter(String date) {
        searchForTargetDate(date);
        if (welActions.getElement(LESS_TARGET_DATE_LESS).getAttribute(XLINK_HREF.getValue()).equals(ICON_GREATER.getValue())) {
            welActions.click(LESS_TARGET_DATE_BUTTON);
        }
        LocalDate targetDate = LocalDate.parse(date);
        return getTaskCreated().stream()
            .map(LocalDate::parse)
            .allMatch(dates -> dates.isBefore(targetDate));
    }

    public boolean isFilteredByCreatedDate() {
        String currentDate = DateUtils.getCurrentDate().toString();
        welActions.click(TASKS_TARGET_DATE_CLOSE);
        selectFilter(TASK_OPTION_ID_CREATED.getValue());
        searchForCreated(currentDate);
        if (welActions.getElement(LESS_TARGET_DATE_LESS).getAttribute(XLINK_HREF.getValue()).equals(ICON_GREATER.getValue())) {
            welActions.click(LESS_CREATED_DATE_BUTTON);
        }
        LocalDate targetDate = LocalDate.parse(currentDate);
        return getTaskCreated().stream()
            .map(LocalDate::parse)
            .noneMatch(date -> date.isAfter(targetDate));
    }

    public boolean isFilteredByMe() {
        String str;
        int pos;
        List<WebElement> elems = welActions.getElements(TASKS_TABLE_TITLECOL);
        List<String> tasksCreatorName = new ArrayList<>();
        for (WebElement elem: elems) {
            elem.click();
            str = welActions.getElement(TASKS_CREATOR_INFO).getText();
            pos = str.indexOf(')') + 1;
            tasksCreatorName.add(str.substring(0, pos));
        }
        return tasksCreatorName.stream().distinct().count() <= 1;
    }

    public boolean isFilteredByDescription(String descr) {
        List<WebElement> elems = welActions.getElements(TASKS_TABLE_TITLECOL);
        List<String> tasksDescription = new ArrayList<>();
        for (WebElement elem: elems) {
            elem.click();
            tasksDescription.add(getDetails().getDetailsDescriptionValue());
        }
        return tasksDescription.stream().anyMatch(s -> s.contains(descr));
    }

    public boolean overlapCheckForTitle(String taskName) {
        return getTaskTitles().stream().anyMatch(s -> s.contains(taskName));
    }

    public boolean isFilteredByTwoFilters(String title, String assignTo) {
        searchByTwoFilters(title, assignTo);
        return  (overlapCheckForTitle(title) && getTaskAssigned().stream().anyMatch(s -> s.equals(assignTo)));
    }

    public boolean saveOwnTitleFilter(String filterName) {
        return getCustomFilters().stream().anyMatch(s -> s.contains(filterName));
    }

    public boolean updateCustomFilter(String taskName, String filterName) {
        welActions.click(TASKS_MY_FILTER_DROPDOWN);
        List<WebElement> elems = welActions.getElements(TASKS_MY_FILTER_LIST);
        int randFilter = RandomUtils.getRandomIntBetween(0, elems.size() - 1);
        elems.get(randFilter).click();
        searchForTask(taskName);
        welActions.click(TASKS_SEARCH_BUTTON);
        welActions.click(TASKS_OPEN_FILTER_BUTTON);
        welActions.clearField(TASKS_SAVE_FILTER_NAME);
        inputFilterName(filterName);
        clickSaveFilterButton();
        return getCustomFilters().stream().anyMatch(s -> s.contains(filterName));
    }

    public String deleteCustomFilter() {
        welActions.click(TASKS_MY_FILTER_DROPDOWN);
        List<WebElement> elems = welActions.getElements(TASKS_MY_FILTER_LIST);
        int randFilter = RandomUtils.getRandomIntBetween(0, elems.size() - 1);
        String str =  elems.get(randFilter).getText();
        elems.get(randFilter).click();
        clickTrashButton();
        clickConfirmDeleteFilter();
        return str;
    }

    public boolean isCustomFilterDeleted(String filterName) {
        return getCustomFilters().stream().anyMatch(s -> s.contains(filterName));
    }

    public boolean isAdvancedSearchFormulaAppeared() {
        String str = welActions.getElement(TASKS_ADVANCED_INPUT_SEARCH).getAttribute(VALUE.getValue());
        return str.isEmpty();
    }

    public boolean isFilterCleared() {
        List<String> beforeClear = getTaskTitles();
        welActions.click(TASKS_FILTER_CLEAR_BUTTON);
        List<String> afterRemoveFilter = getTaskTitles();

        return beforeClear.containsAll(afterRemoveFilter) && afterRemoveFilter.containsAll(beforeClear);
    }

    public boolean permissionMsgVisibility() {
        return super.isElementVisibleOnPage(TASKS_PERMISSION_MSG);
    }

    public void clickXlsExport() {
        welActions.click(TASKS_XLS_BUTTON);
    }

    public void clickCsvExport() {
        welActions.click(TASKS_CSV_BUTTON);
    }

    public List<String> getTitles() {
        return welActions.getElementsText(TASKS_TITLES);
    }

    public List<String> getTaskId() {
        return welActions.getElements(TASKS_COLUMN_ATTRIBUTE).stream()
            .map(element -> element.getAttribute(DATA_TUID.getValue())).collect(Collectors.toList());
    }

    public List<String> getColumnValues() {
        return welActions.getElementsText(TASKS_COLUMN_VALUES).stream()
            .filter(Strings::isNotNullAndNotEmpty).collect(Collectors.toList());
    }

    public List<String> getWorkareaName() {
        List<String> workareaName = new ArrayList<>();
        for (WebElement elem: welActions.getElements(TASKS_COLUMN_ATTRIBUTE)) {
            elem.click();
            workareaName.add(welActions.getElement(TASKS_DETAILS_WORKAREA_NAME).getText());
        }

        return workareaName;
    }

    public List<String> getDescription() {
        List<String> descriptionList = new ArrayList<>();
        for (WebElement elem: welActions.getElements(TASKS_COLUMN_ATTRIBUTE)) {
            elem.click();
            if (Strings.isNotNullAndNotEmpty(getDetails().getDetailsDescriptionValue())) {
                descriptionList.add(getDetails().getDetailsDescriptionValue());
            }
        }
        return descriptionList;
    }

    public boolean isNumberOfEntitiesEquals(String name) {
        int index = welActions.getElement(TASKS_PAGINATION_VALUE).getText().indexOf("/");
        String size = welActions.getElement(TASKS_PAGINATION_VALUE).getText().substring(index + 2);
        return String.valueOf(readXls.getRowNumber(name) - 3).equals(size);
    }

    public List<String> getXlsRowData(int rowNum, String name) {
        return readXls.getDataByRow(rowNum, name);
    }

    public boolean isAllTitlesPresent(int rowNum, String name) {
        int ROW_NUMBER = 15;
        int size = getXlsRowData(rowNum, name).size();
        boolean isEmptyStringsPresent = getXlsRowData(rowNum, name).stream().anyMatch(Strings::isNotNullAndNotEmpty);
        return ROW_NUMBER == size && isEmptyStringsPresent;
    }

    public boolean isAllDataExportedXls(String name) {
        clickXlsExport();
        Stream<String> pageValues = Stream.of(getTaskId(), getWorkareaName(), getDescription(), getTaskTitles()).flatMap(Collection::stream);
        return readXls.readXlsFile(name).containsAll(pageValues.collect(Collectors.toList()));
    }

    public boolean isAllDataExportedCsv(String name) {
        clickCsvExport();
        Stream<String> pageValues = Stream.of(getTaskId(), getWorkareaName(), getDescription(), getTaskTitles()).flatMap(Collection::stream);
        return readCsv.getCsvValues(name).containsAll(pageValues.collect(Collectors.toList()));
    }

    public boolean isNumberOfEntitiesEqualsCsv(String name) {
        int index = welActions.getElement(TASKS_PAGINATION_VALUE).getText().indexOf("/");
        String size = welActions.getElement(TASKS_PAGINATION_VALUE).getText().substring(index + 2);
        return readCsv.getNumberOfRowCsv(name)  == Integer.parseInt(size) + 4;
    }
}
