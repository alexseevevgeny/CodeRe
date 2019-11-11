package datango.ui.enums;

public enum AttributeValues {
    DISPLAY_STYLE_NONE("display: none;"),
    TASK_OPTION_ID_MILESTONE("aSWTaskTaskdscBasicOptionTask_milestone"),
    TASK_OPTION_ID_TARGET_DATE("aSWTaskTaskdscBasicOptionTask_targetDate"),
    TASK_OPTION_ID_CREATOR("aSWTaskTaskdscBasicOptionCreator"),
    TASK_OPTION_ID_PRIORITY("aSWTaskTaskdscBasicOptionTask_priority"),
    TASK_OPTION_ID_DESCRIPTION("aSWTaskTaskdscBasicOptionTask_desc"),
    TASK_OPTION_ID_ASSIGNEE("aSWTaskTaskdscBasicOptionAssignee"),
    TASK_OPTION_ID_CREATED("aSWTaskTaskdscBasicOptionTask_created"),
    TASK_OPTION_ID_TITLE("aSWTaskTaskdscBasicOptionTask_title"),
    TASK_OPTION_ID_TYPE("aSWTaskTaskdscBasicOptionTask_type"),
    TASK_OPTION_ID_OBJECT_TYPE("aSWTaskTaskdscBasicOptionEntity_type"),
    TASK_OPTION_ID_STATUS("aSWTaskTaskdscBasicOptionTask_status"),
    ICON_LESS("#icon-lessThan"),
    ICON_GREATER("#icon-greaterThan"),
    COURSE_TYPE_OPTION_DIGITAL("digital"),
    COURSE_TYPE_OPTION_CLASSROOM("classroom"),
    COURSE_TYPE_OPTION_BLENDED("blended"),
    CA_OPTION_ID_WEIGHT("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_weight"),
    CA_OPTION_ID_DESCRIPTION("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_description"),
    CA_OPTION_ID_REQUIRE_APPROVAL("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_needsApproval"),
    CA_OPTION_ID_RECURRING("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_isRecurring"),
    CA_OPTION_ID_COLOR("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_colorCode"),
    CA_OPTION_ID_CHARGEABLE("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_isChargeable"),
    CA_OPTION_ID_MANDATORY("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_isMandatory"),
    CA_OPTION_ID_COURSETYPE("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_courseType"),
    CA_OPTION_ID_ACTIVE("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_active"),
    CA_OPTION_ID_CERTIFICATE("aSWCourseAssignementCourseAssignmentdscBasicOptionAssignment_allowCertificate"),
    COURSE_OPTION_ID_CAPTION("aSWLearnerAssignmentdscBasicOptionAssignment_caption"),
    COURSE_OPTION_ID_WEIGHT("aSWLearnerAssignmentdscBasicOptionAssignment_weight"),
    COURSE_OPTION_ID_DESC("aSWLearnerAssignmentdscBasicOptionAssignment_desc"),
    COURSE_OPTION_ID_IS_CHARGEABLE("aSWLearnerAssignmentdscBasicOptionAssignment_isChargeable"),
    COURSE_OPTION_ID_IS_COLORCODE("aSWLearnerAssignmentdscBasicOptionAssignment_colorCode"),
    MOVE_TO_OU("moveToOu"),
    DETAIL_GROUP_CLASS("w3-small w3-padding w3-border w3-theme-border w3-theme-button");

    private String name;

    AttributeValues(String name) {this.name = name;}

    public String getValue() {
        return name;
    }

}
