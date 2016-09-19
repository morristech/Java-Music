package collections;

/**
 * Created by Nick on 2016-09-17.
 * Named "CollectionMode" rather than simply "Mode" to distinguish from ChordType.java, which also has Major and Minor
 */
public enum CollectionMode {
    MAJOR, MINOR;

    public static String getSimpleName(CollectionMode mode) {
        switch (mode) {
            case MAJOR: return "maj";
            case MINOR: return "m";
            default: throw new RuntimeException("CollectionMode not found");
        }
    }
}
