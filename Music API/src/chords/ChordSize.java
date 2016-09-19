package chords;

/**
 * Created by Nick on 2016-09-17.
 */
public class ChordSize {
    public static final int TRIAD = 3;
    public static final int SEVENTH = 4;
    public static final int NINTH = 5;
    public static final int ELEVENTH = 6;
    public static final int THIRTEENTH = 7;

    public static String getNumericName(int chordSize) {
        switch (chordSize) {
            case 3: return "";
            case 4: return "7";
            case 5: return "9";
            case 6: return "11";
            case 7: return "13";
            default: throw new RuntimeException("Erroneous chord size");
        }
    }

    private ChordSize() {
        // Prevent instantiation
    }
}
