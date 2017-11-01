package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Staff;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class Staff_Model extends Model{
  private Staff staff;

  // FUNCIONES SET
  public void add(Staff staff)
  {
    this.staff = staff;
    add();
  }

  @Override
  protected void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(staff.getId()));
      parameters.add(staff.getNombre());
      parameters.add(staff.getTelefono());
      parameters.add(staff.getTurno());
      parameters.add(staff.getPosicion());
      parameters.add(SQLFormatter.sqlTime(staff.getFechaNacimiento()));
      query = "INSERT INTO STAFF (ID,NOMBRE,TELEFONO,TURNO,POSICION,FECHANACIMIENTO) " +
          "VALUES ( ?, ?, ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nuevo STAFF (STAFF_ID=%s STAFF_NOMBRE=%s STAFF_FECHANACIMIENTO=%s Fecha=%s)",
          staff.getId(),
          staff.getNombre(),
          staff.getFechaNacimiento(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nueva STAFF - ERROR - Database insertion error (STAFF_ID=%s STAFF_NOMBRE=%s STAFF_FECHANACIMIENTO=%s Fecha=%s)",
          staff.getId(),
          staff.getNombre(),
          staff.getFechaNacimiento(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Staff staff) {
    this.staff = staff;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(staff.getNombre());
      parameters.add(staff.getTelefono());
      parameters.add(staff.getTurno());
      parameters.add(staff.getPosicion());
      parameters.add(SQLFormatter.sqlTime(staff.getFechaNacimiento()));
      parameters.add(SQLFormatter.sqlID(staff.getId()));
      query = "UPDATE STAFF set" +
          " NOMBRE = ?," +
          " TELEFONO = ?," +
          " TURNO = ?" +
          " POSICION = ?" +
          " FECHANACIMIENTO = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar STAFF (STAFF_ID=%s STAFF_NOMBRE=%s STAFF_FECHANACIMIENTO=%s Fecha=%s)",
          staff.getId(),
          staff.getNombre(),
          staff.getFechaNacimiento(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar STAFF - ERROR - Database insertion error (STAFF_ID=%s STAFF_NOMBRE=%s STAFF_FECHANACIMIENTO=%s Fecha=%s)",
          staff.getId(),
          staff.getNombre(),
          staff.getFechaNacimiento(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Staff> getAllStaff() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = null;
    return getStaff(parameters, query);
  }

  public Collection<Staff> getSingleStaffById(Uuid staffId) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(staffId));
    query = "ID = ?";

    return getStaff(parameters, query);
  }

  public Collection<Staff> getSingleStaffByName(Staff staff) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(staff.getNombre());
    query = "NOMBRE = ?";

    return getStaff(parameters, query);
  }

  public Collection<Staff> getSingleStaffByName(String staffName) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(staffName);
    query = "NOMBRE = ?";

    return getStaff(parameters, query);
  }

  private Collection<Staff> getStaff(Vector<String> parameters, String where) {
    String query = "SELECT * FROM Staff";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getStaff(parameters, query);
  }
}
