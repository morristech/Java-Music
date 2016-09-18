package pitches;

/**
 * Created by Nick on 2016-09-17.
 */
public class Interval {
    public static final Interval UNISON = new Interval(0, PitchLetterSpanValue.UNISON);
    public static final Interval m2 = new Interval(1, PitchLetterSpanValue.SECOND);
    public static final Interval M2 = new Interval(2, PitchLetterSpanValue.SECOND);
    public static final Interval aug2 = new Interval(3, PitchLetterSpanValue.SECOND);
    public static final Interval dim3 = new Interval(2, PitchLetterSpanValue.THIRD);
    public static final Interval m3 = new Interval(3, PitchLetterSpanValue.THIRD);
    public static final Interval M3 = new Interval(4, PitchLetterSpanValue.THIRD);
    public static final Interval P4 = new Interval(5, PitchLetterSpanValue.FOURTH);
    public static final Interval aug4 = new Interval(6, PitchLetterSpanValue.FOURTH);
    public static final Interval dim5 = new Interval(6, PitchLetterSpanValue.FIFTH);
    public static final Interval P5 = new Interval(7, PitchLetterSpanValue.FIFTH);
    public static final Interval aug5 = new Interval(8, PitchLetterSpanValue.FIFTH);
    public static final Interval m6 = new Interval(8, PitchLetterSpanValue.SIXTH);
    public static final Interval M6 = new Interval(9, PitchLetterSpanValue.SIXTH);
    public static final Interval aug6 = new Interval(10, PitchLetterSpanValue.SIXTH);
    public static final Interval m7 = new Interval(10, PitchLetterSpanValue.SEVENTH);
    public static final Interval M7 = new Interval(11, PitchLetterSpanValue.SEVENTH);
    public static final Interval OCTAVE = new Interval(12 /* 0 suffices, too */, PitchLetterSpanValue.OCTAVE);

    private final int numberSpan;  // i.e. pitch class number value
    private final int letterSpan;

    public int getNumberSpan() {
        return numberSpan;
    }

    public int getLetterSpan() {
        return letterSpan;
    }

    private Interval(int pitchClassNumberSpan, int pitchLetterSpan) {
        this.numberSpan = pitchClassNumberSpan;
        this.letterSpan = pitchLetterSpan;
    }

    // E.g. C to E would be 2
    public static class PitchLetterSpanValue {
        public static final int UNISON = 0;
        public static final int SECOND = 1;
        public static final int THIRD = 2;
        public static final int FOURTH = 3;
        public static final int FIFTH = 4;
        public static final int SIXTH = 5;
        public static final int SEVENTH = 6;
        public static final int OCTAVE = 7;

        private PitchLetterSpanValue() {
            // Prevent instantiation
        }
    }
}
