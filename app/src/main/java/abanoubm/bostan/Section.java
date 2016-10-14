package abanoubm.bostan;

public class Section {

    private int sid;
    private String text;

    public int getSid() {
        return sid;
    }


    public Section(int sid, String text) {
        this.sid = sid;
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
