package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Medicina;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.util.StringConverter;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 17/10/17.
 */
public class Medicina_Model extends Model{

  private Medicina medicina;

  // FUNCIONES SET
  public void add(Medicina medicina) {
    this.medicina = medicina;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(medicina.getId()));
      parameters.add(medicina.getNombre());
      query = "INSERT INTO MEDICINAS (ID,NOMBRE) " +
          "VALUES ( ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nueva Medicina (ID=%s Nombre=%s Fecha=%s)",
          medicina.getId(),
          medicina.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva Medicina - ERROR - Database insertion error (ID=%s Nombre=%s Fecha=%s)",
          medicina.getId(),
          medicina.getNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Medicina medicina) {
    this.medicina = medicina;
    update();
  }

  @Override
  protected void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(medicina.getNombre());
      parameters.add(SQLFormatter.sqlID(medicina.getId()));
      query = "UPDATE MEDICINAS set" +
          " NOMBRE = ?," +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar Medicina (ID=%s Nombre=%s Fecha=%s)",
          medicina.getId(),
          medicina.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar Medicina - ERROR - Database insertion error (ID=%s Nombre=%s Fecha=%s)",
          medicina.getId(),
          medicina.getNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  // FUNCIONES GET
  public Collection<Medicina> getAllMedicinas() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getMedicinas(parameters, query);
  }

  public Collection<Medicina> getSingleMedicinaByID(Uuid id) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(id));
    query = "ID = ?";

    return getMedicinas(parameters, query);
  }

  public Collection<Medicina> getSingleMedicinaByName(String name) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(name);
    query = "NOMBRE = ?";

    return getMedicinas(parameters, query);
  }

  private Collection<Medicina> getMedicinas(Vector<String> parameters, String where) {
    String query = "SELECT * FROM MEDICINAS";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getMedicinas(parameters, query);
  }

}
