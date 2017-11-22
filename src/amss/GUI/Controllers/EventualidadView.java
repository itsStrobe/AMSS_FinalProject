package amss.GUI.Controllers;

import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Eventualidades;
import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by Jose Zavala on 8/11/17.
 */
public class EventualidadView {
  private Uuid evId;
  private String titulo;
  private String staff;
  private Time fecha;

  private final Staff_Model staff_model = new Staff_Model();

  EventualidadView(Eventualidades eventualidad) {
    this.evId = eventualidad.getId();
    this.titulo = eventualidad.getTitulo();
    this.staff = staff_model.getSingleStaffById(eventualidad.getStaffId()).iterator().next().getNombre();
    this.fecha = eventualidad.getFecha();
  }

  public Uuid getId() {
    return this.evId;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getStaff() {
    return this.staff;
  }

  public Time getFecha() {
    return this.fecha;
  }
}
