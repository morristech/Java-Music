package models;

import javax.inject.Inject;

public class PitchClass {

    private final PitchLetter pitchLetter;
    private final Accidental accidental;

    @Inject
    public PitchClass(PitchLetter pitchLetter, Accidental accidental) {
        this.pitchLetter = pitchLetter;
        this.accidental = accidental;
    }

    public PitchLetter getPitchLetter() {
        return pitchLetter;
    }

    public Accidental getAccidental() {
        return accidental;
    }

    @Override
    public String toString() {
        return pitchLetter.toString() + accidental.toString();
    }
}
