package teraposlauncher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    this.readConfig();
  }
  
  private void readConfig() {
    ResultSet rs = this.pureSQLSelect("SELECT * FROM config");
    try {
      while(rs.next()) {
        ApplicationConfig.LAUNCHER_TYPE = resultSet.getString("launcher_type");
      }
    } catch (SQLException ex) {
      Logger.getLogger(LauncherDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      this.close();
    }
  }
  
}
