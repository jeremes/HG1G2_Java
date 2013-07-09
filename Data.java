
package AsteroidPhaseCurveAnalyzer;

public class Data {
    // Contains the name of the asteroid and the data in an array
    // First column: x (or alpha) values
    // Second column: y (or mag) values
    private String name;
    private double[][] data;
    
    public Data(String givenName, double[][] givenData, int n ) {
        this.name = givenName;
        this.data = new double[n][2];
        for(int j = 0; j < n; ++j) {
            this.data[j][0] = givenData[j][0];
            this.data[j][1] = givenData[j][1];
        }
    }
    
    public double[][] getData() {
        return this.data;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setDataName(String name){
        this.name = name;
    }
    
    public void setData(double[] set, int n){
        System.arraycopy(set, 0, this.data, 0, n);
    }
    
    public int getSize(){
        return this.data.length; 
    }
}
