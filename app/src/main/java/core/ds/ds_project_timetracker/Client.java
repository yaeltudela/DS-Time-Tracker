package core.ds.ds_project_timetracker;

import java.util.Date;

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
        Serializable test is formed by two executions (one for saving the data (3) and another
            for loading the saved data(4)) and must be executed in order.

            1: A1
            2: A2
            3: Serializable (Test A1)
            4: Serializable (Load data from test A1)
            5: A3 (Decorators)
         */

        int test = 7;
        switch (test) {
            case 1: //Test A1
                p1 = new Project("P1", "P1 desc", root);
                t3 = new BaseTask("T3", "T3 desc", p1);
                p2 = new Project("P2", "P1 desc", p1);
                t1 = new BaseTask("T1", "T1 desc", p2);
                t2 = new BaseTask("T2", "T2 desc", p2);

                testA1(root, p1, p2, t1, t2, t3, printerVisitor);
                break;
            case 2: //Test A2
                p1 = new Project("P1", "P1 desc", root);
                t3 = new BaseTask("T3", "T3 desc", p1);
                p2 = new Project("P2", "P1 desc", p1);
                t1 = new BaseTask("T1", "T1 desc", p2);
                t2 = new BaseTask("T2", "T2 desc", p2);

                testA2(root, p1, p2, t1, t2, t3, printerVisitor);
                break;
            case 3: //Test A1 saving to a file
                p1 = new Project("P1", "P1 desc", root);
                t3 = new BaseTask("T3", "T3 desc", p1);
                p2 = new Project("P2", "P1 desc", p1);
                t1 = new BaseTask("T1", "T1 desc", p2);
                t2 = new BaseTask("T2", "T2 desc", p2);

                load = false;
                save = true;
                testSerializable(load, save, root, p1, p2, t1, t2, t3, printerVisitor);
                break;
            case 4: //Loading from file
                load = true;
                testSerializable(load, save, root, p1, p2, t1, t2, t3, printerVisitor);
                break;
            case 5: //Custom Test
                testA3(root, printerVisitor);
                break;
            case 6: //Test decorator (A1 with task1 programmed and limited)
                p1 = new Project("P1", "P1 desc", root);
                t3 = new BaseTask("T3", "T3 desc", p1);
                p2 = new Project("P2", "P1 desc", p1);
                t1 = new ProgrammedTask(new LimitedTask(new BaseTask("T1", "T1 desc", p2), 3), 4);

                t2 = new BaseTask("T2", "T2 desc", p2);

                testA4(root, p1, p2, t1, t2, t3, printerVisitor);
                break;

            case 7: //Test entrega 2
                p1 = new Project("P1", "P1 desc", root);
                p2 = new Project("P2", "P2 desc", root);
                Project p1_2 = new Project("P1_2", "P1.2 desc", p1);
                t1 = new BaseTask("T1", "T1 desc", p1);
                t2 = new BaseTask("T2", "T2 desc", p1);
                Task t4 = new BaseTask("T4", "T4 desc", p1_2);
                t3 = new BaseTask("T3", "T3 desc", p2);

                testEntrega2(p1, p2, p1_2, t1, t2, t3, t4, printerVisitor, root);


                break;
        }

    }

    private static void testEntrega2(Project p1, Project p2, Project p1_2, Task t1, Task t2, Task t3, Task t4, PrinterVisitor printerVisitor, Project root) {
        printerVisitor = new PrinterVisitor(root);
        configClock(printerVisitor);

        try {
            t1.startInterval();
            t4.startInterval();

            Thread.sleep(4000);
            Date d0 = new Date();
            t1.stopInterval();
            t2.startInterval();

            Thread.sleep(6000);
            t2.stopInterval();
            t4.stopInterval();
            t3.startInterval();

            Thread.sleep(4000);
            t3.stopInterval();
            t2.startInterval();

            Date d = new Date();

            Thread.sleep(2000);
            t3.startInterval();

            Thread.sleep(4000);
            t2.stopInterval();
            t3.stopInterval();

            Period period = new Period(d0, d);
            Report report = new DetailedReport(root, period);
            report.generateReport();
            ReportGenerator reportGenerator = new TXTReportGenerator(report);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        endTest(printerVisitor);
    }


    private static void configClock(PrinterVisitor printerVisitor) {
        Clock.getInstance().setRefreshTicks(2);
        Clock.getInstance().addObserver(printerVisitor);
    }

    private static void endTest(PrinterVisitor printerVisitor) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        } catch (InterruptedException e) {
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
        Task limitedTask = new LimitedTask(new BaseTask("Limited", "limitedDesc", p2), 8);
        Task programmedTask = new ProgrammedTask(new BaseTask("Programmed", "programmedDesc", p2), 10);
        Task bothDecorators = new ProgrammedTask(new LimitedTask(new BaseTask("Both", "bothdecorators", p2), 12), 8);

        printerVisitor = new PrinterVisitor(root);
        configClock(printerVisitor);

        try {
            Thread.sleep(1000);
            //T0 --> U = 0; P = 0; L = 0; B = 0;
            undecoratedTask.startInterval();
            Thread.sleep(4000);
            //T5 --> U = 4; P = 0; L = 0; B = 0; //Programmed starts in 5; Both starts in 3
            undecoratedTask.stopInterval();
            limitedTask.startInterval();
            Thread.sleep(6000);
            //T11 --> U = 4; P = 0; L = 6; B = 2;
            undecoratedTask.startInterval();
            Thread.sleep(10000);
            //T21 --> U = 14; P = 10; L = 8; B = 12;
            undecoratedTask.stopInterval();
            Thread.sleep(4000);
            //T25 --> U = 14; P = 14; L = 8; B = 12;
            programmedTask.stopInterval();
            Thread.sleep(5000);
            //T25 --> U = 14; P = 14; L = 8; B = 12;


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void testA4(Project root, Project p1, Project p2, Task t1, Task t2, Task t3, PrinterVisitor printerVisitor) {
        printerVisitor = new PrinterVisitor(root);
        configClock(printerVisitor);

        try {
            t3.startInterval();
            Thread.sleep(3000);
            //T1 = 0; T2 = 0; T3 = 2;
            t3.stopInterval();
            Thread.sleep(7000);
            //T1 = 2; T2 = 0; T3 = 2;
            t2.startInterval();
            Thread.sleep(10000);
            //T1 = 2; T2 = 0; T3 = 2;
            t2.stopInterval();
            t3.startInterval();
            Thread.sleep(2000);
            //T1 = 2; T2 = 10; T3 = 4;
            t3.stopInterval();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        endTest(printerVisitor);
    }
}