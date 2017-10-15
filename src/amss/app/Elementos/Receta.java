package amss.app.Elementos;

import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Vector;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class Receta {
  private Uuid id;
  private String strDocNombre;
  private Uuid idPaciente;
  private Time tFechaInicio;
  private Vector<RecetaMedicina> medicinas;

  public Receta(Uuid id, String strDocNombre, Uuid idPaciente, Time tFechaInicio) {
    this.id = id;
    this.strDocNombre = strDocNombre;
    this.idPaciente = idPaciente;
    this.tFechaInicio = tFechaInicio;
  }


}
