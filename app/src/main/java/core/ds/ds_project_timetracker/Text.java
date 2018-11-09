package core.ds.ds_project_timetracker;

public class Text extends Container {
    private String text;

    public Text(String s) {
        this.text = s;
    }

    @Override
    public String getText() {
        return this.text;
    }


    @Override
    public void accept(Visitor visitor) {

    }
}
