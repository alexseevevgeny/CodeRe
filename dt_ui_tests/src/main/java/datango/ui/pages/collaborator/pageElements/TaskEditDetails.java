package datango.ui.pages.collaborator.pageElements;

import static datango.ui.enums.AttributeValues.DISPLAY_STYLE_NONE;
import static datango.ui.enums.ElementAttribute.CLASS;
import static datango.ui.enums.ElementAttribute.STYLE;
import static datango.ui.enums.ElementAttribute.TITLE;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import datango.ui.pages.BasePage;

/**
 * @author Sergey Kuzhel
 */
public class TaskEditDetails extends BasePage {
    private static final String DETAILS_TITLE_INPUT = "tasks.details.title.input";
    private static final String DETAILS_STATUS_SELECT = "tasks.details.status.select";
    private static final String DETAILS_TYPE_SELECT = "tasks.details.type.select";
    private static final String DETAILS_PRIORITY_SELECT = "tasks.details.priority.select";
    private static final String DETAILS_SAVE_BUTTON = "tasks.details.save.button";
    private static final String DETAILS_SAVECOMMENT_BUTTON = "tasks.details.savecomment.button";
    private static final String DETAILS_DESCRIPTION_INPUT = "tasks.details.description.input";
    private static final String DETAILS_ASSIGNTO_SELECT = "tasks.details.assignto.select";
    private static final String DETAILS_MILESTONE_SELECT = "tasks.details.milestone.select";
    private static final String DETAILS_TARGET_DATE = "tasks.details.targetdate.input";
    private static final String DETAILS_WATCHER_BUTTON = "tasks.details.watcher.button";
    private static final String DETAILS_EXPANDER = "tasks.details.expander";
    private static final String DETAILS_EXPANDER_BUTTON = "tasks.details.expander.button";
    private static final String DETAILS_SAVEMODAL_OK = "tasks.details.savemodal.button";
    private static final String DETAILS_IS_EMAILCHEKED = "tasks.details.checkbox.email";
    private static final String DETAILS_FORM = "tasks.details.modal.form";
    private static final String DETAILS_WATCHER_LIST = "tasks.details.watcherlist";
    private static final String DETAILS_WATCHER_REMOVE_BUTTON = "tasks.details.watcher.remove.button";
    private static final String DETAILS_MODAL_TITLE = "tasks.details.modal.title.input";
    private static final String DETAILS_MODAL_TEXT = "tasks.details.modal.text.input";
    private static final String DETAILS_MODAL = "tasks.details.comments.modal";
    private static final String DETAILS_EVENT_TABLE = "tasks.details.eventstable.log";
    private static final String DETAILS_WATCHER_TAB = "tasks.watcher.tab";
    private static final String WELCOME_USER_NAME = "menu.user.welcome.span";
    private static final String REG_EXP_DATE_TIME = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]";
    
    public TaskEditDetails(WebDriver driver) {
        super(driver);
    }
    
    public void inputTitle(String title) {
        welActions.input(DETAILS_TITLE_INPUT, title);
    }
    
    public String selectStatus() {
        return welActions.select(DETAILS_STATUS_SELECT);
    }
    
    public void selectTargetStatus(String statusName) {
        welActions.selectByText(DETAILS_STATUS_SELECT, statusName);
    }
    
    public void selectTargetType(String typeName) {
        welActions.selectByText(DETAILS_TYPE_SELECT, typeName);
    }
    
    public void selectTargetMilestone(String milestoneName) {
        welActions.selectByText(DETAILS_MILESTONE_SELECT, milestoneName);
    }
    
    public String selectType() {
        return welActions.select(DETAILS_TYPE_SELECT);
    }
    
    public String selectPriority() {
        return welActions.select(DETAILS_PRIORITY_SELECT);
    }
    
    public void clickSaveButton() {
        welActions.click(DETAILS_SAVE_BUTTON);
    }
    
    public boolean isSaveComment() {
        return isElementPresentOnPage(DETAILS_SAVECOMMENT_BUTTON);
    }
    
    public void inputDescription(String descript) {
        welActions.input(DETAILS_DESCRIPTION_INPUT, descript);
    }
    
    public String selectAndReturnAssignTo() {
        return welActions.select(DETAILS_ASSIGNTO_SELECT);
    }
    
    public String selectMilestone() {
        return welActions.select(DETAILS_MILESTONE_SELECT);
    }
    
    public void inputTargetDate(String targetDate) {
        welActions.input(DETAILS_TARGET_DATE, targetDate);
    }
    
    public void clickWatcherButton() {
        if (welActions.getElement(DETAILS_WATCHER_TAB).getAttribute(CLASS.getValue()).contains("w3-hide")) {
            welActions.click(DETAILS_WATCHER_BUTTON);
        }
    }
    
    public void expandWatcherTree() {
        driverManager.shortWaitForLoad();
        if (welActions.getElement(DETAILS_EXPANDER).getAttribute(STYLE.getValue()).equals(DISPLAY_STYLE_NONE.getValue())) {
            welActions.click(DETAILS_EXPANDER_BUTTON);
        } else {
            clickSaveButton();
        }
    }
    
    public void selectBlankForAllDetails() {
        welActions.select(DETAILS_TYPE_SELECT, "0");
        welActions.select(DETAILS_PRIORITY_SELECT, "0");
        welActions.select(DETAILS_MILESTONE_SELECT, "0");
        welActions.select(DETAILS_STATUS_SELECT, "0");
        welActions.select(DETAILS_ASSIGNTO_SELECT, "");
    }
    
    public void clickOkSaveModal() {
        welActions.click(DETAILS_SAVEMODAL_OK);
    }
    
    public void clearTargetDate() {
        welActions.clearField(DETAILS_TARGET_DATE);
    }
    
    public void clearDescription() {
        welActions.clearField(DETAILS_DESCRIPTION_INPUT);
    }
    
    public void uncheckEmailToWatcher() {
        if (welActions.getElement(DETAILS_FORM).isDisplayed() && welActions.isChecked(DETAILS_IS_EMAILCHEKED)
                && welActions.isClickable(DETAILS_IS_EMAILCHEKED)) {
            welActions.click(DETAILS_IS_EMAILCHEKED);
        }
    }
    
    public void removeWatchers() {
        List<WebElement> watcherList = welActions.getElements(DETAILS_WATCHER_LIST);
        for (WebElement watcher : watcherList) {
            watcher.click();
        }
    }
    
    public void clickRemove() {
        welActions.click(DETAILS_WATCHER_REMOVE_BUTTON);
    }
    
    public String getDetailsStatusValue() {
        return welActions.getSelectedValue(DETAILS_STATUS_SELECT);
    }
    
    public String getDetailsTypeValue() {
        return welActions.getSelectedValue(DETAILS_TYPE_SELECT);
    }
    
    public String getDetailsDescriptionValue() {
        return welActions.getInputValue(DETAILS_DESCRIPTION_INPUT);
    }
    
    public String getDetailsPriorityValue() {
        return welActions.getSelectedValue(DETAILS_PRIORITY_SELECT);
    }
    
    public String getDetailsMilestoneValue() {
        return welActions.getSelectedValue(DETAILS_MILESTONE_SELECT);
    }
    
    public String getDetailsTargetDateValue() {
        return welActions.getInputValue(DETAILS_TARGET_DATE);
    }
    
    public boolean isWatcherListEmpty() {
        clickWatcherButton();
        return isElementPresentOnPage(DETAILS_WATCHER_LIST);
    }
    
    public void inputTitleAndTextComment(String title, String text) {
        if (welActions.isPresent(DETAILS_MODAL) && !welActions.getElement(DETAILS_MODAL_TITLE).getText().isEmpty()) {
            welActions.clearField(DETAILS_MODAL_TITLE);
            welActions.input(DETAILS_MODAL_TITLE, title);
        }

        if (!welActions.getElement(DETAILS_MODAL_TEXT).getText().isEmpty()) {
            welActions.clearField(DETAILS_MODAL_TEXT);
            welActions.input(DETAILS_MODAL_TEXT, text);
        }
        
        welActions.input(DETAILS_MODAL_TITLE, title);
        welActions.input(DETAILS_MODAL_TEXT, text);
    }
    
    public boolean isChangeAddedToEventLog(String taskChange, String text) {
        List<WebElement> elem = welActions.getElements(DETAILS_EVENT_TABLE);
        for (WebElement row : elem) {
            if (!row.getText().contains(taskChange) && row.getAttribute(TITLE.getValue()).equals(text)) {
                return false;
            }
        }
        return true;
    }
    
    public void inputTextComment(String text) {
        if (welActions.isPresent(DETAILS_MODAL) && !welActions.getElement(DETAILS_MODAL_TEXT).getText().isEmpty()) {
                welActions.clearField(DETAILS_MODAL_TEXT);
                welActions.input(DETAILS_MODAL_TEXT, text);
        }
        
        welActions.input(DETAILS_MODAL_TEXT, text);
    }
    
    public boolean isTasksChangeLogged(String editedValue) {
        String str;
        String welcomeText = welActions.getElement(WELCOME_USER_NAME).getText();
        int nameStart = 9;
        int nameEnd = welcomeText.length() - 1;
        String name = welcomeText.substring(nameStart, nameEnd);
        List<WebElement> elem = welActions.getElements(DETAILS_EVENT_TABLE);
        for (WebElement row : elem) {
            if (row.getText().contains(editedValue) && row.getText().contains(name)) {
                str = row.getText().substring(row.getText().length() - 19);
                if (str.matches(REG_EXP_DATE_TIME))
                    return true;
            }
        }
        return false;
    }
    
    
}
