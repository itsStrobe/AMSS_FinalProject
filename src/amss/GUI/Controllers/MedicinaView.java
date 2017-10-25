package amss.GUI.Controllers;

import amss.app.Connection.Medicina_Model;
import amss.app.Elementos.PacienteMedicina;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 25/10/17.
 */
public class MedicinaView {
  Uuid medID;
  String strNombre;
  int iCantidad;
  Medicina_Model medicina_model = new Medicina_Model();

  MedicinaView(PacienteMedicina pacienteMedicina) {
    this.medID = pacienteMedicina.getId();
    this.strNombre = medicina_model.getSingleMedicinaByID(pacienteMedicina.getId()).iterator().next().getNombre();
    this.iCantidad = pacienteMedicina.getCantidad();
  }

  public Uuid getMedID() {
    return this.medID;
  }

  public String getNombre() {
    return this.strNombre;
  }

  public int getCantidad() {
    return this.iCantidad;
  }
}
