package collections;

import pitches.PitchClass;
import utils.Interval;

/**
 * Created by Nick on 2016-09-17.
 */
public class PentatonicCollection extends PitchClassCollection {
    public PentatonicCollection(PitchClass tonic, CollectionMode mode) {
        super(tonic);
        pitchClasses[0] = tonic;
        pitchClasses[1] = tonic.add(mode == CollectionMode.MAJOR ? Interval.M2 : Interval.m3);
        pitchClasses[2] = tonic.add(mode == CollectionMode.MAJOR ? Interval.M3 : Interval.P4);
        pitchClasses[3] = tonic.add(Interval.P5);
        pitchClasses[4] = tonic.add(mode == CollectionMode.MAJOR ? Interval.M6 : Interval.m7);
    }

    @Override
    public int getCollectionSize() {
        return 5;
    }
}
