package Music.DiatonicChords;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick on 2016-08-21.
 */
public enum ChordType {
    EMPTY(-1),
    MONO(0),
    DYAD(1),
    TRIAD(2),
    SEVENTH(3),
    NINTH(4),
    ELEVENTH(5),
    THIRTEENTH(6);

    private final int chordSize;
    private static Map<Integer, ChordType> map = new HashMap<>();
    public int getChordSize() { return chordSize; }

    static {
        for (ChordType chordType : ChordType.values()) {
            map.put(chordType.chordSize, chordType);
        }
    }

    ChordType(int chordSize) {
        this.chordSize = chordSize;
    }

    public static ChordType valueOf(int chordSize) {
        return map.get(chordSize);
    }
}
