package datango.ui.pages.collaborator.pageElements;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static datango.ui.pages.collaborator.pageElements.EntityTree.*;

/**
 * @author Sergey Kuzhel
 */
public class WorkareaTreeContainer extends BasePage {
    private static final String TAB_CONTENT = "workarea.tree.content.button";
    private static final String TAB_TRASH = "workarea.tree.trash.button";
    private static final String TAB_UNREFERENCED = "workarea.tree.unreferenced.button";
    private static final String SELECT_ENTITY = "workarea.content.torestore";
    private static final String SELECT_RESTORE_BUTTON = "workarea.select.button";

    private EntityTree entityTree;

    public WorkareaTreeContainer(WebDriver driver) {
        super(driver);
        entityTree = new EntityTree(driver);
    }

    public void clickTrashTab() {
        welActions.click(TAB_TRASH);
    }

    public void clickUnreferencedTab() {
        welActions.click(TAB_UNREFERENCED);
    }

    public void selectRestoredEntity(String name) {
        entityTree.clickEntity(SELECT_ENTITY, name, NEW_FOLDER);
    }

    public void clickContentTab() {
        welActions.click(TAB_CONTENT);
    }

    public void clickWorkareaRoot() {
        entityTree.clickRootEntity(ENTITY_TREE);
    }

    public List<String> getEntityNames() {
        entityTree.expandAll(welActions.getElements(ENTITY_TREE));
        return welActions.getElementsText(ENTITY_TREE);
    }

    public void clickGroup(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, FOLDER);
    }

    public void clickFolder(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, NEW_FOLDER);
    }

    public void clickProject(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, PROJECT);
    }

    public void clickBook(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, BOOK);
    }

    public void clickSlide(String name) {
        entityTree.clickEntity(ENTITY_TREE, name, EntityTree.SLIDE);
    }

    public void clickSelectRestoreButton() {
        welActions.click(SELECT_RESTORE_BUTTON);
    }
}
