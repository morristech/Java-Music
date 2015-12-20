package musicController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import musicModel.*;
import musicUtility.*;


public class ChordBuilder {
    private List<Triad> majorTriads = new ArrayList<>();
    private List<Triad> minorTriads = new ArrayList<>();
    private ScaleBuilder diatonicScales = new ScaleBuilder();
    
    public ChordBuilder() {  
        buildChordsFromScales(majorTriads, diatonicScales.getMajorScalesNaturals());
        buildChordsFromScales(majorTriads, diatonicScales.getMajorScalesSharps());
        buildChordsFromScales(majorTriads, diatonicScales.getMajorScalesFlats());
        
        buildChordsFromScales(minorTriads, diatonicScales.getMinorScalesNaturals());
        buildChordsFromScales(minorTriads, diatonicScales.getMinorScalesSharps());
        buildChordsFromScales(minorTriads, diatonicScales.getMinorScalesFlats());
    }
    
    public void buildChordsFromScales(List<Triad> triads, List<Scale> scales) {
        for (Scale scale : scales) {
            triads.add(new Triad(
                scale.getTonic(), scale.getMediant(), scale.getDominant()
            ));
        }        
    }
    public List<Triad> getMajorTriads() {
        return majorTriads;
    }
    public void setMajorTriads(List<Triad> majorTriads) {
        this.majorTriads = majorTriads;
    }
    public List<Triad> getMinorTriads() {
        return minorTriads;
    }
    public void setMinorTriads(List<Triad> minorTriads) {
        this.minorTriads = minorTriads;
    }
    public ScaleBuilder getDiatonicScales() {
        return diatonicScales;
    }
    public void setDiatonicScales(ScaleBuilder diatonicScales) {
        this.diatonicScales = diatonicScales;
    }
}