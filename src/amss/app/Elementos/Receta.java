package amss.app.Elementos;

import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class Receta {
  private Uuid id;
  private String strDocNombre;
  private Uuid idPaciente;
  private Time tFechaInicio;
  private Collection<RecetaMedicina> medicinas;

  public Receta(Uuid id, String strDocNombre, Uuid idPaciente, Time tFechaInicio) {
    this.id = id;
    this.strDocNombre = strDocNombre;
    this.idPaciente = idPaciente;
    this.tFechaInicio = tFechaInicio;
  }

  public void setMedicinas(Collection<RecetaMedicina> medicinas) {
    this.medicinas = medicinas;
  }

  public Uuid getId() {
    return this.id;
  }

  public String getDocNombre() {
    return this.strDocNombre;
  }

  public Uuid getIdPaciente() {
    return this.idPaciente;
  }

  public Time getFechaInicio() {
    return this.tFechaInicio;
  }

  public Collection<RecetaMedicina> getMedicinas() {
    return this.medicinas;
  }
}
