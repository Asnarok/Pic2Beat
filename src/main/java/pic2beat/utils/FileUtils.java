package pic2beat.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.stream.Collectors;

import pic2beat.song.Song;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FileUtils {

	/**
	 * Writes values in XY format to draw graphs in Excel
	 * @param path path of the file to write into
	 * @param x x values
	 * @param y y values
	 */
    public static void writeXYToFile(String path, double[] x, double[] y) {
        try (PrintWriter writer = new PrintWriter(path)) {

            for(int i = 0; i < x.length; i++) {
                writer.format("%.2f;%.2f\n", x[i], y[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Saves a song in a file
	 * @param f file in which to save the song
	 * @param s song to save
	 */
	public static void saveToFile(File f, Song s) {
    	try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Loads a song as <code>Song</code> object from a file
	 * @param f file whose song is to load
	 * @return the song loaded
	 */
	public static Song loadFromFile(File f) {
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			Song s = (Song) in.readObject();
			in.close();
			return s;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }

	/**
	 * Loads a resource inside class path as a string
	 * @param filename path of the file to load
	 * @return the content of the file as a string
	 * @throws IOException on stream errors
	 */
    public static String resourceFileAsString(String filename) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try(InputStream is = classLoader.getResourceAsStream(filename)) {
			if(is == null) return null;
			try(InputStreamReader isr = new InputStreamReader(is);
				BufferedReader reader = new BufferedReader(isr)) {
				return reader.lines().collect(Collectors.joining(System.lineSeparator()));
			}
		}
	}

	/**
	 * Loads an image inside class path as a <code>ImageIcon</code>
	 * @param filename path of the file to load
	 * @return the image as a new <code>ImageIcon</code>
	 * @throws IOException on stream errors
	 */
	public static ImageIcon resourceFileAsImageIcon(String filename) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try(InputStream is = classLoader.getResourceAsStream(filename)) {
			if(is == null) return null;
			BufferedImage bi = ImageIO.read(is);
			return new ImageIcon(bi);
		}
	}
}
