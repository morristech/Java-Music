package pitches;

import pitches.Accidental;
import pitches.PitchClass;
import pitches.PitchLetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 2016-09-17.
 *
 * A utility class to show which pitch classes a number value can map onto (which is several per number,
 * due to enharmonicism).
 */
public class NumberToPitchClasses {
    public static final Map<Integer, HashMap<PitchLetter, PitchClass>> MAP =
            new HashMap<Integer, HashMap<PitchLetter, PitchClass>>() {{
        put(0, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.B, new PitchClass(PitchLetter.B, Accidental.SHARP));
            put(PitchLetter.C, new PitchClass(PitchLetter.C, Accidental.NATURAL));
            put(PitchLetter.D, new PitchClass(PitchLetter.D, Accidental.DOUBLE_FLAT));
        }});
        put(1, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.B, new PitchClass(PitchLetter.B, Accidental.DOUBLE_SHARP));
            put(PitchLetter.C, new PitchClass(PitchLetter.C, Accidental.SHARP));
            put(PitchLetter.D, new PitchClass(PitchLetter.D, Accidental.FLAT));
        }});
        put(2, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.C, new PitchClass(PitchLetter.C, Accidental.DOUBLE_SHARP));
            put(PitchLetter.D, new PitchClass(PitchLetter.D, Accidental.NATURAL));
            put(PitchLetter.E, new PitchClass(PitchLetter.E, Accidental.DOUBLE_FLAT));
        }});
        put(3, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.D, new PitchClass(PitchLetter.D, Accidental.SHARP));
            put(PitchLetter.E, new PitchClass(PitchLetter.E, Accidental.FLAT));
            put(PitchLetter.F, new PitchClass(PitchLetter.F, Accidental.DOUBLE_FLAT));
        }});
        put(4, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.D, new PitchClass(PitchLetter.D, Accidental.DOUBLE_SHARP));
            put(PitchLetter.E, new PitchClass(PitchLetter.E, Accidental.NATURAL));
            put(PitchLetter.F, new PitchClass(PitchLetter.F, Accidental.FLAT));
        }});
        put(5, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.E, new PitchClass(PitchLetter.E, Accidental.SHARP));
            put(PitchLetter.F, new PitchClass(PitchLetter.F, Accidental.NATURAL));
            put(PitchLetter.G, new PitchClass(PitchLetter.G, Accidental.DOUBLE_FLAT));
        }});
        put(6, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.E, new PitchClass(PitchLetter.E, Accidental.SHARP));
            put(PitchLetter.F, new PitchClass(PitchLetter.F, Accidental.SHARP));
            put(PitchLetter.G, new PitchClass(PitchLetter.G, Accidental.FLAT));
        }});
        put(7, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.F, new PitchClass(PitchLetter.F, Accidental.DOUBLE_SHARP));
            put(PitchLetter.G, new PitchClass(PitchLetter.G, Accidental.NATURAL));
            put(PitchLetter.A, new PitchClass(PitchLetter.A, Accidental.DOUBLE_FLAT));
        }});
        put(8, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.G, new PitchClass(PitchLetter.G, Accidental.SHARP));
            put(PitchLetter.A, new PitchClass(PitchLetter.A, Accidental.FLAT));
        }});
        put(9, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.G, new PitchClass(PitchLetter.G, Accidental.DOUBLE_SHARP));
            put(PitchLetter.A, new PitchClass(PitchLetter.A, Accidental.NATURAL));
            put(PitchLetter.B, new PitchClass(PitchLetter.B, Accidental.DOUBLE_FLAT));
        }});
        put(10, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.A, new PitchClass(PitchLetter.A, Accidental.SHARP));
            put(PitchLetter.B, new PitchClass(PitchLetter.B, Accidental.FLAT));
            put(PitchLetter.C, new PitchClass(PitchLetter.C, Accidental.DOUBLE_FLAT));
        }});
        put(11, new HashMap<PitchLetter, PitchClass>() {{
            put(PitchLetter.A, new PitchClass(PitchLetter.A, Accidental.DOUBLE_SHARP));
            put(PitchLetter.B, new PitchClass(PitchLetter.B, Accidental.NATURAL));
            put(PitchLetter.C, new PitchClass(PitchLetter.C, Accidental.FLAT));
        }});
    }};

    private NumberToPitchClasses() {
        // Prevent instantiation
    }
}
