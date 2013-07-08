
package AsteroidPhaseCurveAnalyzer;

import Jama.Matrix;

public class Results {
    private double[] coeffs;
    private Matrix aMatrix;
    
    public Results() {
        
    }
    
    public Results(double[] gCoeffs, Matrix gAMatrix) {
        this.coeffs = gCoeffs;
        this.aMatrix = gAMatrix;
    }
    
    public double[] getCoeffs() {
        return this.coeffs;
    }
    
    public Matrix getMatrix(){
        return this.aMatrix;
    }
}
