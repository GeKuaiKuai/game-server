import game.Service.GameMapRsyncLoop;
import game.Service.GameRankLoop;
import game.Service.GameStoreLoop;
import manager.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String args[]) throws Exception {
    logger.info("Dream Fight Server start boot.");
    GameMapRsyncLoop mapLoop = new GameMapRsyncLoop();
    GameStoreLoop storeLoop = new GameStoreLoop();
    GameRankLoop rankLoop = new GameRankLoop();

    ServiceManager.getInstance().addLoop(mapLoop);
    ServiceManager.getInstance().addLoop(storeLoop);
    ServiceManager.getInstance().addLoop(rankLoop);
    GameStoreLoop.load();
    logger.info("Dream Fight Server start service finish");

    ServiceManager.getInstance().start();
    GameServerBoot.start();
  }
}
