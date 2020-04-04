import game.Service.GameMapRsyncLoop;
import manager.ServiceManager;

public class Main {
  public static void main(String args[]) throws Exception {
    GameMapRsyncLoop loop = new GameMapRsyncLoop();
    ServiceManager.getInstance().addLoop(loop);
    loop.start();
    GameServerBoot.start();
  }
}
