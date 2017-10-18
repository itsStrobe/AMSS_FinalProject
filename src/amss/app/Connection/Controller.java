package amss.app.Connection;

import amss.app.util.Logger;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Uuid;
import amss.db.DataBaseConnection;

/**
 * Created by Jose Zavala on 17/10/17.
 */

public final class Controller {

  private final Inquilino_Model inquilinoModel;
  private final DataBaseConnection connection = new DataBaseConnection();
  private final Uuid.Generator uuidGenerator;

  private final static Logger.Log LOG = Logger.newLog(Inquilino_Model.class);

  public Controller(Uuid serverId, Inquilino_Model inquilinoModel) {
    this.inquilinoModel = inquilinoModel;
    this.uuidGenerator = new RandomUuidGenerator(serverId, System.currentTimeMillis());
  }

}
