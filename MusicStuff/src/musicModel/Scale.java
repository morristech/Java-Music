package musicModel;

public class Scale {
    private String tonic;
    private String supertonic;
    private String mediant;
    private String subdominant;
    private String dominant;
    private String submediant;
    private String leadingTone;
    
    // Length of every traditional diatonic scale
    public static final int LENGTH = 7;
    // Scale degrees
    public static final int TONIC_SD = 0;
    public static final int SUPERTONIC_SD = 1;
    public static final int MEDIANT_SD = 2;
    public static final int SUBDOMINANT_SD = 3;
    public static final int DOMINANT_SD = 4;
    public static final int SUBMEDIANT_SD = 5;
    public static final int LEADINGTONE_SD = 6;    
    
    public Scale() {}
    public Scale(String tonic, String supertonic, String mediant, 
            String subdominant, String dominant, String submediant, 
            String leadingTone) {
        this.tonic = tonic;
        this.supertonic = supertonic;
        this.mediant = mediant;
        this.subdominant = subdominant;
        this.dominant = dominant;
        this.submediant = submediant;
        this.leadingTone = leadingTone;
    }
    
    public String getTonic() {
        return tonic;
    }
    public void setTonic(String tonic) {
        this.tonic = tonic;
    }
    public String getSupertonic() {
        return supertonic;
    }
    public void setSupertonic(String supertonic) {
        this.supertonic = supertonic;
    }
    public String getMediant() {
        return mediant;
    }
    public void setMediant(String mediant) {
        this.mediant = mediant;
    }
    public String getSubdominant() {
        return subdominant;
    }
    public void setSubdominant(String subdominant) {
        this.subdominant = subdominant;
    }
    public String getDominant() {
        return dominant;
    }
    public void setDominant(String dominant) {
        this.dominant = dominant;
    }
    public String getSubmediant() {
        return submediant;
    }
    public void setSubmediant(String submediant) {
        this.submediant = submediant;
    }
    public String getLeadingTone() {
        return leadingTone;
    }
    public void setLeadingTone(String leadingTone) {
        this.leadingTone = leadingTone;
    }
      
    @Override
    public String toString() {
        return String.format("%s-%s-%s-%s-%s-%s-%s", tonic, supertonic, mediant,
                subdominant, dominant, submediant, leadingTone
        );
    }
}