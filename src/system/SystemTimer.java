package system;

public class SystemTimer {
    private double delta;
    private long firstTime;
    private long now;
    private long timer;
    private int runTime;
    private int FPS, APS;
    private short plusF, plusA;

    public SystemTimer(){
        runTime = 0;
        firstTime = 0;
        timer = 0;
        delta = 0.0;
        FPS = 0;
        APS = 0;
        plusF = 0;
        plusA = 0;
    }

    public void systemShowTime(long timer){
        if (this.timer == 0) this.timer = timer;
        if (System.nanoTime() - this.timer > Constants.NANOTIME) {
            this.timer += Constants.NANOTIME;
            runTime++;
            FPS = plusF;
            APS = plusA;
            plusF = 0;
            plusA = 0;
        }
    }

    public double getDelta(){return delta;}

    public double setDelta(long firstTime){
        if (this.firstTime == 0) this.firstTime = firstTime;
        now = System.nanoTime();
        delta += (now - this.firstTime) / Constants.TIME;
        this.firstTime = now;
        return delta;
    }

    public void restDelta(){delta--;}

    public String getRunTimeText() {
        String time = "0";
        if (runTime > 0 && runTime < 60) time = runTime + " s";
        else if (runTime / 60 > 0 && runTime / 60 < 60) {
            String min = String.valueOf(runTime / 60);
            String s = String.valueOf(runTime - runTime / 60 * 60);
            time = min + ":" + s + " min";
        }
        return time;
    }

    public int getRunTime(){return runTime;}

    public int getFPS(){return FPS;}

    public int getAPS(){return APS;}

    public void plusF(){plusF++;}

    public void plusA(){plusA++;}
}
