package musicController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import musicModel.*;
import musicUtility.*;


public class ChordBuilder {
    public static List<Triad> majorTriads = new ArrayList<>();
    public static List<Triad> minorTriads = new ArrayList<>();
    
    public static void buildChords() {
        ScaleBuilder.buildScales();
        
        buildChordsFromScales(majorTriads, ScaleBuilder.majorScalesNaturals);
        buildChordsFromScales(majorTriads, ScaleBuilder.majorScalesSharps);
        buildChordsFromScales(majorTriads, ScaleBuilder.majorScalesFlats);
        
        buildChordsFromScales(minorTriads, ScaleBuilder.minorScalesNaturals);
        buildChordsFromScales(minorTriads, ScaleBuilder.minorScalesSharps);
        buildChordsFromScales(minorTriads, ScaleBuilder.minorScalesFlats);
    }
    
    public static void buildChordsFromScales(List<Triad> triads, List<Scale> scales) {
        for (Scale scale : scales) {
            triads.add(new Triad(
                scale.getTonic(), scale.getMediant(), scale.getDominant()
            ));
        }        
    }
}