package datango.ui.helpers;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Custom asserts for check collaborator actions
 * @author Sergey Kuzhel
 */
public class Asserts {

    public <T> void assertListSizeInequality(List<T> list, List<T> cLict, String m) {
        assertTrue(list.size() != cLict.size(), m);
    }

    public <T> void assertListSizeEquality(List<T> list, List<T> cLict, String m) {
        assertTrue(list.size() == cLict.size(), m);
    }

}
