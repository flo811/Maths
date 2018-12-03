package maths.homology;

import maths.matrix.IntegerMatrix;

/**
 *
 * @author flo
 */
public final class SNFCalculator implements HomologyCalculator {

    public SNFCalculator() {
    }

    @Override
    public GradedHomology calculateHomology(final DifferentialComplex complex) {
        final int firstGrad = complex.getFirstGrad();
        final int lastGrad = complex.getLastGrad();
        final GradedHomology gradedHomology = new GradedHomology();

        int rank1, rank2, length1, length2, torsion;
        IntegerMatrix mat1, mat2;

        mat1 = IntegerMatrix.EMPTY;

        for (int i = firstGrad; i <= lastGrad + 1; i++) {
            torsion = 0;
            mat2 = complex.getDiff(i).toSNF();

            length1 = Math.min(mat1.getRowNbr(), mat1.getColumnNbr());
            rank1 = length1;
            for (int j = 0; j < length1; j++) {
                final int val = mat1.getij(j, j);
                if (val == 0) {
                    rank1 = j;
                    break;
                } else if (val > 1) {
                    torsion++;
                }
            }

            length2 = Math.min(mat2.getRowNbr(), mat2.getColumnNbr());
            rank2 = length2;
            for (int j = 0; j < length2; j++) {
                if (mat2.getij(j, j) == 0) {
                    rank2 = j;
                    break;
                }
            }

            final int[] tor = new int[torsion];
            for (int j = rank1 - torsion; j < rank1; j++) {
                tor[j - rank1 + torsion] = mat1.getij(j, j);
            }

            final Homology homology = new Homology(mat2.getColumnNbr() - rank1 - rank2, tor);

            gradedHomology.setHomologyAt(i, homology);

            mat1 = mat2;
        }

        return gradedHomology;
    }
}
