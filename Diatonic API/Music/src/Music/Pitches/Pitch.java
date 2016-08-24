package Music.Pitches;

/**
 * Created by Nick on 2016-08-21.
 */
public class Pitch {
    private PitchClass pitchClass;
    private Octave octave;
    private PitchFrequency pitchFrequency;

    public PitchClass getPitchClass() {
        return pitchClass;
    }

    public Octave getOctave() {
        return octave;
    }

    public PitchFrequency getPitchFrequency() {
        return pitchFrequency;
    }

    private Pitch() {}

    public static Pitch newInstance(String pitch) {
        if (!isValidPitchInputSize(pitch))
            throw new PitchStringParseException("Erroneous pitch input size");
        PitchClass pitchClass = PitchClass.newInstance(pitch);
        Octave octave = parseOctave(pitch);
        return newInstance(pitchClass, octave);
    }

    public static Pitch newInstance(PitchClass pitchClass, Octave octave) {
        Pitch pitch = new Pitch();
        pitch.pitchClass = pitchClass;
        pitch.octave = octave;
        pitch.pitchFrequency = PitchFrequency.newInstance(pitch);
        return pitch;
    }

    private static boolean isValidPitchInputSize(String pitch) {
        if (pitch == null) return false;
        int pitchLen = pitch.length();
        return pitchLen >= 2 && pitchLen <= 4;
    }

    private static Octave parseOctave(String pitch) {
        int octaveNumber = Character.getNumericValue(pitch.charAt(pitch.length() - 1));
        Octave octave = Octave.valueOf(octaveNumber);
        if (octave == null)
            throw new PitchStringParseException("Erroneous octave number value");
        return octave;
    }

    @Override
    public String toString() {
        return String.format("%s%d", pitchClass, octave.getOctaveNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pitch pitch = (Pitch) o;

        if (pitchClass != null ? !pitchClass.equals(pitch.pitchClass) : pitch.pitchClass != null) return false;
        if (octave != pitch.octave) return false;
        return pitchFrequency != null ? pitchFrequency.equals(pitch.pitchFrequency) : pitch.pitchFrequency == null;
    }

    @Override
    public int hashCode() {
        int result = pitchClass != null ? pitchClass.hashCode() : 0;
        result = 31 * result + (octave != null ? octave.hashCode() : 0);
        result = 31 * result + (pitchFrequency != null ? pitchFrequency.hashCode() : 0);
        return result;
    }

    private static class PitchStringParseException extends RuntimeException {
        public PitchStringParseException(String message) {
            super(message);
        }
    }
}
