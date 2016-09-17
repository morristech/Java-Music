import chords.Chord;
import chords.ChordSize;
import chords.ChordType;
import collections.DiatonicCollection;
import collections.CollectionMode;
import collections.PentatonicCollection;
import pitches.Pitch;
import pitches.PitchClass;
import utils.*;

/**
 * Created by Nick on 2016-09-17.
 */
public class Driver {
    public static void main(String[] args) {
        Pitch b3 = new Pitch(new PitchClass(PitchLetter.B, Accidental.NATURAL), Octave.THIRD);
        System.out.printf("%s -> major 3rd -> %s%n", b3, b3.add(Interval.M3));

        PitchClass g = new PitchClass(PitchLetter.G, Accidental.NATURAL);
        DiatonicCollection gMinor = new DiatonicCollection(g, CollectionMode.MINOR);
        DiatonicCollection gMajor = new DiatonicCollection(g, CollectionMode.MAJOR);

        System.out.println(gMinor);
        System.out.println(gMajor);

        System.out.println("The third scale degree of G minor diatonic is: "
                + gMinor.getScaleDegree(ScaleDegree.THIRD));

        PitchClass cSharp = new PitchClass(PitchLetter.C, Accidental.SHARP);
        PentatonicCollection cSharpMinorPentatonic = new PentatonicCollection(cSharp, CollectionMode.MINOR);
        PentatonicCollection cSharpMajorPentatonic = new PentatonicCollection(cSharp, CollectionMode.MAJOR);

        System.out.println(cSharpMinorPentatonic);
        System.out.println(cSharpMajorPentatonic);

        System.out.println("The fourth scale degree of C-Sharp major pentatonic is: "
                + cSharpMajorPentatonic.getScaleDegree(ScaleDegree.FOURTH));

        PitchClass c = new PitchClass(PitchLetter.C, Accidental.NATURAL);
        Chord cMajor7th = new Chord(c, ChordType.MINOR, ChordSize.NINTH);
        System.out.println(cMajor7th);

        Pitch db4 = new Pitch(new PitchClass(PitchLetter.D, Accidental.FLAT), Octave.FOURTH);

    }
}
