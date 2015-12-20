package musicView;

import musicController.*;
import musicModel.*;

public class ShowAllScales {
    public static void main(String[] args) {
        ScaleBuilder diatonicScales = new ScaleBuilder();
        
        System.out.println("Major Scales:");
        for (Scale s : diatonicScales.getMajorScalesNaturals()) {
            System.out.println(s);
        }        
        for (Scale s : diatonicScales.getMajorScalesSharps()) {
            System.out.println(s);
        }
        for (Scale s : diatonicScales.getMajorScalesFlats()) {
            System.out.println(s);
        }     
        
        System.out.println("\nMinor Scales:");
        for (Scale s : diatonicScales.getMinorScalesNaturals()) {
            System.out.println(s);
        }         
        for (Scale s : diatonicScales.getMinorScalesSharps()) {
            System.out.println(s);
        }            
        for (Scale s : diatonicScales.getMinorScalesFlats()) {
            System.out.println(s);
        }          
    }
}