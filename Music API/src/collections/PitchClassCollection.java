package collections;

import pitches.PitchClass;
import pitches.Accidental;

import java.util.Arrays;

/**
 * Created by Nick on 2016-09-17.
 */
public abstract class PitchClassCollection {
    /* package-private */ PitchClass[] pitchClasses;
    /* package-private */ CollectionMode mode;

    public CollectionMode getMode() {
        return mode;
    }

    /* package-private */ PitchClassCollection(PitchClass tonic) {
        if (tonic.getAccidental().equals(Accidental.DOUBLE_FLAT)
                || tonic.getAccidental().equals(Accidental.DOUBLE_SHARP)) {
            throw new RuntimeException("\n\nSorry, can't use double accidentals as the basis for a pitch collection. \n"
                    + "This API doesn't support potential triple accidentals that would ensue.\n");
        }

        pitchClasses = new PitchClass[getCollectionSize()];
    }

    public abstract int getCollectionSize();

    // NB zero-based indices
    public PitchClass getScaleDegree(int scaleDegreeNumber) {
        if (scaleDegreeNumber < 0 || scaleDegreeNumber >= getCollectionSize()) {
            throw new IndexOutOfBoundsException();
        }
        return pitchClasses[scaleDegreeNumber];
    }

    @Override
    public String toString() {
        return Arrays.toString(pitchClasses);
    }
}
