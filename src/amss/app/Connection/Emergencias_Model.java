package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Emergencias;
import amss.app.Individuos.Staff;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class Emergencias_Model extends Model{
  private Emergencias emergencias;

  // FUNCIONES SET
  public void add(Emergencias emergencias)
  {
    this.emergencias = emergencias;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(emergencias.getId()));
      parameters.add(emergencias.getTitulo());
      parameters.add(emergencias.getContenido());
      parameters.add(SQLFormatter.sqlID(emergencias.getInquilinoId()));
      parameters.add(SQLFormatter.sqlID(emergencias.getStaffId()));
      parameters.add(emergencias.getHospital());
      parameters.add(SQLFormatter.sqlTime(emergencias.getFecha()));
      query = "INSERT INTO EMERGENCIAS (ID,TITULO,CONTENIDO,INQUILINO,STAFF,HOSPITAL,FECHA) " +
          "VALUES ( ?, ?, ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nueva EMERGENCIA (EMERGENCIA_ID=%s EMERGENCIA_TITULO=%s EMERGENCIA_FECHA=%s Fecha=%s)",
          emergencias.getId(),
          emergencias.getTitulo(),
          emergencias.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva EMERGENCIA - ERROR - Database insertion error (EMERGENCIA_ID=%s EMERGENCIA_TITULO=%s EMERGENCIA_FECHA=%s Fecha=%s)",
          emergencias.getId(),
          emergencias.getTitulo(),
          emergencias.getFecha(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Emergencias emergencias) {
    this.emergencias = emergencias;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(emergencias.getTitulo());
      parameters.add(emergencias.getContenido());
      parameters.add(SQLFormatter.sqlID(emergencias.getInquilinoId()));
      parameters.add(SQLFormatter.sqlID(emergencias.getStaffId()));
      parameters.add(emergencias.getHospital());
      parameters.add(SQLFormatter.sqlTime(emergencias.getFecha()));
      parameters.add(SQLFormatter.sqlID(emergencias.getId()));
      query = "UPDATE EMERGENCIAS set" +
          " TITULO = ?," +
          " CONTENIDO = ?," +
          " INQUILINO = ?" +
          " STAFF = ?" +
          " HOSPITAL = ?" +
          " FECHA = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar EMERGENCIA (EMERGENCIA_ID=%s EMERGENCIA_TITULO=%s EMERGENCIA_FECHA=%s Fecha=%s)",
          emergencias.getId(),
          emergencias.getTitulo(),
          emergencias.getFecha(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar EMERGENCIA - ERROR - Database insertion error (EMERGENCIA_ID=%s EMERGENCIA_TITULO=%s EMERGENCIA_FECHA=%s Fecha=%s)",
          emergencias.getId(),
          emergencias.getTitulo(),
          emergencias.getFecha(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Emergencias> getAllEmergencias() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getEmergencias(parameters, query);
  }

  public Collection<Emergencias> getSingleEmergenciaById(Uuid emergenciaId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(emergenciaId));
    query = "ID = ?";

    return getEmergencias(parameters, query);
  }

  public Collection<Emergencias> getSingleEmergenciaByTitulo(Emergencias emergencias) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(emergencias.getTitulo());
    query = "TITULO = ?";

    return getEmergencias(parameters, query);
  }

  public Collection<Emergencias> getAllEmergenciasOfInquilino(Uuid inquilinoId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilinoId));
    query = " INQUILINO = ?";

    return getEmergencias(parameters, query);
  }

  private Collection<Emergencias> getEmergencias(Vector<String> parameters, String where) {
    String query = "SELECT * FROM EMERGENCIAS";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getEmergencias(parameters, query);
  }
}
