package collections;

import pitches.PitchClass;
import utils.Interval;

/**
 * Created by Nick on 2016-09-17.
 */
public class DiatonicCollection extends PitchClassCollection {
    public DiatonicCollection(PitchClass tonic, CollectionMode mode) {
        super(tonic);
        pitchClasses[0] = tonic;
        pitchClasses[1] = tonic.add(Interval.M2);
        pitchClasses[2] = tonic.add(mode == CollectionMode.MINOR ? Interval.m3 : Interval.M3);
        pitchClasses[3] = tonic.add(Interval.P4);
        pitchClasses[4] = tonic.add(Interval.P5);
        pitchClasses[5] = tonic.add(mode == CollectionMode.MINOR ? Interval.m6 : Interval.M6);
        pitchClasses[6] = tonic.add(mode == CollectionMode.MINOR ? Interval.m7 : Interval.M7);
    }

    @Override
    public int getCollectionSize() {
        return 7;
    }
}
