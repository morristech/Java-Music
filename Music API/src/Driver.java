import chords.Chord;
import chords.ChordSize;
import chords.ChordType;
import chords.PitchedChord;
import collections.*;
import pitches.*;

/**
 * Created by Nick on 2016-09-17.
 */
public class Driver {
    // TODO clean this up
    public static void main(String[] args) {
        Pitch b3 = new Pitch(new PitchClass(PitchLetter.B, Accidental.NATURAL), Octave.THIRD);
        System.out.printf("%s -> major 3rd -> %s%n", b3, b3.transpose(Interval.M3));

        PitchClass g = new PitchClass(PitchLetter.G, Accidental.NATURAL);
        PitchClassCollection gMinor = new DiatonicCollection(g, CollectionMode.MINOR);
        PitchClassCollection gMajor = new DiatonicCollection(g, CollectionMode.MAJOR);

        System.out.println(gMinor);
        System.out.println(gMajor);

        System.out.println("The third scale degree of G minor diatonic is: "
                + gMinor.getScaleDegree(ScaleDegree.THIRD));

        PitchClass cSharp = new PitchClass(PitchLetter.C, Accidental.SHARP);
        PitchClassCollection cSharpMinorPentatonic = new PentatonicCollection(cSharp, CollectionMode.MINOR);
        PitchClassCollection cSharpMajorPentatonic = new PentatonicCollection(cSharp, CollectionMode.MAJOR);

        System.out.println(cSharpMinorPentatonic);
        System.out.println(cSharpMajorPentatonic);

        System.out.println("The fourth scale degree of C-Sharp major pentatonic is: "
                + cSharpMajorPentatonic.getScaleDegree(ScaleDegree.FOURTH));

        PitchClass c = new PitchClass(PitchLetter.C, Accidental.NATURAL);
        Chord cMajor9th = new Chord(c, ChordType.MINOR, ChordSize.NINTH);
        System.out.println(cMajor9th);

        Pitch db4 = new Pitch(new PitchClass(PitchLetter.D, Accidental.FLAT), Octave.FOURTH);
        PitchedChord db4Major7th = new PitchedChord(db4, ChordType.MAJOR, ChordSize.SEVENTH);
        System.out.println(db4Major7th);

        System.out.println(MusicCollections.PITCH_CLASSES.get("Abb"));
        System.out.println(MusicCollections.PITCHES.get("Fx3"));
        System.out.println(MusicCollections.DIATONIC_COLLECTIONS.get("C#m"));  // [C#, D#, E, F#, G#, A, B]
        System.out.println(MusicCollections.CHORDS.get("Dm9"));  // [D, F, A, C, E]
        System.out.println(MusicCollections.PITCHED_CHORDS.get("A#4m9"));  // [A#4, C#5, E#5, G#5, B#5]
    }
}
