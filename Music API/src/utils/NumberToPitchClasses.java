package utils;

import pitches.PitchClass;

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
    public static final Map<Integer, List<PitchClass>> MAP = new HashMap<Integer, List<PitchClass>>() {{
        put(0, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.B, Accidental.SHARP));
            add(new PitchClass(PitchLetter.C, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.D, Accidental.DOUBLE_FLAT));
        }});
        put(1, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.B, Accidental.DOUBLE_SHARP));
            add(new PitchClass(PitchLetter.C, Accidental.SHARP));
            add(new PitchClass(PitchLetter.D, Accidental.FLAT));
        }});
        put(2, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.C, Accidental.DOUBLE_SHARP));
            add(new PitchClass(PitchLetter.D, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.E, Accidental.DOUBLE_FLAT));
        }});
        put(3, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.D, Accidental.SHARP));
            add(new PitchClass(PitchLetter.E, Accidental.FLAT));
            add(new PitchClass(PitchLetter.F, Accidental.DOUBLE_FLAT));
        }});
        put(4, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.D, Accidental.DOUBLE_SHARP));
            add(new PitchClass(PitchLetter.E, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.F, Accidental.FLAT));
        }});
        put(5, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.E, Accidental.SHARP));
            add(new PitchClass(PitchLetter.F, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.G, Accidental.DOUBLE_FLAT));
        }});
        put(6, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.E, Accidental.SHARP));
            add(new PitchClass(PitchLetter.F, Accidental.SHARP));
            add(new PitchClass(PitchLetter.G, Accidental.FLAT));
        }});
        put(7, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.F, Accidental.DOUBLE_SHARP));
            add(new PitchClass(PitchLetter.G, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.A, Accidental.DOUBLE_FLAT));
        }});
        put(8, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.G, Accidental.SHARP));
            add(new PitchClass(PitchLetter.A, Accidental.FLAT));
        }});
        put(9, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.G, Accidental.DOUBLE_SHARP));
            add(new PitchClass(PitchLetter.A, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.B, Accidental.DOUBLE_FLAT));
        }});
        put(10, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.A, Accidental.SHARP));
            add(new PitchClass(PitchLetter.B, Accidental.FLAT));
            add(new PitchClass(PitchLetter.C, Accidental.DOUBLE_FLAT));
        }});
        put(11, new ArrayList<PitchClass>() {{
            add(new PitchClass(PitchLetter.A, Accidental.DOUBLE_SHARP));
            add(new PitchClass(PitchLetter.B, Accidental.NATURAL));
            add(new PitchClass(PitchLetter.C, Accidental.FLAT));
        }});
    }};

    private NumberToPitchClasses() {
        // Prevent instantiation
    }
}
