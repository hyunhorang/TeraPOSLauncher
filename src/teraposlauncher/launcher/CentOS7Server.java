package teraposlauncher.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.dao.QueueDAO;

/**
 *
 * @author Hyun Ho Oh
 */
public class CentOS7Server {
  
  private final QueueDAO queueDao;
  
  public CentOS7Server() {
    this.queueDao = new QueueDAO();
  }
  
  public void run() {
    this.closeFirefox();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException ex) {
      Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.openPrintQueue(false);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    // new CheckRestart().start();
    new CheckReady().start();
    // new CheckPrintQueue().start();
  }
  
  private class CheckRestart implements Runnable {

    private Thread thread;
    private boolean running;
    
    public CheckRestart() {
      this.thread = null;
      this.running = true;
    }
    
    @Override
    public void run() {
      while(this.running) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
  
  private class CheckReady implements Runnable {
    
    private Thread thread;
    private boolean running;
    
    public CheckReady() {
      this.thread = null;
      this.running = true;
    }
    
    @Override
    public void run() {
      if(CentOS7Server.this.queueDao.readReady() == 1) {
        CentOS7Server.this.closeFirefox();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        CentOS7Server.this.openPrintQueue(false);
      }
      while(this.running) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
  
  private class CheckPrintQueue implements Runnable {
    
    private Thread thread;
    private boolean running;
    
    public CheckPrintQueue() {
      this.thread = null;
      this.running = true;
    }
    
    @Override
    public void run() {
      while(this.running) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
  
  public void closeFirefox() {
    this.runCommand("pkill firefox", false);
  }
  
  public void openPrintQueue(boolean newWindow) {
    /*
    String command = "firefox " + ApplicationConfig.PRINT_QUEUE_URL + " > /dev/null 2>&1 &";
    if(newWindow) {
      command = "firefox -new-window " + ApplicationConfig.PRINT_QUEUE_URL + " > /dev/null 2>&1 &";
    }
    this.runCommand(command, false);
    */
  }
  
  private ArrayList<String> runCommand(String command, boolean result) {
    ArrayList<String> processResult = null;
    ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
    try {
      Process process = processBuilder.start();
      boolean waitFor = false;
      if(waitFor) {
        try {
          int resultCode = process.waitFor();
        } catch (InterruptedException ex) {
          Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      if(result) {
        try(
          BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
          )
        ) {
          processResult = new ArrayList<>();
          String line;
          while((line = bufferedReader.readLine()) != null) {
            processResult.add(line);
          }
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(CentOS7Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return processResult;
  }
  
}
