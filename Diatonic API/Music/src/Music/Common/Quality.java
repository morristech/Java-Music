package Music.Common;

/**
 * Created by Nick on 2016-08-21.
 */
public enum Quality {
    // TODO is it still appropriate for DiatonicChord.java to hold a Quality object?
    // These values will be added to Interval.java's IntervalNumber enum to get the
    // interval distance
    UNKNOWN(-2), DIMINISHED(-1), MINOR(0), PERFECT(0), MAJOR(1), AUGMENTED(2);

    private final int qualityNumber;

    public int getQualityNumber() { return qualityNumber; }

    Quality(int qualityNumber) {
        this.qualityNumber = qualityNumber;
    }
}
