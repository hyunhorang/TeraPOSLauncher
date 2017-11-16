package teraposlauncher.launcher;

import teraposlauncher.TeraPOSLauncher;

/**
 *
 * @author Hyun Ho Oh
 */
public class MacOSServer {
  
  public MacOSServer() {
    
  }
  
  public void run() {
    TeraPOSLauncher.debug("Starting MacOSServer ...");
    
    TeraPOSLauncher.closeFirefox();
    TeraPOSLauncher.sleep(2000);
    
  }
  
}
