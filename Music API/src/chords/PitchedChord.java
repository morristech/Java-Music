package chords;

import pitches.Pitch;
import pitches.PitchClass;
import utils.PitchLetter;

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

        for (int i = 0; i < pitchedChordMembers.length; i++) {
            PitchLetter currPitchLetter = chordMembers[i].getPitchLetter();
            if (currPitchLetter.compareTo(tonicPc.getPitchLetter()) < 0) {
                currOctave++;
            }
            pitchedChordMembers[i] = new Pitch(chordMembers[i], currOctave);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(pitchedChordMembers);
    }
}
