package chords;

import org.junit.Before;
import org.junit.Test;
import pitches.Accidental;
import pitches.PitchClass;
import pitches.PitchLetter;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Nick on 2016-09-18.
 */
public class ChordTest {
    private Chord cMinor7th;
    private Chord gSharpMajor9th;
    private Chord aFlatMajor11th;
    private Chord dAugmented13th;
    private Chord fDiminished11th;

    @Before
    public void setUp() throws Exception {
        cMinor7th = new Chord(new PitchClass(PitchLetter.C), ChordType.MINOR, ChordSize.SEVENTH);
        gSharpMajor9th = new Chord(new PitchClass(PitchLetter.G, Accidental.SHARP), ChordType.MAJOR, ChordSize.NINTH);
        aFlatMajor11th = new Chord(new PitchClass(PitchLetter.A, Accidental.FLAT), ChordType.MAJOR, ChordSize.ELEVENTH);
        dAugmented13th = new Chord(new PitchClass(PitchLetter.D, Accidental.NATURAL), ChordType.AUGMENTED, ChordSize.THIRTEENTH);
        fDiminished11th = new Chord(new PitchClass(PitchLetter.F, Accidental.NATURAL), ChordType.DIMINISHED, ChordSize.ELEVENTH);
    }

    @Test
    public void chordMembersAreNotNull() throws Exception {
        Arrays.stream(cMinor7th.getChordMembers()).forEach(member -> assertNotNull("Cm7 members should not have been null", member));
        Arrays.stream(gSharpMajor9th.getChordMembers()).forEach(member -> assertNotNull("G#M9 members should not have been null", member));
        Arrays.stream(aFlatMajor11th.getChordMembers()).forEach(member -> assertNotNull("AbM11 members should not have been null", member));
        Arrays.stream(dAugmented13th.getChordMembers()).forEach(member -> assertNotNull("Daug13 members should not have been null", member));
        Arrays.stream(fDiminished11th.getChordMembers()).forEach(member -> assertNotNull("Fdim11 members should not have been null", member));
    }

    @Test
    public void chordMembersAreCorrect() throws Exception {
        assertEquals("cm7 root should be C", new PitchClass(PitchLetter.C), cMinor7th.getChordMember(ChordMember.ROOT));
        assertEquals("cm7 third should be Eb", new PitchClass(PitchLetter.E, Accidental.FLAT), cMinor7th.getChordMember(ChordMember.THIRD));
        assertEquals("cm7 fifth should be G", new PitchClass(PitchLetter.G), cMinor7th.getChordMember(ChordMember.FIFTH));
        assertEquals("cm7 seventh should be Bb", new PitchClass(PitchLetter.B, Accidental.FLAT), cMinor7th.getChordMember(ChordMember.SEVENTH));

        assertEquals("Daug13 root should be D", new PitchClass(PitchLetter.D), dAugmented13th.getChordMember(ChordMember.ROOT));
        assertEquals("Daug13 third should be F#", new PitchClass(PitchLetter.F, Accidental.SHARP), dAugmented13th.getChordMember(ChordMember.THIRD));
        assertEquals("Daug13 fifth should be A#", new PitchClass(PitchLetter.A, Accidental.SHARP), dAugmented13th.getChordMember(ChordMember.FIFTH));
        assertEquals("Daug13 seventh should be C#", new PitchClass(PitchLetter.C, Accidental.SHARP), dAugmented13th.getChordMember(ChordMember.SEVENTH));
        assertEquals("Daug13 ninth should be E", new PitchClass(PitchLetter.E), dAugmented13th.getChordMember(ChordMember.NINTH));
        assertEquals("Daug13 eleventh should be G", new PitchClass(PitchLetter.G), dAugmented13th.getChordMember(ChordMember.ELEVENTH));
        assertEquals("Daug13 thirteenth should be B", new PitchClass(PitchLetter.B), dAugmented13th.getChordMember(ChordMember.THIRTEENTH));

        assertEquals("Fdim11 fifth should be Cb", new PitchClass(PitchLetter.C, Accidental.FLAT), fDiminished11th.getChordMember(ChordMember.FIFTH));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void chordMemberOutOfBoundsThrowsAIOOBException() throws Exception {
        aFlatMajor11th.getChordMember(ChordMember.THIRTEENTH);
    }
}