package datango.ui.data;

import org.testng.annotations.DataProvider;

/**
 * @author Sergey Kuzhel
 */
public final class InputValuesProvider {

    public static final String TEST_DESCRIPTION = " descr";
    public static final String TEST_EDIT = " edit";
    public static final String TEST_EMPTY = "";
    public static final String TEST_SPACE = " ";
    public static final String TEST_FIRST_NAME = "adtFirstName";
    public static final String TEST_LAST_NAME = "adtLastName";
    public static final String TEST_MIDDLE_NAME = "adtMiddleName";
    public static final String TEST_EMAIL = "col01@datango.com";
    public static final String TEST_REG_CODE = "3333";
    public static final String TEST_PHONE_NUMBER = "0335-4166961";
    public static final String GERMAN_TEXT = "einstellungen";
    public static final String DEUTSCH = "Deutsch";
    public static final String HTML_CODE = "<b style=\"font-size:2em\">not bold</b>";

    private InputValuesProvider() {
        //InputValuesProvider should not be instantiated"
    }

    @DataProvider
    public static Object[][] incorrectWaNames() {
        return new Object[][]{
                {("    ")},
                {(" ")},
                {""}
        };
    }


}
