package core.ds.ds_project_timetracker;

public class Subtitle extends Container {

    private String subtitle;

    Subtitle(final String s) {
        this.subtitle = s;
    }

    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitSubtitle(this);
    }

    public String getText() {
        return subtitle;
    }


}
