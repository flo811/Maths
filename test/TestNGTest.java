
import maths.exceptions.MathsIllegalOperationException;
import maths.matrix.IntegerMatrix;
import maths.numbers.Numbers;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author ib
 */
public class TestNGTest {

    static int[][] mat;

    IntegerMatrix matrix;
    int trace;

    public TestNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        mat = new int[][]{{81, 20, 3, 4, 50, 6, 7, 8, 79}, {1, 2, 3, 0, 5, 60, 5, 8, 9}, {10, 2, 3, 4, 5, 64, 7, 8, 90},
        {1, 2, 3, 64, 5, 6, 7, 8, 9}, {1, 42, 3, 74, 50, 63, 70, 8, 9}, {31, 2, 3, 4, 15, 6, 7, 68, 9},
        {1, 27, 3, 4, 5, 88, 7, 8, 9}, {51, 2, 3, 4, 52, 6, 7, 8, 9}, {1, 34, 3, 4, 5, 46, 74, 68, 9}};
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        matrix = new IntegerMatrix(mat);
        trace = matrix.toSNF().getTrace();
    }

    @Test(groups = "gr",invocationCount = 10000,threadPoolSize = 10)
    public void test1() {
        try {
            assertEquals(matrix.toSNF().getTrace(), trace);
        } catch (MathsIllegalOperationException ex) {
            fail();
        }
    }

    @Test(groups = "gr")
    public void test2() {

    }

    @Test(dependsOnGroups = "gr")
    public void test3() {
        assertFalse(Numbers.isByte("rth"));
        assertFalse(Numbers.isByte("@45"));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
