package amss.app.Individuos;

import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class Staff {
  private Uuid id;
  private String strNombre;
  private String strTelefono;
  private String strTurno;
  private String strPosicion;
  private Time tFechaNacimiento;

  public Staff(Uuid id, String strNombre, String strTelefono, String strTurno, String strPosicion, Time tFechaNacimiento) {
    this.id = id;
    this.strNombre = strNombre;
    this.strTelefono = strTelefono;
    this.strTurno = strTurno;
    this.strPosicion = strPosicion;
    this.tFechaNacimiento = tFechaNacimiento;
  }

  public Uuid getId() {
    return this.id;
  }

  public String getNombre() {
    return this.strNombre;
  }

  public String getTelefono() {
    return this.strTelefono;
  }

  public String getTurno() {
    return this.strTurno;
  }

  public String getPosicion() {
    return this.strPosicion;
  }

  public Time getFechaNacimiento() {
    return this.tFechaNacimiento;
  }
}
