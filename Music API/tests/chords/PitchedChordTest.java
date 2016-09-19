package chords;

import org.junit.Before;
import org.junit.Test;
import pitches.*;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Nick on 2016-09-18.
 */
public class PitchedChordTest {
    private PitchedChord cMinor7th_startsOctave4;
    private PitchedChord gSharpMajor9th_startsOctave2;
    private PitchedChord aFlatMajor11th_startsOctave3;
    private PitchedChord dAugmented13th_startsOctave5;
    private PitchedChord fDiminished11th_startsOctave1;

    @Before
    public void setUp() throws Exception {
        cMinor7th_startsOctave4 = new PitchedChord(new Pitch(
                new PitchClass(PitchLetter.C), Octave.FOURTH), ChordType.MINOR, ChordSize.SEVENTH);
        gSharpMajor9th_startsOctave2 = new PitchedChord(new Pitch(
                new PitchClass(PitchLetter.G, Accidental.SHARP), Octave.SECOND), ChordType.MAJOR, ChordSize.NINTH);
        aFlatMajor11th_startsOctave3 = new PitchedChord(new Pitch(
                new PitchClass(PitchLetter.A, Accidental.FLAT), Octave.THIRD), ChordType.MAJOR, ChordSize.ELEVENTH);
        dAugmented13th_startsOctave5 = new PitchedChord(new Pitch(
                new PitchClass(PitchLetter.D, Accidental.NATURAL), Octave.FIFTH), ChordType.AUGMENTED, ChordSize.THIRTEENTH);
        fDiminished11th_startsOctave1 = new PitchedChord(new Pitch(
                new PitchClass(PitchLetter.F, Accidental.NATURAL), Octave.FIRST), ChordType.DIMINISHED, ChordSize.ELEVENTH);
    }

    @Test
    public void chordMembersAreCorrectOctave() throws Exception {
        assertEquals("Cm7 starting octave 4 should've had its seventh in octave 4",
                4, cMinor7th_startsOctave4.getChordMember(ChordMember.SEVENTH).getOctave());
        assertEquals("G#M9 starting octave 2 should've had its third in octave 2",
                2, gSharpMajor9th_startsOctave2.getChordMember(ChordMember.THIRD).getOctave());
        assertEquals("G#M9 starting octave 2 should've had its fifth in octave 3",
                3, gSharpMajor9th_startsOctave2.getChordMember(ChordMember.FIFTH).getOctave());
        assertEquals("AbM11 starting octave 3 should've had its root in octave 3",
                3, aFlatMajor11th_startsOctave3.getChordMember(ChordMember.ROOT).getOctave());
        assertEquals("AbM11 starting octave 3 should've had its third in octave 4",
                4, aFlatMajor11th_startsOctave3.getChordMember(ChordMember.THIRD).getOctave());
        assertEquals("AbM11 starting octave 3 should've had its eleventh in octave 5",
                5, aFlatMajor11th_startsOctave3.getChordMember(ChordMember.ELEVENTH).getOctave());
        assertEquals("Daug13 starting octave 5 should've had its fifth in octave 5",
                5, dAugmented13th_startsOctave5.getChordMember(ChordMember.FIFTH).getOctave());
        assertEquals("Daug13 starting octave 5 should've had its seventh in octave 6",
                6, dAugmented13th_startsOctave5.getChordMember(ChordMember.SEVENTH).getOctave());
        assertEquals("Daug13 starting octave 5 should've had its thirteenth in octave 6",
                6, dAugmented13th_startsOctave5.getChordMember(ChordMember.THIRTEENTH).getOctave());
        assertEquals("Fdim11 starting octave 1 should've had its third in octave 1",
                1, fDiminished11th_startsOctave1.getChordMember(ChordMember.THIRD).getOctave());
        assertEquals("Fdim11 starting octave 1 should've had its fifth in octave 2",
                2, fDiminished11th_startsOctave1.getChordMember(ChordMember.FIFTH).getOctave());
    }
}