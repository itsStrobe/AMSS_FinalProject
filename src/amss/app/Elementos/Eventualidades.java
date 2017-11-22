package amss.app.Elementos;

import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 8/11/17.
 */
public class Eventualidades {
  private Uuid id;
  private String strTitulo;
  private String strContenido;
  private Uuid uuidStaff;
  private Time tFecha;

  public Eventualidades(Uuid id, String strTitulo, String strContenido, Uuid uuidStaff, Time tFecha) {
    this.id = id;
    this.strTitulo = strTitulo;
    this.strContenido = strContenido;
    this.uuidStaff = uuidStaff;
    this.tFecha = tFecha;
  }

  public Uuid getId() {
    return this.id;
  }

  public String getTitulo() {
    return this.strTitulo;
  }

  public String getContenido() {
    return this.strContenido;
  }

  public Uuid getStaffId() {
    return this.uuidStaff;
  }

  public Time getFecha() {
    return this.tFecha;
  }
}
