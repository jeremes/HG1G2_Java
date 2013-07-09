package AsteroidPhaseCurveAnalyzer;

public class PiecewiseFunctions {

    private int nComponents;
    private double[][] limits;
    private Functions[] functions; 
    
    public PiecewiseFunctions() {
        this.nComponents = 0;
        this.limits = new double[0][0];
        this.functions = new Functions[0];
    }
    
    public PiecewiseFunctions(int nComponents, double[][] limits, Functions[] functions ){
        this.nComponents = nComponents;
        this.limits = limits;
        this.functions = functions;
    }
    
    public PiecewiseFunctions(int nComponents, double[] limits, Functions[] functions) {
        this.nComponents = nComponents;
        this.functions = functions;
        double[][] tempLimits = new double[nComponents][2];
        tempLimits[0][0] = limits[0];
        for(int i = 1; i < (nComponents - 1); i++) {
            tempLimits[i][1] = limits[i + 1];
            tempLimits[i+1][0] = limits[i + 1];
        }
        tempLimits[nComponents - 1][1] = limits[nComponents - 1];
        this.limits = tempLimits;
    }
    
    public void addComponent(double low, double high, Functions F) {
        // Manual catenating: new function and limits go to the bottom
        Functions[] tempFuncs = this.functions;
        double[][] tempLimits = this.limits;
        
        this.nComponents++;
        this.functions = new Functions[this.nComponents];
        this.limits = new double[this.nComponents][2];
        
        if(nComponents != 1) {
           for(int i = 0; i < this.nComponents - 1; i++) {
                this.functions[i] = tempFuncs[i];
                this.limits[i][0] = tempLimits[i][0];
                this.limits[i][1] = tempLimits[i][1];
            } 
        }
        
        this.functions[this.nComponents - 1] = F;
        this.limits[this.nComponents - 1][0] = low;
        this.limits[this.nComponents - 1][1] = high;
    }
    
    public Functions getFunction(int n) {
        return this.functions[n-1];
    }
    
    public double[] getLimits(int n) {
        return this.limits[n-1];
    }
    
    public int getSize(){
        return this.nComponents;
    }
    
    public double getValue(double x) {
        int j = -1;
        
        // Finding the right interval
        for(int i = 0; i < this.nComponents; i++) {
            if(x >= this.limits[i][0] && x <= this.limits[i][1]){
                j = i;
                break;
            }
        }
        
        if(j != -1) {
            double value = this.functions[j].getValue(x);
            return value;
        }
        
        else {
            throw new IllegalArgumentException("Value outside of basis functions");
        }
    }
    
}    