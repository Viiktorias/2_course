import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            Matrix matrix1 = new Matrix(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            matrix1.fileTriangleMatrixInput(scanner);
            matrix1.reverseStep();
            matrix1.solutionOutput(System.out);

            System.out.println("----------------------------------");

            Matrix matrix2 = new Matrix(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            matrix2.fileMatrixInput(scanner);
            matrix2.directStep();
            matrix2.reverseStep();
            matrix2.solutionOutput(System.out);

            System.out.println("----------------------------------");

            Matrix matrix3 = new Matrix(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            matrix3.fileMatrixInput(scanner);
            Matrix matrix3inv = matrix3.inv();
            matrix3inv.fileMatrixOutput(System.out);

            System.out.println("----------------------------------");

            Matrix matrix4 = new Matrix(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            matrix4.fileMatrixInput(scanner);
            Matrix matrix4pow3 = matrix4.pow(5);
            matrix4pow3.fileMatrixOutput(System.out);

            System.out.println("----------------------------------");

            Matrix matrix5 = new Matrix(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            matrix5.fileMatrixInput(scanner);
            double[] scalar = matrix5.scalarMul();
            for (int i = 0; i < scalar.length; i++) {
                System.out.format("%+.3f", scalar[i]);
                System.out.print(" ");
            }
        } catch (FileNotFoundException | InvalidParameterException | NumberFormatException | NoSuchElementException | NegativeArraySizeException e) {
            System.err.println(e.getMessage());
        }

    }
}
