package musicModel;

public class SeventhChord extends Triad {
    private String seventh;
    
    public SeventhChord() {}
    public SeventhChord(String root, String third, String fifth, String seventh) {
        super(root, third, fifth);
        this.seventh = seventh;
    } 
    public SeventhChord(Triad triad, String seventh) {
        super.setRoot(triad.getRoot());
        super.setThird(triad.getThird());
        super.setFifth(triad.getFifth());
        this.seventh = seventh;
    }
    
    public String getSeventh() {
        return seventh;
    }
    public void setSeventh(String seventh) {
        this.seventh = seventh;
    }   
    
    public String toString() {
        return super.toString() + String.format("-%s", seventh);
    }
}