package datango.ui.pages.collaborator;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.ui.pages.collaborator.pageElements.EntityTree.*;
import static datango.utils.RandomUtils.getRandomString;

import org.openqa.selenium.WebDriver;

import datango.ui.pages.BasePage;
import datango.ui.pages.collaborator.pageElements.WorkareaContentContainer;
import datango.ui.pages.collaborator.pageElements.WorkareaTreeContainer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sergey Kuzhel
 */
@Slf4j
@Getter
public class WorkareaPage extends BasePage {
    private String WORKAREA_MSG = "workarea.permission.msg";
    
    private WorkareaTreeContainer storageTree;
    private WorkareaContentContainer storageContent;
    
    public WorkareaPage(WebDriver driver) {
        super(driver);
        this.storageTree = new WorkareaTreeContainer(driver);
        this.storageContent = new WorkareaContentContainer(driver);
    }
    
    public String addNewEntity(String type) {
        String entityName = getRandomString(6, ENGLISH_ALPHABET, true);
        
        return selectEntityType(type, entityName);
    }

    public String addNewEntity(String entityName, String type) {
        return selectEntityType(type, entityName);
    }
    
    private String selectEntityType(String type, String entityName) {
        storageTree.clickWorkareaRoot();
        storageContent.clickStartEditing();
        switch (type) {
            case PROJECT:
                storageContent.addProject(entityName);
                break;
            case FOLDER:
                storageContent.addGroup(entityName);
                break;
            case BOOK:
                storageContent.addBook(entityName);
                break;
            case SLIDE:
                storageContent.addSlide(entityName);
                break;
            case NEW_FOLDER:
                storageContent.addFolder(entityName);
                break;
            default:
                throw new IllegalArgumentException("Type of the content is not specified");
        }
        storageContent.clickFinishEditing();
        return entityName;
    }
}
