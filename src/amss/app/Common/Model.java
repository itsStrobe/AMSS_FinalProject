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
}
