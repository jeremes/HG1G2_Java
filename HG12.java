package AsteroidPhaseCurveAnalyzer;

import Jama.Matrix;
import java.io.File;
import java.io.FileNotFoundException;

public class HG12 extends LeastSquares{

    /* This function manages the functions needed to calculate the HG12 
       phase function and returns a string to be printed.
       Function's input must be a File-object */
    
    public static String HG12Function(File file) throws FileNotFoundException {
        Data data[] = Input.Input(file);
        String output = new String();

        // This for-loop goes through all the lines to scan for multiple asteroids
        for (int j = 0; j < data.length; j++) {
            double[][] ddata = data[j].getData();
            double[] x = new double[ddata.length];
            double[] y = new double[ddata.length];
            for (int i = 0; i < ddata.length; i++) {
                x[i] = ddata[i][0] * Math.PI / 180.0;
                y[i] = ddata[i][1];
            }

            // construction of the basis functions
            PiecewiseFunctions[] basisFunctions = Basisfunctions.Basisfunctions(x);

            // if you wish to put in your own errors for the data, change the value of this vector
            double[] errors = new double[data[j].getSize()];
            for (int i = 0; i < data[j].getSize(); i++) {
                errors[i] = 0.03;
            }

            // calculating the least squares solution and also the coefficient matrix
            Results results = LeastSquares.LeastSquaresG12(basisFunctions, data[j], errors);
            double[] coeffs = results.getCoeffs();
            Matrix aMatrix = results.getMatrix();

            double G12 = coeffs[1] / coeffs[0];
            double H = -2.5 * Math.log10(coeffs[0]);
            double[] fitErrors = Errors.HG12Errors(results.getMatrix(), coeffs);
            double[] confidence = new double[18];

            // Calculating the confidence intervals
            // fitErrors 0-2 means (H, G1, G2)
            // fitErrors 6-8 lower std deviations
            // fitErrors 3-5 upper std deviations
            confidence[0] = fitErrors[0] - 3 * fitErrors[6];
            confidence[1] = fitErrors[1] - 3 * fitErrors[7];
            confidence[2] = fitErrors[2] - 3 * fitErrors[8];
            confidence[3] = fitErrors[0] - 2 * fitErrors[6];
            confidence[4] = fitErrors[1] - 2 * fitErrors[7];
            confidence[5] = fitErrors[2] - 2 * fitErrors[8];
            confidence[6] = fitErrors[0] - 1 * fitErrors[6];
            confidence[7] = fitErrors[1] - 1 * fitErrors[7];
            confidence[8] = fitErrors[2] - 1 * fitErrors[8];
            confidence[9] = fitErrors[0] + 1 * fitErrors[3];
            confidence[10] = fitErrors[1] + 1 * fitErrors[4];
            confidence[11] = fitErrors[2] + 1 * fitErrors[5];
            confidence[12] = fitErrors[0] + 2 * fitErrors[3];
            confidence[13] = fitErrors[1] + 2 * fitErrors[4];
            confidence[14] = fitErrors[2] + 2 * fitErrors[5];
            confidence[15] = fitErrors[0] + 3 * fitErrors[3];
            confidence[16] = fitErrors[1] + 3 * fitErrors[4];
            confidence[17] = fitErrors[2] + 3 * fitErrors[5];


            output += data[j].getName() + "\nH = " + H + "\nG12 = " + G12 + "\n";
            output += "\nH mean = " + fitErrors[0] + "\nG1 mean = " + fitErrors[1] + "\nG2 mean = " + fitErrors[2];
            output += "\nH low 3-sigma = " + confidence[0] + "\nG1 low 3-sigma = " + confidence[1] + "\nG2 low 3-sigma = " + confidence[2];
            output += "\nH low 2-sigma = " + confidence[3] + "\nG1 low 2-sigma = " + confidence[4] + "\nG2 low 2-sigma = " + confidence[5];
            output += "\nH low 1-sigma = " + confidence[6] + "\nG1 low 1-sigma = " + confidence[7] + "\nG2 low 1-sigma = " + confidence[8];
            output += "\nH up 1-sigma = " + confidence[9] + "\nG1 up 1-sigma = " + confidence[10] + "\nG2 up 1-sigma = " + confidence[11];
            output += "\nH up 2-sigma = " + confidence[12] + "\nG1 up 2-sigma = " + confidence[13] + "\nG2 up 2-sigma = " + confidence[14];
            output += "\nH up 3-sigma = " + confidence[15] + "\nG1 up 3-sigma = " + confidence[16] + "\nG2 up 3-sigma = " + confidence[17];
            output += "\n";

        }

        return output;

    }
}
