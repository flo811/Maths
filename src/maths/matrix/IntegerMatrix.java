package maths.matrix;

import java.util.Arrays;
import maths.exceptions.MathsArgumentException;
import maths.exceptions.MathsIllegalOperationException;
import maths.numbers.IntegerCalc;

/**
 * Class representing an integer matrix.
 *
 * @author flo
 */
public final class IntegerMatrix {

    public static final IntegerMatrix EMPTY = new IntegerMatrix(new int[][]{});

    private final int[][] matrix;
    private final int columns, rows;

    /**
     * Create a matrix from a bidimentional array of {@code int}.
     *
     * @param matrix The array for initialization.
     */
    public IntegerMatrix(final int[][] matrix) {
        rows = matrix.length;
        if (rows == 0) {
            columns = 0;
        } else {
            columns = matrix[0].length;
        }

        this.matrix = matrix;
    }

    /**
     * Exchanges two rows in a matrix.
     *
     * @param mat The matrix.
     * @param row1 The first row index.
     * @param row2 The second row index.
     */
    private static void exchangeRows(final int mat[][], final int row1, final int row2) {
        final int temp[] = mat[row1];
        mat[row1] = mat[row2];
        mat[row2] = temp;
    }

    /**
     * Exchanges two columns in a matrix.
     *
     * @param mat The matrix.
     * @param row1 The first column index.
     * @param row2 The second column index.
     */
    private static void exchangeColumns(final int mat[][], final int column1, final int column2) {
        final int rowNbr = mat.length;
        int temp;
        for (int k = 0; k < rowNbr; k++) {
            temp = mat[k][column1];
            mat[k][column1] = mat[k][column2];
            mat[k][column2] = temp;
        }
    }

    /**
     * Adds an integer multiple of a row to another in a matrix.
     *
     * @param mat The matrix.
     * @param row1 The row index in wich we add a multiple of the second one.
     * @param row2 The second row index.
     * @param k The integer.
     */
    private static void addRow(int[][] mat, final int row1, final int row2, final int k) {
        final int columnsNbr = mat[0].length;
        for (int l = 0; l < columnsNbr; l++) {
            mat[row1][l] += k * mat[row2][l];
        }
    }

    /**
     * Adds an integer multiple of a column to another in a matrix.
     *
     * @param mat The matrix.
     * @param column1 The column index in wich we add a multiple of the second
     * one.
     * @param column2 The column row index.
     * @param k The integer.
     */
    private static void addColumn(int[][] mat, final int column1, final int column2, final int k) {
        final int rowNbr = mat.length;
        for (int l = 0; l < rowNbr; l++) {
            mat[l][column1] += k * mat[l][column2];
        }
    }

    /**
     * Multiply a row by an integer.
     *
     * @param mat The matrix.
     * @param row The row index to myltiply.
     * @param k The integer.
     */
    private static void multiplyRow(int[][] mat, final int row, final int k) {
        final int columnsNbr = mat[0].length;
        for (int l = 0; l < columnsNbr; l++) {
            mat[row][l] *= k;
        }
    }

    /**
     * Give the sum with another matrix.
     *
     * @param mat The matrix to add with.
     *
     * @return The sum of both matrices.
     *
     * @throws MathsArgumentException If matrices have different sizes.
     */
    public IntegerMatrix add(final IntegerMatrix mat) throws MathsArgumentException {
        if (mat.getRowNbr() != rows || mat.getColumnNbr() != columns) {
            throw new MathsArgumentException("Only matrices of same size can be added.");
        }

        final int[][] sum = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum[i][j] = matrix[i][j] + mat.getij(i, j);
            }
        }

        return new IntegerMatrix(sum);
    }

    /**
     * Give the product with another matrix.
     *
     * @param mat The matrix to multiply with.
     *
     * @return The product of this matrix with {@code mat}.
     *
     * @throws MathsArgumentException If matrices sizes doesn't fit for product.
     */
    public IntegerMatrix multiply(final IntegerMatrix mat) throws MathsArgumentException {
        if (columns != mat.getRowNbr()) {
            throw new MathsArgumentException("Incompatible size for matrix multiplication.");
        }

        final int pcolumn = mat.getColumnNbr();
        final int[][] product = new int[rows][pcolumn];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < pcolumn; j++) {
                for (int k = 0; k < columns; k++) {
                    product[i][j] += matrix[i][k] * mat.getij(k, j);
                }
            }
        }

        return new IntegerMatrix(product);
    }

    /**
     * Give the transpose of the matrix.
     *
     * @return The transposed matrix.
     */
    public IntegerMatrix transpose() {
        final int[][] transpose = new int[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }

        return new IntegerMatrix(transpose);
    }

    /**
     * @return @throws MathsIllegalOperationException
     */
    public int getDet() throws MathsIllegalOperationException {
        if (!isSquare()) {
            throw new MathsIllegalOperationException("The matrix is not squared.");
        }

        switch (rows) {
            case 0:
                return 0;
            case 1:
                return matrix[0][0];
            case 2:
                return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
            case 3:
                return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                        - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                        + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
            default:
                final int[][] gaussJordan = new int[rows][];
                for (int i = 0; i < rows; i++) {
                    gaussJordan[i] = Arrays.copyOf(matrix[i], rows);
                }

                int det = 1;

                for (int col = 0; col < columns; col++) {
                    int firstNotNullIndex = -1;
                    for (int line = col; line < rows; line++) {
                        if (gaussJordan[line][col] != 0) {
                            firstNotNullIndex = line;
                            break;
                        }
                    }
                    if (firstNotNullIndex == -1) {
                        return 0;
                    }

                    int firstNotNullValue = gaussJordan[firstNotNullIndex][col];
                    for (int i = firstNotNullIndex + 1; i < rows; i++) {
                        final int val = gaussJordan[i][col];
                        if (val == 0) {
                            continue;
                        }

                        if (firstNotNullValue % val == 0) {
                            addRow(gaussJordan, firstNotNullIndex, i, -firstNotNullValue / val);
                            firstNotNullIndex = i;
                            firstNotNullValue = val;
                        } else if (val % firstNotNullValue == 0) {
                            addRow(gaussJordan, i, firstNotNullIndex, -val / firstNotNullValue);
                        } else {
                            if (Math.abs(val) > Math.abs(firstNotNullValue)) {
                                addRow(gaussJordan, i, firstNotNullIndex, -val / firstNotNullValue);
                            } else {
                                addRow(gaussJordan, firstNotNullIndex, i, -firstNotNullValue / val);
                                firstNotNullValue = gaussJordan[firstNotNullIndex][col];
                            }
                            i--;
                        }
                    }

                    if (firstNotNullIndex != col) {
                        exchangeRows(gaussJordan, firstNotNullIndex, col);
                        det = -det;
                    }
                    det *= firstNotNullValue;
                }

                return det;
        }
    }

    /**
     * Give the Smith normal form of the matrix.
     *
     * @return The Smith normal form of the matrix.
     */
    public IntegerMatrix toSNF() {
        final int[][] snf = new int[rows][];
        final int length = Math.min(rows, columns);

        boolean modified, rowExchanged;

        for (int i = 0; i < rows; i++) {
            snf[i] = matrix[i].clone();
        }

        for (int i = 0; i < length; i++) {
            do {
                rowExchanged = false;
                int min;
                do {
                    modified = false;
                    min = Math.abs(snf[i][i]);
                    int position = i;
                    for (int j = i + 1; j < columns; j++) {
                        final int temp = Math.abs(snf[i][j]);
                        if (temp > 0 && (temp < min || min == 0)) {
                            min = temp;
                            position = j;
                            if (min == 1) {
                                break;
                            }
                        }
                    }

                    if (min == 0) {
                        break;
                    } else if (position != i) {
                        exchangeColumns(snf, i, position);
                    }

                    for (int j = i + 1; j < columns; j++) {
                        if (snf[i][j] != 0) {
                            addColumn(snf, j, i, -snf[i][j] / snf[i][i]);
                            modified = true;
                        }
                    }
                } while (modified == true && min != 1);

                do {
                    modified = false;
                    min = Math.abs(snf[i][i]);
                    int position = i;
                    for (int j = i + 1; j < rows; j++) {
                        final int temp = Math.abs(snf[j][i]);
                        if (temp > 0 && (temp < min || min == 0)) {
                            min = temp;
                            position = j;
                            if (min == 1) {
                                break;
                            }
                        }
                    }

                    if (min == 0) {
                        break;
                    } else if (position != i) {
                        exchangeRows(snf, i, position);
                        rowExchanged = modified = true;
                    }

                    for (int j = i + 1; j < rows; j++) {
                        if (snf[j][i] != 0) {
                            addRow(snf, j, i, -snf[j][i] / snf[i][i]);
                            modified = true;
                        }
                    }
                } while (modified == true && min != 1);
            } while (rowExchanged == true);
        }

        do {
            modified = false;
            for (int i = 0; i < length - 1; i++) {
                final int v1 = snf[i][i];
                final int v2 = snf[i + 1][i + 1];
                if (v1 == 0) {
                    if (v2 != 0) {
                        snf[i][i] = v2;
                        snf[i + 1][i + 1] = 0;
                        modified = true;
                    }
                } else if (v2 != v1 * (v2 / v1)) {
                    snf[i][i] = IntegerCalc.gCD(v1, v2);
                    snf[i + 1][i + 1] = v1 * v2 / snf[i][i];
                    modified = true;
                }
            }
        } while (modified == true);

        for (int i = 0; i < length; i++) {
            snf[i][i] = Math.abs(snf[i][i]);
        }

        return new IntegerMatrix(snf);
    }

    /**
     * Returns a matrix filled with zeros.
     *
     * @param row The number of rows.
     * @param column The number of columns.
     *
     * @return The empty matrix.
     */
    public static IntegerMatrix getEmpty(final int row, final int column) {
        return new IntegerMatrix(new int[row][column]);
    }

    /**
     * Gives the trace of the matrix.
     *
     * @return The trace of the matrix.
     *
     * @throws MathsIllegalOperationException If the matrix is not squared.
     */
    public int getTrace() throws MathsIllegalOperationException {
        if (!isSquare()) {
            throw new MathsIllegalOperationException("The matrix is not squared.");
        }

        int trace = 0;
        for (int i = 0; i < columns; i++) {
            trace += matrix[i][i];
        }

        return trace;
    }

    /**
     * Returns an element of the matrix.
     *
     * @param i The row number.
     * @param j The column number.
     *
     * @return The value of the (i,j) element of the matrix.
     */
    public int getij(final int i, final int j) {
        return matrix[i][j];
    }

    /**
     * Returns the number of rows.
     *
     * @return The number of rows.
     */
    public int getRowNbr() {
        return rows;
    }

    /**
     * Returns the number of columns.
     *
     * @return The number of columns.
     */
    public int getColumnNbr() {
        return columns;
    }

    /**
     * Tells if the matrix is squared.
     *
     * @return {@code true} if the matrix is a squared, {@code false} otherwise.
     */
    public boolean isSquare() {
        return rows == columns;
    }

    /**
     * Tells if the matrix is empty.
     *
     * @return {@code true} if the matrix is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return rows == 0;
    }

    @Override
    public String toString() {
        final StringBuilder matString = new StringBuilder(200);

        if (rows == 1) {
            matString.append('[');
            for (int i = 0; i < columns; i++) {
                matString.append(matrix[0][i]).append('\t');
            }
            matString.replace(matString.length() - 1, matString.length(), "]\n");
            return matString.toString();
        }

        for (int[] row : matrix) {
            matString.append('|');
            for (int j = 0; j < columns; j++) {
                matString.append(row[j]).append('\t');
            }
            matString.replace(matString.length() - 1, matString.length(), "|\n");
        }

        return matString.toString();
    }
}
