package datango.ui.pages.collaborator.pageElements;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static datango.ui.enums.ElementAttribute.*;

/**
 * @author Sergey Kuzhel
 */
public class EntityTree extends BasePage {
    public static final String ENTITY_TREE = "entity.tree.values";

    public static final String USER = "_U_";
    public static final String GROUP = "_G_";
    public static final String OU = "_OU_";
    public static final String FOLDER = "_GR_";
    public static final String PROJECT = "_PR_";
    public static final String BOOK = "_BO_";
    public static final String SLIDE = "_SL_";
    public static final String NEW_FOLDER = "_FL_";

    private int treeSize = 0;

    public EntityTree(WebDriver driver) {
        super(driver);
    }

    public void clickRootEntity(String tree) {
        driverManager.shortWaitForLoad();
        WebElement wel = welActions.getElements(tree).stream()
                .filter(e -> e.getAttribute(CLASS.getValue()).contains("title"))
                .findFirst().orElseThrow(() -> new WebDriverException(String.format("Failed to find element: [%s]", tree)));
        if (welActions.isClickable(wel)) {
            welActions.click(wel, tree);
        }
    }

    public void clickEntity(String tree, String name, String type) {
        List<WebElement> wels = welActions.getElements(tree);
        int currSize = wels.size();
        if (!findAndClick(wels, name, type)) {
            expandAll(wels);
            if (currSize > treeSize) {
                treeSize = currSize;
                clickEntity(tree, name, type);
            }
        }
    }

    public void clickEntity(String tree, String name) {
        welActions.getElements(tree).stream()
            .filter(element -> element.getText().contains(name))
            .forEach(WebElement::click);
    }

    void expandAll(List<WebElement> wels) {
        boolean isExpanded = false;
        if (!wels.isEmpty() && welActions.isClickable(wels.get(0))) {
                welActions.click(wels.get(0), ENTITY_TREE);
        }
        for (WebElement wel : wels) {
            if (wel.getAttribute(EXPANDED.getValue()) != null) {
                if (wel.getAttribute(EXPANDED.getValue()).contains("false")) {
                    isExpanded = false;
                } else if (wel.getAttribute(EXPANDED.getValue()).contains("true")) {
                    isExpanded = true;
                    continue;
                }
            }

            if (wel.getAttribute(CLASS.getValue()).contains("expander")
                    && !isExpanded
                    && welActions.isClickable(wel)) {
                welActions.click(wel, ENTITY_TREE);
                isExpanded = true;
            }
        }
    }

    private boolean findAndClick(List<WebElement> wels, String name, String type) {
        boolean isFound = false;
        for (int i = 0; i < wels.size(); i++) {
            if (wels.get(i).getAttribute(ID.getValue()) == null) {
                continue;
            }
            if (wels.get(i).getAttribute(ID.getValue()).contains(type)
                    && wels.get(i + 2).getText().startsWith(name)
                    && ((i + 2) <= (wels.size() - 1))) {
                welActions.click(wels.get(i + 2), name);
                isFound = true;
            }
        }
        return isFound;
    }

}
