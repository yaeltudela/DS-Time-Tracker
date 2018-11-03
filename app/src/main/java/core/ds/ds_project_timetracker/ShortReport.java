package core.ds.ds_project_timetracker;

public class ShortReport extends Report implements Visitor {

    public ShortReport(Project rootVisitable, Period reportPeriod) {
        super(rootVisitable, reportPeriod);
    }

    @Override
    protected void generateReport() {

        createReportEntries();

        for (Node p : rootVisitable.getActivities()) {
            p.accept(this);
        }
    }

    @Override
    public void visitProject(Project project) {
        if (project.getEndDate().after(this.reportPeriod.getStartDate()) || project.getStartDate().before(this.reportPeriod.getEndDate())) {
            //Caso sobresale por los dos lados
            if (project.getStartDate().before(this.reportPeriod.getStartDate()) && project.getEndDate().after(this.reportPeriod.getEndDate())) {
                reportPeriod.getData().add(
                        createEntry(project.name, project.description, reportPeriod.getStartDate(),
                                reportPeriod.getEndDate(), reportPeriod.getEndDate().getTime() - reportPeriod.getStartDate().getTime()
                        ));
            } else {
                //Caso mediofuera principio TODO preguntar como trato la duracion
                if (project.getStartDate().before(this.reportPeriod.getStartDate())) {
                    reportPeriod.getData().add(
                            createEntry(project.name, project.description, reportPeriod.getStartDate(),
                                    project.getEndDate(), project.getDuration()
                            ));
                } else {
                    //Caso mediofuera final TODO preguntar como trato la duracion
                    if (project.getEndDate().after(this.reportPeriod.getEndDate())) {
                        reportPeriod.getData().add(createEntry(project.name, project.description,
                                project.getStartDate(), reportPeriod.getEndDate(), project.getDuration()));
                    } else {
                        //Caso totalmente dentro
                        reportPeriod.getData().add(createEntry(project.name, project.description,
                                project.getStartDate(), project.getEndDate(), project.getDuration()));
                    }
                }
            }
        }

    }

    @Override
    public void visitTask(Task task) {
        //Do nothing
    }

    @Override
    public void visitInterval(Interval interval) {
        //Do nothing
    }


}
