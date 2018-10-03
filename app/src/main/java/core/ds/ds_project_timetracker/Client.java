package core.ds.ds_project_timetracker;

public class Client {

    public static void main(String[] args) {

        DataManager dataManager = new DataManager("save.db");
        //Project root = (Project) dataManager.loadData();

        ///*
        Project root = new Project("/", "rootProject", null);
        Project p1 = new Project("P1", "P1 desc", root);
        Task t3 = new Task("T3", "T3 desc", p1);
        Project p2 = new Project("P2", "P1 desc", p1);
        Task t1 = new Task("T1", "T1 desc", p2);
        Task t2 = new Task("T2", "T2 desc", p2);
        //*/


        Clock.getInstance().setRefreshTicks(2);
        System.out.println("Name  Start Date \t\t\t\t\t End Date \t\t\t\t\t Duration");
        Clock.getInstance().addObserver(root);


        /*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        /*

        //A1
        t3.startInterval();
        Thread.sleep(3000);
        t3.stopInterval();
        Thread.sleep(7000);
        t2.startInterval();
        Thread.sleep(10000);
        t2.stopInterval();
        t3.startInterval();
        Thread.sleep(2000);
        t3.stopInterval();

        */

        ///*
        //A2
        try {
            t3.startInterval();
            Thread.sleep(4000);
            t2.startInterval();
            Thread.sleep(2000);
            t3.stopInterval(); //t3 = 6
            Thread.sleep(2000);
            t1.startInterval();
            Thread.sleep(4000);
            t1.stopInterval(); //t1 = 4
            Thread.sleep(2000);
            t2.stopInterval(); //t2 = 10
            Thread.sleep(4000);
            t3.startInterval();
            Thread.sleep(2000);
            t3.stopInterval(); //t3 = 8
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        //*/

        Clock.getInstance().deleteObserver(root);
        Clock.getInstance().stopClock();
        System.out.println("Test A2 ended");
        dataManager.saveData(root);

    }

}