package models;

import qualifiers.OctaveValue;

import javax.inject.Inject;

public class Octave {

    private final int octave;

    @Inject
    public Octave(@OctaveValue int octave) {
        this.octave = octave;
    }

    public int getOctave() {
        return octave;
    }

    @Override
    public String toString() {
        return String.valueOf(octave);
    }
}
