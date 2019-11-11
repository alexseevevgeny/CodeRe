package datango.ui.pages.collaborator.pageElements;

import datango.ui.pages.BasePage;
import datango.utils.RandomUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static datango.ui.enums.AttributeValues.COURSE_TYPE_OPTION_DIGITAL;
import static datango.ui.enums.ElementAttribute.TITLE;
import static datango.utils.RandomUtils.AUTOTESTS_PREFIX;

/**
 * @author Sergey Kuzhel
 */
public class CourseAssignmentEditDetails extends BasePage {
    private static final String SECTION1_BUTTON = "details.section1.button";
    private static final String SECTION1_CAPTION_INPUT = "details.section1.caption.input";
    private static final String SECTION1_DESCRIPTION_FIELD = "details.section1.description";
    private static final String SECTION1_TYPE_SELECT = "details.section1.type.select";
    private static final String SECTION1_IS_CHARGEABLE = "details.section1.ischargeable.checkbox";
    private static final String SECTION1_IS_ACTIVE = "details.section1.isactive.checkbox";
    private static final String SECTION1_IS_MANDATORY = "details.section1.ismandatory.checkbox";
    private static final String SECTION1_IS_CERTIFICATE = "details.section1.iscertificateforuser.checkbox";
    private static final String SECTION1_IS_REQAPPROVAL = "details.section1.isapproval.checkbox";
    private static final String SECTION1_IS_SHOWDESCRIPTION_CHECKBOX = "details.section1.showdescript.checkbox";
    private static final String SECTION1_COLOR_BUTTON = "details.section1.color.button";
    private static final String SECTION1_COLOR_INPUT = "details.section1.color.input";
    private static final String SECTION1_INPUT_WEIGHT = "details.section1.weight.input";
    private static final String SECTION1_COLOR_SELECTION = "details.section1.color.sp";
    private static final String SECTION2_BUTTON = "details.section2.button";
    private static final String SECTION2_RECURRING_BUTTON = "details.section2.recurring.button";
    private static final String SECTION3_BUTTON = "details.section3.button";
    private static final String SECTION3_WA_TREE = "details.section3.workareas.tree";
    private static final String SECTION4_BUTTON = "details.section4.button";
    private static final String SECTION3_ADDENTITY_BUTTON = "details.section3.entityadd.button";
    private static final String SECTION3_SELECTENTITY_SELECT = "details.section3.entityselect.select";
    private static final String SECTION3_SELECTENTITY_FIELD = "details.section3.assignmentedit.field";
    private static final String SECTION3_SELECTENTITY_BUTTON = "details.section3.entityselect.button";
    private static final String SECTION4_ASSIGNTO_BUTTON = "details.section4.assignto.button";
    private static final String SECTION4_USERS_TREE = "details.section4.users.tree";
    private static final String SECTION4_ENTITY_SELECT_BUTTON = "details.section4.entityselect.button";
    private static final String SECTION4_IS_USER_NOTIFY_AFTER_CA = "details.section4.notify.after.course.assign.checkbox";
    private static final String SECTION4_PERSONALIZED_NOTIFY_AFTER_CA = "details.section4.personalized.radio";
    private static final String DETAILS_SAVE_BUTTON = "details.save.button";
    private static final String DETAILS_CLOSE_ICON = "details.close.icon";
    private static final String DETAILS_DESCRIPTION_EDIT_MODAL = "details.section1.edit.modal";
    private static final String MODAL_OK_BUTTON = "details.section1.modal.okbutton";
    private static final String INPUT_SEARCH_VALUE = "details.section4.entitysearch.input";
    private static final String SEARCH_BUTTON = "details.section4.entitysearch.button";
    private static final String SECTION4_FOUNDED_ENTITY_LIST = "details.section4.users.list";
    private static final String SECTION4_PERSONALIZED_BUTTON = "details.section4.personalized.button";
    private static final String SECTION4_DELETE_ICON = "details.section4.delete.icon";

    private EntityTree entityTree;

    private static final String HEIGHT = "HEIGHT";
    private static final String WIDTH = "WIDTH";

    public CourseAssignmentEditDetails(WebDriver driver) {
        super(driver);
        this.entityTree = new EntityTree(driver);
    }

    public void inputCaption(String caption) {
        welActions.input(SECTION1_CAPTION_INPUT, caption);
        driverManager.shortWaitForLoad();
    }

    public void clickSection(int number) {
        switch (number) {
            case 1:
                welActions.click(SECTION1_BUTTON);
                break;
            case 2:
                welActions.click(SECTION2_BUTTON);
                break;
            case 3:
                welActions.click(SECTION3_BUTTON);
                break;
            case 4:
                welActions.click(SECTION4_BUTTON);
                break;
            default:
                welActions.click(SECTION1_BUTTON);
                break;
        }
        driverManager.shortWaitForLoad();
    }

    public void clickAddEntity() {
        welActions.click(SECTION3_ADDENTITY_BUTTON);
    }

    public void clickSelectionEntitySelect() {
        welActions.click(SECTION3_SELECTENTITY_BUTTON);
    }

    public void clickDetailsSaveButton() {
        driverManager.shortWaitForLoad();
        welActions.click(DETAILS_SAVE_BUTTON);
    }

    public void clickDetailsCloseIcon() {
        driverManager.shortWaitForLoad();
        welActions.click(DETAILS_CLOSE_ICON);
    }

    public void clickReccuringButton() {
        welActions.click(SECTION2_RECURRING_BUTTON);
    }

    public void clickDetailsDescription(){
        welActions.click(SECTION1_DESCRIPTION_FIELD);
    }

    public void clickColorButton() {
        welActions.click(SECTION1_COLOR_BUTTON);
    }

    public void checkIsChargeable() {
        welActions.click(SECTION1_IS_CHARGEABLE);
    }

    public void checkIsActive() {
        driverManager.shortWaitForLoad();
        welActions.click(SECTION1_IS_ACTIVE);
    }

    public void checkIsMandatory() {
        welActions.click(SECTION1_IS_MANDATORY);
    }

    public void checkIsCertificate() {
        welActions.click(SECTION1_IS_CERTIFICATE);
    }

    public void checkIsReqApproval() {
        welActions.click(SECTION1_IS_REQAPPROVAL);
    }

    public void selectEntityWorkarea() {
        driverManager.increaseWait(2);
        String waName = welActions.getSelectValues(SECTION3_SELECTENTITY_SELECT).stream()
                .filter(v -> v.contains(AUTOTESTS_PREFIX))
                .findFirst().get();
        welActions.selectByText(SECTION3_SELECTENTITY_SELECT, waName);
        entityTree.clickRootEntity(SECTION3_WA_TREE);
    }

    public void selectEntityWorkarea(String waName) {
        driverManager.increaseWait(2);
        welActions.selectByText(SECTION3_SELECTENTITY_SELECT, waName);
        entityTree.clickRootEntity(SECTION3_WA_TREE);
    }

    public String selectCourseType() {
        return welActions.select(SECTION1_TYPE_SELECT);
    }

    public void selectCourseTypeByDigital() {
        welActions.select(SECTION1_TYPE_SELECT, COURSE_TYPE_OPTION_DIGITAL.getValue());
    }

    public String getCourseType() {
        driverManager.waitForLoad();
        return welActions.getSelectedValue(SECTION1_TYPE_SELECT);
    }

    public String getColor() {
        driverManager.waitForLoad();
        clickColorButton();
        return welActions.getInputValue(SECTION1_COLOR_INPUT);
    }

    public String getDescription() {
        driverManager.waitForLoad();
        return welActions.getElement(SECTION1_DESCRIPTION_FIELD).getText();
    }

    public void assignTo(String name, String type, boolean viaSearch) {
        welActions.click(SECTION4_ASSIGNTO_BUTTON);
        if (viaSearch){
            searchWorkarea(name);
            entityTree.clickEntity(SECTION4_FOUNDED_ENTITY_LIST, name);
            welActions.click(SECTION4_ENTITY_SELECT_BUTTON);
        } else {
            entityTree.clickEntity(SECTION4_USERS_TREE, name, type);
            welActions.click(SECTION4_ENTITY_SELECT_BUTTON);
        }
    }

    public void inputCourseDescription(String description){
        welActions.input(DETAILS_DESCRIPTION_EDIT_MODAL, description);
    }

    public void clickOkModal(){
        welActions.click(MODAL_OK_BUTTON);
    }

    public void clickCheckboxDescription(){
        if(!welActions.getElement(SECTION1_IS_SHOWDESCRIPTION_CHECKBOX).isDisplayed()){
            welActions.click(SECTION1_IS_SHOWDESCRIPTION_CHECKBOX);
        }
    }

    public void clickUncheckboxDescription(){
        if(welActions.getElement(SECTION1_IS_SHOWDESCRIPTION_CHECKBOX).isDisplayed()){
            welActions.click(SECTION1_IS_SHOWDESCRIPTION_CHECKBOX);
        }
    }

    public String getTooltipTitle(){
       return welActions.getElement(SECTION3_SELECTENTITY_FIELD).getAttribute(TITLE.getValue());
    }

    public String inputWeight() {
        int rand = RandomUtils.getRandomIntBetween(0, 10000);
        String randInt = Integer.toString(rand);
        welActions.input(SECTION1_INPUT_WEIGHT, randInt);
        return randInt;
    }

    public Map<String, Integer> getLocation() {
        HashMap<String, Integer> colorDivParam = new HashMap<>();
        Dimension pt = welActions.getElement(SECTION1_COLOR_SELECTION).getSize();
        int countHeight = pt.getHeight() / 2;
        int countWidth = pt.getWidth() / 2;
        colorDivParam.put(HEIGHT, countHeight);
        colorDivParam.put(WIDTH, countWidth);
        return colorDivParam;
    }

    public void selectColor() {
        int xOffset = RandomUtils.getRandomIntBetween(-getLocation().get(HEIGHT), getLocation().get(HEIGHT));
        int yOffset = RandomUtils.getRandomIntBetween(-getLocation().get(WIDTH), getLocation().get(WIDTH));
        welActions.moveByOffset(SECTION1_COLOR_SELECTION, xOffset, yOffset);
    }

    public void searchWorkarea(String name) {
        welActions.input(INPUT_SEARCH_VALUE, name);
        welActions.click(SEARCH_BUTTON);
    }

    public void clickNotifyUserAfterCourseAssignment() {
        welActions.click(SECTION4_IS_USER_NOTIFY_AFTER_CA);
    }

    public void clickPersonalizedNotifyUserCA() {
        welActions.click(SECTION4_PERSONALIZED_NOTIFY_AFTER_CA);
    }

    public void clickPersonalizedButtonNotifyUserCA() {
        welActions.click(SECTION4_PERSONALIZED_BUTTON);
    }

    public void clickDeleteIcon(String name) {
        welActions.click(SECTION4_DELETE_ICON, name, 3);
    }
}

