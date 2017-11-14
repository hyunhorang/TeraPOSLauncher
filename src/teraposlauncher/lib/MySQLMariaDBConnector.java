package teraposlauncher.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic MySQL/MariaDB connection class which should be extended and
 * it contains basic methods for the select, update and insert.
 * @author Hyun Ho Oh
 */
public class MySQLMariaDBConnector {
  
  protected final String URL;
  protected final String PORT;
  protected final String DATABASE;
  protected final String USERNAME;
  protected final String PASSWORD;

  protected Connection connection;
  protected Statement statement;
  protected PreparedStatement prepared;
  protected ResultSet resultSet;
  
  /**
   * The constructor with basic setting with loading the driver.
   * @param url String
   * @param port String
   * @param database String
   * @param username String
   * @param password  String
   */
  public MySQLMariaDBConnector(
    String url, String port, String database, String username, String password
  ) {
    this.URL = url;
    this.PORT = port;
    this.DATABASE = database;
    this.USERNAME = username;
    this.PASSWORD = password;
    this.connection = null;
    this.statement = null;
    this.prepared = null;
    this.resultSet = null;
    this.loadDriver();
  }
  
  /**
   * Return true when the driver is loaded successfully but false otherwise.
   * @return boolean
   */
  private boolean loadDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch(ClassNotFoundException ex) {
      Logger.getLogger(MySQLMariaDBConnector.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
    return true;
  }
  
  /**
   * Return true when the connection is made successfully but false otherwise.
   * @return boolean
   */
  protected boolean connect() {
    try {
      String metaData = "";
      metaData += "jdbc:mysql://";
      metaData += this.URL + ":";
      metaData += this.PORT + "/";
      metaData += this.DATABASE;
      this.connection = DriverManager.getConnection(metaData, this.USERNAME, this.PASSWORD
      );
    } catch(SQLException ex) {
      Logger.getLogger(MySQLMariaDBConnector.class.getName()).log(Level.SEVERE, null, ex);
      System.exit(1);
    }
    return this.connection != null;
  }
  
  /**
   * Return true when the connection is closed successfully
   * but false otherwise.
   * @return boolean
   */
  protected boolean close() {
    try {
      if(this.connection != null) {
        this.connection.close();
        this.connection = null;
      }
      if(this.statement != null) {
        this.statement.close();
        this.statement = null;
      }
      if(this.prepared != null) {
        this.prepared.close();
        this.prepared = null;
      }
      if(this.resultSet != null) {
        this.resultSet.close();
        this.resultSet = null;
      }
    } catch(SQLException ex) {
      Logger.getLogger(MySQLMariaDBConnector.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
    return true;
  }
  
  /**
   * Checks connectivity with the database server.
   * @return boolean
   */
  protected synchronized boolean checkConnectivity() {
    return this.connect();
  }
  
  /**
   * Send select query to the server and the ResultSet must be closed outside
   * of an instance of this class.
   * @param query String
   * @return ResultSet
   */
  protected synchronized ResultSet pureSQLSelect(String query) {
    if(this.connect()) {
      try {
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(query);
      } catch(SQLException ex) {
        Logger.getLogger(MySQLMariaDBConnector.class.getName()).log(Level.SEVERE, null, ex);
        this.resultSet = null;
      }
    }
    return this.resultSet;
  }
  
  /**
   * Send update query to the server and close the connection after.
   * @param query String
   */
  protected synchronized void pureSQLUpdate(String query) {
    if(this.connect()) {
      try {
        this.statement = this.connection.createStatement();
        this.statement.executeUpdate(query);
      } catch(SQLException ex) {
        Logger.getLogger(MySQLMariaDBConnector.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        this.close();
      }
    }
  }
  
  /**
   * Send insert query to the server and close the connection after.
   * @param query String
   */
  protected synchronized void pureSQLInsert(String query) {
    if(this.connect()) {
      try {
        this.statement = this.connection.createStatement();
        this.statement.executeUpdate(query);
      } catch(SQLException ex) {
        Logger.getLogger(MySQLMariaDBConnector.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        this.close();
      }
    }
  }
  
}
