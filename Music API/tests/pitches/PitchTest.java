package pitches;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nick on 2016-09-18.
 */
public class PitchTest {
    private Pitch cFlat4;
    private Pitch bSharp2;
    private Pitch dSharp3;
    private Pitch e5;


    @Before
    public void setUp() throws Exception {
        cFlat4 = new Pitch(new PitchClass(PitchLetter.C, Accidental.FLAT), Octave.FOURTH);
        bSharp2 = new Pitch(new PitchClass(PitchLetter.B, Accidental.SHARP), Octave.SECOND);
        dSharp3 = new Pitch(new PitchClass(PitchLetter.D, Accidental.SHARP), Octave.THIRD);
        e5 = new Pitch(new PitchClass(PitchLetter.E), Octave.FIFTH);
    }

    @Test
    public void pitchFrequency() throws Exception {
        assertEquals("Cb4 should have a frequency of 246.924", 246.942, cFlat4.getPitchFrequency(), 0.001);
        assertEquals("B#2 should have a frequency of 130.813", 130.813, bSharp2.getPitchFrequency(), 0.001);
        assertEquals("D#3 should have a frequency of 155.563", 155.563, dSharp3.getPitchFrequency(), 0.001);
        assertEquals("e5 should have a frequency of 659.255", 659.255, e5.getPitchFrequency(), 0.001);
    }

    @Test
    public void transposingReturnsCorrectOctave() throws Exception {
        Pitch cFlat4Other = cFlat4.transpose(Interval.UNISON);
        Pitch g3 = bSharp2.transpose(Interval.P4).transpose(Interval.dim3);
        Pitch dSharp4 = dSharp3.transpose(Interval.OCTAVE);
        Pitch c5 = e5.transpose(Interval.m6);

        assertEquals("Cb4 transposed by unison should be in octave 4", 4, cFlat4Other.getOctave());
        assertEquals("B#2 transposed by P4 then dim3 should be in octave 3", 3, g3.getOctave());
        assertEquals("D#3 transposed by an octave should be in octave 4", 4, dSharp4.getOctave());
        assertEquals("E5 transposed by m6 should be in octave 6", 6, c5.getOctave());
    }
}