package Music.Common;

/**
 * Created by Nick on 2016-08-24.
 */
public enum IntervalNumber {
    // By default these numeric values represent minor/perfect intervals. Adding
    // the values from Quality's enums will get the correct interval distance value.
    UNISON(0), SECOND(1), THIRD(3), FOURTH(5), FIFTH(7), SIXTH(8), SEVENTH(10);

    private final int intervalNumber;

    public int getIntervalNumber() { return intervalNumber; }

    IntervalNumber(int intervalNumber) {
        this.intervalNumber = intervalNumber;
    }
}