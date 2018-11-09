
package core.ds.ds_project_timetracker;

/**
 * Concrete class to store data from a Detailed Report.
 * It contains all the projects, subprojects, tasks and intervals.
 */
public class DetailedReport extends Report implements TreeVisitor {


    private Container title = new Title("Detailed Report");
    private Container subtitleReports = new Subtitle("Period");
    private Container subtitleRootProjects = new Subtitle("Root Projects");
    private Container subtitleSubProjects = new Subtitle("Subprojects");
    private Container textSubProjects = new Text("Subtitle subprojects");
    private Container subtitleTasks = new Subtitle("Task");
    private Container textTask = new Text("Subtitle tasks");
    private Container subtitleIntervals = new Subtitle("Intervals");
    private Container textIntervals = new Text("Subtitle intervals");
    private Container footer = new Text("Time Tracker 1.0");


    protected DetailedReport(final Project rootVisitable, final Period reportPeriod, final ReportGenerator reportGenerator) {
        super(rootVisitable, reportPeriod, reportGenerator);

    }

    @Override
    public void createReport() {
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitTitle((Title) this.title);
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleReports);
        this.reportGenerator.visitTable(createReportTable());
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleRootProjects);
        //this.reportGenerator.visitTable();
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleSubProjects);
        this.reportGenerator.visitText((Text) this.textSubProjects);
        //this.reportGenerator.visitTable();
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleTasks);
        this.reportGenerator.visitText((Text) this.textTask);
        //this.reportGenerator.visitTable();
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitSubtitle((Subtitle) this.subtitleIntervals);
        this.reportGenerator.visitText((Text) this.textIntervals);
        //this.reportGenerator.visit();
        this.reportGenerator.visitSeparator(new Separator());
        this.reportGenerator.visitText((Text) footer);


    }

    @Override
    public void visitProject(Project project) {

    }

    @Override
    public void visitTask(Task task) {

    }

    @Override
    public void visitInterval(Interval interval) {

    }

    /*
    *
    * @Override
    public void visitProject(final Project project) {
        if (isInPeriod(project.getStartDate(), project.getEndDate())) {
            String name = project.getName();
            String desc = project.getDescription();
            Date startDate = getNewStartDate(project.getStartDate());
            Date endDate = getNewEndDate(project.getEndDate());
            long duration = getNewDuration(project.getStartDate(), project.getEndDate(), project.getDuration()); //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            if (project.getParent().getParent() == null) {
                this.rootProjectsTable.addRow(entry);
            } else {
                this.subrojectsTable.addRow(entry);
            }
        }
    }

    @Override
    public void visitTask(final Task task) {

        long taskDuration = 0;
        for (Interval i : task.getIntervals()) {
            if (isInPeriod(i.getStartDate(), i.getEndDate())) {
                i.accept(this);
                taskDuration += i.getDuration();
            }
        }

        if (isInPeriod(task.getStartDate(), task.getEndDate())) {
            String name = task.getName();
            String desc = task.getDescription();
            Date startDate = getNewStartDate(task.getStartDate());
            Date endDate = getNewEndDate(task.getEndDate());
            long duration = taskDuration; //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            this.tasksTable.addRow(entry);
        }
    }

    @Override
    public void visitInterval(final Interval interval) {
        if (isInPeriod(interval.getStartDate(), interval.getEndDate())) {
            String name = interval.getParentTask().getName() + "_interval";
            String desc = interval.getParentTask().getDescription() + "_interval";
            Date startDate = getNewStartDate(interval.getStartDate());
            Date endDate = getNewEndDate(interval.getEndDate());
            long duration = getNewDuration(interval.getStartDate(), interval.getEndDate(), interval.getDuration()); //ESTO NO ES REAL SIEMPRE


            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(), String.valueOf(duration)));
            this.intervalsTable.addRow(entry);
        }
    }

    */

}

