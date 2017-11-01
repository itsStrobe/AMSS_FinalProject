package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Receta;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 18/10/17.
 */
public class Familiar_Model extends Model{
  private Familiar familiar;

  // FUNCIONES SET
  public void add(Familiar familiar)
  {
    this.familiar = familiar;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(familiar.getId()));
      parameters.add(SQLFormatter.sqlID(familiar.getInquilinoId()));
      parameters.add(familiar.getNombre());
      parameters.add(familiar.getTelefono());
      parameters.add(familiar.getDireccion());
      System.out.println("En BD, INQUILINOID: " + familiar.getInquilinoId());
      query = "INSERT INTO FAMILIARES (ID,INQUILINOID,NOMBRE,TELEFONO,DIRECCION) " +
          "VALUES ( ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nuevo Familiar (ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nuevo Familiar - ERROR - Database insertion error ((ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Familiar familiar) {
    this.familiar = familiar;
    update();
  }

  @Override
  protected void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(familiar.getInquilinoId()));
      parameters.add(familiar.getNombre());
      parameters.add(familiar.getTelefono());
      parameters.add(familiar.getDireccion());
      parameters.add(SQLFormatter.sqlID(familiar.getId()));
      query = "UPDATE FAMILIARES set" +
          " INQUILINOID = ?," +
          " NOMBRE = ?," +
          " TELEFONO = ?," +
          " DIRECCION = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar Familiar (ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar Familiar - ERROR - Database insertion error (ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Familiar> getAllFamiliares() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getFamiliares(parameters, query);
  }

  public Collection<Familiar> getSingleFamiliarById(Uuid id) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(id));
    query = "ID = ?";

    return getFamiliares(parameters, query);
  }

  public Collection<Familiar> getFamiliaresOfInquilino(Inquilino inquilino) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilino.getId()));
    query = "INQUILINOID = ?";

    return getFamiliares(parameters, query);
  }

  public Collection<Familiar> getFamiliaresOfInquilinoByName(Inquilino inquilino, String name) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(inquilino.getId()));
    parameters.add(name);
    query = "INQUILINOID = ? AND NOMBRE = ?";

    return getFamiliares(parameters, query);
  }

  private Collection<Familiar> getFamiliares(Vector<String> parameters, String where) {
    String query = "SELECT * FROM FAMILIARES";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getFamiliares(parameters, query);
  }
}
