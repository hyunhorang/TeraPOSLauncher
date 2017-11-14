package teraposlauncher;

import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.customtype.OSType;
import teraposlauncher.dao.LauncherDAO;
import teraposlauncher.lib.OSValidator;
import teraposlauncher.system.CentOS7;

/**
 * TeraPOS Launcher Application
 * @author Hyun Ho Oh
 */
public class TeraPOSLauncher {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) { new TeraPOSLauncher().run(); }
  
  public static final LauncherDAO DAO = new LauncherDAO();
  
  public TeraPOSLauncher() {
    
  }
  
  public void run() {
    this.validateOS();
    if(ApplicationConfig.OS_TYPE == OSType.UNIX) {
      new CentOS7().run();
    }
  }
  
  private void validateOS() {
    ApplicationConfig.OS_TYPE = new OSValidator().getOSType();
  }
  
}
