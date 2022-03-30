package pic2beat.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {

    public static void writeXYToFile(String path, double[] x, double[] y) {
        try (PrintWriter writer = new PrintWriter(path)) {

            for(int i = 0; i < x.length; i++) {
                writer.format("%.2f;%.2f\n", x[i], y[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
