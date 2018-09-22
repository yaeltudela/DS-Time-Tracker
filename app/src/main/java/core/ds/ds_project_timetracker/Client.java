package core.ds.ds_project_timetracker;

public class Client {

    public static void main(String[] args) {


        //Get clock and start it
        Clock clock = new Clock();

        /*
            Create A1 tree
            P1
              T3
              P1
                T1
                T2
         */

        Project p1 = new Project("P1", "P1 desc", null);
        p1.addTask("T3", "T3 desc");

        Project p2 = new Project("P2", "P1 desc", p1);
        p1.addProject(p2);
        p2.addTask("T4", "T4 desc");
        p2.addTask("T5", "T5 desc");


        clock.setRefreshTicks(2);

        System.out.println("Task created");


    }

}