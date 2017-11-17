package teraposlauncher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import teraposlauncher.TeraPOSLauncher;
import teraposlauncher.config.ApplicationConfig;
import teraposlauncher.config.DatabaseConfig;
import teraposlauncher.lib.MySQLMariaDBConnector;

/**
 *
 * @author Hyun Ho Oh
 */
public class LauncherDAO extends MySQLMariaDBConnector {
  
  public LauncherDAO() {
    super(
      DatabaseConfig.URL, DatabaseConfig.PORT, DatabaseConfig.DATABASE, 
      DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD
    );
  }
  
  public void readConfig() {
    ResultSet rs = this.pureSQLSelect("SELECT * FROM config");
    try {
      while(rs.next()) {
        ApplicationConfig.LAUNCHER_TYPE = rs.getString("launcher_type");
        ApplicationConfig.DATABASE_NAME = rs.getString("database_name");
        ApplicationConfig.SCREEN_RESOLUTION_WIDTH = rs.getInt("screen_resolution_width");
        ApplicationConfig.SCREEN_RESOLUTION_HEIGHT = rs.getInt("screen_resolution_height");
      }
    } catch (SQLException ex) {
      Logger.getLogger(LauncherDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      TeraPOSLauncher.debug("=====================================");
      TeraPOSLauncher.debug("= APPLICATION CONFIGRATION DATA =====");
      TeraPOSLauncher.debug("=====================================");
      TeraPOSLauncher.debug("DATABASE_NAME=" + ApplicationConfig.DATABASE_NAME);
      TeraPOSLauncher.debug("LAUNCHER_TYPE=" + ApplicationConfig.LAUNCHER_TYPE);
      TeraPOSLauncher.debug("=====================================");
      this.close();
    }
  }
  
  public void readBackupDB() {
    ApplicationConfig.BACKUP_DB_LIST = new ArrayList<>();
    ResultSet rs = this.pureSQLSelect("SELECT * FROM backup_db");
    try {
      while(rs.next()) {
        ApplicationConfig.BACKUP_DB_LIST.add(rs.getString("name"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(LauncherDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      TeraPOSLauncher.debug("=====================================");
      TeraPOSLauncher.debug("= BACKUP DATABASES ==================");
      TeraPOSLauncher.debug("=====================================");
      for(String backupDB : ApplicationConfig.BACKUP_DB_LIST) {
        TeraPOSLauncher.debug(backupDB);
      }
      TeraPOSLauncher.debug("=====================================");
      this.close();
    }
  }
  
}
