package core.ds.ds_project_timetracker;

import java.util.Date;

public class DetailedReport extends Report implements Visitor {

    protected DetailedReport(Project rootVisitable, Period reportPeriod) {
        super(rootVisitable, reportPeriod);
        this.subrojectsTable = new Table(2, 2);
        this.tasksTable = new Table(2, 2);
        this.intervalsTable = new Table(2, 2);

        this.tables.add(this.subrojectsTable);
        this.tables.add(this.tasksTable);
        this.tables.add(this.intervalsTable);
    }

    @Override
    public void visitProject(Project project) {
        if (isInPeriod(project.getStartDate(), project.getEndDate())) {
            String name = project.getName();
            String desc = project.getDescription();
            Date startDate = project.getStartDate();
            Date endDate = project.getEndDate();
            long duration = project.getDuration();
            if (project.getParent().getParent() == null) {
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
            } else {
                //Caso sobresale por los dos lados
                if (project.getStartDate().before(this.reportPeriod.getStartDate()) && project.getEndDate().after(this.reportPeriod.getEndDate())) {
                    this.subrojectsTable.addRow(name, desc, startDate, endDate, this.reportPeriod.getDuration());
                } else {
                    //Caso mediofuera principio TODO preguntar como trato la duracion
                    if (project.getStartDate().before(this.reportPeriod.getStartDate())) {
                        this.subrojectsTable.addRow(name, desc, startDate, endDate, duration);
                    } else {
                        //Caso mediofuera final TODO preguntar como trato la duracion
                        if (project.getEndDate().after(this.reportPeriod.getEndDate())) {
                            this.subrojectsTable.addRow(name, desc, startDate, endDate, duration);
                        } else {
                            //Caso totalmente dentro
                            this.subrojectsTable.addRow(name, desc, startDate, endDate, duration);
                        }
                    }
                }
            }
            for (Node n : project.getActivities()) {
                n.accept(this);
            }
        }
    }

    @Override
    public void visitTask(Task task) {

        String name = task.getName();
        String desc = task.getDescription();
        Date startDate = task.getStartDate();
        Date endDate = task.getEndDate();
        long duration = task.getDuration();


        if (task.getStartDate().before(this.reportPeriod.getStartDate()) && task.getEndDate().after(this.reportPeriod.getEndDate())) {
            this.tasksTable.addRow(name, desc, startDate, endDate, this.reportPeriod.getDuration());
        } else {
            //Caso mediofuera principio TODO preguntar como trato la duracion
            if (task.getStartDate().before(this.reportPeriod.getStartDate())) {
                this.tasksTable.addRow(name, desc, startDate, endDate, duration);
            } else {
                //Caso mediofuera final TODO preguntar como trato la duracion
                if (task.getEndDate().after(this.reportPeriod.getEndDate())) {
                    this.tasksTable.addRow(name, desc, startDate, endDate, duration);
                } else {
                    //Caso totalmente dentro
                    this.tasksTable.addRow(name, desc, startDate, endDate, duration);
                }
            }
        }

        for (Interval i : task.getIntervals()) {
            i.accept(this);
        }
    }

    @Override
    public void visitInterval(Interval interval) {
        if (isInPeriod(interval.getStartDate(), interval.getEndDate())) {
            String name = interval.getParentTask().getName() + "_interval";
            String desc = interval.getParentTask().getDescription() + "_interval";
            Date startDate = interval.getStartDate();
            Date endDate = interval.getEndDate();
            long duration = interval.getDuration();

            //Caso sobresale por los dos lados
            if (interval.getStartDate().before(this.reportPeriod.getStartDate()) && interval.getEndDate().after(this.reportPeriod.getEndDate())) {
                this.intervalsTable.addRow(name, desc, startDate, endDate, this.reportPeriod.getDuration());
            } else {
                //Caso mediofuera principio TODO preguntar como trato la duracion
                if (interval.getStartDate().before(this.reportPeriod.getStartDate())) {
                    this.intervalsTable.addRow(name, desc, startDate, endDate, duration);
                } else {
                    //Caso mediofuera final TODO preguntar como trato la duracion
                    if (interval.getEndDate().after(this.reportPeriod.getEndDate())) {
                        this.intervalsTable.addRow(name, desc, startDate, endDate, duration);
                    } else {
                        //Caso totalmente dentro
                        this.intervalsTable.addRow(name, desc, startDate, endDate, duration);
                    }
                }
            }
        }
    }
}
