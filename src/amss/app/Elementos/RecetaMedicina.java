package amss.app.Elementos;

import amss.app.Elementos.Medicina;
import amss.app.util.Uuid;
import amss.app.util.Time;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Jose Zavala on 15/10/17.
 */
public class RecetaMedicina extends Medicina{
  private boolean bMorning;
  private boolean bEvening;
  private boolean bNight;
  private Time tEndDate;

  public RecetaMedicina(boolean bMorning, boolean bEvening, boolean bNight, Time tEndDate) {
    this.bMorning = bMorning;
    this.bEvening = bEvening;
    this.bNight = bNight;
    this.tEndDate = tEndDate;
  }

  public RecetaMedicina(Medicina medicina, RecetaMedicina recetaMedicina) {
    this.id = medicina.id;
    this.strNombre = medicina.strNombre;
    this.bMorning = recetaMedicina.bMorning;
    this.bEvening = recetaMedicina.bEvening;
    this.bNight = recetaMedicina.bNight;
    this.tEndDate = recetaMedicina.tEndDate;
  }
}
