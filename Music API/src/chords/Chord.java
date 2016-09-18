package chords;

import collections.DiatonicCollection;
import pitches.PitchClass;
import collections.CollectionMode;
import utils.Interval;
import utils.ScaleDegree;

import java.util.Arrays;

/**
 * Created by Nick on 2016-09-17.
 */

// TODO add constructor for Pitches, too
public class Chord {
    private PitchClass[] chordMembers;

    public PitchClass[] getChordMembers() {
        return chordMembers;
    }

    public Chord(PitchClass tonic, ChordType chordType, int chordSize) {
        if (chordSize < 3) throw new RuntimeException("Chord size must be at least 3");

        chordMembers = new PitchClass[chordSize];
        DiatonicCollection diatonicCollection = new DiatonicCollection(tonic,
                chordType == ChordType.MAJOR || chordType == ChordType.AUGMENTED
                        ? CollectionMode.MAJOR
                        : CollectionMode.MINOR);

        // Cycle thru a diatonic collection by thirds to get the chord members
        for (int i = 0, currScaleDegree = 0;
                i < chordMembers.length;
                i++, currScaleDegree = (currScaleDegree + ScaleDegree.THIRD) % diatonicCollection.getCollectionSize()) {
            chordMembers[i] = diatonicCollection.getScaleDegree(currScaleDegree);
        }

        if (chordType == ChordType.DIMINISHED || chordType == ChordType.AUGMENTED) {
            if (chordType == ChordType.DIMINISHED) {
                chordMembers[ChordMember.FIFTH] = tonic.transpose(Interval.dim5);
            } else {
                chordMembers[ChordMember.FIFTH] = tonic.transpose(Interval.aug5);
            }
        }
    }

    // NB inputting an arg of n means requesting the nth chord member. See ChordMember.java's constants.
    public PitchClass getScaleDegree(int chordMember) {
        if (chordMember < 0 || chordMember >= chordMembers.length) {
            throw new IndexOutOfBoundsException();
        }
        return chordMembers[chordMember];
    }

    @Override
    public String toString() {
        return Arrays.toString(chordMembers);
    }
}
