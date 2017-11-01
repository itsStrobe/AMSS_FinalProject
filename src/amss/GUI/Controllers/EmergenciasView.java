package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Elementos.Emergencias;
import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class EmergenciasView {
  Uuid id;
  String titulo;
  String inquilino;
  Time fecha;
  Inquilino_Model inquilino_model = new Inquilino_Model();

  EmergenciasView(Emergencias emergencias) {
    this.id = emergencias.getId();
    this.titulo = emergencias.getTitulo();
    this.inquilino = inquilino_model.getSingleInquilinoById(emergencias.getInquilinoId()).iterator().next().getNombre();
    this.fecha = emergencias.getFecha();
  }

  public Uuid getId() {
    return this.id;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getInquilino() {
    return this.inquilino;
  }

  public Time getFecha() {
    return this.fecha;
  }
}
