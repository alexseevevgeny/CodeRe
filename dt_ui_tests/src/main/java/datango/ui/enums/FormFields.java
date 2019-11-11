package datango.ui.enums;

/**
 * Declares the constants used in form filed names
 * @author Sergey Kuzhel
 */
public enum FormFields {
    LOGIN("login"),
    PASSWORD("password"),
    NAME("name"),
    LAST_NAME("lastName"),
    EMAIL("email");

    private String name;

    FormFields(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
