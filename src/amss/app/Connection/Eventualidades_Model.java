package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Eventualidades;
import amss.app.Elementos.Recomendaciones;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by strobe on 8/11/17.
 */
public class Eventualidades_Model extends Model {
  private Eventualidades eventualidad;

  // FUNCIONES SET
  public void add(Eventualidades eventualidades)
  {
    this.eventualidad = eventualidades;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(eventualidad.getId()));
      parameters.add(eventualidad.getTitulo());
      parameters.add(eventualidad.getContenido());
      parameters.add(SQLFormatter.sqlID(eventualidad.getStaffId()));
      parameters.add(SQLFormatter.sqlTime(eventualidad.getFecha()));
      query = "INSERT INTO EVENTUALIDADES (ID,TITULO,CONTENIDO,STAFF,FECHA) " +
          "VALUES ( ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nueva EVENTUALIDAD (EVENTUALIDAD_ID=%s EVENTUALIDAD_TITULO=%s EVENTUALIDAD_FECHA=%s Fecha=%s)",
          eventualidad.getId(),
          eventualidad.getTitulo(),
          eventualidad.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva EVENTUALIDAD - ERROR - Database insertion error (EVENTUALIDAD_ID=%s EVENTUALIDAD_TITULO=%s EVENTUALIDAD_FECHA=%s Fecha=%s)",
          eventualidad.getId(),
          eventualidad.getTitulo(),
          eventualidad.getFecha(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Eventualidades eventualidad) {
    this.eventualidad = eventualidad;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(eventualidad.getTitulo());
      parameters.add(eventualidad.getContenido());
      parameters.add(SQLFormatter.sqlID(eventualidad.getStaffId()));
      parameters.add(SQLFormatter.sqlTime(eventualidad.getFecha()));
      parameters.add(SQLFormatter.sqlID(eventualidad.getId()));
      query = "UPDATE EVENTUALIDADES set" +
          " TITULO = ?," +
          " CONTENIDO = ?," +
          " STAFF = ?" +
          " FECHA = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar EVENTUALIDAD (EVENTUALIDAD_ID=%s EVENTUALIDAD_TITULO=%s EVENTUALIDAD_FECHA=%s Fecha=%s)",
          eventualidad.getId(),
          eventualidad.getTitulo(),
          eventualidad.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar RECOMENDACION - ERROR - Database insertion error (EVENTUALIDAD_ID=%s EVENTUALIDAD_TITULO=%s EVENTUALIDAD_FECHA=%s Fecha=%s)",
          eventualidad.getId(),
          eventualidad.getTitulo(),
          eventualidad.getFecha(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Eventualidades> getAllEventualidades() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getEventualidades(parameters, query);
  }

  public Collection<Eventualidades> getSingleEventualidadById(Uuid eventualidadId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(eventualidadId));
    query = "ID = ?";

    return getEventualidades(parameters, query);
  }

  public Collection<Eventualidades> getSingleEventualidadByTitulo(Eventualidades eventualidad) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(eventualidad.getTitulo());
    query = "TITULO = ?";

    return getEventualidades(parameters, query);
  }

  private Collection<Eventualidades> getEventualidades(Vector<String> parameters, String where) {
    String query = "SELECT * FROM EVENTUALIDADES";
    if (where != null)
      query += " where " + where;
    query += " ORDER BY FECHA Desc";
    query += ";";
    return dbConnection.getEventualidades(parameters, query);
  }
}
