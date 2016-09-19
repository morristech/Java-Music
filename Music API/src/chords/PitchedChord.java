package chords;

import pitches.Pitch;
import pitches.PitchClass;
import pitches.PitchLetter;

import java.util.Arrays;

/**
 * Created by Nick on 2016-09-17.
 * Like the Chord class but uses an array of Pitches rather than PitchClasses
 */
public class PitchedChord {
    private Pitch[] pitchedChordMembers;

    public PitchedChord(Pitch tonic, ChordType chordType, int chordSize) {
        PitchClass tonicPc = tonic.getPitchClass();
        Chord chord = new Chord(tonicPc, chordType, chordSize);
        PitchClass[] chordMembers = chord.getChordMembers();
        pitchedChordMembers = new Pitch[chord.getChordMembers().length];
        int currOctave = tonic.getOctave();

        //TODO rethink this logic
        boolean flag = true;
        for (int i = 0; i < pitchedChordMembers.length; i++) {
            PitchLetter currPitchLetter = chordMembers[i].getPitchLetter();
            if (currPitchLetter.compareTo(tonicPc.getPitchLetter()) < 0 && flag) {
                currOctave++;
            }
            flag = currPitchLetter.compareTo(tonicPc.getPitchLetter()) >= 0;
            pitchedChordMembers[i] = new Pitch(chordMembers[i], currOctave);
        }
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
}
