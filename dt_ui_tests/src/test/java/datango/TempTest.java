package datango;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuzhel
 */
@Slf4j
public class TempTest extends BaseTest {

    @BeforeClass
    public void setUpClass() {
        collaborator.openLoginPage();
        collaborator.performDefaultLogin();
    }

    @Test
    public void deleteOuAndGroups() {
        collaborator.gotoUserAdministration();
        List<String> names = orgViewPage.getOuNames();
        for (String name : names) {
            if (name.startsWith("adt")) {
                orgViewPage.clickOU(name);
                orgViewPage.clickOuDeleteButton();
            }
        }
    }

    @Test
    public void profilerTest() {
        int n = 1000;
        LocalTime start;
        LocalTime finish;
        start = LocalTime.now();
        log.info("Start time: ", start);
        for (int i = 0; i < n; i++) {
        }
        finish = LocalTime.now();
        log.info("Finish time: ", finish);
    }

    @Test
    public void allureLogTest() {
        assertTrue(false);
    }

}

