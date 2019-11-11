package datango.ui.pages.collaborator.administration;

import datango.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RegistrationCodes extends BasePage {
    private static final String REGCODE_ROW = "regcode.tab.regcode.row";

    public RegistrationCodes(WebDriver driver){
        super(driver);
    }

    public String getRegCodeByOuName(String ouName){
        String cellValue = null;
        driverManager.shortWaitForLoad();
        List<WebElement> rows = welActions.getElements(REGCODE_ROW);
        for (WebElement row: rows){
            if (row.getText().contains(ouName)){
                cellValue = row.getText();
                break;
            }
        }

        return cellValue;
    }
}
