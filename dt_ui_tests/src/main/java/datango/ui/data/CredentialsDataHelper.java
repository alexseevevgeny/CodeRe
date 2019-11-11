package datango.ui.data;

import datango.config.AppConfigProvider;
import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Map;

import static datango.ui.enums.FormFields.*;

/**
 * @author Sergey Kuzhel
 */
public final class CredentialsDataHelper {

    private CredentialsDataHelper() {
        //CredentialsDataHelper should not be instantiated"
    }

    public static Map<String, String> getDefaultCredentials() {
        Map<String, String> creds = new HashMap<>();
        creds.put(LOGIN.getValue(), AppConfigProvider.get().defLogin());
        creds.put(PASSWORD.getValue(), AppConfigProvider.get().defPassword());
        return creds;
    }

    public static Map<String, String> getInactiveCredentials() {
        Map<String, String> creds = new HashMap<>();
        creds.put(LOGIN.getValue(), AppConfigProvider.get().inactiveLogin());
        creds.put(PASSWORD.getValue(), AppConfigProvider.get().inactivePassword());
        return creds;
    }

    public static Map<String, String> getDefaultTestDataCredentials() {
        Map<String, String> creds = new HashMap<>();
        creds.put(LOGIN.getValue(), AppConfigProvider.get().defGlobalLogin());
        creds.put(PASSWORD.getValue(), AppConfigProvider.get().defGlobalPassword());
        return creds;
    }

    @DataProvider
    public static Object[][] invalidCredentials() {
        Map<String, String> empty = new HashMap<>();
        Map<String, String> invalidCombination = new HashMap<>();
        Map<String, String> invalidSymbols = new HashMap<>();
        empty.put(LOGIN.getValue(), "");
        empty.put(PASSWORD.getValue(), "");
        invalidCombination.put(LOGIN.getValue(), "sdfjkhdsk");
        invalidCombination.put(PASSWORD.getValue(), "dsfjldllgg");
        invalidSymbols.put(LOGIN.getValue(), " ");
        invalidSymbols.put(PASSWORD.getValue(), " ");

        return new Object[][]{
                {empty},
                {invalidCombination},
                {invalidSymbols}
        };
    }


}
