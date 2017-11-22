package amss.app.Common;

import amss.app.util.Logger;
import amss.db.DataBaseConnection;

/**
 * Created by Jose Zavala on 17/10/17.
 */
public abstract class Model {
  protected final static Logger.Log LOG = Logger.newLog(Model.class);

  protected static DataBaseConnection dbConnection = new DataBaseConnection();

  protected abstract void add();

  protected abstract void update();

  public static void createDatabase() {
    DataBaseConnection connection = new DataBaseConnection();
    connection.createTables();
    System.out.println("Finished Creating Database Successfully!!!");
  }

  public static void deleteDatabase() {
    DataBaseConnection connection = new DataBaseConnection();
    connection.dropTables();
    System.out.println("Finished Deleting Database Successfully!!!");
  }

  public static void resetDatabase() {
    DataBaseConnection connection = new DataBaseConnection();
    connection.dropTables();
    connection.createTables();
    System.out.println("Finished Resetting Database Successfully!!!");
  }
}
