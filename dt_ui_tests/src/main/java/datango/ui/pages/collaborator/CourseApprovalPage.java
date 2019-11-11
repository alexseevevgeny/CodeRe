package datango.ui.pages.collaborator;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * @author Sergey Kuzhel
 */
public class CourseApprovalPage extends BasePage {
    private static final String TAB_MANDATORY = "courseapproval.page.mandatory.tab";
    private static final String TAB_OPTIONAL = "courseapproval.page.optional.tab";
    private static final String TABLE_MANDATORY = "courseapproval.table.mandatory.values";
    private static final String TABLE_OPTIONAL = "courseapproval.table.optional.values";
    private static final String TABLE_MANDATORY_NAMECOL = "courseapproval.mandatory.namecol.values";
    private static final String TABLE_OPTIONAL_NAMECOL = "courseapproval.optional.namecol.values";

    public CourseApprovalPage(WebDriver driver) {
        super(driver);
    }

    public void clickOptionalTab() {
        welActions.click(TAB_OPTIONAL);
    }

    public void clickMandatoryTab() {
        welActions.click(TAB_MANDATORY);
    }

    public List<String> getMandatoryCourses() {
        return welActions.getElementsText(TABLE_MANDATORY_NAMECOL);
    }

    public List<String> getOptionalCourses() {
        return welActions.getElementsText(TABLE_OPTIONAL_NAMECOL);
    }

    public void clickApproveMandatory(String name) {
        welActions.click(TABLE_MANDATORY, name, 13);
    }

    public void clickRejectMandatory(String name) {
        welActions.click(TABLE_MANDATORY, name, 14);
    }

    public void clickApproveOptinal(String name) {
        welActions.click(TABLE_OPTIONAL, name, 13);
    }

    public void clickRejectOptional(String name) {
        welActions.click(TABLE_OPTIONAL, name, 14);
    }

    public boolean isMandatoryCoursePresent(String entityName) {
        return getMandatoryCourses().stream().anyMatch( name -> name.equals(entityName));
    }

    public boolean isOptionalCoursePresent(String entityName) {
        return getOptionalCourses().stream().anyMatch( name -> name.equals(entityName));
    }
}
