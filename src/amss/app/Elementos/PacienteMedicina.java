package amss.app.Elementos;

import amss.app.Individuos.Inquilino;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class PacienteMedicina extends Medicina {
  private int iCantidad;
  private Uuid pacienteId;

  public PacienteMedicina(Uuid medicinaId, Uuid inquilinoId, int iCantidad) {
    this.id = medicinaId;
    this.pacienteId = inquilinoId;
    this.iCantidad = iCantidad;
  }

  public PacienteMedicina(Medicina medicina, Uuid inquilinoId, int iCantidad) {
    this.id = medicina.id;
    this.pacienteId = inquilinoId;
    this.strNombre = medicina.strNombre;
    this.iCantidad = iCantidad;
  }

  public int getCantidad() {
    return this.iCantidad;
  }

  public Uuid getPacienteId() {
    return this.pacienteId;
  }
}
