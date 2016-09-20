package collections;

import chords.Chord;
import chords.ChordSize;
import chords.ChordType;
import chords.PitchedChord;
import pitches.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 2016-09-19.
 */

// TODO test all these collections hold no nulls
public class MusicCollections {
    private static List<String> accidentals = new ArrayList<String>() {{
        add(Accidental.DOUBLE_FLAT);
        add(Accidental.FLAT);
        add(Accidental.NATURAL);
        add(Accidental.SHARP);
        add(Accidental.DOUBLE_SHARP);
    }};

    public static Map<String, PitchClass> PITCH_CLASSES =
            new HashMap<String, PitchClass>() {{
                for (PitchLetter pitchLetter : PitchLetter.values()) {
                    for (String accidental : accidentals) {
                        PitchClass pitchClass = new PitchClass(pitchLetter, accidental);
                        put(pitchClass.toString(), pitchClass);
                    }
                }
            }};

    public static Map<String, Pitch> PITCHES =
            new HashMap<String, Pitch>() {{
                for (PitchClass pitchClass : PITCH_CLASSES.values()) {
                    for (int i = Octave.ZEROTH; i <= Octave.TENTH; i++) {
                        Pitch pitch = new Pitch(pitchClass, i);
                        put(pitch.toString(), pitch);
                    }
                }
            }};

    public static Map<String, DiatonicCollection> DIATONIC_COLLECTIONS =
            new HashMap<String, DiatonicCollection>() {{
                for (PitchClass pitchClass : PITCH_CLASSES.values()) {
                    for (CollectionMode mode : CollectionMode.values()) {
                        if (!isSuitableTonic(pitchClass, mode.toString())) continue;
                        DiatonicCollection diatonicCollection =
                                new DiatonicCollection(pitchClass, mode);
                        put(diatonicCollection.getName(), diatonicCollection);
                    }
                }
            }};

    public static Map<String, Chord> CHORDS =
            new HashMap<String, Chord>() {{
                for (PitchClass pitchClass : PITCH_CLASSES.values()) {
                    for (ChordType chordType : ChordType.values()) {
                        if (!isSuitableTonic(pitchClass, chordType.toString())) continue;
                        for (int i = ChordSize.TRIAD; i <= ChordSize.THIRTEENTH; i++) {
                            Chord chord = new Chord(pitchClass, chordType, i);
                            put(chord.getName(), chord);
                        }
                    }
                }
            }};

    public static Map<String, PitchedChord> PITCHED_CHORDS =
            new HashMap<String, PitchedChord>() {{
                for (Pitch pitch : PITCHES.values()) {
                    for (ChordType chordType : ChordType.values()) {
                        if (!isSuitableTonic(pitch, chordType.toString())) continue;
                        for (int i = ChordSize.TRIAD; i <= ChordSize.THIRTEENTH; i++) {
                            PitchedChord pitchedChord = new PitchedChord(pitch, chordType, i);
                            put(pitchedChord.getName(), pitchedChord);
                        }
                    }
                }
            }};

    private MusicCollections() {
        // Prevent instantiation
    }

    // This API doesn't support potential triple accidentals, e.g.
    // starting a collection with tonic Gx yields a Fx# leading tone.
    // There's also the edge case of an augmented chord beginning with B# which leads to its
    // augmented fifth being Fx#, so avoid that, too.
    private static boolean isSuitableTonic(PitchClass pitchClass, String fifthType) {
        return !pitchClass.getAccidental().equals(Accidental.DOUBLE_FLAT)
                && !pitchClass.getAccidental().equals(Accidental.DOUBLE_SHARP)
                && !(pitchClass.getPitchLetter() == PitchLetter.B
                    && pitchClass.getAccidental().equals(Accidental.SHARP)
                    && fifthType.equals("AUGMENTED"));
    }

    private static boolean isSuitableTonic(Pitch pitch, String fifthType) {
        return isSuitableTonic(pitch.getPitchClass(), fifthType);
    }
}
