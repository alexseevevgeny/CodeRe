package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import datango.utils.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.enums.AttributeValues.COURSE_OPTION_ID_IS_CHARGEABLE;
import static datango.ui.enums.AttributeValues.COURSE_OPTION_ID_WEIGHT;
import static datango.ui.enums.ElementAttribute.ID;

/**
 * @author Sergey Kuzhel
 */
public class CoursesPage extends BasePage {
    private static final String TAB_MANDATORY_COURSES = "courses.page.mandatory.tab";
    private static final String TAB_OPTIONAL_COURSES = "courses.page.optional.tab";
    private static final String TAB_PAST_COURSES = "courses.page.past.tab";
    private static final String TABLE_MANDATORY_NAMES = "courses.mandatory.namecol.values";
    private static final String TABLE_OPTIONAL_NAMES = "courses.optional.namecol.values";
    private static final String TABLE_OPTIONAL_APPROVAL_NAMES = "courses.optional.approval.namecol.values";
    private static final String TABLE_PAST_NAMES = "courses.past.namecol.values";
    private static final String TABLE_MANDATORY_APPROVAL = "courses.mandatoryapproval.table.values";
    private static final String TABLE_MANDATORY= "courses.mandatory.table.values";
    private static final String TABLE_OPTIONAL_APPROVAL = "courses.optionalapproval.table.values";
    private static final String TABLE_OPTIONAL_ROWS = "courses.page.optional.tab.row";
    private static final String DESCRIPTION_POPUP = "course.description.popup";
    private static final String PERMISSION_INFO_MSG = "course.permission.info.msg";
    private static final String CAPTION_INPUT_FIELD = "course.search.caption.input";
    private static final String DESCRIPTION_INPUT_FIELD = "course.search.description.input";
    private static final String WEIGHT_INPUT_FIELD = "course.search.weight.input";
    private static final String COLOR_LIST_FIELD_LABEL = "course.search.color.label";
    private static final String COLOR_LIST_FIELD_INPUT = "course.search.color.input";
    private static final String FILTER_MORE_BUTTON = "course.search.filter.more.button";
    private static final String SEARCH_BUTTON = "course.search.button";
    private static final String SEARCH_FILTER_LIST = "course.filter.option.list";
    private static final String COLOR_FILTER = "course.search.color.list";
    private static final String SWITCH_BUTTON_SEARCH = "course.search.switch.button";
    private static final String ADVANCED_INPUT_FIELD = "course.search.advanced.input";
    private static final String CLEAR_FILTER_BUTTON = "course.search.clear.button";
    private static final String SAVE_FILTER_BUTTON = "course.search.save.filter";
    private static final String COURSE_MY_FILTER_LIST = "course.search.myfilter.list";
    private static final String INPUT_FILTER_NAME = "user.modal.filter.name.input";
    private static final String SAVE_MODAL_BUTTON = "user.save.filter.ok";
    private static final String MY_FILTER_DROPDOWN = "course.search.myfilter.button";
    private static final String DELETE_FILTER_BUTTON = "course.search.delete.button";
    private static final String OPTIONAL_DESCRIPTION_ICON = ".//*[@*='#icon-infoCircle']";


    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public void clickMandatoryCoursesTab() {
        welActions.click(TAB_MANDATORY_COURSES);
    }

    public void clickOptionCoursesTab() {
        welActions.click(TAB_OPTIONAL_COURSES);
    }

    public void clickPastCoursesTab() {
        welActions.click(TAB_PAST_COURSES);
    }

    public List<String> getMandatoryCourses() {
        return welActions.getElementsText(TABLE_MANDATORY_NAMES);
    }

    public List<String> getOptionalCourses() {
        return welActions.getElementsText(TABLE_OPTIONAL_NAMES);
    }

    public List<String> getOptionalApprovalCourses() {
        return welActions.getElementsText(TABLE_OPTIONAL_APPROVAL_NAMES);
    }

    public List<String> getPastCourses() {
        return welActions.getElementsText(TABLE_PAST_NAMES);
    }

    public void clickApplyOptionalCourse(String name) {
        welActions.click(TABLE_OPTIONAL_APPROVAL, name, 2);
    }

    public void clickApplyMandatoryCourse(String name) {
        welActions.click(TABLE_MANDATORY_APPROVAL, name, 2);
    }

    public void clickCertificateMandatoryCourse(String name) {
        welActions.click(TABLE_MANDATORY, name, 2);
    }

    public void courseOpenPopup(String name) {
        List<WebElement> rows = welActions.getElements(TABLE_OPTIONAL_ROWS);
        for (WebElement row : rows) {
            if (row.getText().contains(name)) {
                driverManager.shortWaitForLoad();
                welActions.click(row.findElement(By.xpath(OPTIONAL_DESCRIPTION_ICON)), null);
                break;
            }
        }
    }

    public boolean descrIconNotVisible(String name) {
        boolean courseRow = false;
        List<WebElement> rows = welActions.getElements(TABLE_OPTIONAL_ROWS);
        for (WebElement row : rows) {
            if (row.getText().equals(name)) {
                courseRow = welActions.isPresent(row,OPTIONAL_DESCRIPTION_ICON); }
        }
        return courseRow;
    }

    public boolean popupIsPresent(){
        driverManager.shortWaitForLoad();
        return welActions.getElement(DESCRIPTION_POPUP).isDisplayed();
    }

    public boolean permissionMsgVisibility() {
        return super.isElementVisibleOnPage(PERMISSION_INFO_MSG);
    }

    public boolean checkAvailabilityPastCourses() {
        if (getPastCourses().isEmpty()) {
            return true;
        } else return !getPastCourses().isEmpty();
    }

    public void inputCaption(String text) {
        welActions.input(CAPTION_INPUT_FIELD, text);
        welActions.click(SEARCH_BUTTON);
    }

    public void inputDescription(String text) {
        welActions.input(DESCRIPTION_INPUT_FIELD, text);
        welActions.click(SEARCH_BUTTON);
    }

    public void inputWeight(String weight) {
        selectFilter(COURSE_OPTION_ID_WEIGHT.getValue());
        welActions.input(WEIGHT_INPUT_FIELD, weight);
        welActions.click(SEARCH_BUTTON);
    }

    public void selectChargeableFilter() {
        selectFilter(COURSE_OPTION_ID_IS_CHARGEABLE.getValue());
        welActions.click(SEARCH_BUTTON);
    }

    public void clickColorFilter() {
        welActions.click(COLOR_FILTER);
    }

    public void selectColor(String color) {
        int index = -1;
        List<WebElement> elems = welActions.getElements(COLOR_LIST_FIELD_LABEL);
        for (WebElement elem: elems) {
            if (elem.getText().contains(color)) {
                index = elems.indexOf(elem);
            }
        }
        welActions.getElements(COLOR_LIST_FIELD_INPUT).get(index).click();
    }

    private void selectFilter(String filterNameId) {
        welActions.click(FILTER_MORE_BUTTON);
        List<WebElement> elems = welActions.getElements(SEARCH_FILTER_LIST);
        for (WebElement elem: elems) {
            if (elem.getAttribute(ID.getValue()).equals(filterNameId)) {
                elem.click();
            }
        }
    }

    public void clickSwitchButton() {
        welActions.click(SWITCH_BUTTON_SEARCH);
    }

    public boolean isSwitchedToAdvancedSearch() {
        clickSwitchButton();
        return welActions.isPresent(ADVANCED_INPUT_FIELD);
    }

    public void clickClearFilter() {
        welActions.click(CLEAR_FILTER_BUTTON);
    }

    public void clickSaveButton() {
        welActions.click(SAVE_FILTER_BUTTON);
    }

    public void inputFilterName(String text) {
        welActions.input(INPUT_FILTER_NAME, text);
    }

    public void clickSaveModalButton() {
        welActions.click(SAVE_MODAL_BUTTON);
    }

    public void clickMyFilter() {
        welActions.click(MY_FILTER_DROPDOWN);
    }

    public void saveMyFilter(String weight, String filterName) {
        inputWeight(weight);
        clickSaveButton();
        inputFilterName(filterName);
        clickSaveModalButton();
        clickMyFilter();
    }

    public List<String> getMyFiltersText() {
        return welActions.getElementsText(COURSE_MY_FILTER_LIST);
    }

    public boolean updateCustomFilter(String weight, String filterName) {
        String updWeight = RandomUtils.getRandomString(2, NUMERIC, false);
        String updFilterName = RandomUtils.getRandomString(8, ENGLISH_ALPHABET, true);
        saveMyFilter(weight, filterName);
        welActions.getElements(COURSE_MY_FILTER_LIST).stream()
            .filter(element -> element.getText().contains(filterName))
            .forEach(WebElement::click);
        welActions.input(WEIGHT_INPUT_FIELD, updWeight);
        welActions.click(SEARCH_BUTTON);
        clickSaveButton();
        inputFilterName(updFilterName);
        clickSaveModalButton();
        clickMyFilter();

        return getMyFiltersText().stream().anyMatch(s -> s.contains(updFilterName));
    }

    public void selectCustomFilter(String filterName) {
        List<WebElement> elems = welActions.getElements(COURSE_MY_FILTER_LIST);
        for (WebElement elem: elems) {
            if (elem.getText().equals(filterName)) {
                elem.click();
            }
        }
    }

    public void clickDeleteFilter() {
        welActions.click(DELETE_FILTER_BUTTON);
    }
}
