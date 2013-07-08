
package AsteroidPhaseCurveAnalyzer;

public class Basisfunctions {
    
    public static PiecewiseFunctions[] Basisfunctions(double[] x) {
        
        double[] coeffA1 = new double[2];
        coeffA1[0] = 1.0;
        coeffA1[1] = -6/Math.PI;
        PolynomialFunction a1Linear = new PolynomialFunction(x, coeffA1); 
        
        double[] coeffA2 = new double[2]; 
        coeffA2[0] = 1.0;
        coeffA2[1] = -9/(5*Math.PI);
        PolynomialFunction a2Linear = new PolynomialFunction(x, coeffA2);
        
        double[] coeffA3 = new double[1];
        coeffA3[0] = 0.0;
        PolynomialFunction a3Constant = new PolynomialFunction(x, coeffA3);  
        
        double[] knots12 = new double[6];
        knots12[0] = 7.5 * Math.PI / 180.0; 
        knots12[1] = 30.0 * Math.PI / 180.0;
        knots12[2] = 60.0 * Math.PI / 180.0;
        knots12[3] = 90.0 * Math.PI / 180.0;
        knots12[4] = 120.0 * Math.PI / 180.0;
        knots12[5] = 150.0 * Math.PI / 180.0;
        
        double[] yval1 = new double[6];
        yval1[0] = 0.75; 
        yval1[1] = 0.33486;
        yval1[2] = 0.134106;
        yval1[3] = 0.0511048;
        yval1[4] = 0.0214657;
        yval1[5] = 0.0036397;
        
        
        double[] yval2 = new double[6];
        yval2[0] = 0.925;
        yval2[1] = 0.628842;
        yval2[2] = 0.317555;
        yval2[3] = 0.127164;
        yval2[4] = 0.0223739;
        yval2[5] = 0.000165057;
                
        double[] knots3 = new double[9];
        knots3[0] = 0.0 * Math.PI / 180.0;
        knots3[1] = 0.3 * Math.PI / 180.0;
        knots3[2] = 1.0 * Math.PI / 180.0;
        knots3[3] = 2.0 * Math.PI / 180.0;
        knots3[4] = 4.0 * Math.PI / 180.0;
        knots3[5] = 8.0 * Math.PI / 180.0;
        knots3[6] = 12.0 * Math.PI / 180.0;
        knots3[7] = 20.0 * Math.PI / 180.0;
        knots3[8] = 30.0 * Math.PI / 180.0;
        
        double[] yval3 = new double[9];
        yval3[0] = 1.0;
        yval3[1] = 0.833812;
        yval3[2] = 0.577354;
        yval3[3] = 0.421448;
        yval3[4] = 0.231742;
        yval3[5] = 0.103482;
        yval3[6] = 0.0617335;
        yval3[7] = 0.016107;
        yval3[8] = 0.0;
        
        double[] deriv1 = new double[2];
        deriv1[0] = -6/(Math.PI); // at  pi/24 radians
        deriv1[1] = -0.091328612; // at 5pi/6 radians
        
        double[] deriv2 = new double[2];
        deriv2[0] = -9/(5*Math.PI); // at  pi/24 radians
        deriv2[1] = -8.6573138*Math.pow(10.0, -8.0); // at 5pi/6 radians
        
        
        double[] deriv3 = new double[2];
        deriv3[0] = -0.10640097; // at  0 radians
        deriv3[1] = 0.0; // at pi/6 radians

        //Now we define the splines for basis functions
        Spline S1 = Splines.Splines(knots12, yval1, deriv1); 
        Spline S2 = Splines.Splines(knots12, yval2, deriv2);
        Spline S3 = Splines.Splines(knots3, yval3, deriv3);
                 
        PiecewiseFunctions basisFunction1 = new PiecewiseFunctions();
        double upperLimit = 7.5*Math.PI/180.0; //Degrees -> radians
        basisFunction1.addComponent(0.0, upperLimit, a1Linear);
        basisFunction1.addComponent(knots12[0], knots12[knots12.length -1], S1);
        
        PiecewiseFunctions basisFunction2 = new PiecewiseFunctions();
        basisFunction2.addComponent(0.0, upperLimit, a2Linear);
        basisFunction2.addComponent(knots12[0], knots12[knots12.length - 1], S2);
        
        PiecewiseFunctions basisFunction3 = new PiecewiseFunctions();
        basisFunction3.addComponent(knots3[0], knots3[knots3.length - 1], S3);

        double lowerLimit3 = 30.0 * Math.PI / 180.0;
        double upperLimit3 = 150.0 * Math.PI / 180.0;
        basisFunction3.addComponent(lowerLimit3, upperLimit3, a3Constant);
        
        PiecewiseFunctions[] basisFunctions = new PiecewiseFunctions[3];
        basisFunctions[0] = basisFunction1;
        basisFunctions[1] = basisFunction2;
        basisFunctions[2] = basisFunction3;
        
        return basisFunctions;
    }
    
}
