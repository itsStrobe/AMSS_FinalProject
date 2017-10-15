package amss.app.Elementos;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class PacienteMedicina extends Medicina {
  int iCantidad;

  public PacienteMedicina(Medicina medicina, int iCantidad) {
    this.id = medicina.id;
    this.strNombre = medicina.strNombre;
    this.iCantidad = iCantidad;
  }
}
