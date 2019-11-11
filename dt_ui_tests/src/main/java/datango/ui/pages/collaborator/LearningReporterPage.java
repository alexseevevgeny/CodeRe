package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.EntityTree;
import org.openqa.selenium.WebDriver;

import static datango.ui.pages.collaborator.pageElements.EntityTree.ENTITY_TREE;

/**
 * @author Sergey Kuzhel
 */
public class LearningReporterPage extends BasePage {
    private static final String SECTION_LESSON = "lreporter.section.lesson.button";
    private static final String SECTION_USER = "lreporter.section.user.button";
    private static final String SECTION_TEST = "lreporter.section.test.button";
    private static final String SECTION_LESSON_WA_SELECT = "lreporter.lesson.workarea.select";
    private static final String IS_TIMEPEIOD_CHECKBOX = "lreporter.report.timeperiod.checkbox" ;
    private static final String PERIOD_FROM = "lreporter.timeperiod.from.input" ;
    private static final String PERIOD_TO = "lreporter.timeperiod.to.input" ;
    private static final String SELECT_BUTTON = "lreporter.report.select.button";
    private static final String REPORT_PERMISSION_MSG = "lreporter.permission.info.msg";

    EntityTree entityTree;

    public LearningReporterPage(WebDriver driver) {
        super(driver);
        this.entityTree = new EntityTree(driver);
    }

    public void clickLessonReport() {
        welActions.click(SECTION_LESSON);
    }

    public void clickUserReport() {
        welActions.click(SECTION_USER);
    }

    public void clickTestReport() {
        welActions.click(SECTION_TEST);
    }

    public void checkIsDemo() {
        //stub
    }

    public void checkIsPractice() {
        //stub
    }

    public void checkIsConcurrent() {
        //stub
    }

    public void checkIsTest() {
        //stub
    }

    public void checkIsBook() {
        //stub
    }

    public void checkIsTimePeriod() {
        if (!welActions.isChecked(IS_TIMEPEIOD_CHECKBOX)) {
            welActions.click(IS_TIMEPEIOD_CHECKBOX);
        }
    }

    public void selectRandomWorkarea() {
        welActions.select(SECTION_LESSON_WA_SELECT);
        entityTree.clickRootEntity(ENTITY_TREE);
    }

    public void selectWorkarea(String name) {
        welActions.selectByText(SECTION_LESSON_WA_SELECT, name);
        entityTree.clickRootEntity(ENTITY_TREE);
    }

    public void clickSelect() {
        welActions.click(SELECT_BUTTON);
    }

    public void setTimePeriod(String from, String to) {
        welActions.input(PERIOD_FROM, from);
        welActions.input(PERIOD_TO, to);
    }

    public void selectUser(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, EntityTree.USER);
    }

    public boolean permissionMsgVisibility() {
        return super.isElementVisibleOnPage(REPORT_PERMISSION_MSG);
    }

}
