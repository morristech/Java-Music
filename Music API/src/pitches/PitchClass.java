package pitches;

import utils.Accidental;
import utils.Interval;
import utils.NumberToPitchClasses;
import utils.PitchLetter;

/**
 * Created by Nick on 2016-08-24.
 */
public class PitchClass {
    private final PitchLetter pitchLetter;
    private final String accidental;
    private final int number;

    public PitchLetter getPitchLetter() {
        return pitchLetter;
    }

    public String getAccidental() {
        return accidental;
    }

    public int getNumber() {
        return number;
    }

    public PitchClass(PitchLetter pitchLetter) {
        this(pitchLetter, Accidental.NATURAL);
    }

    public PitchClass(PitchLetter pitchLetter, String accidental) {
        this.pitchLetter = pitchLetter;
        this.accidental = accidental;
        this.number = calculateNumber(pitchLetter, accidental);
    }

    private int calculateNumber(PitchLetter pitchLetter, String accidental) {
        return modTwelve(PitchLetter.calculatePitchLetterNumber(pitchLetter)
                + Accidental.getAccidentalNumber(accidental));
    }

    // Uses the pitch class number value, expected PitchLetter, and a HashMap of possible pitch class
    // number values to PitchClass to determine the correct intervallic output
    public PitchClass transpose(Interval interval) {
        int addedPcNumber = modTwelve(this.number + interval.getNumberSpan());
        PitchLetter addedLetter = PitchLetter.interval(this.pitchLetter, interval.getLetterSpan());

        return NumberToPitchClasses.MAP.get(addedPcNumber)
                .stream()
                .filter(pc -> pc.getPitchLetter() == addedLetter)
                .findFirst()
                .orElse(null);
    }

    private static int modTwelve(int n) {
        return (n + 12) % 12;
    }

    @Override
    public String toString() {
        return String.format("%s%s", pitchLetter, accidental);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PitchClass that = (PitchClass) o;

        if (number != that.number) return false;
        if (pitchLetter != that.pitchLetter) return false;
        return accidental != null ? accidental.equals(that.accidental) : that.accidental == null;

    }

    @Override
    public int hashCode() {
        int result = pitchLetter != null ? pitchLetter.hashCode() : 0;
        result = 31 * result + (accidental != null ? accidental.hashCode() : 0);
        result = 31 * result + number;
        return result;
    }
}
