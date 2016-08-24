package Music.Pitches;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick on 2016-08-21.
 */
public enum PitchLetter {
    C(0), D(1), E(2), F(3), G(4), A(5), B(6);

    private final int pitchLetterNumber;
    private static Map<Integer, PitchLetter> map = new HashMap<>();
    public int getPitchLetterNumber() { return pitchLetterNumber; }

    static {
        for (PitchLetter pitchLetter : PitchLetter.values()) {
            map.put(pitchLetter.pitchLetterNumber, pitchLetter);
        }
    }

    PitchLetter(int pitchLetterNumber) {
        this.pitchLetterNumber = pitchLetterNumber;
    }

    public static PitchLetter valueOf(int pitchLetterNumber) {
        return map.get(pitchLetterNumber);
    }
}