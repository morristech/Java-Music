package pitches;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick on 2016-09-18.
 */
public class Pitches {
    public static Map<PitchLetter, Map<String, Map<Integer, Pitch>>> MAP =
            new HashMap<PitchLetter, Map<String, Map<Integer, Pitch>>>() {{
                for (PitchLetter pitchLetter : PitchLetter.values()) {
                    Map<String, Pitch> innerMap = new HashMap<>();

                    for (Map<String, PitchClass> value : PitchClasses.MAP.values()) {
                        for (String accidental : value.keySet()) {
                            for (int i = Octave.ZEROTH; i < Octave.TENTH; i++) {
                                
                            }
                        }
                    }
                }
            }};

    private Pitches() {
        // Prevent instantiation
    }
}
