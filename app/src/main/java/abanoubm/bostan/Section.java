package abanoubm.bostan;

public class Section {

    private int sid;
    private String text;

    public Section(int sid, String text) {
        this.sid = sid;
        this.text = text;
    }

    public int getSid() {
        return sid;
    }

    public String getText() {
        return text;
    }

}
