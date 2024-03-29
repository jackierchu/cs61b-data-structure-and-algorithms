package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author Jacqueline Chu
 */

public class ListsTest {
    /** FIXME
     */
    @Test
    public void testNaturalRuns() {
        IntList L = IntList.list(1, 3, 7, 5, 4, 6, 9, 10);
        int[][] expectedArray = new int[][] {{1, 3, 7}, {5}, {4, 6, 9, 10}};
        IntListList expected = IntListList.list(expectedArray);
        IntListList actual = Lists.naturalRuns(L);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
