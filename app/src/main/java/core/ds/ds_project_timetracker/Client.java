package core.ds.ds_project_timetracker;

public class Client {

    public static void main(String[] args) throws InterruptedException {

        //TEST A1 (One simultaneous task)
        Clock clock = new Clock();


        Project p1 = new Project("P1", "P1 desc", null);
        Task t3 = new Task("T3", "T3 desc", p1);
        Project p2 = new Project("P2", "P1 desc", p1);
        Task t1 = new Task("T1", "T1 desc", p2);
        Task t2 = new Task("T2", "T2 desc", p2);


        System.out.println("Name  Start Date \t\t\t\t\t End Date \t\t\t\t\t Duration");
        clock.addObserver(p1);
        clock.setRefreshTicks(2);

/*
        //A1
        t3.startInterval(clock);
        Thread.sleep(3000);
        t3.stopInterval(clock);
        Thread.sleep(7000);
        t2.startInterval(clock);
        Thread.sleep(10000);
        t2.stopInterval(clock);
        t3.startInterval(clock);
        Thread.sleep(2000);
        t3.stopInterval(clock);
*/


        //A2
        t3.startInterval(clock);
        Thread.sleep(4000);
        t2.startInterval(clock);
        Thread.sleep(2000);
        t3.stopInterval(clock);
        Thread.sleep(2000);
        t1.startInterval(clock);
        Thread.sleep(4000);
        t1.stopInterval(clock);
        Thread.sleep(2000);
        t2.stopInterval(clock);
        Thread.sleep(4000);
        t3.startInterval(clock);
        Thread.sleep(2000);
        t3.stopInterval(clock);

        clock.deleteObserver(p1);






    }

}