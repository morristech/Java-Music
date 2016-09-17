package utils;

/**
 * Created by Nick on 2016-08-23.
 */
public class Accidental {
    public static final String DOUBLE_FLAT = "bb";
    public static final String FLAT = "b";
    public static final String NATURAL = "";
    public static final String SHARP = "#";
    public static final String DOUBLE_SHARP = "x";

    private Accidental() {
        // Prevent instantiation
    }

    public static int getAccidentalNumber(String accidental) {
        switch (accidental) {
            case Accidental.DOUBLE_FLAT: return -2;
            case Accidental.FLAT: return -1;
            case Accidental.NATURAL: return 0;
            case Accidental.SHARP: return 1;
            case Accidental.DOUBLE_SHARP: return 2;
            default: throw new RuntimeException("Error getting accidental number value");
        }
    }
}
