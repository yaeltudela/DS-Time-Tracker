package core.ds.ds_project_timetracker;


import java.util.Date;

public class Clock extends Thread{

    private int refreshTicks = 1000;
    private Date date = null;

    public void run(){
        try{
            while(true){
                sleep(refreshTicks);
                updateClock();
            }
        }catch (InterruptedException e){
            System.out.println("aaa");
        }
    }

    private void updateClock(){
        date = new Date();
    }

    private void startClock(){
        if(date == null){
            this.date = new Date();
            start();
        }
    }

    public String getTime(){
        return date.toString();
    }

    private void setRefreshTicks(int secs) throws Exception {
        if(secs >= 1){
            refreshTicks = secs * 1000;
        }else{
            throw new Exception(); //Out of limits
        }
    }

    public static void main(String[] args) throws Exception{

        Clock clock = new Clock();
        clock.startClock();
        int secs = 1;


        clock.setRefreshTicks(secs);

        for (int i = 0; i < 10; i++){
            System.out.println(clock.getTime());
            System.out.println(clock.isAlive());
        }

    }


}
