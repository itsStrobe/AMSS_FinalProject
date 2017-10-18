package amss.app.Elementos;

import amss.app.Elementos.Medicina;
import amss.app.util.Uuid;
import amss.app.util.Time;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class RecetaMedicina extends Medicina{
  private Uuid recetaId;
  private boolean bMorning;
  private boolean bEvening;
  private boolean bNight;
  private Time tEndDate;

  public RecetaMedicina(Uuid medicinaId, Uuid recetaId, boolean bMorning, boolean bEvening, boolean bNight, Time tEndDate) {
    this.id = medicinaId;
    this.recetaId = recetaId;
    this.bMorning = bMorning;
    this.bEvening = bEvening;
    this.bNight = bNight;
    this.tEndDate = tEndDate;
  }

  public RecetaMedicina(Medicina medicina, Receta recetaMedicina, boolean bMorning, boolean bEvening, boolean bNight, Time tEndDate) {
    this.id = medicina.id;
    this.recetaId = recetaMedicina.getId();
    this.strNombre = medicina.strNombre;
    this.bMorning = bMorning;
    this.bEvening = bEvening;
    this.bNight = bNight;
    this.tEndDate = tEndDate;
  }

  public Uuid getRecetaId() {
    return this.recetaId;
  }

  public boolean getMorning() {
    return this.bMorning;
  }

  public boolean getEvening() {
    return this.bEvening;
  }

  public boolean getNight() {
    return this.bNight;
  }

  public Time getEndDate() {
    return this.tEndDate;
  }
}
