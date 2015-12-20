package musicView;

import musicController.*;
import musicModel.*;
import musicUtility.*;

public class ShowAllTriads {
    public static void main(String[] args) {
        ChordBuilder.buildChords();
        
        for (Triad triad : ChordBuilder.majorTriads) {
            System.out.println(triad);
        }
        
        System.out.println();
        
        for (Triad triad : ChordBuilder.minorTriads) {
            System.out.println(triad);
        }        
    }
}