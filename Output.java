package AsteroidPhaseCurveAnalyzer;

import java.io.FileWriter;

public class Output {

    public static void writeFile(String fileName, String data) throws Exception {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        }
    }
}
