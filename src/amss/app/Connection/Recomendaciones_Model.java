package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Recomendaciones;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class Recomendaciones_Model extends Model{
  private Recomendaciones recomendaciones;

  // FUNCIONES SET
  public void add(Recomendaciones recomendaciones)
  {
    this.recomendaciones = recomendaciones;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(recomendaciones.getId()));
      parameters.add(recomendaciones.getTitulo());
      parameters.add(recomendaciones.getContenido());
      parameters.add(SQLFormatter.sqlID(recomendaciones.getInquilinoId()));
      parameters.add(SQLFormatter.sqlID(recomendaciones.getStaffId()));
      parameters.add(SQLFormatter.sqlTime(recomendaciones.getFecha()));
      query = "INSERT INTO RECOMENDACIONES (ID,TITULO,CONTENIDO,INQUILINO,STAFF,FECHA) " +
          "VALUES ( ?, ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nueva RECOMENDACION (RECOMENDACION_ID=%s RECOMENDACION_TITULO=%s RECOMENDACION_FECHA=%s Fecha=%s)",
          recomendaciones.getId(),
          recomendaciones.getTitulo(),
          recomendaciones.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva RECOMENDACION - ERROR - Database insertion error (RECOMENDACION_ID=%s RECOMENDACION_TITULO=%s RECOMENDACION_FECHA=%s Fecha=%s)",
          recomendaciones.getId(),
          recomendaciones.getTitulo(),
          recomendaciones.getFecha(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Recomendaciones recomendaciones) {
    this.recomendaciones = recomendaciones;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(recomendaciones.getTitulo());
      parameters.add(recomendaciones.getContenido());
      parameters.add(SQLFormatter.sqlID(recomendaciones.getInquilinoId()));
      parameters.add(SQLFormatter.sqlID(recomendaciones.getStaffId()));
      parameters.add(SQLFormatter.sqlTime(recomendaciones.getFecha()));
      parameters.add(SQLFormatter.sqlID(recomendaciones.getId()));
      query = "UPDATE RECOMENDACIONES set" +
          " TITULO = ?," +
          " CONTENIDO = ?," +
          " INQUILINO = ?" +
          " STAFF = ?" +
          " FECHA = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar RECOMENDACION (RECOMENDACION_ID=%s RECOMENDACION_TITULO=%s RECOMENDACION_FECHA=%s Fecha=%s)",
          recomendaciones.getId(),
          recomendaciones.getTitulo(),
          recomendaciones.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar RECOMENDACION - ERROR - Database insertion error (RECOMENDACION_ID=%s RECOMENDACION_TITULO=%s RECOMENDACION_FECHA=%s Fecha=%s)",
          recomendaciones.getId(),
          recomendaciones.getTitulo(),
          recomendaciones.getFecha(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Recomendaciones> getAllRecomendaciones() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getRecomendaciones(parameters, query);
  }

  public Collection<Recomendaciones> getSingleRecomendacionById(Uuid recomendacionId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(recomendacionId));
    query = "ID = ?";

    return getRecomendaciones(parameters, query);
  }

  public Collection<Recomendaciones> getSingleRecomendacionByTitulo(Recomendaciones recomendaciones) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(recomendaciones.getTitulo());
    query = "TITULO = ?";

    return getRecomendaciones(parameters, query);
  }

  public Collection<Recomendaciones> getAllRecomendacionesOfInquilino(Uuid inquilinoId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilinoId));
    query = " INQUILINO = ?";

    return getRecomendaciones(parameters, query);
  }

  private Collection<Recomendaciones> getRecomendaciones(Vector<String> parameters, String where) {
    String query = "SELECT * FROM RECOMENDACIONES";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getRecomendaciones(parameters, query);
  }
}
