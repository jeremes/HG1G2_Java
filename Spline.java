package AsteroidPhaseCurveAnalyzer;

public class Spline implements Functions{
    private double[] knots;
    private double[] yval;
    private double[] deriv;
    
    public Spline(double[] gKnots, double[] gYval, double[] gDeriv){
      this.knots = gKnots;
      this.yval = gYval;
      this.deriv = gDeriv;
    }

    Spline() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public int getKnots() {
        int n = this.knots.length;
        return n;
    }
    
    public double getKnot(int i) {
        double value = knots[i];
        return value;
     }
    
    public double getYval(int i) {
        double value = yval[i];
        return value;
    }
    
    public double getDeriv(int i) {
        double value = deriv[i];
        return value;
    }
    
    /**
     *
     * @param x
     * @return
     */
    @Override
    
    // getValue - method uses SplineFunction located in Splines.java
    public double getValue(double x) {
        Spline spline = new Spline(this.knots, this.yval, this.deriv);
        double value = Splines.SplineFunction(spline, x);
        return value;
    }
    
}
