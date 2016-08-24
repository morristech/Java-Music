package Music.Pitches;

/**
 * Created by Nick on 2016-08-24.
 */
public class PitchFrequency {
    private double hertzValue;

    public double getHertzValue() {
        return hertzValue;
    }

    private PitchFrequency() {}

    public static PitchFrequency newInstance(Pitch pitch) {
        PitchFrequency pitchFrequency = new PitchFrequency();
        pitchFrequency.hertzValue = calculateFrequency(pitch);
        return pitchFrequency;
    }

    private static double calculateFrequency(Pitch pitch) {
        int pianoKeyNumber = getPianoKeyNumber(pitch);
        return Math.pow(2, (pianoKeyNumber - 49) / 12.0) * 440;
    }

    private static int getPianoKeyNumber(Pitch pitch) {
        int octaveNumber = pitch.getOctave().getOctaveNumber();
        int pitchClassNumber = pitch.getPitchClass().getPitchClassNumber();

        PitchLetter pitchLetter = pitch.getPitchClass().getPitchLetter();
        Accidental accidental = pitch.getPitchClass().getAccidental();

        // This resolves the edge cases of Cbb, Cb, B#, and Bx technically going outside their octave number.
        // E.g.  Cb4 is actually a lower frequency than B#3
        // See: https://en.wikipedia.org/wiki/Scientific_pitch_notation#Nomenclature
        if (pitchLetter == PitchLetter.C
                && (accidental == Accidental.DOUBLE_FLAT || accidental == Accidental.FLAT)) {
            octaveNumber--;
        }
        if (pitchLetter == PitchLetter.B
                && (accidental == Accidental.SHARP || accidental == Accidental.DOUBLE_SHARP)) {
            octaveNumber++;
        }

        return octaveNumber * 12 + pitchClassNumber - 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PitchFrequency pitchFrequency = (PitchFrequency) o;

        return Double.compare(pitchFrequency.hertzValue, hertzValue) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(hertzValue);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return String.format("%.3f Hz", hertzValue);
    }
}
