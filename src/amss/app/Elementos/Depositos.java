package amss.app.Elementos;

import amss.app.Connection.Familiar_Model;
import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.Medicina_Model;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.Time;
import amss.app.util.Uuid;

/**
 * Created by strobe on 15/11/17.
 */
public class Depositos {
  private Uuid id;
  private Uuid inquilino;
  private Uuid familiar;
  private Uuid medicina;
  private int cantidad;
  private Time fecha;

  private Inquilino_Model inquilino_model = new Inquilino_Model();
  private Familiar_Model familiar_model = new Familiar_Model();
  private Medicina_Model medicina_model = new Medicina_Model();

  public Depositos(Uuid id, Inquilino inquilino, Familiar familiar, Medicina medicina, int cantidad, Time fecha) {
    this.id = id;
    this.inquilino = inquilino.getId();
    this.familiar = familiar.getId();
    this.medicina = medicina.getId();
    this.cantidad = cantidad;
    this.fecha = fecha;
  }

  public Depositos(Uuid id, Uuid inquilino, Uuid familiar, Uuid medicina, int cantidad, Time fecha) {
    this.id = id;
    this.inquilino = inquilino;
    this.familiar = familiar;
    this.medicina = medicina;
    this.cantidad = cantidad;
    this.fecha = fecha;
  }

  public Uuid getId() {
    return this.id;
  }

  public Uuid getInquilino() {
    return this.inquilino;
  }

  public Uuid getFamiliar() {
    return this.familiar;
  }

  public Uuid getMedicina() {
    return this.medicina;
  }

  public int getCantidad() {
    return this.cantidad;
  }

  public Time getFecha() {
    return this.fecha;
  }
}
