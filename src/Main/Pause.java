package Main;

public class Pause {
    public int timeInSec;
    public Pause(int timeInSec)
    {
        this.timeInSec = timeInSec;
    }
    public void pause()
    {
        long startTime;
        startTime = System.nanoTime()/1000000000;
        long endTime = timeInSec + startTime;
        while(endTime - startTime > 0)
        {
            startTime = System.nanoTime()/1000000000;
        }
    }
}
