package datango.ui.enums;

/**
 * Declares the constant used in element attribute names
 *
 * @author Sergey Kuzhel
 */
public enum  ElementAttribute {
    ID("id"),
    CLASS("class"),
    STYLE("style"),
    VALUE("value"),
    EXPANDED("aria-expanded"),
    DATA_ASSIGNMENT("data-assignment"),
    TITLE("title"),
    TEXT("textContent"),
    DATA_NULL("data-null"),
    XLINK_HREF("xlink:href"),
    DATA_TUID("data-tuid"),
    ARIA_SELECTED("aria-selected");

    private String name;

    ElementAttribute(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
