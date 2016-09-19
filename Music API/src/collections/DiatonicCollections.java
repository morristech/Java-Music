package collections;

import pitches.*;

import java.util.*;

/**
 * Created by Nick on 2016-09-18.
 * A map of all diatonic collections that don't start with a double-accidentalled tonic
 */
public class DiatonicCollections {
    public static List<DiatonicCollection> LIST =
            new ArrayList<DiatonicCollection>() {{
               for (CollectionMode mode : CollectionMode.values()) {
                   for (PitchClass pitchClass : PitchClasses.LIST) {
                       add(new DiatonicCollection(pitchClass, mode));
                   }
               }
            }};

    public static Map<CollectionMode, Map<PitchClass, DiatonicCollection>> MAP =
            new HashMap<CollectionMode, Map<PitchClass, DiatonicCollection>>() {{

                for (CollectionMode mode : CollectionMode.values()) {
                    Map<PitchClass, DiatonicCollection> innerMap = new HashMap<>();

                    for (PitchLetter pitchLetter : PitchLetter.values()) {
                        for (PitchClass pitchClass : PitchClasses.MAP.get(pitchLetter).values()) {
                            innerMap.put(pitchClass, new DiatonicCollection(pitchClass, mode));
                        }
                    }

                    put(mode, innerMap);
                }
            }};

    private DiatonicCollections() {
        // Prevent instantiation
    }
}
