package pitches;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nick on 2016-09-18.
 */
public class PitchClassTest {
    private PitchClass bSharp;
    private PitchClass a;
    private PitchClass gFlat;
    private PitchClass aFlat;

    @Before
    public void setUp() throws Exception {
        bSharp = new PitchClass(PitchLetter.B, Accidental.SHARP);
        a = new PitchClass(PitchLetter.A);
        gFlat = new PitchClass(PitchLetter.G, Accidental.FLAT);
        aFlat = new PitchClass(PitchLetter.A, Accidental.FLAT);
    }

    @Test
    public void transposingReturnsCorrectPitchLetter() throws Exception {
        PitchClass cSharp = bSharp.transpose(Interval.m2);
        PitchClass fFlat = a.transpose(Interval.P4).transpose(Interval.dim3);
        PitchClass gFlatOther = gFlat.transpose(Interval.OCTAVE);

        assertEquals("B sharp transposed by m2 should be C sharp", PitchLetter.C, cSharp.getPitchLetter());
        assertEquals("F flat transposed by P4 then dim3 should be F flat", PitchLetter.F, fFlat.getPitchLetter());
        assertEquals("G flat transposed by an octave should be G flat", PitchLetter.G, gFlatOther.getPitchLetter());
    }

    @Test
    public void transposingReturnsCorrectAccidental() throws Exception {
        PitchClass cSharp = bSharp.transpose(Interval.m2);
        PitchClass fDoubleFlat = aFlat.transpose(Interval.P4).transpose(Interval.dim3);
        PitchClass gFlatOther = gFlat.transpose(Interval.OCTAVE);

        assertEquals("B sharp transposed by m2 should be C sharp", Accidental.SHARP, cSharp.getAccidental());
        assertEquals("A flat transposed by P4 then dim3 should be F double-flat", Accidental.DOUBLE_FLAT, fDoubleFlat.getAccidental());
        assertEquals("G flat transposed by an octave should be G flat", Accidental.FLAT, gFlatOther.getAccidental());
    }

    @Test(expected = RuntimeException.class)
    public void incorrectAccidentalStringInput() throws Exception {
        new PitchClass(PitchLetter.A, "Kaboom!");
    }

    @Test(expected = RuntimeException.class)
    public void nullAccidentalInput() throws Exception {
        new PitchClass(PitchLetter.A, null);
    }

    @Test
    public void correctNumberOnNewInstance() throws Exception {
         assertEquals(8, aFlat.getNumber());
    }

    @Test
    public void transposingReturnsDifferentNumber() throws Exception {
        PitchClass cSharp = bSharp.transpose(Interval.m2);

        assertEquals(1, cSharp.getNumber());
    }

    @Test
    public void transposingReturnsSameNumber() throws Exception {
        PitchClass gFlatOther = gFlat.transpose(Interval.OCTAVE);

        assertEquals(6, gFlatOther.getNumber());
    }
}