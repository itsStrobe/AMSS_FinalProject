package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.util.Logger;
import amss.app.util.SQLFormatter;
import amss.app.Individuos.Inquilino;
import amss.app.util.Time;
import amss.app.util.Uuid;
import amss.db.DataBaseConnection;

import java.util.*;

/**
 * Created by Jose Zavala on 17/10/17.
 */
public final class Inquilino_Model extends Model {

  private Inquilino inquilino;

  // FUNCIONES SET
  public void add(Inquilino inquilino)
  {
    this.inquilino = inquilino;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(inquilino.getId()));
      parameters.add(inquilino.getNombre());
      parameters.add(inquilino.getDireccion());
      parameters.add(SQLFormatter.sqlTime(inquilino.getFechaNacimiento()));
      parameters.add(SQLFormatter.sqlInt(inquilino.getEdad()));
      parameters.add(SQLFormatter.sqlID(inquilino.getIdResponsable()));
      parameters.add(SQLFormatter.sqlChar(inquilino.getEstatus()));
      parameters.add(inquilino.getCuarto());
      query = "INSERT INTO INQUILINOS (ID,NOMBRE,DIRECCION,FECHANACIMIENTO,EDAD,RESPONSABLE,ESTATUS,CUARTO) " +
          "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nuevo Inquilino (ID=%s Nombre=%s Fecha=%s)",
          inquilino.getId(),
          inquilino.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nuevo Inquilino - ERROR - Database insertion error (ID=%s Nombre=%s Fecha=%s)",
          inquilino.getId(),
          inquilino.getNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Inquilino inquilino) {
    this.inquilino = inquilino;
    update();
  }

  @Override
  protected void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(inquilino.getNombre());
      parameters.add(inquilino.getDireccion());
      parameters.add(SQLFormatter.sqlTime(inquilino.getFechaNacimiento()));
      parameters.add(SQLFormatter.sqlInt(inquilino.getEdad()));
      parameters.add(SQLFormatter.sqlID(inquilino.getIdResponsable()));
      parameters.add(SQLFormatter.sqlChar(inquilino.getEstatus()));
      parameters.add(inquilino.getCuarto());
      parameters.add(SQLFormatter.sqlID(inquilino.getId()));
      query = "UPDATE INQUILINOS set" +
          " NOMBRE = ?," +
          " DIRECCION = ?," +
          " FECHANACIMIENTO = ?," +
          " EDAD = ?," +
          " RESPONSABLE = ?," +
          " ESTATUS = ?," +
          " CUARTO = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar Inquilino (ID=%s Nombre=%s Fecha=%s)",
          inquilino.getId(),
          inquilino.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar Inquilino - ERROR - Database insertion error (ID=%s Nombre=%s Fecha=%s)",
          inquilino.getId(),
          inquilino.getNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }



  // FUNCIONES GET
  public Collection<Inquilino> getAllInquilinos() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getInquilinos(parameters, query);
  }

  public Collection<Inquilino> getSingleInquilinoById(Uuid id) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(id));
    query = "ID = ?";

    return getInquilinos(parameters, query);
  }

  private Collection<Inquilino> getInquilinos(Vector<String> parameters, String where) {
    String query = "SELECT * FROM INQUILINOS";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getInquilinos(parameters, query);
  }

  // Get:
  // Las medicinas de un inquilino
  // Recetas de un inquilino
  // Familiares de uno
}
