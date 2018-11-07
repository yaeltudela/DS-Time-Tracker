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
        String name = project.getName();
        String desc = project.getDescription();
        Date startDate = project.getStartDate();
        Date endDate = project.getEndDate();
        long duration = project.getDuration();

        if (isInPeriod(project.getStartDate(), project.getEndDate())) {

            if (project.getParent().getParent() == null) {
                this.rootProjectsTable.addRow();
                desc, this.getNewStartDate(), this.getNewEndDate(), this.getnewDuration())
            } else {
                this.subrojectsTable.addRow(name, desc, this.getNewStartDate(), this.getNewEndDate(), this.getnewDuration());
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


        this.rootProjectsTable.addRow(name, desc, this.getNewStartDate(), this.getNewEndDate(), this.getnewDuration());

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

            this.rootProjectsTable.addRow(name, desc, this.getNewStartDate(), this.getNewEndDate(), this.getnewDuration());

        }
    }
}
