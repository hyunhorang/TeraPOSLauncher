package teraposlauncher.application;

import teraposlauncher.TeraPOSLauncher;

/**
 *
 * @author Hyun Ho Oh
 */
public class SystemAutoRestart implements Runnable {
 
  private Thread thread;
  private boolean runnable;
  
  public SystemAutoRestart() {
    this.thread = null;
    this.runnable = true;
  }

  @Override
  public void run() {
    while(this.runnable) {
      TeraPOSLauncher.sleep(1000);
    }
  }
  
  public void start() {
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  public void stop() {
    this.runnable = false;
  }
  
}
