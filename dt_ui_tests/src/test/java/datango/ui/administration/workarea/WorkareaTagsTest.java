package datango.ui.administration.workarea;

import datango.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static datango.enums.CharacterSet.ENGLISH_ALPHABET;
import static datango.enums.CharacterSet.NUMERIC;
import static datango.ui.data.InputValuesProvider.TEST_DESCRIPTION;
import static datango.ui.enums.AssertMessages.*;
import static datango.utils.RandomUtils.getRandomString;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Epic("Automation Test cases implementation for Collaborator(Server) 3.2")
@Story("[Administration - Workareas/Tags] automation test cases")
/**
 * @deprecated (currently not used in testing)
 */
@Deprecated
public class WorkareaTagsTest extends BaseTest {
    private static final String DESCR = "descr";
    private String waName;

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
        waName = getRandomString(7, ENGLISH_ALPHABET, true);
        collaborator.gotoWorkareaAdministration();
        workareaAdmPage.addNewWorkarea(waName,
                getRandomString(4, NUMERIC, false));
    }

    @BeforeMethod
    public void setUpMethod() {
        collaborator.gotoWorkareaAdministration();
    }

    @TmsLink("1572")
    @Test(description = "Verify adding a Tag to a Workarea (correct input)", groups = "smoke-test", enabled = false)
    public void addWorkareaTagCorrectTest() {
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(getRandomString(5, ENGLISH_ALPHABET, true), TEST_DESCRIPTION);
        assertTrue(!workareaAdmPage.getTagNames().isEmpty(), MSG_TAG_CREATED.getValue());
    }

    @TmsLink("1775")
    @Test(description = "Verify adding a Tag to a Workarea (incorrect input)", groups = "regression-test", enabled = false)
    public void addWorkareaTagIncorrectCorrectTest() {
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(" ", TEST_DESCRIPTION);
        assertTrue(dialogBox.isErrorMessage(), MSG_TAG_NOT_CREATED.getValue());
        dialogBox.clickApplyButton();
    }

    @TmsLink("1776")
    @Test(description = "Verify adding a Tag to a Workarea (existing input)", groups = "regression-test", enabled = false)
    public void addWorkareaTagExistingNameTest() {
        String tagname = "existing";
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(tagname, DESCR);
        workareaAdmPage.addWorkareaTag(tagname, DESCR);
        assertTrue(dialogBox.isErrorMessage(), MSG_TAG_NOT_CREATED.getValue());
        dialogBox.clickApplyButton();
    }

    @TmsLink("1573")
    @Test(description = "Verify editing a Tag (correct input)", groups = "regression-test", enabled = false)
    public void editWorkareaTagCorrectTest() {
        String tagname = getRandomString(5, ENGLISH_ALPHABET, true);
        String cTagName = tagname + " edit";
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(tagname, TEST_DESCRIPTION);
        workareaAdmPage.editWorkareaTag(tagname, cTagName);
        assertTrue(workareaAdmPage.getTagNames().contains(cTagName), MSG_TAG_EDITED.getValue());
    }

    @TmsLink("1777")
    @Test(description = "Verify editing a Tag (incorrect input)", groups = "regression-test", enabled = false)
    public void editWorkareaTagIncorrectTest() {
        String tagname = getRandomString(5, ENGLISH_ALPHABET, true);
        String cTagName = " ";
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(tagname, TEST_DESCRIPTION);
        workareaAdmPage.editWorkareaTag(tagname, cTagName);
        assertTrue(!workareaAdmPage.getTagNames().contains(""), MSG_TAG_NOT_EDITED.getValue());
    }

    @TmsLink("1779")
    @Test(description = "Verify editing a Tag (existing input)", groups = "regression-test", enabled = false)
    public void editWorkareaTagExistingTest() {
        String tagname = getRandomString(5, ENGLISH_ALPHABET, true);
        String cTagName = tagname + " edit";
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(tagname, TEST_DESCRIPTION);
        workareaAdmPage.editWorkareaTag(tagname, cTagName);
        workareaAdmPage.editWorkareaTag(cTagName, cTagName);
        assertTrue(workareaAdmPage.getTagNames().contains(cTagName), MSG_TAG_EDITED.getValue());
    }

    @TmsLink("1778")
    @Test(description = "Verify editing a Tag (incorrect input)", groups = "regression-test", enabled = false)
    public void editWorkareaTagEmtyMandatoryTest() {
        String tagname = getRandomString(5, ENGLISH_ALPHABET, true);
        String cTagName = "";
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(tagname, DESCR);
        workareaAdmPage.editWorkareaTag(tagname, cTagName);
        assertTrue(!workareaAdmPage.getTagNames().contains(""), MSG_TAG_NOT_EDITED.getValue());
    }

    @TmsLink("1574")
    @Test(description = "Verify deleting a Tag ", groups = "regression-test", enabled = false)
    public void delWorkareaTagTest() {
        String tagname = getRandomString(5, ENGLISH_ALPHABET, true);
        workareaAdmPage.clickOnWorkarea(waName);
        workareaAdmPage.addWorkareaTag(tagname, DESCR);
        workareaAdmPage.clickDeleteWorkareaTag(tagname);
        dialogBox.clickApplyButton();
        assertTrue(!workareaAdmPage.getTagNames().contains(tagname), MSG_TAG_DELETED.getValue());
    }

}
