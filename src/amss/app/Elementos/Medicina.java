package amss.app.Elementos;

import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class Medicina {
  protected Uuid id;
  protected String strNombre;

  public Medicina() {
    this.id = Uuid.NULL;
    this.strNombre = null;
  }

  public Medicina(Uuid id, String strNombre){
    this.id = id;
    this.strNombre = strNombre;
  }
}
