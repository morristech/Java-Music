package pitches;

import utils.Accidental;
import utils.Interval;
import utils.PitchFrequency;
import utils.PitchLetter;

/**
 * Created by Nick on 2016-08-21.
 */
public class Pitch implements Comparable<Pitch> {
    private final PitchClass pitchClass;
    private final int octave;
    private final double pitchFrequency;

    public PitchClass getPitchClass() {
        return pitchClass;
    }

    public int getOctave() {
        return octave;
    }

    public double getPitchFrequency() {
        return pitchFrequency;
    }

    public Pitch(PitchClass pitchClass, int octave) {
        this.pitchClass = pitchClass;
        this.octave = octave;
        this.pitchFrequency = PitchFrequency.calculateFrequency(this);
    }

    public Pitch add(Interval interval) {
        PitchClass addedPitchClass = pitchClass.add(interval);
        if (addedPitchClass == null) return null;
        int addedOctave = this.octave;

        if (PitchLetter.compare(this.pitchClass.getPitchLetter(), PitchLetter.C) < 0
                && PitchLetter.compare(addedPitchClass.getPitchLetter(), PitchLetter.C) >= 0) {
            addedOctave++;
        }

        return new Pitch(addedPitchClass, addedOctave);
    }

    // TODO subtract interval

    @Override
    public String toString() {
        return String.format("%s%d", pitchClass, octave);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pitch pitch = (Pitch) o;

        if (octave != pitch.octave) return false;
        if (Double.compare(pitch.pitchFrequency, pitchFrequency) != 0) return false;
        return pitchClass.equals(pitch.pitchClass);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = pitchClass.hashCode();
        result = 31 * result + octave;
        temp = Double.doubleToLongBits(pitchFrequency);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Pitch o) {
        return this.pitchFrequency < o.pitchFrequency ? -1 : (this.pitchFrequency == o.pitchFrequency ? 0 : 1);
    }
}
