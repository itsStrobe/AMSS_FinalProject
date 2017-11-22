package amss.GUI.Controllers;

import amss.app.Connection.Medicina_Model;
import amss.app.Connection.RecetaMedicina_Model;
import amss.app.Connection.Receta_Model;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 25/10/17.
 */
public class MedicinaView {
  Uuid medID;
  String strNombre;
  int iCantidad;
  Medicina_Model medicina_model = new Medicina_Model();
  Receta_Model receta_model = new Receta_Model();
  RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();

  MedicinaView(PacienteMedicina pacienteMedicina) {
    this.medID = pacienteMedicina.getId();
    this.strNombre = medicina_model.getSingleMedicinaByID(pacienteMedicina.getId()).iterator().next().getNombre();
    this.iCantidad = pacienteMedicina.getCantidad() - calcularPastillas(pacienteMedicina.getPacienteId());
  }

  MedicinaView(PacienteMedicina pacienteMedicina, Time selectedFecha) {
    this.medID = pacienteMedicina.getId();
    this.strNombre = medicina_model.getSingleMedicinaByID(pacienteMedicina.getId()).iterator().next().getNombre();
    this.iCantidad = pacienteMedicina.getCantidad() - calcularPastillas(pacienteMedicina.getPacienteId(), selectedFecha);
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

  private int calcularPastillas(Uuid inquilino) {
    long consumidas = 0;

    for (Receta receta : receta_model.getAllRecetasOfPaciente(inquilino)) {
      for (RecetaMedicina recetaMedicina : recetaMedicina_model.getAllMedicinasOfReceta(receta.getId())) {
        if(recetaMedicina.getId().equals(medID)) {
          long perDay = recetaMedicina.getMorning() + recetaMedicina.getEvening() + recetaMedicina.getNight();

          if (recetaMedicina.getEndDate().inMs() > Time.now().inMs()) {
            consumidas += perDay * Time.getDiferenceInDays(Time.now(), receta.getFechaInicio());
          }
          else {
            consumidas += perDay * Time.getDiferenceInDays(recetaMedicina.getEndDate(), receta.getFechaInicio());
          }
        }
      }
    }

    return (int) consumidas;
  }

  private int calcularPastillas(Uuid inquilino, Time selectedFecha) {
    long consumidas = 0;

    for (Receta receta : receta_model.getAllRecetasOfPaciente(inquilino)) {
      for (RecetaMedicina recetaMedicina : recetaMedicina_model.getAllMedicinasOfReceta(receta.getId())) {
        if(recetaMedicina.getId().equals(medID)) {
          long perDay = recetaMedicina.getMorning() + recetaMedicina.getEvening() + recetaMedicina.getNight();

          if (recetaMedicina.getEndDate().inMs() > selectedFecha.inMs()) {
            consumidas += perDay * Time.getDiferenceInDays(selectedFecha, receta.getFechaInicio());
          }
          else {
            consumidas += perDay * Time.getDiferenceInDays(recetaMedicina.getEndDate(), receta.getFechaInicio());
          }
        }
      }
    }

    return (int) consumidas;
  }
}
