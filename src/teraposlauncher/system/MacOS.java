package teraposlauncher.system;

import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.launcher.MacOSServer;

/**
 *
 * @author Hyun Ho Oh
 */
public class MacOS {
  
  public MacOS() {
    
  }
  
  public void run() {
    if(ApplicationConfig.LAUNCHER_TYPE.equals("server")) {
      new MacOSServer().run();
    }
  }
  
}
