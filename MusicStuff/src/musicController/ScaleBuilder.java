package musicController;

import java.util.ArrayList;
import java.util.List;
import musicModel.*;
import musicUtility.*;

// Builds all standard diatonic scales in the tonal system
public class ScaleBuilder {
    private List<Scale> majorScalesSharps = new ArrayList<>(); // G to C#
    private List<Scale> majorScalesFlats = new ArrayList<>(); // F to Cb
    private List<Scale> minorScalesSharps = new ArrayList<>(); // Em to A#m
    private List<Scale> minorScalesFlats = new ArrayList<>(); // Dm to Abm
    private List<Scale> majorScalesNaturals = new ArrayList<>(); // C
    private List<Scale> minorScalesNaturals = new ArrayList<>(); // Am
    
    public ScaleBuilder() {
        buildMajorScales(Accidental.IS_SHARPS, PitchLetter.START_G, majorScalesSharps);
        buildMajorScales(Accidental.IS_FLATS, PitchLetter.START_F, majorScalesFlats);
        
        buildMinorScales(majorScalesSharps, minorScalesSharps);  
        buildMinorScales(majorScalesFlats, minorScalesFlats);
        
        buildNaturalScales(PitchLetter.START_C, majorScalesNaturals);
        buildNaturalScales(PitchLetter.START_A, minorScalesNaturals);
    }     

    public void buildMajorScales(boolean accidentalType, int tonic, List<Scale> scales) {
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
    public void buildMinorScales(List<Scale> majorScales, List<Scale> minorScales) {
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

    public void buildNaturalScales(int tonic, List<Scale> scales) {
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
    
    public String findAccidental(int key, boolean accidentalType, int scaleDegree) {
        String accidental = Accidental.NATURAL;
        
        switch (key) {
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
    public List<Scale> getMajorScalesSharps() {
        return majorScalesSharps;
    }
    public void setMajorScalesSharps(List<Scale> majorScalesSharps) {
        this.majorScalesSharps = majorScalesSharps;
    }
    public List<Scale> getMajorScalesFlats() {
        return majorScalesFlats;
    }
    public void setMajorScalesFlats(List<Scale> majorScalesFlats) {
        this.majorScalesFlats = majorScalesFlats;
    }
    public List<Scale> getMinorScalesSharps() {
        return minorScalesSharps;
    }
    public void setMinorScalesSharps(List<Scale> minorScalesSharps) {
        this.minorScalesSharps = minorScalesSharps;
    }
    public List<Scale> getMinorScalesFlats() {
        return minorScalesFlats;
    }
    public void setMinorScalesFlats(List<Scale> minorScalesFlats) {
        this.minorScalesFlats = minorScalesFlats;
    }
    public List<Scale> getMajorScalesNaturals() {
        return majorScalesNaturals;
    }
    public void setMajorScalesNaturals(List<Scale> majorScalesNaturals) {
        this.majorScalesNaturals = majorScalesNaturals;
    }
    public List<Scale> getMinorScalesNaturals() {
        return minorScalesNaturals;
    }
    public void setMinorScalesNaturals(List<Scale> minorScalesNaturals) {
        this.minorScalesNaturals = minorScalesNaturals;
    }
}


