package amss.app.Individuos;

import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import amss.app.Individuos.Familiar;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by Jose Zavala on 14/10/17.
 */
public final class Inquilino {
  private Uuid id;
  private String strNombre;
  private String strDireccion;
  private Time tFechaNacimiento;
  private int iEdad;
  // El familiar responsable se guarda como una ID que apunta hacia el tuplo en la
  // base de datos que contiene su info.
  private Uuid idResponsable;
  // FOTOGRAFIA
  private char cEstatus;
  private String strCuarto;
  private Vector<Familiar> familiares;

  public Inquilino(Uuid id, String strNombre, String strDireccion, int iEdad, Uuid idResponsable, char cEstatus, String strCuarto) {
    // El ID debe ser creada en el controlador. Preguntar a Jose por la funcion para generar Uuids.
    this.id = id;
    this.strNombre = strNombre;
    this.strDireccion = strDireccion;
    this.iEdad = iEdad;
    this.idResponsable = idResponsable;
    this.cEstatus = cEstatus;
    this.strCuarto = strCuarto;
  }
}
