package musicView;

import musicController.*;
import musicModel.*;

public class ShowAllTriads {
    public static void main(String[] args) {
        ChordBuilder diatonicChords = new ChordBuilder();
        
        System.out.println("Major triads:");
        for (Triad triad : diatonicChords.getMajorTriads()) {
            System.out.println(triad);
        }
        
        System.out.println("\nMinor triads:");
        for (Triad triad : diatonicChords.getMinorTriads()) {
            System.out.println(triad);
        }        
    }
}