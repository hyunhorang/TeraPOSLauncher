package teraposlauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.customtype.OSType;
import teraposlauncher.dao.LauncherDAO;
import teraposlauncher.lib.OSValidator;
import teraposlauncher.system.CentOS7;
import teraposlauncher.system.MacOS;

/**
 * TeraPOS Launcher Application
 * @author Hyun Ho Oh
 */
public class TeraPOSLauncher {

  /**
   * No command line arguments.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new TeraPOSLauncher().run();
  }
  
  // DEGUG mode on/off
  public static final boolean DEBUG = true;
  
  /**
   * Empty constructor
   */
  public TeraPOSLauncher() {
    
  }
  
  /**
   * Run the class
   */
  public void run() {
    TeraPOSLauncher.debug("DEBUG : on");
    this.initConfig();
    this.validateOS();
    if(ApplicationConfig.OS_TYPE == OSType.UNIX) {
      new CentOS7().run();
    } else if(ApplicationConfig.OS_TYPE == OSType.MAC) {
      new MacOS().run();
    }
  }
  
  private void initConfig() {
    LauncherDAO launcherDAO = new LauncherDAO();
    launcherDAO.readConfig();
  }
  
  private void validateOS() {
    ApplicationConfig.OS_TYPE = new OSValidator().getOSType();
  }
  
  public static void debug(String output) {
    if(TeraPOSLauncher.DEBUG) { System.out.println(output); }
  }
  
  public static void sleep(int millisecond) {
    try {
      Thread.sleep(millisecond);
    } catch (InterruptedException ex) {
      Logger.getLogger(TeraPOSLauncher.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static ArrayList<String> runCommand(String command, boolean result) {
    ArrayList<String> processResult = null;
    ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
    try {
      Process process = processBuilder.start();
      boolean waitFor = false;
      if(waitFor) {
        try {
          int resultCode = process.waitFor();
        } catch (InterruptedException ex) {
          System.out.println(ex.getMessage());
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
          TeraPOSLauncher.debug("=====================================");
          TeraPOSLauncher.debug("= COMMAND RESULT ====================");
          TeraPOSLauncher.debug("=====================================");
          TeraPOSLauncher.debug(command);
          TeraPOSLauncher.debug("=====================================");
          while((line = bufferedReader.readLine()) != null) {
            TeraPOSLauncher.debug(line);
            processResult.add(line);
          }
          TeraPOSLauncher.debug("=====================================");
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(TeraPOSLauncher.class.getName()).log(Level.SEVERE, null, ex);
    }
    return processResult;
  }
  
  public static void openFirefox(String url, boolean newWindow) {
    String command = "";
    if(ApplicationConfig.OS_TYPE == OSType.MAC) {
      command = "/Applications/Firefox.app/Contents/MacOS/firefox " + url;
      if(newWindow) {
        command += "/Applications/Firefox.app/Contents/MacOS/firefox -new-window " + url;
      }
    }
    TeraPOSLauncher.runCommand(command, false);
  }
  
  public static void closeFirefox() {
    String command = "";
    if(ApplicationConfig.OS_TYPE == OSType.MAC) {
      command = "pkill firefox";
    }
    TeraPOSLauncher.runCommand(command, false);
  }
  
}
