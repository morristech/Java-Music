package main;

import models.*;
import modules.PitchModule;
import qualifiers.AccidentalDegree;
import qualifiers.OctaveValue;
import components.DaggerPitchComponent;

public class Main {
    public static void main(String[] args) {

        // Ingredients for a Bbb3 pitch
        final PitchLetter pitchLetter = PitchLetter.B;
        final AccidentalType accidentalType = AccidentalType.FLAT;
        final @AccidentalDegree int accidentalDegree = 2;
        final @OctaveValue int octaveValue = 3;

        // Without DI
        Accidental accidental = new Accidental(accidentalType, accidentalDegree);
        PitchClass pitchClass = new PitchClass(pitchLetter, accidental);
        Octave octave = new Octave(octaveValue);
        Pitch bDoubleFlat3 = new Pitch(pitchClass, octave);

        System.out.println(bDoubleFlat3);  // Bbb3

        // With DI
        PitchModule pitchModule = new PitchModule(pitchLetter, accidentalType, accidentalDegree, octaveValue);

        Pitch pitch = DaggerPitchComponent.builder()
                .pitchModule(pitchModule)
                .build()
                .pitch();

        System.out.println(pitch);  // Bbb3
    }
}
