package datango.ui.helpers;

import datango.ui.utils.UiMapping;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static datango.enums.FilesConsts.PATH_TO_FILE;
import static datango.ui.enums.ElementAttribute.STYLE;
import static datango.ui.enums.ElementAttribute.VALUE;
import static datango.utils.RandomUtils.getRandomIntBetween;


/**
 * Interaction with web page elements
 *
 * @author Sergey Kuzhel
 */
@Slf4j
public class WebElementActions {
    private static final String LOADING_MODAL = "page.element.loading.modal";
    private static final String MODAL_STYLE_VALUE = "display: none";

    private WebDriverWait wait;

    private WebDriver driver;

    public WebElementActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        UiMapping.getInstance();
    }

    /**
     * Click on an element
     */
    @Step("Click on an element [{locator.}]")
    public void click(String locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(UiMapping.ui(LOADING_MODAL)));
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        wel.click();
    }

    /**
     * Click on an element
     */
    @Step("Click on an element [{locator}]")
    public void click(WebElement element, String locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(UiMapping.ui(LOADING_MODAL)));
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.ignoring(NoSuchElementException.class);
        new Actions(driver).moveToElement(element).perform();
        element.click();
    }

    /**
     * Click on an element with offset
     *
     * @param locator
     * @param element
     * @param offset
     */
    @Step("Click on an element in array [{locator}]")
    public void click(String locator, String element, int offset) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(UiMapping.ui(LOADING_MODAL)));
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement el = getElement(locator, element, offset);
        wait.until(ExpectedConditions.elementToBeClickable(el));
        new Actions(driver).moveToElement(el).build().perform();
        el.click();
    }

    /**
     * Insert value into text input
     */
    @Step("Insert [{text}] to input field [{locator}]")
    public void input(String locator, String text) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(UiMapping.ui(LOADING_MODAL)));
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        wel.clear();
        wel.sendKeys(text);
    }

    /**
     * Select option by value
     *
     * @param locator
     * @param value
     */
    public void select(String locator, String value) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        drp.selectByValue(value);
    }

    /**
     * Select option by text
     *
     * @param locator
     * @param text
     */
    public void selectByText(String locator, String text) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        drp.selectByVisibleText(text);
    }

    @Step("Select element [{locator}]")
    public void select(WebElement element, String locator, String text) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(UiMapping.ui(LOADING_MODAL)));
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        new Select(element).selectByValue(text);
    }

    /**
     * Select random option
     *
     * @param locator
     * @return
     */
    public String select(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        int index = getRandomIntBetween(1, (drp.getOptions().size() - 1));
        drp.selectByIndex(index);
        return drp.getFirstSelectedOption().getText();
    }

    /**
     * Get selected value
     *
     * @param locator
     * @return value
     */
    public String getSelectedValue(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        return drp.getFirstSelectedOption().getText();
    }

    /**
     * Select element by value text
     * @param locator
     * @param valueName
     */
    public void selectTargetValue(String locator, String valueName) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        drp.selectByValue(valueName);
    }

    /**
     * Get select values
     *
     * @param locator
     * @return value
     */
    public List<String> getSelectValues(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        return drp.getOptions().stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    /**
     * Get input value
     *
     * @param locator
     * @return value
     */
    public String getInputValue(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        return wel.getAttribute(VALUE.getValue());
    }

    /**
     * Get web element from array with offset
     *
     * @param locator
     * @param element
     * @param offset
     * @return Web element
     */
    public WebElement getElement(String locator, String element, int offset) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        int nameIndex = -1;
        List<WebElement> wels = getElements(locator);
        for (WebElement wel : wels) {
            if (wel.getText().startsWith(element)) {
                nameIndex = wels.indexOf(wel);
            }
        }
        WebElement el = null;
        if (nameIndex != -1) {
            if (offset >= 0) {
                el = wels.get(nameIndex + Math.abs(offset));
            } else {
                el = wels.get(nameIndex - Math.abs(offset));
            }
        }
        return el;
    }

    /**
     * Get web element
     *
     * @param locator
     * @return Web element
     */
    public WebElement getElement(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        return driver.findElement(UiMapping.ui(locator));
    }

    /**
     * Get list of web elements
     *
     * @param locator
     * @return list of WebElements
     */
    public List<WebElement> getElements(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        return driver.findElements(UiMapping.ui(locator));
    }

    public List<String> getElementsText(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        return getElements(locator).stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    /**
     * Checks that element can be clicked
     *
     * @param locator
     * @return
     */
    public boolean isClickable(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        if (isPresent(locator)) {
            WebElement wel = getElement(locator);
            return isClickable(wel);
        }
        return false;
    }

    /**
     * Checks is page element clickable
     *
     * @param element
     * @return
     */
    public boolean isClickable(WebElement element) {
        return element.isEnabled() && element.isDisplayed();
    }

    /**
     * Checks that element checkbox is ticked
     *
     * @param locator
     * @return
     */
    public boolean isChecked(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        return getElement(locator).isSelected();
    }

    /**
     * Checks that element is present on page
     *
     * @param locator
     * @return
     */
    public boolean isPresent(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        return !getElements(locator).isEmpty();
    }

    public boolean isVisible(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        return getElement(locator).isDisplayed();
    }

    public  boolean isPresent(WebElement parent, String locator) {
        return (parent.findElements(By.xpath(locator)).size() != 0);
    }

    /**
     * Focuses to element on page using locator
     *
     * @param locator
     * @return
     */
    public void moveToElement(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = getElement(locator);
        moveToElement(wel);
    }

    /**
     * Focuses to element on page
     *
     * @param element
     * @return
     */
    public void moveToElement(WebElement element) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        Actions act = new Actions(driver);
        act.moveToElement(element).build().perform();
    }

    public void moveByOffset(WebElement elem, int x, int y) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        Actions act = new Actions(driver);
        act.moveToElement(elem, x, y).click().build().perform();
    }

    public void moveByOffset(String locator, int x, int y) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement elem = getElement(locator);
        moveByOffset(elem, x, y);
    }

    /**
     * Clear input field on page
     *
     * @param locator
     */
    public void clearField(String locator) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        WebElement wel = getElement(locator);
        wel.clear();
    }

    public void doubleClick(String locator, String element, int offset) {
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(LOADING_MODAL), STYLE.getValue(), MODAL_STYLE_VALUE));
        wait.ignoring(NoSuchElementException.class);
        Actions act = new Actions(driver);
        act.doubleClick(getElement(locator, element, offset)).perform();
    }

    public void fileUploadedWaiter(String name) {
        Path path = Paths.get(PATH_TO_FILE.getValue());
        File fileTocheck = path.resolve(name).toFile();

        wait.until(driver1 -> fileTocheck.exists());
    }


}
