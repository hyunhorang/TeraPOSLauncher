package teraposlauncher.application;

import teraposlauncher.TeraPOSLauncher;

/**
 *
 * @author Hyun Ho Oh
 */
public class NetworkChecker implements Runnable {

  private Thread thread;
  private boolean running;
  
  public NetworkChecker() {
    this.thread = null;
    this.running = true;
  }
  
  @Override
  public void run() {
    while(running) {
      TeraPOSLauncher.sleep(1000);
    }
  }
  
  public void start() {
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  public void stop() {
    this.running = false;
  }
  
}
