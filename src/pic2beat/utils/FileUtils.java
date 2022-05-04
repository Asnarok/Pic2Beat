package pic2beat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import pic2beat.song.Song;

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
    
    public static void saveToFile(File f, Song s) {
    	try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static Song loadFromFile(File f) {
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			Song s = (Song) in.readObject();
			in.close();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

}
