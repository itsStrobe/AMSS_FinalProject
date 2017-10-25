package amss.app.Elementos;

import amss.app.Elementos.Medicina;
import amss.app.util.Uuid;
import amss.app.util.Time;
import com.sun.org.apache.regexp.internal.RE;
import javafx.util.StringConverter;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class RecetaMedicina extends Medicina{
  private Uuid recetaId;
  private int bMorning;
  private int bEvening;
  private int bNight;
  private Time tEndDate;

  public RecetaMedicina(Uuid medicinaId, Uuid recetaId, int bMorning, int bEvening, int bNight, Time tEndDate) {
    this.id = medicinaId;
    this.recetaId = recetaId;
    this.bMorning = bMorning;
    this.bEvening = bEvening;
    this.bNight = bNight;
    this.tEndDate = tEndDate;
  }

  public RecetaMedicina(Medicina medicina, Receta recetaMedicina, int bMorning, int bEvening, int bNight, Time tEndDate) {
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

  public String getNombre() {
    return this.strNombre;
  }

  public int getMorning() {
    return this.bMorning;
  }

  public int getEvening() {
    return this.bEvening;
  }

  public int getNight() {
    return this.bNight;
  }

  public Time getEndDate() {
    return this.tEndDate;
  }
}
