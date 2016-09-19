package collections;

import pitches.PitchClass;
import pitches.Interval;

/**
 * Created by Nick on 2016-09-17.
 */
public class DiatonicCollection extends PitchClassCollection {
    public DiatonicCollection(PitchClass tonic, CollectionMode mode) {
        super(tonic);
        pitchClasses[0] = tonic;
        pitchClasses[1] = tonic.transpose(Interval.M2);
        pitchClasses[2] = tonic.transpose(mode == CollectionMode.MINOR ? Interval.m3 : Interval.M3);
        pitchClasses[3] = tonic.transpose(Interval.P4);
        pitchClasses[4] = tonic.transpose(Interval.P5);
        pitchClasses[5] = tonic.transpose(mode == CollectionMode.MINOR ? Interval.m6 : Interval.M6);
        pitchClasses[6] = tonic.transpose(mode == CollectionMode.MINOR ? Interval.m7 : Interval.M7);

        this.mode = mode;
    }

    @Override
    public int getCollectionSize() {
        return 7;
    }

    public String getName() {
        return String.format("%s%s", this.getScaleDegree(ScaleDegree.FIRST),
                mode == CollectionMode.MAJOR ? "maj" : "m");
    }
}
