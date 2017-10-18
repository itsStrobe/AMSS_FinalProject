package amss.app.Individuos;

import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 14/10/17.
 */
public class Familiar {
  private Uuid id;
  private String strNombre;
  private String strTelefono;
  private String strDireccion;

  public Familiar(Uuid id, String strNombre, String strTelefono, String strDireccion) {
    this.id = id;
    this.strNombre = strNombre;
    this.strTelefono = strTelefono;
    this.strDireccion = strDireccion;
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

  public String getDireccion() {
    return this.strDireccion;
  }
}
