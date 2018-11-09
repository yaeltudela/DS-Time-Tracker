package core.ds.ds_project_timetracker;

public class Separator extends Container {

    @Override
    public void accept(final Visitor visitor) {
        ((ReportVisitor) visitor).visitSeparator(this);
    }


    public String getText() {
        return "-------------------------------------------------------";
    }


}
