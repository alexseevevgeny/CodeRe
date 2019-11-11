package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static datango.ui.enums.ElementAttribute.TEXT;

/**
 * @author Sergey Kuzhel
 */
public class MainMenu extends BasePage {
    private static final String ADMINISTRATION_BUTTON = "menu.main.administration.link";
    private static final String ADMINISTRATION_WORKAREA_BUTTON = "menu.administration.workarea.link";
    private static final String ADMINISTRATION_STATUS_BUTTON = "menu.administration.status.link";
    private static final String ADMINISTRATION_PERMISSIONS_BUTTON = "menu.administration.permissions.link";
    private static final String ADMINISTRATION_ORGVIEW_BUTTON = "menu.administration.user.link";
    private static final String ADMINISTRATION_COURSEASSIGN_BUTTON = "menu.administration.courseassignments.link";
    private static final String ADMINISTRATION_REGCODES_BUTTON = "menu.administration.regcodes.link";
    private static final String ADMINISTRATION_SERVER_SETTINGS_BUTTON = "menu.administration.server.settings.link";
    private static final String TASKS_BUTTON = "menu.tasks.link";
    private static final String COURSES_BUTTON = "menu.courses.link";
    private static final String COURSE_APPROVAL_BUTTON = "menu.curseapproval.link";
    private static final String WORKAREAS_BUTTON = "menu.workareas.link";
    private static final String WORKAREAS_TEST = "menu.workareas.testbutton";
    private static final String WORKAREAS_LIST = "menu.workareas.list";
    private static final String SETTINGS_BUTTON = "menu.settings.link";
    private static final String LEARNING_REPORTER = "menu.learningreporter.link";
    private static final String LEARNING_REPORTER_LESSON = "menu.lreporter.lesson.link";

    MainMenu(WebDriver driver) {
        super(driver);
    }

    void clickAdministration() {
        welActions.click(ADMINISTRATION_BUTTON);
    }

    void clickWorkareasAdm() {
        welActions.click(ADMINISTRATION_WORKAREA_BUTTON);
    }

    void clickStatus() {
        welActions.click(ADMINISTRATION_STATUS_BUTTON);
    }

    void clickPermissions() {
        welActions.click(ADMINISTRATION_PERMISSIONS_BUTTON);
    }

    void clickRegCodes() {
        welActions.click(ADMINISTRATION_REGCODES_BUTTON);
    }

    void clickServerSettings() {
        welActions.click(ADMINISTRATION_SERVER_SETTINGS_BUTTON);
    }

    void clickUser() {
        welActions.click(ADMINISTRATION_ORGVIEW_BUTTON);
    }

    void clickCourseAssignments() {
        welActions.click(ADMINISTRATION_COURSEASSIGN_BUTTON);
    }

    void clickTasks() {
        welActions.click(TASKS_BUTTON);
    }

    void clickCourses() {
        welActions.click(COURSES_BUTTON);
    }

    void clickCourseApproval() {
        welActions.click(COURSE_APPROVAL_BUTTON);
    }

    void clickWorkareas() {
        welActions.click(WORKAREAS_BUTTON);
    }

    public void clickSettings() {
        welActions.click(SETTINGS_BUTTON);
    }

    public void clickLearningReporter() {
        welActions.click(LEARNING_REPORTER);
    }

    public void clickLessonReport() {
        welActions.click(LEARNING_REPORTER_LESSON);
    }

    public void clickWorkarea(String name) {
        welActions.moveToElement(WORKAREAS_TEST);
        List<WebElement> was = welActions.getElements(WORKAREAS_LIST);
        for (WebElement wa : was) {
            if (wa.getAttribute(TEXT.getValue()).equals(name)) {
                welActions.moveToElement(wa);
                welActions.click(wa, wa.getAttribute(TEXT.getValue()));
                break;
            }
        }
    }
}
