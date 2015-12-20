package musicModel;

public class Triad {    
    private String root;
    private String third;
    private String fifth;
    
    public Triad() {}
    public Triad(String root, String third, String fifth) {
        this.root = root;
        this.third = third;
        this.fifth = fifth;
    }
    
    public String getRoot() {
        return root;
    }
    public void setRoot(String root) {
        this.root = root;
    }
    public String getThird() {
        return third;
    }
    public void setThird(String third) {
        this.third = third;
    }
    public String getFifth() {
        return fifth;
    }
    public void setFifth(String fifth) {
        this.fifth = fifth;
    }   
    
    @Override
    public String toString() {
        return String.format("%s-%s-%s", root, third, fifth);
    }
}