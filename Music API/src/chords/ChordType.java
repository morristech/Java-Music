package chords;

/**
 * Created by Nick on 2016-09-17.
 */
public enum ChordType {
    MAJOR, MINOR, AUGMENTED, DIMINISHED;

    public static String getSimpleName(ChordType chordType) {
        switch (chordType) {
            case MAJOR: return "maj";
            case MINOR: return "m";
            case AUGMENTED: return "aug";
            case DIMINISHED: return "dim";
            default: throw new RuntimeException("ChordType not found");
        }
    }
}
