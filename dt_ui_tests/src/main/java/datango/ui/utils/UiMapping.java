package datango.ui.utils;

import datango.config.AppConfigProvider;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Creates an singleton object that manages page locators.
 *
 * @author Sergey Kuzhel
 */
@Slf4j
public final class UiMapping {

    private static volatile UiMapping instance;
    private static Properties properties = new Properties();
    private static volatile  Map<String, String> locatorMap = new HashMap<>();

    private UiMapping() {
        loadDataFromFile();
        properties.forEach((key, value) -> locatorMap.put((String) key, (String) value));
    }

    public static synchronized UiMapping getInstance() {
        if (instance == null) {
            instance = new UiMapping();
            return instance;
        }
        return instance;
    }

    private static Properties loadDataFromFile() {
        File file = new File(AppConfigProvider.get().uiMappingFile());
        if (file.exists()) {
            try {
                properties.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            } catch (IOException e) {
                log.error("Failed to load file", e);
            }
        } else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                log.error("{} file not found!", AppConfigProvider.get().uiMappingFile(), e);
            }
        }
        return properties;
    }

    public static By ui(String key) {
        String[] partsOfLocators = new String[0];
        try {
            partsOfLocators = locatorMap.get(key).split("\"");
        } catch (NullPointerException e) {
            log.error("locator {} is not specified in properties", key, e);
        }

        String findMethod = "";
        String locatorValue = "";
        try {
            findMethod = partsOfLocators[0];
            locatorValue = partsOfLocators[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error(e.getMessage(), e);
        }

        switch (findMethod) {
            case "id":
                return By.id(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
                return By.cssSelector(locatorValue);
            case "name":
                return By.name(locatorValue);
            default:
                return By.xpath(locatorValue);

        }
    }
}
