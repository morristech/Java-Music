package pitches;

import pitches.Pitch;

/**
 * Created by Nick on 2016-09-17.
 */
public class PitchFrequency {
    private PitchFrequency() {
        // Prevent instantiation
    }

    public static double calculateFrequency(Pitch pitch) {
        int pianoKeyNumber = getPianoKeyNumber(pitch);
        // Source: https://en.wikipedia.org/wiki/Piano_key_frequencies
        return Math.pow(2, (pianoKeyNumber - 49) / 12.0) * 440.0;
    }

    private static int getPianoKeyNumber(Pitch pitch) {
        PitchClass pitchClass = pitch.getPitchClass();
        int octave = pitch.getOctave();
        int number = pitchClass.getNumber();

        PitchLetter pitchLetter = pitchClass.getPitchLetter();
        String accidental = pitchClass.getAccidental();

        // This resolves the edge cases of Cbb, Cb, B#, and Bx technically going outside their octave number.
        // E.g.  Cb4 is actually a lower frequency than B#3
        // See: https://en.wikipedia.org/wiki/Scientific_pitch_notation#Nomenclature
        if (pitchLetter == PitchLetter.C
                && (accidental.equals(Accidental.DOUBLE_FLAT) || accidental.equals(Accidental.FLAT))) {
            octave--;
        }
        if (pitchLetter == PitchLetter.B
                && (accidental.equals(Accidental.SHARP) || accidental.equals(Accidental.DOUBLE_SHARP))) {
            octave++;
        }

        return octave * 12 + number - 8;
    }
}
