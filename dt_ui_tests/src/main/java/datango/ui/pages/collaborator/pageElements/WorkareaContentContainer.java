package datango.ui.pages.collaborator.pageElements;

import datango.enums.CharacterSet;
import datango.ui.pages.BasePage;
import datango.utils.RandomUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuzhel
 */
public class WorkareaContentContainer extends BasePage {
    private static final String MENUEDIT_BUTTON = "workarea.content.menuedit.button";
    private static final String STARTEDIT_BUTTON = "workarea.content.startedit.button";
    private static final String STARTEDIT_ALL_BUTTON = "workarea.content.starteditall.button";
    private static final String FINISHEDIT_BUTTON = "workarea.content.finishedit.button";
    private static final String FINISHEDIT_ALL_BUTTON = "workarea.content.finisheditall.button";
    private static final String CONTROL_FOLDER = "workarea.content.new.button";
    private static final String CONTROL_NEW_GROUP = "workarea.new.group.link";
    private static final String CONTROL_NEW_PROJECT = "workarea.new.project.link";
    private static final String CONTROL_NEW_BOOK = "workarea.new.book.link";
    private static final String CONTROL_NEW_SLIDE = "workarea.new.slide.link";
    private static final String CONTROL_RESTORE = "workarea.content.restore.button";
    private static final String CONTROL_DELETE = "workarea.content.delete.button";
    private static final String CONTROL_DELETE_FINALLY = "workarea.content.deletefinally.button";
    private static final String CONTROL_EMPTY_TRASH = "workarea.content.emptytrash.button";
    private static final String ENTITY_ADD_NAME = "workarea.entityadd.name.input";
    private static final String ENTITY_OK_BUTTON = "workarea.entityadd.ok.button";
    private static final String ENTITY_SAVE_BUTTON = "workarea.entitysave.ok.button";
    private static final String IS_SEND_MAIL_EN = "workarea.entitysave.sendmail.checkbox";
    private static final String IS_SEND_MAIL_COM = "workarea.comment.sendmail.checkbox";
    private static final String CONTENT_HEADER = "workarea.content.header.text";
    private static final String TAB_OVERVIEW = "workarea.content.overview.tab";
    private static final String OVERVIEW_CAPTION_INPUT = "workarea.overview.caption.input";
    private static final String OVERVIEW_SAVE_BUTTON = "workarea.overview.save.button";
    private static final String TAB_WORKFLOW = "workarea.content.workflow.tab";
    private static final String WORKFLOW_TITLE_INPUT = "workarea.workflow.title.input";
    private static final String WORKFLOW_TYPE_SELECT = "workarea.workflow.type.select";
    private static final String WORKFLOW_PRIORITY_SELECT = "workarea.workflow.priority.select";
    private static final String WORKFLOW_STATUS_SELECT = "workarea.workflow.status.select";
    private static final String WORKFLOW_SAVE_BUTTON = "workarea.workflow.save.button";
    private static final String COMMENT_SAVE_BUTTON = "workarea.comment.save.button";
    private static final String TAB_COURSE = "workarea.content.course.tab";
    private static final String MENUPUBLISH_BUTTON = "workarea.content.menupublish.button";
    private static final String PUBLISH_ALL_BUTTON = "workarea.content.publishall.button";
    private static final String VERSION_LABEL_INFO = "workarea.version.info";
    private static final String VERSION_TABLE = "workarea.version.table";
    private static final String VERSION_LINK_BUTTON = "workarea.version.link";
    private static final String VIEW_PUBLISHED_CHECKBOX = "workarea.view.published.checkbox";
    private static final String WORKAREA_MSG = "workarea.permission.msg";

    public WorkareaContentContainer(WebDriver driver) {
        super(driver);
    }

    public void clickStartEditing() {
        welActions.click(STARTEDIT_BUTTON);
    }

    public void clickStartEditingAll() {
        welActions.moveToElement(MENUEDIT_BUTTON);
        welActions.click(STARTEDIT_ALL_BUTTON);
    }

    public void clickPublishAll() {
        if (welActions.isClickable(MENUPUBLISH_BUTTON)) {
            welActions.moveToElement(MENUPUBLISH_BUTTON);
            welActions.click(PUBLISH_ALL_BUTTON);
        }
    }

    public void clickViewPublished() {
        welActions.click(VIEW_PUBLISHED_CHECKBOX);
    }

    public String getVersionStatus() {
        return welActions.getElement(VERSION_LABEL_INFO).getText();
    }

    public void openVersionModal() {
        welActions.click(VERSION_LINK_BUTTON);
    }

    public void clickOnShowVersionButton(String name) {
        welActions.click(VERSION_TABLE, name, 4);
    }

    public void clickPublishEntity() {
        welActions.click(MENUPUBLISH_BUTTON);
    }

    public void clickFinishEditing() {
        welActions.click(FINISHEDIT_BUTTON);
        returnSaveEntity();
    }

    private void returnSaveEntity() {
        driverManager.shortWaitForLoad();
        if (welActions.isChecked(IS_SEND_MAIL_EN)
            && welActions.isClickable(IS_SEND_MAIL_EN)) {
            welActions.click(IS_SEND_MAIL_EN);
        } else if (welActions.isChecked(IS_SEND_MAIL_COM)
            && welActions.isClickable(IS_SEND_MAIL_COM)) {
            welActions.click(IS_SEND_MAIL_COM);
        }
        for (int i = 0; i < 2; i++) {
            if (welActions.isClickable(ENTITY_SAVE_BUTTON)) {
                welActions.click(ENTITY_SAVE_BUTTON);
            } else if (welActions.isClickable(COMMENT_SAVE_BUTTON)) {
                welActions.click(COMMENT_SAVE_BUTTON);
            }
        }

    }

    public void addFolder(String name) {
        welActions.click(CONTROL_FOLDER);
        welActions.input(ENTITY_ADD_NAME, name);
        welActions.click(ENTITY_OK_BUTTON);
    }

    //TODO: will be removed/updated die new business logic
    public void addGroup(String name) {
        welActions.click(CONTROL_FOLDER);
        welActions.click(CONTROL_NEW_GROUP);
        welActions.input(ENTITY_ADD_NAME, name);
        welActions.click(ENTITY_OK_BUTTON);
    }

    //TODO: will be removed/updated die new business logic
    public void addProject(String name) {
        welActions.click(CONTROL_FOLDER);
        welActions.click(CONTROL_NEW_PROJECT);
        welActions.input(ENTITY_ADD_NAME, name);
        welActions.click(ENTITY_OK_BUTTON);
    }

    public boolean isAddOptionActive() {
        return welActions.isClickable(CONTROL_FOLDER);
    }

    //TODO: will be removed/updated die new business logic
    public void addBook(String name) {
        welActions.click(CONTROL_FOLDER);
        welActions.click(CONTROL_NEW_BOOK);
        welActions.input(ENTITY_ADD_NAME, name);
        welActions.click(ENTITY_OK_BUTTON);
    }

    //TODO: will be removed/updated die new business logic
    public void addSlide(String name) {
        welActions.click(CONTROL_FOLDER);
        welActions.click(CONTROL_NEW_SLIDE);
        welActions.input(ENTITY_ADD_NAME, name);
        welActions.click(ENTITY_OK_BUTTON);
    }

    public void clickOverviewTab() {
        welActions.click(TAB_OVERVIEW);
    }

    public void clickWorkflowTab() {
        welActions.click(TAB_WORKFLOW);
    }

    public void clickCoursesTab() {
        welActions.click(TAB_COURSE);
    }

    public void inputCaption(String text) {
        welActions.input(OVERVIEW_CAPTION_INPUT, text);
    }

    public void clickSaveOverView() {
        welActions.click(CONTENT_HEADER);
        welActions.click(OVERVIEW_SAVE_BUTTON);
        returnSaveEntity();
    }

    public void clickFinishEditingAll() {
        welActions.moveToElement(MENUEDIT_BUTTON);
        welActions.click(FINISHEDIT_ALL_BUTTON);
    }

    public void clickDelete() {
        welActions.click(CONTROL_DELETE);
    }

    public void clickDeleteFinally() {
        welActions.click(CONTROL_DELETE_FINALLY);
    }

    public void clickEmptyTrash() {
        welActions.moveToElement(CONTROL_DELETE_FINALLY);
        welActions.click(CONTROL_EMPTY_TRASH);
    }

    public void clickRestore() {
        welActions.click(CONTROL_RESTORE);
    }

    public void selectWorkflowValues() {
        welActions.input(WORKFLOW_TITLE_INPUT, RandomUtils.getRandomString(8, CharacterSet.ENGLISH_ALPHABET, true));
        welActions.select(WORKFLOW_TYPE_SELECT);
        welActions.select(WORKFLOW_PRIORITY_SELECT);
        welActions.select(WORKFLOW_STATUS_SELECT);
    }

    public void selectPriorityFromList(String priorityName){
        clickWorkflowTab();
        welActions.select(WORKFLOW_PRIORITY_SELECT);
    }

    public void selectTypeFromList(String taskName){
        clickWorkflowTab();
        welActions.selectByText(WORKFLOW_TYPE_SELECT, taskName);
    }

    public List<String> getWorkwlowValues() {
        List<String> vals = new ArrayList<>();
        vals.add(welActions.getInputValue(WORKFLOW_TITLE_INPUT));
        vals.add(welActions.getSelectedValue(WORKFLOW_TYPE_SELECT));
        vals.add(welActions.getSelectedValue(WORKFLOW_PRIORITY_SELECT));
        vals.add(welActions.getSelectedValue(WORKFLOW_STATUS_SELECT));
        return vals;
    }

    public void saveWorkflowTab() {
        welActions.click(WORKFLOW_SAVE_BUTTON);
        returnSaveEntity();
    }

    public boolean permissionMsgVisibility() {
        return super.isElementVisibleOnPage(WORKAREA_MSG);
    }
}
