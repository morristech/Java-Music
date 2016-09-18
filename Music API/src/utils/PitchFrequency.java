package utils;

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
        return pitch.getOctave() * 12 + pitch.getPitchClass().getNumber() - 8;
    }
}
