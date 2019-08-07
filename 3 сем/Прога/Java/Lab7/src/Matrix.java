import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Matrix {
    private double[][] matrix;
    private int row;
    private int col;

    public Matrix(int row, int col) throws NegativeArraySizeException {
        if ((row < 0) || (col < 0)) {
            throw new NegativeArraySizeException("Negative size");
        }
        this.row = row;
        this.col = col;
        this.matrix = new double[this.row][this.col];
    }

    public Matrix() {
        this(0, 0);
    }

    public Matrix(Matrix right) {
        this.row = right.row;
        this.col = right.col;
        this.matrix = new double[this.row][this.col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.matrix[i][j] = right.matrix[i][j];
            }
        }
    }

    public void fileMatrixInput(Scanner input) throws NoSuchElementException {
        try {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = Double.parseDouble(input.next());
                }
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Incorrect data");
        } catch (NumberFormatException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }


    public void fileTriangleMatrixInput(Scanner input) throws NumberFormatException {
        try {
            for (int i = 0; i < row; i++) {
                for (int j = i; j < col; j++) {
                    matrix[i][j] = Double.parseDouble(input.next());
                }
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Incorrect data");
        } catch (NumberFormatException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public void fileMatrixOutput(PrintStream output) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                output.format("%+.3f", matrix[i][j]);
                output.print(" ");
            }
            output.println();
        }
    }

    public void directStep() throws InvalidParameterException {
        if ((col != row + 1) && (col != 2 * row)) {
            throw new InvalidParameterException("Incorrect size");
        }
        double koef;
        for (int i = 0; i < row; i++) {//от i-ой строки
            if (matrix[i][i] == 0) {
                int j = 0;
                while ((j < row) && (matrix[j][i] == 0)) {
                    j++;
                }
                if (j == row) {
                    throw new InvalidParameterException("Null determiner");
                }
                for (int k = 0; k < col; k++) {
                    matrix[i][k] += matrix[j][k];
                }
            }
            for (int j = 0; j < i; j++) {//отнимаем все предыдущие j-ые
                if (matrix[i][j] != 0) {
                    koef = matrix[i][j] / matrix[j][j];
                    for (int k = j; k < col; k++) {
                        matrix[i][k] -= matrix[j][k] * koef;
                    }
                }
            }
            if (matrix[i][i] == 0) {
                throw new InvalidParameterException("Null determiner");
            }
        }
    }

    public void reverseStep() throws InvalidParameterException {
        if ((col != row + 1) && (col != 2 * row)) {
            throw new InvalidParameterException("Incorrect size");
        }
        double koef;
        for (int i = row - 1; i >= 0; i--) {
            if (matrix[i][i] == 0) {
                throw new InvalidParameterException("Matrix is not triangle");
            }
            for (int j = i + 1; j < row; j++) {
                if (matrix[i][j] != 0) {
                    koef = matrix[i][j] / matrix[j][j];
                    for (int k = j; k < col; k++) {
                        matrix[i][k] -= matrix[j][k] * koef;
                    }
                }
            }
            for (int j = row; j < col; j++) {
                matrix[i][j] /= matrix[i][i];
            }
            matrix[i][i] = 1;
        }
    }

    public void solutionOutput(PrintStream output) throws InvalidParameterException {
        if (col != row + 1) {
            throw new InvalidParameterException("Matrix is not extended");
        }
        for (int i = 0; i < row; i++) {
            output.format("%+.3f", matrix[i][col - 1]);
            if (i != row - 1) {
                output.print(", ");
            } else {
                output.println(".");
            }
        }
    }

    public Matrix inv() throws InvalidParameterException {
        if (row != col) {
            throw new InvalidParameterException("Matrix is not square");
        }
        Matrix extended = new Matrix(row, 2 * col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                extended.matrix[i][j] = matrix[i][j];
                extended.matrix[i][i + row] = 1;
            }
        }
        extended.directStep();
        extended.reverseStep();
        Matrix temp = new Matrix(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                temp.matrix[i][j] = extended.matrix[i][j + row];
            }
        }
        return temp;
    }

    private Matrix multiply(Matrix right) throws InvalidParameterException {
        if (col != right.row) {
            throw new InvalidParameterException("Incompatible sizes");
        }
        Matrix temp = new Matrix(row, right.col);
        for (int i = 0; i < temp.row; i++) {
            for (int j = 0; j < temp.col; j++) {
                for (int k = 0; k < col; k++) {
                    temp.matrix[i][j] += this.matrix[i][k] * right.matrix[k][j];
                }
            }
        }
        return temp;
    }

    public Matrix pow(int power) throws InvalidParameterException {
        if (row != col) {
            throw new InvalidParameterException("Matrix is not square");
        }
        if (power < 0) {
            throw new InvalidParameterException("Incorrect power");
        }
        if (power == 0) {
            Matrix temp = new Matrix(row, col);
            for (int i = 0; i < row; i++) {
                temp.matrix[i][i] = 1;
            }
            return temp;
        }
        if (power == 1)
            return new Matrix(this);
        if (power % 2 == 0) {
            Matrix temp = this.pow(power / 2);
            return temp.multiply(temp);
        }
        Matrix temp = this.pow(power - 1);
        return this.multiply(temp);
    }

    public double[] scalarMul() {
        if (row != col) {
            throw new InvalidParameterException("Matrix is not square");
        }
        class Coord {
            protected int x;
            protected int y;

            protected Coord(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        ArrayList<Coord> min = new ArrayList<Coord>();
        ArrayList<Coord> max = new ArrayList<Coord>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if ((min.isEmpty()) || (matrix[i][j] == matrix[min.get(0).x][min.get(0).y])) {
                    min.add(new Coord(i, j));
                }
                if (matrix[i][j] < matrix[min.get(0).x][min.get(0).y]) {
                    min.clear();
                    min.add(new Coord(i, j));
                }
                if ((max.isEmpty()) || (matrix[i][j] == matrix[max.get(0).x][max.get(0).y])) {
                    max.add(new Coord(i, j));
                }
                if (matrix[i][j] > matrix[max.get(0).x][max.get(0).y]) {
                    max.clear();
                    max.add(new Coord(i, j));
                }
            }
        }
        double[] temp = new double[min.size() * max.size()];
        for (int i = 0; i < min.size(); i++) {
            for (int j = 0; j < max.size(); j++) {
                temp[i * max.size() + j] = 0;
                for (int k = 0; k < row; k++) {
                    temp[i * max.size() + j] += matrix[min.get(i).x][k]*matrix[k][max.get(j).y];
                }
            }
        }
        return temp;
    }
}
