package AsteroidPhaseCurveAnalyzer;

import Jama.Matrix;
import Jama.QRDecomposition;

public class LeastSquares {
    
    public static Results LeastSquares(PiecewiseFunctions[] basis, Data ddata, double[] errors) {
        int nData = ddata.getSize();
        double[][] data = ddata.getData();
        double[] xvalues = new double[nData];
        for (int i = 0; i < nData; i++) {
            xvalues[i] = data[i][0]* Math.PI/180;
        }
        
        // Transforming the yvalues (see developer's guide)
        double[] yvalues = new double[nData];
        for (int i = 0; i < nData; i++) {
            yvalues[i] = Math.pow(10.0, -0.4*data[i][1]);
        }
        
        // Transforming the errors from tha magnitude realm
        double[] sigmas = new double[nData];
        for(int i = 0; i < nData; i++) {
            sigmas[i] = yvalues[i]*(Math.pow(10.0, 0.4*errors[i]) - 1);
        }
        
        int nFuncs = basis.length; 
        Matrix aMatrix = new Matrix(nData, nFuncs); 
        for(int i = 0; i < nData; i++) {
            for(int j = 0; j < nFuncs; j++) {
                // getValue is getting the value of basis function j
                // at xvalues[i], for aMatrix this is then divided by errors
                aMatrix.set(i, j, (basis[j].getValue(xvalues[i]))/sigmas[i]);
            }
        }
        /*
        for(int i = 0; i < nData; i++) {
            for(int j = 0; j < nFuncs; j++) {
                System.out.print(aMatrix.get(i, j) + "   ");
            }
            System.out.println("");
        } */
        
        for(int i = 0; i < nData; i++) {
            yvalues[i] = 1/(Math.pow(10.0, 0.4*errors[i]) - 1);
        }
        
        Matrix Coeffs;
        Matrix Y = new Matrix(yvalues, nData);
        
        // Solving the equation Y = A*coeffs by QR decomposition
        QRDecomposition qr = new QRDecomposition(aMatrix);
        Coeffs = qr.solve(Y);
        
        double[] coeffs = new double[3];
        for(int i = 0; i < 3; i++) {
            coeffs[i] = Coeffs.get(i, 0);
           //  System.out.println("a" + i + " = " + coeffs[i]);
        }
        
        Results results = new Results(coeffs, aMatrix);
        return results;
    }
    
    @SuppressWarnings("empty-statement")
    public static Results LeastSquaresG12(PiecewiseFunctions[] basis, Data ddata, double[] errors) {
        // this method uses the same principles as the above, but this time for 
        // two different definitions depending on the value of G12
        //
        // the one chosen is the one with smaller rms-error
        
        int nData = ddata.getSize();
        double b10 = 0.06164;
        double b11 = 0.7527;
        double g10 = 0.6270;
        double g11 = -0.9612;
        
        double b20 = 0.02162;
        double b21 = 0.9529;
        double g20 = 0.5572;
        double g21 = -0.6125;
        
        
        double[][] data = ddata.getData();
        double[] xvalues = new double[nData];
        for (int i = 0; i < nData; i++) {
            xvalues[i] = data[i][0]* Math.PI/180;
        }
        
        double[] yvalues = new double[nData];
        for (int i = 0; i < nData; i++) {
            yvalues[i] = Math.pow(10.0, -0.4*data[i][1]);
        }
        
        double[] sigmas = new double[nData];
        for(int i = 0; i < nData; i++) {
            sigmas[i] = yvalues[i]*(Math.pow(10.0, 0.4*errors[i]) - 1);
        }
        
        Matrix aMatrix1 = new Matrix(nData, 2); 
        for(int i = 0; i < nData; i++) {
            aMatrix1.set(i, 0, (b10*basis[0].getValue(xvalues[i]) + g10*basis[1].getValue(xvalues[i]) + (1 - b10 - g10)*basis[2].getValue(xvalues[i]))/sigmas[i]);
            aMatrix1.set(i, 1, (b11*basis[0].getValue(xvalues[i]) + g11*basis[1].getValue(xvalues[i]) - (b11 + g11)*basis[2].getValue(xvalues[i]))/sigmas[i]);
        }
       /* 
        for(int i = 0; i < nData; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print(aMatrix1.get(i, j) + "   ");
            }
            System.out.println("");
        } 
        */
        Matrix aMatrix2 = new Matrix(nData, 2); 
        for(int i = 0; i < nData; i++) {
            aMatrix2.set(i, 0, (b20*basis[0].getValue(xvalues[i]) + g20*basis[1].getValue(xvalues[i]) + (1 - b20 - g20)*basis[2].getValue(xvalues[i]))/sigmas[i]);
            aMatrix2.set(i, 1, (b21*basis[0].getValue(xvalues[i]) + g21*basis[1].getValue(xvalues[i]) - (b21 + g21)*basis[2].getValue(xvalues[i]))/sigmas[i]);
        }
        /*
        for(int i = 0; i < nData; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print(aMatrix2.get(i, j) + "   ");
            }
            System.out.println("");
        } 
        */
        for(int i = 0; i < nData; i++) {
            yvalues[i] = 1/(Math.pow(10.0, 0.4*errors[i]) - 1);
        }
        
        Matrix Coeffs1;
        Matrix Coeffs2;
        Matrix Y = new Matrix(yvalues, nData);
        
        QRDecomposition qr1 = new QRDecomposition(aMatrix1);
        Coeffs1 = qr1.solve(Y);
        
        QRDecomposition qr2 = new QRDecomposition(aMatrix2);
        Coeffs2 = qr2.solve(Y);

        double error1 = 0;
        double error2 = 0;
        
        for(int i = 0; i < yvalues.length; i++) {
            error1 += Math.pow((Y.get(i, 0) - Coeffs1.get(0, 0)*aMatrix1.get(i, 0) - Coeffs1.get(1, 0)*aMatrix1.get(i, 1)), 2);
            error2 += Math.pow((Y.get(i, 0) - Coeffs2.get(0, 0)*aMatrix2.get(i, 0) - Coeffs2.get(1, 0)*aMatrix2.get(i, 1)), 2);;
        }
        
        double rmsError1 = Math.sqrt(error1/yvalues.length);
        double rmsError2 = Math.sqrt(error2/yvalues.length);
        
        double[] coeffs = new double[2];
        Matrix aMatrix;
        
        if(rmsError1 < rmsError2) {
            for(int i = 0; i < 2; i++) {
                coeffs[i] = Coeffs1.get(i, 0);
               // System.out.println("c" + i + " = " + coeffs[i]);
            }
            aMatrix = aMatrix1;
        }
        
        else {
            for(int i = 0; i < 2; i++) {
                coeffs[i] = Coeffs2.get(i, 0);
               // System.out.println("c" + i + " = " + coeffs[i]);
            }  
            aMatrix = aMatrix2;
        }
        
        Results results = new Results(coeffs, aMatrix);
        return results;
    }
    
    
    
}   
    
