package Music.Common;

import Music.Pitches.Accidental;
import Music.Pitches.Pitch;
import Music.Pitches.PitchLetter;

/**
 * Created by Nick on 2016-08-24.
 */
public class Interval {
    private Quality quality;
    private IntervalNumber intervalNumber;

    public Quality getQuality() {
        return quality;
    }

    public IntervalNumber getIntervalNumber() {
        return intervalNumber;
    }

    private Interval() {}

    public static Interval newInstance(Quality quality, IntervalNumber intervalNumber) {
        Interval interval = new Interval();
        interval.quality = quality;
        interval.intervalNumber = intervalNumber;
        return interval;
    }
}