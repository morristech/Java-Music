package musicView;

import musicController.*;
import musicModel.*;
import musicUtility.*;

public class ShowAllScales {
    public static void main(String[] args) {
        ScaleBuilder.buildScales();
        
        for (Scale s : ScaleBuilder.majorScalesNaturals) {
            System.out.println(s);
        }        
        for (Scale s : ScaleBuilder.majorScalesSharps) {
            System.out.println(s);
        }
        for (Scale s : ScaleBuilder.majorScalesFlats) {
            System.out.println(s);
        }     
        
        System.out.println();
        
        for (Scale s : ScaleBuilder.minorScalesNaturals) {
            System.out.println(s);
        }         
        for (Scale s : ScaleBuilder.minorScalesSharps) {
            System.out.println(s);
        }            
        for (Scale s : ScaleBuilder.minorScalesFlats) {
            System.out.println(s);
        }          
    }
}