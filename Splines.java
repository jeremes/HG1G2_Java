package AsteroidPhaseCurveAnalyzer;

public class Splines {
    
    public static Spline Splines(double[] xval, double[] yval, double[] deriv) {
        int N = xval.length;
        int nDeriv = deriv.length;
        double[] A = new double[N];
        double[] B = new double[N];
        double[] C = new double[N];
        double[] R = new double[N];
        double[] gamma = new double[N];
        double[] U = new double[N];
        for(int i = 0; i < N; i++) {
                B[i] = 1.0;
                A[i] = 0.0;
                C[i] = 0.0;
                R[i] = 0.0;
                U[i] = 0.0;
                gamma[i] = 0.0;
        }
        
        R[0] = deriv[0];
        
        for(int i = 1; i < (N-1); i++) {
            A[i] = 1/(xval[i] - xval[i-1]);
            B[i] = 2/(xval[i] - xval[i-1]) + 2/(xval[i+1] - xval[i]);
            C[i] = 1/(xval[i+1] - xval[i]); //uutta!
            R[i] += 3*(yval[i] - yval[i-1])/((xval[i]-xval[i-1])*(xval[i]-xval[i-1]));
            R[i] += 3*(yval[i+1] - yval[i])/((xval[i+1]-xval[i])*(xval[i+1]-xval[i]));
        }
        
        R[N-1] = deriv[nDeriv-1];
        
        // Tri-diagonal solver
        double beta = B[0];
        U[0] = R[0]/beta;
        
        for(int i = 1; i < N; i++) {
            gamma[i] = C[i-1]/beta;
            beta = B[i] - A[i]*gamma[i];
            U[i] = (R[i] -A[i]*U[i-1])/beta;
        }
        
        for(int i = N-2; i > 0; i--) {
            U[i] = U[i] - gamma[i+1]*U[i+1];
            
        }
        
        double[] tempXval = xval;
        double[] tempYval = yval;
        double[] tempU = U;
         
        Spline S = new Spline(tempXval, tempYval, tempU);
        return S;
        
    }
    
    //This function gives polynomial coefficients for specific part
    public static double SplineFunction(Spline spline, double x) {
        int nKnots = spline.getKnots();
        int i = -1;
        
        for(int j = 0; j < (nKnots -1); j++){
            if(spline.getKnot(j) <= x && x <= spline.getKnot(j+1)) {
                i = j;
                break;
            }
        }
        
        if(i == -1){
            System.out.println(i + "    " + x);
            throw new IllegalArgumentException("x-value outside spline");
        }
        
        double x1 = spline.getKnot(i);
        double x2 = spline.getKnot(i+1);
        double y1 = spline.getYval(i);
        double y2 = spline.getYval(i+1);
        double d1 = spline.getDeriv(i);
        double d2 = spline.getDeriv(i+1);
        double xd = x2 - x1;
        double yd = y2 - y1;
        double a = d1*xd - yd;
        double b = -d2*xd + yd;
        double t = (x-x1)/xd;
        double y = (1-t)*y1 + t*y2 + t*(1-t)*(a*(1-t) + b*t);
        
        return y; 
        
        
    }
   
        }
        

