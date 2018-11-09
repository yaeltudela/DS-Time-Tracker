package core.ds.ds_project_timetracker;

public class Separator extends Container {

    private String separator = "--------------------------------------------------------------------------------------------------------";

    @Override
    public void accept(final ReportVisitor visitor) {
        visitor.visitSeparator(this);
    }


    public String getText() {
        return separator;
    }


}
