package core.ds.ds_project_timetracker;

import java.util.Date;

public class Client {

    public static void main(String[] args) {

        DataManager dataManager = new DataManager("save.db");
        boolean loadFile = false;
        boolean saveFile = false;
        Project root = null;

        if (loadFile) {
            root = (Project) dataManager.loadData();
        }

        if (root == null) {
            root = new Project("/", "rootProject", null);
        }

        /*
        Project p1 = new Project("P1", "P1 desc", root);
        Task t3 = new BaseTask("T3", "T3 desc", p1);
        Project p2 = new Project("P2", "P1 desc", p1);
        Task t1 = new BaseTask("T1", "T1 desc", p2);
        Task t2 = new BaseTask("T2", "T2 desc", p2);
        */

        PrinterVisitor printerVisitor = new PrinterVisitor(root);
        Clock.getInstance().setRefreshTicks(2);
        Clock.getInstance().addObserver(printerVisitor);

        System.out.println("Name  Start Date \t\t\t\t\t End Date \t\t\t\t\t Duration");

        if (loadFile) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Test A1
        //testA1(t3, t2);

        //Test A2
        //testA2(t3, t1, t2);

        //Test A3
        testA3(root);


        Clock.getInstance().deleteObserver(printerVisitor);
        Clock.getInstance().stopClock();
        System.out.println("Test ended.");

        if (saveFile) {
            dataManager.saveData(root);
        }

    }

    private static void testA1(Task t3, Task t2) {
        try {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testA2(Task t3, Task t1, Task t2) {
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
    }

    private static void testA3(Project root) {
        Project p1 = new Project("P1", "p1desc", root);
        Task t1 = new BaseTask("T1", "T1 desc", p1);
        Project p2 = new Project("P1", "p1desc", p1);
        Date stdate1 = new Date();
        stdate1.setTime(stdate1.getTime() + 10000);
        Task t2 = new ProgrammedTask(new BaseTask("T2", "T2desc", p2), stdate1);

        try {

            Date end1 = new Date();
            end1.setTime(new Date().getTime() + 5000);
            Task t3 = new LimitedTask(new BaseTask("T3", "limitedtask", p2), end1);
            t3.startInterval();
            Thread.sleep(2000);
            t1.startInterval();
            Thread.sleep(8000);
            t1.stopInterval(); //T3 = 6; T1 = 8
            Thread.sleep(10000); //T3 = 6; T1 = 8
            t2.stopInterval();



        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}