
package core.ds.ds_project_timetracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Concrete class that represent a basic and Short Report.
 * It contains only the Report data and the rootProjects data.
 */
public class ShortReport extends Report implements Visitor {

    /**
     * Constructor for the ShortReport.
     * It creates the title and the tables.
     *
     * @param rootVisitable the first Visitable to visit.
     * @param reportPeriod  The period to be reported.
     */
    public ShortReport(final Project rootVisitable, final Period reportPeriod) {
        super(rootVisitable, reportPeriod);

        createSectionTable("Short Report", null);
        createSeparatorTable();
        createCommonTables();
    }


    @Override
    public void visitProject(final Project project) {
        /*
        long newDuration = 0;
        for (Node n : project.getActivities()) {
            newDuration += n.getNewDuration(this);
        }

         */

        if (isInPeriod(project.getStartDate(), project.getEndDate())) {

            String name = project.getName();
            String desc = project.getDescription();
            Date startDate = getNewStartDate(project.getStartDate());
            Date endDate = getNewEndDate(project.getEndDate());
            long duration = getNewDuration(project.getStartDate(), project.getEndDate(), project.getDuration()); //TODO ESTO NO ES REAL SIEMPRE

            ArrayList<String> entry = new ArrayList<>(Arrays.asList(name, desc,
                    startDate.toString(), endDate.toString(),
                    String.valueOf(duration)));

            this.rootProjectsTable.addRow(entry);
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
