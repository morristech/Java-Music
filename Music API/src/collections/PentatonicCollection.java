package collections;

import pitches.PitchClass;
import pitches.Interval;

/**
 * Created by Nick on 2016-09-17.
 */
public class PentatonicCollection extends PitchClassCollection {
    public PentatonicCollection(PitchClass tonic, CollectionMode mode) {
        super(tonic);
        pitchClasses[0] = tonic;
        pitchClasses[1] = tonic.transpose(mode == CollectionMode.MAJOR ? Interval.M2 : Interval.m3);
        pitchClasses[2] = tonic.transpose(mode == CollectionMode.MAJOR ? Interval.M3 : Interval.P4);
        pitchClasses[3] = tonic.transpose(Interval.P5);
        pitchClasses[4] = tonic.transpose(mode == CollectionMode.MAJOR ? Interval.M6 : Interval.m7);

        this.mode = mode;
    }

    @Override
    public int getCollectionSize() {
        return 5;
    }
}
