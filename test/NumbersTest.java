
import maths.exceptions.MathsArgumentException;
import maths.numbers.Numbers;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NumbersTest {

   

    @Test(expectedExceptions = MathsArgumentException.class)
    public void maxExTest() throws MathsArgumentException {
        Numbers.max(new double[]{});
    }

    @DataProvider(name = "minPvd")
    public Object[][] minData() {
        return new Object[][]{
            {new double[]{0}, 0},
            {new double[]{-0, -2, 8, -9}, -9},
            {new double[]{-5, -2398545, -8, -0.9}, -2398545},
            {new double[]{-Double.MAX_VALUE, -10, 50000000, Double.MAX_VALUE}, -Double.MAX_VALUE},
            {new double[]{0.0000001, -0.00000000002, 8, 1e-11}, -0.00000000002}
        };
    }

    @Test(dataProvider = "minPvd")
    public void minTest(final double[] list, final double expected) throws MathsArgumentException {
        assertEquals(Numbers.min(list), expected);
    }

    @Test(expectedExceptions = MathsArgumentException.class)
    public void minExTest() throws MathsArgumentException {
        Numbers.min(new double[]{});
    }

    @DataProvider(name = "minAbsPvd")
    public Object[][] minAbsData() {
        return new Object[][]{
            {new double[]{0}, 0},
            {new double[]{-5.4}, 5.4},
            {new double[]{-0, -2, 8, -9}, 0},
            {new double[]{-5, -2398545, -8, -0.9}, 0.9},
            {new double[]{Double.MIN_VALUE, Double.MIN_NORMAL, -10, 50000000, -Double.MAX_VALUE}, Double.MIN_VALUE},
            {new double[]{0.0000001, -0.00000000002, 8, -1e-11}, 1e-11}
        };
    }

    @Test(dataProvider = "minAbsPvd")
    public void minAbsTest(final double[] list, final double expected) throws MathsArgumentException {
        assertEquals(Numbers.minAbs(list), expected);
    }

    @Test(expectedExceptions = MathsArgumentException.class)
    public void minAbsExTest() throws MathsArgumentException {
        Numbers.minAbs(new double[]{});
    }

    @DataProvider(name = "maxAbsPvd")
    public Object[][] maxAbsData() {
        return new Object[][]{
            {new double[]{0}, 0},
            {new double[]{-5.4}, 5.4},
            {new double[]{-0, -2, 8, -9}, 9},
            {new double[]{-5, 45, -8, -0.9}, 45},
            {new double[]{Double.MIN_VALUE, -Double.MIN_NORMAL, 0}, Double.MIN_NORMAL},
            {new double[]{0.0000001, -0.00000000002, -1e-11}, 0.0000001}
        };
    }

    @Test(dataProvider = "maxAbsPvd")
    public void maxAbsTest(final double[] list, final double expected) throws MathsArgumentException {
        assertEquals(Numbers.maxAbs(list), expected);
    }

    @Test(expectedExceptions = MathsArgumentException.class)
    public void maxAbsExTest() throws MathsArgumentException {
        Numbers.maxAbs(new double[]{});
    }
}
