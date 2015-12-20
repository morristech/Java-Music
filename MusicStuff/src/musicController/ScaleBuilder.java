package musicController;

import java.util.ArrayList;
import java.util.List;
import musicModel.*;
import musicUtility.*;

// Builds all standard diatonic scales in the tonal system
public class ScaleBuilder {
    public static List<Scale> majorScalesSharps = new ArrayList<>(); // G to C#
    public static List<Scale> majorScalesFlats = new ArrayList<>(); // F to Cb
    public static List<Scale> minorScalesSharps = new ArrayList<>(); // Em to A#m
    public static List<Scale> minorScalesFlats = new ArrayList<>(); // Dm to Abm
    public static List<Scale> majorScalesNaturals = new ArrayList<>(); // C
    public static List<Scale> minorScalesNaturals = new ArrayList<>(); // Am
    
    public static void buildScales() {
        // 6 means start on G and ascend by fifths
        buildMajorScales(true, 6, majorScalesSharps);
        // 5 means start on F and ascend by fourths
        buildMajorScales(false, 5, majorScalesFlats);
        
        buildMinorScales(majorScalesSharps, minorScalesSharps);  
        buildMinorScales(majorScalesFlats, minorScalesFlats);
        
        // 2 means start on C
        buildNaturalScales(2, majorScalesNaturals);
        // 0 means start on A
        buildNaturalScales(0, minorScalesNaturals);
    }     

    //accidentalType: true == sharps, false == flats
    public static void buildMajorScales(boolean accidentalType, int tonic, List<Scale> scales) {
        // Scale.LENGTH is also the number of keys per mode/accidental combination
        for (int i = 0; i < Scale.LENGTH; 
                i++, tonic = (tonic + (accidentalType ? Interval.FIFTH : Interval.FOURTH)) % Scale.LENGTH) {
            
            int supertonic = (tonic + Interval.SECOND) % Scale.LENGTH;
            int mediant = (tonic + Interval.THIRD) % Scale.LENGTH;
            int subdominant = (tonic + Interval.FOURTH) % Scale.LENGTH;
            int dominant = (tonic + Interval.FIFTH) % Scale.LENGTH;
            int submediant = (tonic + Interval.SIXTH) % Scale.LENGTH;
            int leadingTone = (tonic + Interval.SEVENTH) % Scale.LENGTH;

            scales.add(new Scale(
                PitchLetter.NAMES[tonic] + findAccidental(i, accidentalType, Scale.TONIC_SD),
                PitchLetter.NAMES[supertonic] + findAccidental(i, accidentalType, Scale.SUPERTONIC_SD),
                PitchLetter.NAMES[mediant] + findAccidental(i, accidentalType, Scale.MEDIANT_SD),
                PitchLetter.NAMES[subdominant] + findAccidental(i, accidentalType, Scale.SUBDOMINANT_SD),
                PitchLetter.NAMES[dominant] + findAccidental(i, accidentalType, Scale.DOMINANT_SD),
                PitchLetter.NAMES[submediant] + findAccidental(i, accidentalType, Scale.SUBMEDIANT_SD),
                PitchLetter.NAMES[leadingTone] + findAccidental(i, accidentalType, Scale.LEADINGTONE_SD)
            ));            
        }      
    }
    
    // Minor scales are based off already made major scales
    public static void buildMinorScales(List<Scale> majorScales, List<Scale> minorScales) {
        for (Scale scale : majorScales) {
            minorScales.add(new Scale(
                scale.getSubmediant(),
                scale.getLeadingTone(),
                scale.getTonic(),
                scale.getSupertonic(),
                scale.getMediant(),
                scale.getSubdominant(),
                scale.getDominant()
            ));
        }     
    }

    public static void buildNaturalScales(int tonic, List<Scale> scales) {
        majorScalesNaturals.add(new Scale(
            PitchLetter.NAMES[tonic],
            PitchLetter.NAMES[(tonic + Interval.SECOND) % Scale.LENGTH],
            PitchLetter.NAMES[(tonic + Interval.THIRD) % Scale.LENGTH],
            PitchLetter.NAMES[(tonic + Interval.FOURTH) % Scale.LENGTH],
            PitchLetter.NAMES[(tonic + Interval.FIFTH) % Scale.LENGTH],
            PitchLetter.NAMES[(tonic + Interval.SIXTH) % Scale.LENGTH],
            PitchLetter.NAMES[(tonic + Interval.SEVENTH) % Scale.LENGTH]
        ));       
    }
    
    public static String findAccidental(int key, boolean accidentalType, int scaleDegree) {
        String accidental = Accidental.NATURAL;
        
        switch(key) {
            // Fall through intentional for every case
            case 6: if (accidentalType && scaleDegree == Scale.SUBDOMINANT_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.LEADINGTONE_SD) accidental = Accidental.FLAT;
            case 5: if (accidentalType && scaleDegree == Scale.TONIC_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.MEDIANT_SD) accidental = Accidental.FLAT;
            case 4: if (accidentalType && scaleDegree == Scale.DOMINANT_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.SUBMEDIANT_SD) accidental = Accidental.FLAT;
            case 3: if (accidentalType && scaleDegree == Scale.SUPERTONIC_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.SUPERTONIC_SD) accidental = Accidental.FLAT;
            case 2: if (accidentalType && scaleDegree == Scale.SUBMEDIANT_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.DOMINANT_SD) accidental = Accidental.FLAT;
            case 1: if (accidentalType && scaleDegree == Scale.MEDIANT_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.TONIC_SD) accidental = Accidental.FLAT;
            case 0: if (accidentalType && scaleDegree == Scale.LEADINGTONE_SD) accidental = Accidental.SHARP;
                else if (!accidentalType && scaleDegree == Scale.SUBDOMINANT_SD) accidental = Accidental.FLAT;
        }
        
        return accidental;
    }        
}


