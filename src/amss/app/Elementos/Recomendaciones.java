package amss.app.Elementos;

import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class Recomendaciones {
  private Uuid id;
  private String strTitulo;
  private String strContenido;
  private Uuid uuidInquilino;
  private Uuid uuidStaff;
  private Time tFecha;

  public Recomendaciones(Uuid id, String strTitulo, String strContenido, Uuid uuidInquilino, Uuid uuidStaff, Time tFecha) {
    this.id = id;
    this.strTitulo = strTitulo;
    this.strContenido = strContenido;
    this.uuidInquilino = uuidInquilino;
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

  public Uuid getInquilinoId() {
    return this.uuidInquilino;
  }

  public Uuid getStaffId() {
    return this.uuidStaff;
  }

  public Time getFecha() {
    return this.tFecha;
  }
}
