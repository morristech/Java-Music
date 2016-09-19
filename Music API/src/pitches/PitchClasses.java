package pitches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 2016-09-18.
 * A map of all non-double-accidental pitch classes
 */
public class PitchClasses {
    public static Map<PitchLetter, Map<String, PitchClass>> MAP =
            new HashMap<PitchLetter, Map<String, PitchClass>>() {{
               for (PitchLetter pitchLetter : PitchLetter.values()) {
                   Map<String, PitchClass> innerMap = new HashMap<>();

                   // All non-double-accidentals
                   List<String> accidentals = new ArrayList<String>() {{
                       add(Accidental.FLAT);
                       add(Accidental.NATURAL);
                       add(Accidental.SHARP);
                   }};

                   for (String accidental : accidentals) {
                       PitchClass pitchClass = new PitchClass(pitchLetter, accidental);
                       innerMap.put(accidental, pitchClass);
                   }

                   put(pitchLetter, innerMap);
               }
            }};

    private PitchClasses() {
        // Prevent instantiation
    }
}
