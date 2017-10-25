package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import sun.security.krb5.internal.PAData;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 18/10/17.
 */
public class PacienteMedicina_Model extends Model {
  private PacienteMedicina pacienteMedicina;

  // FUNCIONES SET
  public void add(PacienteMedicina pacienteMedicina)
  {
    this.pacienteMedicina = pacienteMedicina;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(pacienteMedicina.getId(), pacienteMedicina.getPacienteId()));
      parameters.add(SQLFormatter.sqlID(pacienteMedicina.getPacienteId()));
      parameters.add(SQLFormatter.sqlID(pacienteMedicina.getId()));
      parameters.add(SQLFormatter.sqlInt(pacienteMedicina.getCantidad()));
      query = "INSERT INTO MEDICINA_PACIENTE (ID,PACIENTE,MEDICINA,CANTIDAD) " +
          "VALUES ( ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nuevo PacienteMedicina (MEDICINA_ID=%s PACIENTE_ID=%s Cantidad=%s Fecha=%s)",
          pacienteMedicina.getId(),
          pacienteMedicina.getPacienteId(),
          pacienteMedicina.getCantidad(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nuevo PacienteMedicina - ERROR - Database insertion error (MEDICINA_ID=%s PACIENTE_ID=%s Cantidad=%s Fecha=%s)",
          pacienteMedicina.getId(),
          pacienteMedicina.getPacienteId(),
          pacienteMedicina.getCantidad(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(PacienteMedicina pacienteMedicina) {
    this.pacienteMedicina = pacienteMedicina;
    update();
  }

  @Override
  protected void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(pacienteMedicina.getPacienteId()));
      parameters.add(SQLFormatter.sqlID(pacienteMedicina.getId()));
      parameters.add(SQLFormatter.sqlInt(pacienteMedicina.getCantidad()));
      parameters.add(SQLFormatter.sqlID(pacienteMedicina.getId(), pacienteMedicina.getPacienteId()));
      query = "UPDATE MEDICINA_PACIENTE set" +
          " PACIENTE = ?," +
          " MEDICINA = ?," +
          " CANTIDAD = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar MEDICINA_PACIENTE (MEDICINA_ID=%s PACIENTE_ID=%s Cantidad=%s Fecha=%s)",
          pacienteMedicina.getId(),
          pacienteMedicina.getPacienteId(),
          pacienteMedicina.getCantidad(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar MEDICINA_PACIENTE - ERROR - Database insertion error (MEDICINA_ID=%s PACIENTE_ID=%s Cantidad=%s Fecha=%s)",
          pacienteMedicina.getId(),
          pacienteMedicina.getPacienteId(),
          pacienteMedicina.getCantidad(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<PacienteMedicina> getAllMedicinasOfPaciente() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getMedicinasOfPaciente(parameters, query);
  }

  public Collection<PacienteMedicina> getAllMedicinasOfPaciente(Uuid inquilinoId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilinoId));
    query = "PACIENTE = ?";

    return getMedicinasOfPaciente(parameters, query);
  }

  public Collection<PacienteMedicina> getAllMedicinasOfPaciente(Inquilino inquilino) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilino.getId()));
    query = "PACIENTE = ?";

    return getMedicinasOfPaciente(parameters, query);
  }

  public Collection<PacienteMedicina> getSingleMedicinaOfPaciente(Uuid medicinaId, Uuid pacienteId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(medicinaId, pacienteId));
    query = "ID = ?";

    return getMedicinasOfPaciente(parameters, query);
  }

  private Collection<PacienteMedicina> getMedicinasOfPaciente(Vector<String> parameters, String where) {
    String query = "SELECT * FROM MEDICINA_PACIENTE";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getMedicinasOfPaciente(parameters, query);
  }
}
