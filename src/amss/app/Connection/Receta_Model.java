package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Receta;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by strobe on 18/10/17.
 */
public class Receta_Model extends Model {
  private Receta receta;

  // FUNCIONES SET
  public void add(Receta receta)
  {
    this.receta = receta;
    add();
  }

  @Override
  public void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(receta.getId()));
      parameters.add(receta.getDocNombre());
      parameters.add(SQLFormatter.sqlID(receta.getIdPaciente()));
      parameters.add(SQLFormatter.sqlTime(receta.getFechaInicio()));
      query = "INSERT INTO RECETAS (ID,DOCTOR,PACIENTE,FECHA) " +
          "VALUES ( ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nueva Receta (ID=%s ID_Paciente=%s Doc_Nombre=%s Fecha=%s)",
          receta.getId(),
          receta.getIdPaciente(),
          receta.getDocNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva Receta - ERROR - Database insertion error (ID=%s ID_Paciente=%s Doc_Nombre=%s Fecha=%s)",
          receta.getId(),
          receta.getIdPaciente(),
          receta.getDocNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Receta receta) {
    this.receta = receta;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(receta.getDocNombre());
      parameters.add(SQLFormatter.sqlID(receta.getIdPaciente()));
      parameters.add(SQLFormatter.sqlTime(receta.getFechaInicio()));
      parameters.add(SQLFormatter.sqlID(receta.getId()));
      query = "UPDATE RECETAS set" +
          " DOCTOR = ?," +
          " PACIENTE = ?," +
          " FECHA = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar Receta (ID=%s ID_Paciente=%s Doc_Nombre=%s Fecha=%s)",
          receta.getId(),
          receta.getIdPaciente(),
          receta.getDocNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar Receta - ERROR - Database insertion error (ID=%s ID_Paciente=%s Doc_Nombre=%s Fecha=%s)",
          receta.getId(),
          receta.getIdPaciente(),
          receta.getDocNombre(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Receta> getAllRecetas() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = "";
    return getRecetas(parameters, query);
  }

  public Collection<Receta> getSingleRecetaById(Uuid id) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(id));
    query = "ID = ?";

    return getRecetas(parameters, query);
  }

  private Collection<Receta> getRecetas(Vector<String> parameters, String where) {
    String query = "SELECT * FROM RECETAS";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getRecetas(parameters, query);
  }
}
