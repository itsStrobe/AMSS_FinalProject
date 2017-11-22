package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Depositos;
import amss.app.Elementos.Emergencias;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 15/11/17.
 */
public class Depositos_Model extends Model{
  private Depositos depositos;

  // FUNCIONES SET
  public void add(Depositos depositos)
  {
    this.depositos = depositos;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(depositos.getId()));
      parameters.add(SQLFormatter.sqlID(depositos.getInquilino()));
      parameters.add(SQLFormatter.sqlID(depositos.getFamiliar()));
      parameters.add(SQLFormatter.sqlID(depositos.getMedicina()));
      parameters.add(SQLFormatter.sqlInt(depositos.getCantidad()));
      parameters.add(SQLFormatter.sqlTime(depositos.getFecha()));
      query = "INSERT INTO DEPOSITOS (ID,INQUILINO,FAMILIAR,MEDICINA,CANTIDAD,FECHA) " +
          "VALUES ( ?, ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nuevo DEPOSITOS (DEPOSITO_ID=%s DEPOSITO_MEDICINA=%s DEPOSITO_FECHA=%s Fecha=%s)",
          depositos.getId(),
          depositos.getMedicina(),
          depositos.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nuevo DEPOSITOS - ERROR - Database insertion error (DEPOSITO_ID=%s DEPOSITO_MEDICINA=%s DEPOSITO_FECHA=%s Fecha=%s)",
          depositos.getId(),
          depositos.getMedicina(),
          depositos.getFecha(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Depositos depositos) {
    this.depositos = depositos;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(depositos.getInquilino()));
      parameters.add(SQLFormatter.sqlID(depositos.getFamiliar()));
      parameters.add(SQLFormatter.sqlID(depositos.getMedicina()));
      parameters.add(SQLFormatter.sqlInt(depositos.getCantidad()));
      parameters.add(SQLFormatter.sqlTime(depositos.getFecha()));
      parameters.add(SQLFormatter.sqlID(depositos.getId()));
      query = "UPDATE DEPOSITOS set" +
          " INQUILINO = ?," +
          " FAMILIAR = ?," +
          " MEDICINA = ?," +
          " CANTIDAD = ?" +
          " FECHA = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar DEPOSITOS (DEPOSITO_ID=%s DEPOSITO_MEDICINA=%s DEPOSITO_FECHA=%s Fecha=%s)",
          depositos.getId(),
          depositos.getMedicina(),
          depositos.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar DEPOSITOS - ERROR - Database insertion error (DEPOSITO_ID=%s DEPOSITO_MEDICINA=%s DEPOSITO_FECHA=%s Fecha=%s)",
          depositos.getId(),
          depositos.getMedicina(),
          depositos.getFecha(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Depositos> getAllDepositosOfInquilino(Uuid inquilinoId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilinoId));
    query = " INQUILINO = ?";

    return getDepositos(parameters, query);
  }

  public Collection<Depositos> getAllDepositosOfFamiliar(Uuid familiarId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(familiarId));
    query = " FAMILIAR = ?";

    return getDepositos(parameters, query);
  }

  private Collection<Depositos> getDepositos(Vector<String> parameters, String where) {
    String query = "SELECT * FROM DEPOSITOS";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getDepositos(parameters, query);
  }
}
