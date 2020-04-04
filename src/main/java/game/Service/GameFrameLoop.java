package game.Service;

public abstract class GameFrameLoop implements Runnable {
  protected long EXE_PERIOD = 500;

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
      nextTime = startTime + EXE_PERIOD;
      try {
        work();
      }catch (Exception e){
        System.out.println(e.toString());
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
