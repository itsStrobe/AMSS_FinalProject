package amss.app.Individuos;

import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 14/10/17.
 */
public class Familiar {
  private Uuid id;
  private Uuid inquilinoId;
  private String strNombre;
  private String strTelefono;
  private String strDireccion;

  public Familiar(Uuid id, Uuid inquilinoId, String strNombre, String strTelefono, String strDireccion) {
    this.id = id;
    this.inquilinoId = inquilinoId;
    this.strNombre = strNombre;
    this.strTelefono = strTelefono;
    this.strDireccion = strDireccion;
  }

  public Uuid getId() {
    return this.id;
  }

  public Uuid getInquilinoId() {
    return this.inquilinoId;
  }

  public String getNombre() {
    return this.strNombre;
  }

  public String getTelefono() {
    return this.strTelefono;
  }

  public String getDireccion() {
    return this.strDireccion;
  }
}
