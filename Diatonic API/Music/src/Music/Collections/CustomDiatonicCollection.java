package Music.Collections;

import Music.Common.Interval;
import Music.Pitches.PitchClass;
import Music.Pitches.PitchLetter;

/**
 * Created by Nick on 2016-08-23.
 */
public class CustomDiatonicCollection {
    // TODO ask for starting pitch class, then accidentals
    // Based on the number of diatonic scale degrees
    public static final int MAX_PITCH_COLLECTION_SIZE = 7;
    private static final int TONIC = 0;
    private static final int SUPERTONIC = 1;
    private static final int MEDIANT = 2;
    private static final int SUBDOMINANT = 3;
    private static final int DOMINANT = 4;
    private static final int SUBMEDIANT = 5;
    private static final int LEADING_TONE = 6;

    private final PitchClass[] pitchCollection = new PitchClass[MAX_PITCH_COLLECTION_SIZE];

    public PitchClass[] getPitchCollection() {
        return pitchCollection;
    }

    public CustomDiatonicCollection(String tonic,
                                    String supertonic,
                                    String mediant,
                                    String subdominant,
                                    String dominant,
                                    String submediant,
                                    String leadingTone) {
        pitchCollection[TONIC] = PitchClass.newInstance(tonic);
        pitchCollection[SUPERTONIC] = PitchClass.newInstance(supertonic);
        pitchCollection[MEDIANT] = PitchClass.newInstance(mediant);
        pitchCollection[SUBDOMINANT] = PitchClass.newInstance(subdominant);
        pitchCollection[DOMINANT] = PitchClass.newInstance(dominant);
        pitchCollection[SUBMEDIANT] = PitchClass.newInstance(submediant);
        pitchCollection[LEADING_TONE] = PitchClass.newInstance(leadingTone);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        for (PitchClass pitchClass : pitchCollection) {
            if (pitchClass != null) {
                sb.append(String.format("%s ", pitchClass.toString()));
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
