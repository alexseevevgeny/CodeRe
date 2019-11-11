package datango.ui.pages.collaborator.administration;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.EntityTree;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuzhel
 */
public class PermissionsPage extends BasePage {
    private static final String EDIT_BUTTON = "permissions.permissions.edit.button";
    private static final String PERMISSIONS_ADD_LIST = "permissions.add.list.values";
    private static final String PERMISSIONS_CURRENT_LIST = "permissions.current.list.values";
    private static final String SAVE_BUTTON = "permissions.permissions.save.button";
    private static final String CANCEL_BUTTON = "permissions.permissions.cancel.button";
    private static final String SEARCH_INPUT = "permissions.search.input";
    private static final String SEARCH_BUTTON = "permissions.search.button";
    private static final String SEARCH_CLEAR_BUTTON = "permissions.search.clear.button";
    private static final String SEARCH_LIST = "permissions.search.list";
    private static final String EXPANDER_BUTTON = "permissions.click.expander.button";
    private static final String FILTERED_SEARCH_LIST = "entity.tree.search.values";
    private static final String SELECT_ALL_PERMISSION = "permission.add.all.check";

    @Getter
    private EntityTree entityTree;

    public PermissionsPage(WebDriver driver) {
        super(driver);
        this.entityTree = new EntityTree(driver);
    }

    public void clickEditPermissions() {
        welActions.click(EDIT_BUTTON);
        driverManager.shortWaitForLoad();
    }

    public void clickExpander() {
        welActions.click(EXPANDER_BUTTON);
    }

    public void clickSave() {
        welActions.click(SAVE_BUTTON);
    }

    public void clickCancel() {
        welActions.click(CANCEL_BUTTON);
    }

    public List<String> checkPermissions(int count) {
        List<WebElement> wels = welActions.getElements(PERMISSIONS_ADD_LIST);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < count * 2; i++) {
            if (i % 2 == 0) {
                if (wels.get(i).isSelected()) {
                    welActions.click(wels.get(i), PERMISSIONS_ADD_LIST);
                }
                welActions.click(wels.get(i), PERMISSIONS_ADD_LIST);
                res.add(wels.get(i + 1).getText());
            }
        }
        return res;
    }

    public List<String> unCheckPermissions(int count) {
        List<WebElement> wels = welActions.getElements(PERMISSIONS_ADD_LIST);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < count * 2; i++) {
            if (i % 2 == 0 && wels.get(i).isSelected()) {
                    welActions.click(wels.get(i), PERMISSIONS_ADD_LIST);
                    res.add(wels.get(i + 1).getText());

            }
        }
        return res;
    }

    public void clearPermissions() {
        List<WebElement> wels = welActions.getElements(PERMISSIONS_ADD_LIST);
        for (WebElement wel : wels) {
            if (wel.isSelected() && wel.isEnabled()) {
                welActions.click(wel, PERMISSIONS_ADD_LIST);
            }
        }
    }

    public List<String> getCurrentPermissions() {
        driverManager.shortWaitForLoad();
        return welActions.getElementsText(PERMISSIONS_CURRENT_LIST);
    }

    public void inputSearchKey(String searchKey) {
        welActions.input(SEARCH_INPUT, searchKey);
    }

    public void clickSearchButton() {
        welActions.click(SEARCH_BUTTON);
    }

    public void clickClearSearchField() {
        welActions.click(SEARCH_CLEAR_BUTTON);
    }

    public boolean isAllResultContainsSearchKey(String searchKey) {
        return welActions.getElements(SEARCH_LIST).stream()
            .allMatch(e -> e.getText().toLowerCase().contains(searchKey));
    }

    public String getSearchFieldInputValue() {
        return welActions.getInputValue(SEARCH_INPUT);
    }

    public List<String> initialEntitiesList() {
        return welActions.getElementsText(SEARCH_LIST);
    }

    public void searchEntity(String entityName) {
        welActions.input(SEARCH_INPUT, entityName);
        welActions.click(SEARCH_BUTTON);
        List<WebElement> searchResult = welActions.getElements(SEARCH_LIST);
        searchResult.stream()
            .filter(element -> element.getText().contains(entityName))
            .forEach(WebElement::click);
    }

    public void clickAddAllPermission() {
        welActions.click(SELECT_ALL_PERMISSION);
    }

    public void uncheckPermisson(String permission) {
        welActions.click(PERMISSIONS_ADD_LIST, permission, 0);
    }

}
