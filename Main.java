
package AsteroidPhaseCurveAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) throws FileNotFoundException {
        // Calls GUI
        GUI GUinterface = new GUI();
        SwingUtilities.invokeLater(GUinterface);
        
        // Uncomment the lines below to override the GUI 
        
        //File file = new File(path); // insert the path - remember ""
        //String output = new String();
        //output += "HG1G2 results: \n" + HG1G2.HG1G2Function(file);
        //output += "\nHG12 results: \n" + HG12.HG12Function(file);
        //Output.writeFile(fileName, output); // insert the fileName - remember ""
         
    }
    
}
