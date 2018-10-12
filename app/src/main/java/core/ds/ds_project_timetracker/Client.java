package core.ds.ds_project_timetracker;

public class Client {


    public static void main(String[] args) {

        Project root, p1, p2;
        root = p1 = p2 = null;
        Task t1, t2, t3;
        t1 = t2 = t3 = null;
        boolean load = true;
        boolean save = true;
        PrinterVisitor printerVisitor = null;


        root = new Project("/", "rootProject", null);
        /*
        p1 = new Project("P1", "P1 desc", root);
        t3 = new BaseTask("T3", "T3 desc", p1);
        p2 = new Project("P2", "P1 desc", p1);
        t1 = new BaseTask("T1", "T1 desc", p2);
        t2 = new BaseTask("T2", "T2 desc", p2);
        */


        //testA1(root, p1, p2, t1, t2, t3);
        //testA2(root, p1, p2, t1, t2, t3);
        //testSerializable(load, save,root, p1, p2, t1, t2, t3, printerVisitor);
        testA3(root, printerVisitor);

    }

    private static void configClock(PrinterVisitor printerVisitor) {
        Clock.getInstance().setRefreshTicks(2);
        Clock.getInstance().addObserver(printerVisitor);
    }

    private static void endTest(PrinterVisitor printerVisitor) {
        Clock.getInstance().deleteObserver(printerVisitor);
        Clock.getInstance().stopClock();
        System.out.println("Test ended.");
    }

    private static void testA1(Project root, Project p1, Project p2, Task t1, Task t2, Task t3, PrinterVisitor printerVisitor) {
        printerVisitor = new PrinterVisitor(root);
        configClock(printerVisitor);

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

        endTest(printerVisitor);

    }

    private static void testA2(Project root, Project p1, Project p2, Task t1, Task t2, Task t3, PrinterVisitor printerVisitor) {
        printerVisitor = new PrinterVisitor(root);
        configClock(printerVisitor);

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

        endTest(printerVisitor);
    }

    private static void testSerializable(boolean load, boolean save, Project root, Project p1, Project p2, Task t1, Task t2, Task t3, PrinterVisitor printerVisitor) {
        DataManager dataManager = new DataManager("save.db");

        if (load) {
            root = (Project) dataManager.loadData();
            printerVisitor = new PrinterVisitor(root);
            configClock(printerVisitor);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            printerVisitor = new PrinterVisitor(root);
            configClock(printerVisitor);
            testA1(root, p1, p2, t1, t2, t3, printerVisitor);
        }

        if (save) {
            dataManager.saveData(root);
        }

        endTest(printerVisitor);

    }

    private static void testA3(Project root, PrinterVisitor printerVisitor) {

        Project p1 = new Project("P1", "", root);
        Task undecoratedTask = new BaseTask("Undecorated", "undecoratedDesc", p1);
        Project p2 = new Project("P2", "", p1);
        Task limitedTask = new LimitedTask(new BaseTask("Limited", "limitedDesc", p2), 12);
        Task programmedTask = new ProgrammedTask(new BaseTask("Programmed", "programmedDesc", p2), 10);
        Task bothDecorators = new LimitedTask(new ProgrammedTask(new BaseTask("Both", "bothdecorators", p2), 8), 15);

        printerVisitor = new PrinterVisitor(root);
        configClock(printerVisitor);

        try {
            //T0 --> U = 0; P = 0; L = 0; B = 0;
            Thread.sleep(1000);
            undecoratedTask.startInterval();
            Thread.sleep(4000);
            //T5 --> U = 4; P = 0; L = 0; B = 0; //Programmed starts in 5; Both starts in 3
            undecoratedTask.stopInterval();
            limitedTask.startInterval();
            Thread.sleep(6000);
            //T11 --> U = 4; P = 0; L = 6; B = 2;
            undecoratedTask.startInterval();
            Thread.sleep(10000);
            //T21 --> U = 14; P = 10; L = 12; B = 12;
            undecoratedTask.stopInterval();
            Thread.sleep(4000);
            //T25 --> U = 14; P = 14; L = 12; B = 14;
            programmedTask.stopInterval();
            Thread.sleep(20000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}