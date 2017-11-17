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
   * Empty constructor.
   */
  public TeraPOSLauncher() {
    
  }
  
  /**
   * Runs the class.
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
  
  /**
   * Allocates data to variables in ApplicationConfig class.
   */
  private void initConfig() {
    LauncherDAO launcherDAO = new LauncherDAO();
    launcherDAO.readConfig();
    launcherDAO.readBackupDB();
  }
  
  /**
   * Validates OS type and allocates value to the ApplicationConfig.OS_TYPE.
   */
  private void validateOS() {
    ApplicationConfig.OS_TYPE = new OSValidator().getOSType();
  }
  
  /**
   * Print output in the console if the debug mode is on.
   * @param output Console output
   */
  public static void debug(String output) {
    if(TeraPOSLauncher.DEBUG) { System.out.println(output); }
  }
  
  /**
   * Make the current thread sleep for certain millisecond.
   * @param millisecond sleeping time in millisecond form
   */
  public static void sleep(int millisecond) {
    try {
      Thread.sleep(millisecond);
    } catch (InterruptedException ex) {
      Logger.getLogger(TeraPOSLauncher.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * Executes system command.
   * @param command command to be executed
   * @param result 
   * @return ArrayList<String>
   */
  public static ArrayList<String> runCommand(String command, boolean result) {
    String pb1 = "";
    String pb2 = "";
    switch(ApplicationConfig.OS_TYPE) {
      case WINDOWS:
        pb1 = "CMD";
        pb2 = "/C";
        break;
      case MAC:
        pb1 = "/bin/bash";
        pb2 = "-c";
        break;
      case UNIX:
        pb1 = "/bin/bash";
        pb2 = "-c";
        break;
      default:
    }
    ArrayList<String> processResult = null;
    ProcessBuilder processBuilder = new ProcessBuilder(pb1, pb2, command);
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
  
  /**
   * 
   * @param url
   * @param newWindow 
   */
  public static void openFirefox(String url, boolean newWindow) {
    String command = "";
    if(ApplicationConfig.OS_TYPE == OSType.MAC) {
      command = "/Applications/Firefox.app/Contents/MacOS/firefox " + url;
      if(newWindow) {
        command = "/Applications/Firefox.app/Contents/MacOS/fir efox -new-window " + url;
      }
    } else if(ApplicationConfig.OS_TYPE == OSType.UNIX) {
      command = "firefox " + url + " > /dev/null 2>&1 &";
      if(newWindow) {
        command = "firefox -new-window " + url + " > /dev/null 2>&1 &";
      }
    }
    TeraPOSLauncher.runCommand(command, false);
  }
  
  /**
   * 
   */
  public static void closeFirefox() {
    String command = "";
    if(ApplicationConfig.OS_TYPE == OSType.MAC || ApplicationConfig.OS_TYPE == OSType.UNIX) {
      command = "pkill firefox";
    }
    TeraPOSLauncher.runCommand(command, false);
  }
  
}
