package musicController;

import java.util.ArrayList;
import java.util.List;
import musicModel.*;
import musicUtility.*;

// Builds all the natural diatonic scales in the tonal system
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
        // build the minor scales based off the already made major scales
        buildMinorScales(majorScalesSharps, minorScalesSharps);  
        buildMinorScales(majorScalesFlats, minorScalesFlats);
        buildNaturalScales();
    }     

    //accidentalType: true == sharps, false == flats
    public static void buildMajorScales(boolean accidentalType, int tonic, List<Scale> scales) {
        // Scale.LENGTH is also the number of sharp keys
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
    
    // Hardcoded for simplicity's sake
    public static void buildNaturalScales() {
        majorScalesNaturals.add(new Scale(
            PitchLetter.NAMES[2],
            PitchLetter.NAMES[3],
            PitchLetter.NAMES[4],
            PitchLetter.NAMES[5],
            PitchLetter.NAMES[6],
            PitchLetter.NAMES[0],
            PitchLetter.NAMES[1]
        ));
        
        minorScalesNaturals.add(new Scale(
            PitchLetter.NAMES[0],
            PitchLetter.NAMES[1],
            PitchLetter.NAMES[2],
            PitchLetter.NAMES[3],
            PitchLetter.NAMES[4],
            PitchLetter.NAMES[5],
            PitchLetter.NAMES[6]
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


