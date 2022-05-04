package pic2beat.melodia;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;
import pic2beat.melodia.rhythms.FullQuaverRhythm;

public abstract class Rhythm {

    /**
     * Contains all classes that inherits from <code>Rhythm</code>
     */
    private static final List<Class<? extends Rhythm>> SUB_CLASSES = new ArrayList<>();

    /*
     * Lazy generator to fill up the list above
     */
	static {
    	String packageName = "pic2beat.melodia.rhythms";
    	List<Class<? extends Rhythm>> commands = new ArrayList<Class<? extends Rhythm>>();
    	URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

    	// Filter .class files.
    	File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
    	    public boolean accept(File dir, String name) {
    	        return name.endsWith(".class");
    	    }
    	});

    	// Find classes implementing ICommand.
    	for (File file : files) {
    	    String className = file.getName().replaceAll(".class$", "");
    	    Class<?> cls = null;
			try {
				cls = Class.forName(packageName + "." + className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    	    if (Rhythm.class.isAssignableFrom(cls)) {
    	        commands.add((Class<Rhythm>) cls);
    	    }
    	    
    	    SUB_CLASSES.addAll(commands);
    	}
    }

    /**
     * @return a random <code>Rhythm</code> object
     */
    public static Rhythm randomRhythm() {
        final double random = Math.random();
        final int nbr = SUB_CLASSES.size();
        for(int i = 0; i < nbr; i++) {
            if(random < (i + 1) / (float) nbr) {
                try {
                    return SUB_CLASSES.get(i).getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        return new FullQuaverRhythm();
    }

    private final List<Note> notes;

    public Rhythm() {
        this.notes = new ArrayList<>();
    }

    /**
     * Applies a rhythm to the notes given
     * @param notes the notes on which to apply the rhythm
     * @return the current instance
     */
    public final Rhythm apply(List<Note> notes) {
        if(notes.size() != getDurations().size()) {
            final String child = this.getClass().getName();
            throw new IllegalArgumentException("A " + child + " must contain " + getDurations().size() + " notes.");
        }

        this.notes.clear();
        this.notes.addAll(notes);

        for(int i = 0; i < getDurations().size(); i++) {
            this.notes.get(i).setRhythmValue(getDurations().get(i), true);
        }

        return this;
    }

    /**
     * Offers the possibility for children to have their own generation system, replacing the fully random one
     * @return the current instance
     */
    public Rhythm generate() {return this;}

    public final List<Note> asNotes() {
        return notes;
    }

    public abstract String getName();

    public abstract List<Double> getDurations();

    public int getNbrOfNotes() {
        return getDurations().size();
    }

}
