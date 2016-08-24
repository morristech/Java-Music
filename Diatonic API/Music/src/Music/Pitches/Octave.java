package Music.Pitches;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick on 2016-08-24.
 */
public enum Octave {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7);

    private final int octaveNumber;
    private static Map<Integer, Octave> map = new HashMap<>();
    public int getOctaveNumber() { return octaveNumber; }

    static {
        for (Octave octave : Octave.values()) {
            map.put(octave.octaveNumber, octave);
        }
    }

    Octave(int octaveNumber) {
        this.octaveNumber = octaveNumber;
    }

    public static Octave valueOf(int octaveNumber) {
        return map.get(octaveNumber);
    }
}
