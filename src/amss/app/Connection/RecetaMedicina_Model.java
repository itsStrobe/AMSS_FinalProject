package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by strobe on 18/10/17.
 */
public class RecetaMedicina_Model extends Model {
  private RecetaMedicina recetaMedicina;

  // FUNCIONES SET
  public void add(RecetaMedicina recetaMedicina)
  {
    this.recetaMedicina = recetaMedicina;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(recetaMedicina.getId(), recetaMedicina.getRecetaId()));
      parameters.add(SQLFormatter.sqlID(recetaMedicina.getRecetaId()));
      parameters.add(SQLFormatter.sqlID(recetaMedicina.getId()));
      parameters.add(SQLFormatter.sqlInt((recetaMedicina.getMorning())));
      parameters.add(SQLFormatter.sqlInt(recetaMedicina.getEvening()));
      parameters.add(SQLFormatter.sqlInt(recetaMedicina.getNight()));
      parameters.add(SQLFormatter.sqlTime(recetaMedicina.getEndDate()));
      query = "INSERT INTO RECETA_MEDICINA (ID,RECETA,MEDICINA,MANANA,TARDE,NOCHE,FECHAFIN) " +
          "VALUES ( ?, ?, ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nueva RECETA_MEDICINA (RECETA_ID=%s MEDICINA_ID=%s FechaFin=%s Fecha=%s)",
          recetaMedicina.getRecetaId(),
          recetaMedicina.getId(),
          recetaMedicina.getEndDate(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva RECETA_FAMILIAR - ERROR - Database insertion error (RECETA_ID=%s MEDICINA_ID=%s FechaFin=%s Fecha=%s)",
          recetaMedicina.getRecetaId(),
          recetaMedicina.getId(),
          recetaMedicina.getEndDate(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(RecetaMedicina recetaMedicina) {
    this.recetaMedicina = recetaMedicina;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(recetaMedicina.getRecetaId()));
      parameters.add(SQLFormatter.sqlID(recetaMedicina.getId()));
      parameters.add(SQLFormatter.sqlInt(recetaMedicina.getMorning()));
      parameters.add(SQLFormatter.sqlInt(recetaMedicina.getEvening()));
      parameters.add(SQLFormatter.sqlInt(recetaMedicina.getNight()));
      parameters.add(SQLFormatter.sqlTime(recetaMedicina.getEndDate()));
      parameters.add(SQLFormatter.sqlID(recetaMedicina.getId(), recetaMedicina.getRecetaId()));
      query = "UPDATE RECETA_MEDICINA set" +
          " RECETA = ?," +
          " MEDICINA = ?," +
          " MANANA = ?" +
          " TARDE = ?" +
          " NOCHE = ?" +
          " FECHAFIN = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar RECETA_MEDICINA (RECETA_ID=%s MEDICINA_ID=%s FechaFin=%s Fecha=%s)",
          recetaMedicina.getRecetaId(),
          recetaMedicina.getId(),
          recetaMedicina.getEndDate(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar RECETA_MEDICINA - ERROR - Database insertion error (RECETA_ID=%s MEDICINA_ID=%s FechaFin=%s Fecha=%s)",
          recetaMedicina.getRecetaId(),
          recetaMedicina.getId(),
          recetaMedicina.getEndDate(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<RecetaMedicina> getAllMedicinasOfReceta() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getMedicinasOfReceta(parameters, query);
  }

  public Collection<RecetaMedicina> getSingleMedicinaOfReceta(Uuid recetaId, Uuid medicinaId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(medicinaId, recetaId));
    query = "ID = ?";

    return getMedicinasOfReceta(parameters, query);
  }

  public Collection<RecetaMedicina> getAllMedicinasOfReceta(Uuid recetaId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(recetaId));
    query = "RECETA = ?";

    return getMedicinasOfReceta(parameters, query);
  }

  private Collection<RecetaMedicina> getMedicinasOfReceta(Vector<String> parameters, String where) {
    String query = "SELECT * FROM RECETA_MEDICINA";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getMedicinasOfReceta(parameters, query);
  }
}
