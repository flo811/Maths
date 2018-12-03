package maths.numbers;

import maths.exceptions.MathsArgumentException;

/**
 * Static class dealing with numbers.
 *
 * @author flo
 */
public final class Numbers {

    /**
     * Non instanciable class.
     */
    private Numbers() {
    }

    /**
     * Find the maximum in a list of {@code double}.
     *
     * @param numbers The list of numbers.
     *
     * @return The biggest number in {@code numbers}.
     *
     * @throws MathsArgumentException If {@code numbers} is empty.
     */
    public static double max(final double... numbers) throws MathsArgumentException {
        final int size = numbers.length;
        if (size == 0) {
            throw new MathsArgumentException("Empty list of numbers.");
        }

        double max = numbers[0];
        for (int i = 1; i < size; i++) {
            if (max < numbers[i]) {
                max = numbers[i];
            }
        }

        return max;
    }

    /**
     * Find the minimum in a list of {@code double}.
     *
     * @param numbers The list of numbers.
     *
     * @return The smallest number in {@code numbers}.
     *
     * @throws MathsArgumentException If {@code numbers} is empty.
     */
    public static double min(final double... numbers) throws MathsArgumentException {
        final int size = numbers.length;
        if (size == 0) {
            throw new MathsArgumentException("Empty list of numbers.");
        }

        double min = numbers[0];
        for (int i = 1; i < size; i++) {
            if (min > numbers[i]) {
                min = numbers[i];
            }
        }

        return min;
    }

    /**
     * Find the maximum absolute value of a list of {@code double}.
     *
     * @param numbers The list of numbers.
     *
     * @return The biggest absolute value in {@code numbers}.
     *
     * @throws MathsArgumentException If {@code numbers} is empty.
     */
    public static double maxAbs(final double... numbers) throws MathsArgumentException {
        final int size = numbers.length;
        if (size == 0) {
            throw new MathsArgumentException("Empty list of numbers.");
        }

        final double[] absNumbers = new double[size];
        for (int i = 0; i < size; i++) {
            absNumbers[i] = numbers[i] < 0 ? -numbers[i] : numbers[i];
        }

        return max(absNumbers);
    }

    /**
     * Find the minimum absolute value of a list of {@code double}.
     *
     * @param numbers The list of numbers.
     *
     * @return The smallest absolute value in {@code numbers}.
     *
     * @throws MathsArgumentException If {@code numbers} is empty.
     */
    public static double minAbs(final double... numbers) throws MathsArgumentException {
        final int size = numbers.length;
        if (size == 0) {
            throw new MathsArgumentException("Empty list of numbers.");
        }

        final double[] absNumbers = new double[size];
        for (int i = 0; i < size; i++) {
            absNumbers[i] = numbers[i] < 0 ? -numbers[i] : numbers[i];
        }

        return min(absNumbers);
    }

    /**
     * Test if a {@code String} represents a {@code Byte}.
     *
     * @param number The {@code String} to test.
     *
     * @return {@code true} if {@code number} represents a {@code Byte},
     * {@code false} otherwise.
     */
    public static boolean isByte(final String number) {
        final String trimmedNumber = number.trim();
        if (!trimmedNumber.matches("[+-]?[ ]*[0]*[0-9]{1,3}")) {
            return false;
        }

        final String vals[] = trimmedNumber.split("^[+-]?[ ]*[0]*");
        if (vals.length == 0) {
            return true;
        }
        final String val = vals[0].isEmpty() ? vals[1] : vals[0];
        if (val.length() < 3) {
            return true;
        }

        final char v0 = val.charAt(0);
        if (v0 != '1') {
            return v0 < '1';
        }
        final char v1 = val.charAt(1);
        if (v1 != '2') {
            return v1 < '2';
        }
        final char v2 = val.charAt(2);
        
        return trimmedNumber.charAt(0) == '-' ? v2 <= '8' : v2 < '8';
    }

    /**
     * Test if a {@code String} represents a {@code Short}.
     *
     * @param number The {@code String} to test.
     *
     * @return {@code true} if {@code number} represents a {@code Short},
     * {@code false} otherwise.
     */
    public static boolean isShort(final String number) {
        final String trimmedNumber = number.trim();
        if (!trimmedNumber.matches("[+-]?[ ]*[0]*[0-9]{1,5}")) {
            return false;
        }

        final String vals[] = trimmedNumber.split("^[+-]?[ ]*[0]*");
        if (vals.length == 0) {
            return true;
        }
        final String val = vals[0].isEmpty() ? vals[1] : vals[0];
        if (val.length() < 5) {
            return true;
        }

        final char v0 = val.charAt(0);
        if (v0 != '3') {
            return v0 < '3';
        }
        final char v1 = val.charAt(1);
        if (v1 != '2') {
            return v1 < '2';
        }
        final char v2 = val.charAt(2);
        if (v2 != '7') {
            return v2 < '7';
        }
        final char v3 = val.charAt(3);
        if (v3 != '6') {
            return v3 < '6';
        }
        final char v4 = val.charAt(4);
        
        return trimmedNumber.charAt(0) == '-' ? v4 <= '8' : v4 < '8';
    }

    /**
     * Test if a {@code String} represents a {@code Integer}.
     *
     * @param number The {@code String} to test.
     *
     * @return {@code true} if {@code number} represents a {@code Integer},
     * {@code false} otherwise.
     */
    public static boolean isInteger(final String number) {
        final String trimmedNumber = number.trim();
        if (!trimmedNumber.matches("[+-]?[ ]*[0]*[0-9]{1,10}")) {
            return false;
        }

        final String vals[] = trimmedNumber.split("^[+-]?[ ]*[0]*");
        if (vals.length == 0) {
            return true;
        }
        final String val = vals[0].isEmpty() ? vals[1] : vals[0];
        if (val.length() < 10) {
            return true;
        }

        final char v0 = val.charAt(0);
        if (v0 != '2') {
            return v0 < '2';
        }
        final char v1 = val.charAt(1);
        if (v1 != '1') {
            return v1 < '1';
        }
        final char v2 = val.charAt(2);
        if (v2 != '4') {
            return v2 < '4';
        }
        final char v3 = val.charAt(3);
        if (v3 != '7') {
            return v3 < '7';
        }
        final char v4 = val.charAt(4);
        if (v4 != '4') {
            return v4 < '4';
        }
        final char v5 = val.charAt(5);
        if (v5 != '8') {
            return v5 < '8';
        }
        final char v6 = val.charAt(6);
        if (v6 != '3') {
            return v6 < '3';
        }
        final char v7 = val.charAt(7);
        if (v7 != '6') {
            return v7 < '6';
        }
        final char v8 = val.charAt(8);
        if (v8 != '4') {
            return v8 < '4';
        }
        final char v9 = val.charAt(9);
        
        return trimmedNumber.charAt(0) == '-' ? v9 <= '8' : v9 < '8';
    }

    /**
     * Test if a {@code String} represents a {@code Long}.
     *
     * @param number The {@code String} to test.
     *
     * @return {@code true} if {@code number} represents a {@code Long},
     * {@code false} otherwise.
     */
    public static boolean isLong(final String number) {
        final String trimmedNumber = number.trim();
        if (!trimmedNumber.matches("[+-]?[ ]*[0]*[0-9]{1,19}")) {
            return false;
        }

        final String vals[] = trimmedNumber.split("^[+-]?[ ]*[0]*");
        if (vals.length == 0) {
            return true;
        }
        final String val = vals[0].isEmpty() ? vals[1] : vals[0];
        if (val.length() < 19) {
            return true;
        }

        final char v0 = val.charAt(0);
        if (v0 != '9') {
            return v0 < '9';
        }
        final char v1 = val.charAt(1);
        if (v1 != '2') {
            return v1 < '2';
        }
        final char v2 = val.charAt(2);
        if (v2 != '2') {
            return v2 < '2';
        }
        final char v3 = val.charAt(3);
        if (v3 != '3') {
            return v3 < '3';
        }
        final char v4 = val.charAt(4);
        if (v4 != '3') {
            return v4 < '3';
        }
        final char v5 = val.charAt(5);
        if (v5 != '7') {
            return v5 < '7';
        }
        final char v6 = val.charAt(6);
        if (v6 != '2') {
            return v6 < '2';
        }
        if (val.charAt(7) != '0') {
            return false;
        }
        final char v8 = val.charAt(8);
        if (v8 != '3') {
            return v8 < '3';
        }
        final char v9 = val.charAt(9);
        if (v9 != '6') {
            return v9 < '6';
        }
        final char v10 = val.charAt(10);
        if (v10 != '8') {
            return v10 < '8';
        }
        final char v11 = val.charAt(11);
        if (v11 != '5') {
            return v11 < '5';
        }
        final char v12 = val.charAt(12);
        if (v12 != '4') {
            return v12 < '4';
        }
        final char v13 = val.charAt(13);
        if (v13 != '7') {
            return v13 < '7';
        }
        final char v14 = val.charAt(14);
        if (v14 != '7') {
            return v14 < '7';
        }
        final char v15 = val.charAt(15);
        if (v15 != '5') {
            return v15 < '5';
        }
        final char v16 = val.charAt(16);
        if (v16 != '8') {
            return v16 < '8';
        }
        if (val.charAt(17) != '0') {
            return false;
        }
        final char v18 = val.charAt(18);
        
        return trimmedNumber.charAt(0) == '-' ? v18 <= '8' : v18 < '8';
    }
}
