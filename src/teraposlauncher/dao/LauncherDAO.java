package teraposlauncher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
  
}
