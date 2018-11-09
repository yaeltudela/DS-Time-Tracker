package core.ds.ds_project_timetracker;

public class Title extends Container {

    private String title;

    Title(String s) {
        this.title = s;
    }

    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitTitle(this);
    }

    @Override
    public String getText() {
        return this.title;
    }
}
