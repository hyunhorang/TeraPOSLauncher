package teraposlauncher.system;

import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.launcher.WindowsClient;

/**
 *
 * @author Hyun Ho Oh
 */
public class Windows {
  
  public Windows() {
    
  }
  
  public void run() {
    if(ApplicationConfig.LAUNCHER_TYPE.equals("client")) {
      new WindowsClient().run();
    }
  }
  
}
