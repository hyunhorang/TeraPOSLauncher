package teraposlauncher.system;

import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.launcher.CentOS7Server;

/**
 *
 * @author Hyun Ho Oh
 */
public class CentOS7 {
  
  public CentOS7() {
    
  }
  
  public void run() {
    if(ApplicationConfig.LAUNCHER_TYPE.equals("server")) {
      new CentOS7Server().run();
    }
  }
  
}
