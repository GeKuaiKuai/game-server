package game.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GameFrameLoop implements Runnable {
  private static Logger logger = LogManager.getLogger(GameFrameLoop.class);

  protected long EXE_PERIOD = 500;
  abstract protected long getPeriod();

  public void start(){
    var thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {
    long startTime = 0;
    long nextTime = 0;
    long endTime = 0;
    long sleepTime = 0;
    while (true) {
      startTime = System.currentTimeMillis();
      nextTime = startTime + getPeriod();
      try {
        work();
      }catch (Exception e){
        logger.error("",e);
        e.printStackTrace();
      }
      endTime = System.currentTimeMillis();
      sleepTime = nextTime - endTime;
      if(sleepTime > 0){
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  protected abstract void work();
}
