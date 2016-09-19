package collections;

import org.junit.Before;
import org.junit.Test;
import pitches.Accidental;
import pitches.PitchClass;
import pitches.PitchLetter;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by Nick on 2016-09-18.
 */
public class PitchClassCollectionTest {
    private PitchClassCollection fSharpMajorDiatonic;
    private PitchClassCollection bFlatMinorPentatonic;

    @Before
    public void setUp() throws Exception {
        fSharpMajorDiatonic = new DiatonicCollection(new PitchClass(PitchLetter.F, Accidental.SHARP), CollectionMode.MAJOR);
        bFlatMinorPentatonic = new PentatonicCollection(new PitchClass(PitchLetter.B, Accidental.FLAT), CollectionMode.MINOR);
    }

    @Test
    public void allCollectionMembersAreNotNull() throws Exception {
        IntStream.range(0, fSharpMajorDiatonic.getCollectionSize())
                .forEach(i -> assertNotNull("F#M diatonic collection should not hold any nulls",
                                            fSharpMajorDiatonic.getScaleDegree(i)));

        IntStream.range(0, bFlatMinorPentatonic.getCollectionSize())
                .forEach(i -> assertNotNull("Bbm pentatonic collection should not hold any nulls",
                        bFlatMinorPentatonic.getScaleDegree(i)));
    }

    @Test
    public void collectionMembersAreCorrect() throws Exception {
        assertEquals("First scale degree of F#M diatonic should be F#",
                new PitchClass(PitchLetter.F, Accidental.SHARP), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.FIRST));
        assertEquals("Second scale degree of F#M diatonic should be G#",
                new PitchClass(PitchLetter.G, Accidental.SHARP), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.SECOND));
        assertEquals("Third scale degree of F#M diatonic should be A#",
                new PitchClass(PitchLetter.A, Accidental.SHARP), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.THIRD));
        assertEquals("Fourth scale degree of F#M diatonic should be B",
                new PitchClass(PitchLetter.B), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.FOURTH));
        assertEquals("Fifth scale degree of F#M diatonic should be C#",
                new PitchClass(PitchLetter.C, Accidental.SHARP), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.FIFTH));
        assertEquals("Sixth scale degree of F#M diatonic should be D#",
                new PitchClass(PitchLetter.D, Accidental.SHARP), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.SIXTH));
        assertEquals("Seventh scale degree of F#M diatonic should be E#",
                new PitchClass(PitchLetter.E, Accidental.SHARP), fSharpMajorDiatonic.getScaleDegree(ScaleDegree.SEVENTH));

        assertEquals("First scale degree of Bbm pentatonic should be Bb",
                new PitchClass(PitchLetter.B, Accidental.FLAT), bFlatMinorPentatonic.getScaleDegree(ScaleDegree.FIRST));
        assertEquals("Second scale degree of Bbm pentatonic should be Db",
                new PitchClass(PitchLetter.D, Accidental.FLAT), bFlatMinorPentatonic.getScaleDegree(ScaleDegree.SECOND));
        assertEquals("Third scale degree of Bbm pentatonic should be Eb",
                new PitchClass(PitchLetter.E, Accidental.FLAT), bFlatMinorPentatonic.getScaleDegree(ScaleDegree.THIRD));
        assertEquals("Fourth scale degree of Bbm pentatonic should be F",
                new PitchClass(PitchLetter.F), bFlatMinorPentatonic.getScaleDegree(ScaleDegree.FOURTH));
        assertEquals("Fifth scale degree of Bbm pentatonic should be Ab",
                new PitchClass(PitchLetter.A, Accidental.FLAT), bFlatMinorPentatonic.getScaleDegree(ScaleDegree.FIFTH));
    }
}