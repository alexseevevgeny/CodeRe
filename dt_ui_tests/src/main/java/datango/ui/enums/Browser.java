package datango.ui.enums;

/**
 * Browser type enum
 * @author Sergey Kuzhel
 */
public enum Browser {
    FIREFOX("firefox"),
    CHROME("chrome"),
    IE("ie");

    private String browserType;

    Browser(String browserType) {
        this.browserType = browserType;
    }

    public String getValue() {
        return browserType;
    }


}
