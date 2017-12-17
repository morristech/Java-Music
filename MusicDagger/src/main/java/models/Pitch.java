package models;

import javax.inject.Inject;

public class Pitch {

    private final PitchClass pitchClass;
    private final Octave octave;

    @Inject
    public Pitch(PitchClass pitchClass, Octave octave) {
        this.pitchClass = pitchClass;
        this.octave = octave;
    }

    public PitchClass getPitchClass() {
        return pitchClass;
    }

    public Octave getOctave() {
        return octave;
    }

    @Override
    public String toString() {
        return pitchClass.toString() + octave.toString();
    }
}
