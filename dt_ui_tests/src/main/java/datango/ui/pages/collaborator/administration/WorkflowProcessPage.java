package datango.ui.pages.collaborator.administration;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * @author Sergey Kuzhel
 */
public class WorkflowProcessPage extends BasePage {

    private static final String STATUS_TABLE = "workflow.status.table.values";
    private static final String STATUS_NAME_INPUT = "workflow.status.name.input";
    private static final String STATUS_ORDER_INPUT = "workflow.status.order.input";
    private static final String STATUS_ADD_ICON = "workflow.status.add.icon";
    private static final String STATUS_EDIT_ICON = "workflow.status.edit.icon";
    private static final String STATUS_ACTIVATE_CHECKBOX = "workflow.status.activate.checkbox";
    private static final String STATUS_DELETEALL_ICON = "workflow.status.deleteall.icon";
    private static final String STATUS_TB_NAMES = "workflow.status.namecol.values";
    private static final String STATUS_TB_ORDERS = "workflow.status.ordercol.values";
    private static final String STATUS_SAVE_ICON = "workflow.status.save.icon";
    private static final String MILESTONE_TABLE = "workflow.milestone.table.values";
    private static final String MILESTONE_NAME_INPUT = "workflow.milestone.name.input";
    private static final String MILESTONE_DESCR_INPUT = "workflow.milestone.description.input";
    private static final String MILESTONE_TARGETDATE_INPUT = "workflow.milestone.targetdate.input";
    private static final String MILESTONE_ADD_ICON = "workflow.milestone.add.icon";
    private static final String MILESTONE_EDIT_ICON = "workflow.milestone.edit.icon";
    private static final String MILESTONE_ACTIVATE_CHECKBOX = "workflow.milestone.activate.checkbox";
    private static final String MILESTONE_TB_NAMES = "workflow.milestone.namecol.values";
    private static final String MILESTONE_DELETEALL_ICON = "workflow.milestone.deleteall.icon";
    private static final String MILESTONE_SAVE_ICON = "workflow.milestone.save.button";
    private static final String TYPE_TABLE = "workflow.type.table.values";
    private static final String TYPE_NAME_INPUT = "workflow.type.name.input";
    private static final String TYPE_DESCR_INPUT = "workflow.type.description.input";
    private static final String TYPE_ADD_ICON = "workflow.type.add.icon";
    private static final String TYPE_EDIT_ICON = "workflow.type.edit.icon";
    private static final String TYPE_DELETEALL_ICON = "workflow.type.deleteall.icon";
    private static final String TYPE_TB_NAMES = "workflow.type.namecol.values";
    private static final String PRIORITY_TABLE = "workflow.priority.table.values";
    private static final String PRIORITY_NAME_INPUT = "workflow.priority.name.input";
    private static final String PRIORITY_ORDER_INPUT = "workflow.priority.order.input";
    private static final String PRIORITY_DESCR_INPUT = "workflow.priority.description.input";
    private static final String PRIORITY_ADD_ICON = "workflow.priority.add.icon";
    private static final String PRIORITY_EDIT_ICON = "workflow.priority.edit.icon";
    private static final String PRIORITY_TB_NAMES = "workflow.priority.namecol.values";
    private static final String PRIORITY_DELETEALL_ICON = "workflow.priority.deleteall.icon";

    public WorkflowProcessPage(WebDriver driver) {
        super(driver);
    }

    public void inputStatusName(String name) {
        welActions.input(STATUS_NAME_INPUT, name);
    }

    public void inputStatusOrder(String order) {
        welActions.input(STATUS_ORDER_INPUT, order);
    }

    public void clickStatusAddIcon() {
        welActions.click(STATUS_ADD_ICON);
    }

    public void clickStatus(String name) {
        welActions.click(STATUS_TABLE, name, 1);
    }

    public void clickStatusEditIcon() {
        welActions.click(STATUS_EDIT_ICON);
    }

    public void clickStatusCheck(String name) {
        welActions.click(STATUS_TABLE, name, -1);
    }

    public void clickStatusEditActivate() {
        welActions.click(STATUS_ACTIVATE_CHECKBOX);
    }

    public void clickStatusDeleteAllIcon() {
        welActions.click(STATUS_DELETEALL_ICON);
    }

    public void clickStatusActivate(String name) {
        welActions.click(STATUS_TABLE, name, 3);
    }

    public void clickMilestoneActivate(String name) {
        welActions.click(MILESTONE_TABLE, name, 4);
    }

    public void clickSaveMilestone() {
        welActions.click(MILESTONE_SAVE_ICON);
    }

    public void clickSaveStatus() {
        welActions.click(STATUS_SAVE_ICON);
    }

    public boolean isMilestoneActivated(String name) {
        return welActions.getElement(MILESTONE_TABLE, name, 4).isSelected();
    }

    public void clickMilestoneActivateModal() {
        welActions.click(MILESTONE_ACTIVATE_CHECKBOX);
    }

    public void clickStatusDelete(String name) {
        welActions.click(STATUS_TABLE, name, 5);
    }

    public void inputMilestoneName(String name) {
        welActions.input(MILESTONE_NAME_INPUT, name);
    }

    public void inputMilestoneDescr(String descr) {
        welActions.input(MILESTONE_DESCR_INPUT, descr);
    }

    public void inputMilestoneTargetDate(String date) {
        welActions.click(MILESTONE_TARGETDATE_INPUT);
        welActions.input(MILESTONE_TARGETDATE_INPUT, date);
    }

    public void clickMilestoneAddIcon() {
        welActions.click(MILESTONE_ADD_ICON);
    }

    public void clickMilestoneEditIcon() {
        welActions.click(MILESTONE_EDIT_ICON);
    }

    public void clickMilestone(String name) {
        welActions.click(MILESTONE_TABLE, name, 0);
    }

    public void clickMilestoneCheck(String name) {
        welActions.click(MILESTONE_TABLE, name, -1);
    }

    public void clickMilestoneDeleteIcon(String name) {
        welActions.click(MILESTONE_TABLE, name, 6);
    }

    public void clickMilestoneDeleteAllIcon() {
        welActions.click(MILESTONE_DELETEALL_ICON);
    }

    public void inputTypeName(String name) {
        welActions.input(TYPE_NAME_INPUT, name);
    }

    public void inputTypeDescription(String descr) {
        welActions.input(TYPE_DESCR_INPUT, descr);
    }

    public void clickTypeCheck(String name) {
        welActions.click(TYPE_TABLE, name, -1);
    }

    public void clickType(String name) {
        welActions.click(TYPE_TABLE, name, 1);
    }

    public void clickTypeDelete(String name) {
        welActions.click(TYPE_TABLE, name, 3);
    }

    public void clickTypeAddIcon() {
        welActions.click(TYPE_ADD_ICON);
    }

    public void clickTypeEditIcon() {
        welActions.click(TYPE_EDIT_ICON);
    }

    public void clickTypeDeleteAllIcon() {
        welActions.click(TYPE_DELETEALL_ICON);
    }

    public void clickPriorityAddIcon() {
        welActions.click(PRIORITY_ADD_ICON);
    }

    public void clickPriorityEditIcon() {
        welActions.click(PRIORITY_EDIT_ICON);
    }

    public void inputPriorityName(String name) {
        welActions.input(PRIORITY_NAME_INPUT, name);
    }

    public void inputPriorityWeight(String weight) {
        welActions.input(PRIORITY_ORDER_INPUT, weight);
    }

    public void inputPriorityDescription(String descr) {
        welActions.input(PRIORITY_DESCR_INPUT, descr);
    }

    public void clickPriority(String name) {
        welActions.click(PRIORITY_TABLE, name, 1);
    }

    public void clickPriorityCheck(String name) {
        welActions.click(PRIORITY_TABLE, name, -1);
    }

    public void clickPriorityDelete(String name) {
        welActions.click(PRIORITY_TABLE, name, 3);
    }

    public void clearPriorityName(){
        welActions.clearField(PRIORITY_NAME_INPUT);
    }

    public void clearPriorityOrder(){
        welActions.clearField(PRIORITY_ORDER_INPUT);
    }

    public void clickPriorityDeleteAllIcon(){
        welActions.click(PRIORITY_DELETEALL_ICON);
    }

    public List<String> getStatusNames() {
        return welActions.getElementsText(STATUS_TB_NAMES);
    }

    public List<String> getStatusOrders() {
        return welActions.getElementsText(STATUS_TB_ORDERS);
    }

    public List<String> getMilestoneNames() {
        return welActions.getElementsText(MILESTONE_TB_NAMES);
    }

    public List<String> getTypeNames() {
        return welActions.getElementsText(TYPE_TB_NAMES);
    }

    public List<String> getPriorityNames() {
        return welActions.getElementsText(PRIORITY_TB_NAMES);
    }

    public String addNewType(String name, String descr) {
        inputTypeName(name);
        inputTypeDescription(descr);
        clickTypeAddIcon();
        return name;
    }

    public String addNewStatus(String name, String order) {
        inputStatusName(name);
        inputStatusOrder(order);
        clickStatusAddIcon();
        return name;
    }

    public void addNewPriority(String name, String weight, String descr) {
        inputPriorityName(name);
        inputPriorityWeight(weight);
        inputPriorityDescription(descr);
        clickPriorityAddIcon();
    }

    public String addNewMilestone(String tDate, String name, String descr) {
        inputMilestoneTargetDate(tDate);
        inputMilestoneName(name);
        inputMilestoneDescr(descr);
        welActions.click(MILESTONE_NAME_INPUT);
        driverManager.shortWaitForLoad();
        clickMilestoneAddIcon();
        return name;
    }

    public void multipleSelectionStatus() {
        for (String iter: getStatusNames()) {
            if (iter.contains("adt")) {
                clickStatusCheck(iter);
            }
        }
    }

    public void multipleSelectionType() {
        for (String iter: getTypeNames()) {
            if (iter.contains("adt")) {
                clickTypeCheck(iter);
            }
        }
    }

    public void multipleSelectionMilestones() {
        for (String iter: getMilestoneNames()) {
            if (iter.contains("adt")) {
                clickMilestoneCheck(iter);
            }
        }
    }

}

