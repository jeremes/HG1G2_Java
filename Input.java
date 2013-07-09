package AsteroidPhaseCurveAnalyzer;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Input {
 
    public static Data[] Input(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        int k = 0;
        while(reader.hasNext()) {
            String line = reader.nextLine();
            // Note: this DOES NOT WORK if the names of the asteroids do not
            // start with a "("
            if(line.startsWith("(")) {
                k++;
            }
        }
        
        reader.close();
        reader = new Scanner(file);
        
        Data[] dataSet = new Data[k];
        
        // The file is assumed to be in .txt-format and to contain the data in
        // the following style:
        // "AsteroidName NrOfDatapoints"
        // "PhaseAngle1 ReducedMagnitude1 " and so on
        
        int j = 0;
        
       while(j < k) {
       
       String asteroid = reader.next();

       int n = 0;
       if(reader.hasNextInt()) {
            n = Integer.parseInt(reader.next());
       }
       else {
           System.out.println("File not of valid format - read the documentations");
       }
       
       double[][] tempData;
        tempData = new double[n][2];
        
       int i = 0;
       while(i < n) {
           tempData[i][0] = Double.parseDouble(reader.next());
           tempData[i][1] = Double.parseDouble(reader.next());
           i++;
       }
        
        Data data;
        data = new Data(asteroid, tempData, n);
        dataSet[j] = data;
        
        j++;
       
        }
       return dataSet;
    }
}
