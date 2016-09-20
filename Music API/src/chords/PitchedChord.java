package chords;

import pitches.Pitch;
import pitches.PitchClass;
import pitches.PitchLetter;

import java.util.Arrays;

/**
 * Created by Nick on 2016-09-17.
 * Like the Chord class but uses an array of Pitches rather than PitchClasses
 */

// TODO rethink the utility of this class. Chords are not typically stacked by strict thirds in pitch-space, but more
// akin to how a harmonic series is stacked
public class PitchedChord {
    private Pitch[] pitchedChordMembers;
    private ChordType chordType;
    private int chordSize;

    public int getChordSize() {
        return chordSize;
    }

    public PitchedChord(Pitch tonic, ChordType chordType, int chordSize) {
        PitchClass tonicPc = tonic.getPitchClass();
        Chord chord = new Chord(tonicPc, chordType, chordSize);
        PitchClass[] chordMembers = chord.getChordMembers();
        pitchedChordMembers = new Pitch[chord.getChordMembers().length];
        int currOctave = tonic.getOctave();

        // TODO rethink this logic
        // Use Pitch.java's transpose logic? Why is this dependent on the tonic?
        boolean flag = true;
        for (int i = 0; i < pitchedChordMembers.length; i++) {
            PitchLetter currPitchLetter = chordMembers[i].getPitchLetter();
            if (currPitchLetter.compareTo(tonicPc.getPitchLetter()) < 0 && flag) {
                currOctave++;
            }
            flag = currPitchLetter.compareTo(tonicPc.getPitchLetter()) >= 0;
            pitchedChordMembers[i] = new Pitch(chordMembers[i], currOctave);
        }

        this.chordType = chordType;
        this.chordSize = chordSize;
    }

    // NB inputting an arg of n means requesting the nth chord member. See ChordMember.java's constants.
    public Pitch getChordMember(int chordMember) {
        if (chordMember < 0 || chordMember >= pitchedChordMembers.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return pitchedChordMembers[chordMember];
    }

    @Override
    public String toString() {
        return Arrays.toString(pitchedChordMembers);
    }

    public String getName() {
        return String.format("%s%s%s",
                getChordMember(ChordMember.ROOT),
                ChordType.getSimpleName(chordType),
                ChordSize.getNumericName(chordSize));
    }
}
