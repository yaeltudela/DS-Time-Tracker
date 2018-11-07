package core.ds.ds_project_timetracker;

import java.util.Date;

public class ShortReport extends Report implements Visitor {

    public ShortReport(final Project rootVisitable, final Period reportPeriod) {
        super(rootVisitable, reportPeriod);

    }

    @Override
    public void visitProject(final Project project) {
        long newDuration = 0;
        for (Node n : project.getActivities()) {
            newDuration += n.getNewDuration(this);
        }

        if (isInPeriod(project.getStartDate(), project.getEndDate())) {

            String name = project.getName();
            String desc = project.getDescription();
            Date startDate = project.getStartDate();
            Date endDate = project.getEndDate();
            long duration = project.getDuration();


            //Caso sobresale por los dos lados
            if (project.getStartDate().before(this.reportPeriod.getStartDate()) && project.getEndDate().after(this.reportPeriod.getEndDate())) {
                this.rootProjectsTable.addRow(name, desc, startDate, endDate, this.reportPeriod.getDuration());
            } else {
                //Caso mediofuera principio TODO preguntar como trato la duracion
                if (project.getStartDate().before(this.reportPeriod.getStartDate())) {
                    this.rootProjectsTable.addRow(name, desc, startDate, endDate, duration);
                } else {
                    //Caso mediofuera final TODO preguntar como trato la duracion
                    if (project.getEndDate().after(this.reportPeriod.getEndDate())) {
                        this.rootProjectsTable.addRow(name, desc, startDate, endDate, duration);
                    } else {
                        //Caso totalmente dentro
                        this.rootProjectsTable.addRow(name, desc, startDate, endDate, duration);
                    }
                }
            }
        }

    }

    @Override
    public void visitTask(final Task task) {

    }

    @Override
    public void visitInterval(final Interval interval) {
        //Do nothing
    }


}
