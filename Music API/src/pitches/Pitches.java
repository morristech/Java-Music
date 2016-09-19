package pitches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 2016-09-18.
 */
public class Pitches {
    public static List<Pitch> LIST = new ArrayList<Pitch>() {{
       for (int i = Octave.ZEROTH; i <= Octave.TENTH; i++) {
           for (Map<String, PitchClass> map : PitchClasses.MAP.values()) {
               for (PitchClass pitchClass : map.values()) {
                   add(new Pitch(pitchClass, i));
               }
           }
       }
       sort((a, b) -> a.compareTo(b));
    }};

    public static Map<Integer, Map<PitchLetter, Map<String, Pitch>>> MAP =
            new HashMap<Integer, Map<PitchLetter, Map<String, Pitch>>>() {{
               for (int i = Octave.ZEROTH; i <= Octave.TENTH; i++) {
                   Map<PitchLetter, Map<String, Pitch>> innerMap = new HashMap<>();

                   for (Entry<PitchLetter, Map<String, PitchClass>> outerEntrySet : PitchClasses.MAP.entrySet()) {
                       Map<String, Pitch> innerInnerMap = new HashMap<>();

                       for (Entry<String, PitchClass> innerEntrySet : outerEntrySet.getValue().entrySet()) {
                           innerInnerMap.put(innerEntrySet.getKey(), new Pitch(innerEntrySet.getValue(), i));
                       }

                       innerMap.put(outerEntrySet.getKey(), innerInnerMap);
                   }
                   put(i, innerMap);
               }
            }};

    private Pitches() {
        // Prevent instantiation
    }
}
