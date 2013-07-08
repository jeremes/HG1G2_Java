
package AsteroidPhaseCurveAnalyzer;

public class PolynomialFunction  implements Functions{
    private double[] coeff;
    private double[] x;
    
    public PolynomialFunction(double[] x, double[] gCoeff) {
        this.coeff = gCoeff;
        this.x = x;
    }
    
    public PolynomialFunction() {
        this.coeff = new double[2];
        this.coeff[0] = 0;
        this.coeff[1] = 0;
        this.x = new double[2];
        this.x[0] = 0;
        this.x[1] = 0;
    }
    
    public double[] Polynomial(PolynomialFunction F) {
        double[] y = new double[this.x.length];
            for(int i = 0; i < x.length; i++) {
                y[i] = this.coeff[0];
                for(int j = 0; j < this.coeff.length; j++) {
                    y[i] += this.coeff[j]*Math.pow(x[i], j);
                }
            }
            return y;
    }
    
    /**
     *
     * @param x
     * @return
     */
    @Override
    public double getValue(double x) {
        double value = this.coeff[0];
        for(int i = 1; i < this.coeff.length; i++) {
            value += this.coeff[i]*Math.pow(x, i);
        }
        
        return value;
    }
    
}
