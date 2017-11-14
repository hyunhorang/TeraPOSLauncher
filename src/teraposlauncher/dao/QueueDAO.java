package teraposlauncher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import teraposlauncher.config.DatabaseConfig;
import teraposlauncher.lib.MySQLMariaDBConnector;

/**
 *
 * @author Hyun Ho Oh
 */
public class QueueDAO extends MySQLMariaDBConnector {
  
  public QueueDAO() {
    super(
      DatabaseConfig.Queue.URL, 
      DatabaseConfig.Queue.PORT, 
      DatabaseConfig.Queue.DATABASE, 
      DatabaseConfig.Queue.USERNAME, 
      DatabaseConfig.Queue.PASSWORD
    );
  }
  
  public int readReady() {
    ResultSet rs = this.pureSQLSelect("SELECT ready FROM queue");
    int ready = 0;
    try {
      while(rs.next()) {
        ready = rs.getInt("ready");
      }
    } catch (SQLException ex) {
      Logger.getLogger(QueueDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      this.close();
    }
    return ready;
  }
  
}
