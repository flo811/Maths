package maths.numbers;

import maths.exceptions.MathsArgumentException;

/**
 * Static class dealing with integer methods.
 *
 * @author flo
 */
public final class IntegerCalc {

    /**
     * Non instanciable class.
     */
    private IntegerCalc() {
    }

    /**
     * Returns the remainder and the coefficients in Bézout's identity for two
     * {@code int}.
     *
     * @param a The first integer.
     * @param b The second integer.
     *
     * @return An array of three integers representing the reminder and the
     * coefficients in Bézout's identity.
     */
    public static int[] extendedEuclid(final int a, final int b) {
        final long result[] = extendedEuclid(a, (long) b);

        return new int[]{(int) result[0], (int) result[1], (int) result[2]};
    }

    /**
     * Returns the remainder and the coefficients in Bézout's identity for two
     * {@code long}.
     *
     * @param a The first integer.
     * @param b The second integer.
     *
     * @return An array of three integers representing the reminder and the
     * coefficients in Bézout's identity.
     */
    public static long[] extendedEuclid(final long a, final long b) {
        long r = a, u = 1, v = 0;
        long q, r2 = b, u2 = 0, v2 = 1, r3, u3, v3;

        while (r2 != 0) {
            q = r / r2;
            r3 = r;
            u3 = u;
            v3 = v;

            r = r2;
            u = u2;
            v = v2;

            r2 = r3 - q * r2;
            u2 = u3 - q * u2;
            v2 = v3 - q * v2;
        }

        if (r < 0) {
            r = -r;
            u = -u;
            v = -v;
        }

        return new long[]{r, u, v};
    }

    /**
     * Returns the modular multiplicative inverse of an integers.
     *
     * @param a The integer from which we want the inverse.
     * @param m The modulus.
     *
     * @return The modular multiplicative inverse of {@code a}.
     *
     * @throws MathsArgumentException If {@code a} and {@code m} are not
     * coprime.
     */
    public static long modularInverse(final long a, final long m) throws MathsArgumentException {
        final long[] res = extendedEuclid(a, m);

        if (res[0] != 1) {
            throw new MathsArgumentException("Integers a and m must be coprime.");
        }

        return res[1];
    }

    /**
     * Returns the greatest common divisor of two integers.
     *
     * @param a The first integer.
     * @param b The second integer.
     *
     * @return The greatest common divisor of {@code a} and {@code b}.
     */
    public static int gCD(final int a, final int b) {
        if (b == 0) {
            return a;
        }
        if (a == 0) {
            return b;
        }
        if (a == 1 || b == 1) {
            return 1;
        }

        int a2 = a, b2 = b, r;
        while (true) {
            r = a2 % b2;
            if (r == 0) {
                return Math.abs(b2);
            }
            a2 = b2;
            b2 = r;
        }
    }

    /**
     * Returns the greatest common divisor of a some integers.
     *
     * @param a The array of integers.
     *
     * @return The greatest common divisor of all the integers in the array.
     *
     * @throws MathsArgumentException If the array is empty.
     */
    public static int gCD(final int... a) throws MathsArgumentException {
        if (a.length == 0) {
            throw new MathsArgumentException("The array must contain at least one integer.");
        }

        int gcd = a[0];
        for (int i = 1; i < a.length; i++) {
            gcd = gCD(a[i], gcd);
            if (gcd == 1) {
                return 1;
            }
        }

        return gcd;
    }

    /**
     * Allows fast calculation of the reminder of the quotient of the power of
     * an integer by an integer by a positive integer.
     *
     * @param n The base.
     * @param power The power.
     * @param modulus The modulus.
     *
     * @return The smallest positive integer representing
     * {@code n^power mod modulus}.
     *
     * @throws MathsArgumentException If power is less than zero ou if modulus
     * is less or equal to zero.
     */
    public static int powMod(final int n, final int power, final int modulus) throws MathsArgumentException {
        if (power < 0) {
            throw new MathsArgumentException("Power must be a positive integer.");
        }
        if (modulus <= 0) {
            throw new MathsArgumentException("Modulus must be a strictly positive integer.");
        }

        long result = 1;
        for (int i = 31; i >= 0; i--) {
            result = (result * result) % modulus;
            if ((power & (1 << i)) != 0) {
                result = (result * n) % modulus;
            }
        }

        return (int) result;
    }

    /**
     * Fast calculation of an integer power of 2.
     *
     * @param n The power to which we want to raise 2.
     *
     * @return The value {@code 2^n}.
     *
     * @throws MathsArgumentException If {@code n} is not in the range between 0
     * and 30.
     */
    public static int pow2(final int n) throws MathsArgumentException {
        if (0 <= n && n <= 30) {
            return 1 << n;
        } else {
            throw new MathsArgumentException("The power must be an integer in the range between 0 and 30.");
        }
    }

    /**
     * Find the base 2 logarithm of an integer power of 2.
     *
     * @param n The value we want to find the base 2 logarithm.
     *
     * @return The integer base 2 logarithm of {@code n}.
     *
     * @throws MathsArgumentException If {@code n} isn't an integer power of 2
     * or if it is not in the range between 1 and 9223372036854775807.
     */
    public static int log2(final long n) throws MathsArgumentException {
        if (Long.bitCount(n) == 1 && n > 0) {
            return Long.numberOfTrailingZeros(n);
        } else if (n <= 0) {
            throw new MathsArgumentException("The integer must be in the range between 1 and 9223372036854775807.");
        } else {
            throw new MathsArgumentException("The integer must be an integer power of 2");
        }
    }

    /**
     * Determinist prime number test for a 32 bits integer, using an
     * implementation of Miller-Rabin's algorithm.
     *
     * @param n Integer to test.
     *
     * @return {@code true} if {@code n} is a positive prime, {@code false}
     * otherwise.
     */
    public static boolean isPrime(final int n) {
        final int m = n < 0 ? -n : n;

        if (m <= 1) {
            return false;
        } else if (m == 2 || m == 3 || m == 5 || m == 7 || m == 11 || m == 13) {
            return true;
        } else if (m % 2 == 0 || m % 3 == 0 || m % 5 == 0 || m % 7 == 0) {
            return false;
        } else {
            return millerRabin(m, 2) && (m <= 7 || millerRabin(m, 7)) && (m <= 61 || millerRabin(m, 61));
        }
    }

    /**
     * Miller-Rabin's test for a particular witness.
     *
     * @param n Integer to test.
     * @param witness Miller-Rabin's witness.
     *
     * @return {@code false} if the test find that {@code n} is composite and
     * {@code true} if {@code n} is probably prime.
     */
    private static boolean millerRabin(final int n, final int witness) {
        boolean isTestValid = false;

        try {
            final int nbrOfZero = Integer.numberOfTrailingZeros(n - 1);
            int powerMod = powMod(witness, (n - 1) >> nbrOfZero, n);

            if (powerMod == 1) {
                return true;
            }

            for (int i = 0; i < nbrOfZero - 1; i++) {
                if (powerMod == n - 1) {
                    return true;
                }
                powerMod = powMod(powerMod, 2, n);
            }

            isTestValid = powerMod == n - 1;
        } catch (final MathsArgumentException ex) {
            System.out.println("An error occurred in Miller-Rabin's test.");
        }

        return isTestValid;
    }
}
