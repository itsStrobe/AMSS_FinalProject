package amss.GUI.Controllers;

import amss.app.Connection.Medicina_Model;
import amss.app.Connection.RecetaMedicina_Model;
import amss.app.Elementos.RecetaMedicina;
import amss.app.util.Uuid;
import javafx.util.StringConverter;

/**
 * Created by Jose Zavala on 25/10/17.
 */
public class PastilleroView {
  private Uuid medID;
  private String medNombre;
  private int pastMa;
  private int pastTa;
  private int pastNo;

  private final Medicina_Model medicina_model = new Medicina_Model();

  PastilleroView(RecetaMedicina recetaMedicina) {
    this.medID = recetaMedicina.getId();
    this.medNombre = medicina_model.getSingleMedicinaByID(medID).iterator().next().getNombre();
    this.pastMa = recetaMedicina.getMorning();
    this.pastTa = recetaMedicina.getEvening();
    this.pastNo = recetaMedicina.getNight();
  }

  public void update(int iMa, int iTa, int iNo) {
    this.pastMa += iMa;
    this.pastTa += iTa;
    this.pastNo += iNo;
  }

  public String getNombre() {
    return this.medNombre;
  }

  public int getManana() {
    return this.pastMa;
  }

  public int getTarde() {
    return this.pastTa;
  }

  public int getNoche() {
    return this.pastNo;
  }
}
