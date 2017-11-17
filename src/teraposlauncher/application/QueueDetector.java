package teraposlauncher.application;

import teraposlauncher.TeraPOSLauncher;
import teraposlauncher.dao.QueueDAO;

/**
 * Working with 'queue' table
 * @author Hyun Ho Oh
 */
public class QueueDetector implements Runnable {
  
  private Thread thread;
  private boolean running;
  private QueueDAO queueDAO;
  
  public QueueDetector() {
    this.thread = null;
    this.running = true;
    this.queueDAO = new QueueDAO();
  }

  @Override
  public void run() {
    while(this.running) {
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
