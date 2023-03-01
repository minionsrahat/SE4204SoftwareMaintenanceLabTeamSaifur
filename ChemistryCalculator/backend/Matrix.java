package ChemistryCalculator.backend;

import java.util.Arrays;

public class Matrix {

    private final Fraction[][] matrix;
    private Fraction[] nullSpaces = null;

    public Matrix(Fraction[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix(int[][] matrix) {
        this.matrix = intToFraction(matrix);
    }

    int getHeight() {
        return matrix.length;
    }

    int getWidth() {
        return matrix[0].length;
    }

    private Fraction[][] intToFraction(int[][] matrix) {
        Fraction[][] newMatrix = new Fraction[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = new Fraction(matrix[i][j]);
            }
        }
        return newMatrix;
    }


    //Transforming a matrix into Row Echelon Form using Gaussian elimination method
    //A rectangular matrix is in row echelon form if it has the following three properties:
    //All nonzero rows are above any rows of all zeros
    //Each leading entry of a row is in a column to the right of the leading entry of the row above it
    //All entries of a column below a leading entry are zeros

    //This matrix is in row echelon form:
    //    1   0   3   3
    //    0   1   0   4
    //    0   0   0   1

    public Matrix gaussianElimination() {
        int i = 0;
        int j = 0;
        while (i < matrix.length && j < matrix[0].length) {
            int pivot = i;
            for (int k = i + 1; k < matrix.length; k++) {
                if (matrix[k][j].isAbsGreaterThan(matrix[pivot][j])) {
                    pivot = k;
                }
            }
            if (!matrix[pivot][j].isZero()) {
                swapRows(i, pivot);
                divideRow(i, matrix[i][j]);

                for (int u = i + 1; u < matrix.length; u++) {
                    subtractRows(u, i, matrix[u][j]);
                }
            }
            j++;
            i++;
        }
        return new Matrix(matrix);
    }

    private void swapRows(int row1, int row2) {
        Fraction[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private void divideRow(int row, Fraction divisor) {
        for (int idx = 0; idx < matrix[0].length; idx++) {
            matrix[row][idx] = matrix[row][idx].divide(divisor);
        }
    }

    private void subtractRows(int targetRow, int subtractRow, Fraction multiplier) {
        for (int l = 0; l < matrix[0].length; l++) {
            matrix[targetRow][l] = matrix[targetRow][l].subtract(multiplier.multiply(matrix[subtractRow][l]));
        }
    }

    //back Substitution is the process of solving a linear system of equations that has been transformed into row-echelon form or reduced row-echelon form
    //The last equation is solved first, then the next-to-last, etc.
    private void backSubstitute(int n, Matrix echelonForm) {

        for (int i = n - 1; i >= 0; i--) {
            Fraction sum = new Fraction(0);
            for (int j = i + 1; j < echelonForm.getWidth(); j++) {
                sum = sum.add(echelonForm.matrix[i][j].multiply(echelonForm.nullSpaces[j]));
            }
            echelonForm.nullSpaces[i] = sum.multiply(new Fraction(-1));

        }
    }

    //Invalid Chemical Equation has no nullSpace
    public Fraction[] nullSpace() {
        Matrix echelonFormed = gaussianElimination();

        if (echelonFormed.getHeight() > echelonFormed.getWidth()) {
            for (int k = 0; k < echelonFormed.getWidth(); k++) {
                if (k == echelonFormed.getWidth() - 1 && echelonFormed.matrix[k][k].equals(new Fraction(1))) {
                  // "Has No Null Space"
                    break;
                }

                if (echelonFormed.matrix[k][k].isZero()) {
                    echelonFormed.nullSpaces = new Fraction[echelonFormed.getWidth()];
                    Arrays.fill(echelonFormed.nullSpaces, k, echelonFormed.getWidth(), new Fraction(1));
                    backSubstitute(k, echelonFormed);
                    break;
                }

            }

        } else if (echelonFormed.getHeight() < echelonFormed.getWidth()) {
            for (int k = 0; k < echelonFormed.getHeight(); k++) {
                if (k == echelonFormed.getHeight() - 1 && echelonFormed.matrix[k][k].equals(new Fraction(1))) {
                    echelonFormed.nullSpaces = new Fraction[echelonFormed.getWidth()];
                    Arrays.fill(echelonFormed.nullSpaces, k + 1, echelonFormed.getWidth(), new Fraction(1));
                    backSubstitute(k + 1, echelonFormed);
                    break;
                }

                if (echelonFormed.matrix[k][k].isZero()) {
                    echelonFormed.nullSpaces = new Fraction[echelonFormed.getWidth()];
                    Arrays.fill(echelonFormed.nullSpaces, k, echelonFormed.getWidth(), new Fraction(1));
                    backSubstitute(k, echelonFormed);
                    break;
                }
            }

        } else {
            for (int k = 0; k < echelonFormed.getHeight(); k++) {
                if (echelonFormed.matrix[k][k].isZero()) {
                    echelonFormed.nullSpaces = new Fraction[echelonFormed.getWidth()];
                    Arrays.fill(echelonFormed.nullSpaces, k, echelonFormed.getWidth(), new Fraction(1));
                    backSubstitute(k, echelonFormed);
                    break;
                } else if (k == echelonFormed.getHeight() - 1 && echelonFormed.matrix[k][k].equals(new Fraction(1))) {
                   //"Has No NullSpace"
                    break;
                }
            }

        }
        return echelonFormed.nullSpaces;

    }

    public Matrix transpose() {
        Fraction[][] transposedMatrix = new Fraction[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }
        return new Matrix(transposedMatrix);
    }

    @Override
    public String toString() {
        return "Matrix{" + "matrix=" + Arrays.deepToString(matrix) + '}';
    }

}
