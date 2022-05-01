package pic2beat.utils;

public interface Scales {

    int[] CHROMATIC_INTERVALS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    int[] NATURAL_MAJOR_INTERVALS = new int[]{0, 2, 4, 5, 7, 9, 11};
    int[] NATURAL_MINOR_INTERVALS = new int[]{0, 2, 3, 5, 7, 8, 10};
    int[] HARMONIC_MINOR_INTERVALS = new int[]{0, 2, 3, 5, 7, 8, 11};
    int[] MELODIC_MINOR_INTERVALS = new int[]{0, 2, 3, 5, 7, 8, 9, 10, 11};
    int[] DIATONIC_MINOR = new int[]{0, 2, 3, 5, 7, 8, 10};
    int[] DORIAN_INTERVALS = new int[]{0, 2, 3, 5, 7, 9, 10};
    int[] LYDIAN_INTERVALS = new int[]{0, 2, 4, 6, 7, 9, 11};
    int[] MIXOLYDIAN_INTERVALS = new int[]{0, 2, 4, 5, 7, 9, 10};
    int[] PENTATONIC_INTERVALS = new int[]{0, 2, 4, 7, 9};
    int[] BLUES_INTERVALS = new int[]{0, 2, 3, 4, 5, 7, 9, 10, 11};
    int[] TURKISH_INTERVALS = new int[]{0, 1, 3, 5, 7, 10, 11};
    int[] INDIAN_INTERVALS = new int[]{0, 1, 1, 4, 5, 8, 10};
    int[] PHRYGIAN_INTERVALS = new int[]{0, 1, 3, 5, 7, 8, 10};
    int[] AEOLIAN_INTERVALS = new int[]{0, 2, 3, 5, 7, 8, 10};
    int[] LOCRIAN_INTERVALS = new int[]{0, 1, 3, 5, 6, 8, 10};
    
    int[][] GREGORIAN_INTERVALS = {NATURAL_MAJOR_INTERVALS, DORIAN_INTERVALS, PHRYGIAN_INTERVALS, LYDIAN_INTERVALS, MIXOLYDIAN_INTERVALS, AEOLIAN_INTERVALS, LOCRIAN_INTERVALS};
    String[] GREGORIAN_MODES_LABELS = {"IONIAN", "DORIAN", "PHRYGIAN", "LYDIAN", "MIXOLYDIAN", "AEOLIAN", "LOCRIAN"};
    
}
