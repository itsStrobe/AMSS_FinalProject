package amss.app.Individuos;

import amss.app.Elementos.Emergencias;
import amss.app.Elementos.Receta;
import amss.app.Elementos.Recomendaciones;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import amss.app.Individuos.Familiar;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 14/10/17.
 */
public final class Inquilino {
  private Uuid id;
  private String strNombre;
  private String strDireccion;
  private Time tFechaNacimiento;
  // El familiar responsable se guarda como una ID que apunta hacia el tuplo en la
  // base de datos que contiene su info.
  private Uuid idResponsable;
  // FOTOGRAFIA
  private char cEstatus;
  private String strCuarto;
  private String strPadecimientos;
  private Collection<Familiar> familiares;
  private Collection<Receta> recetas;
  private Collection<Recomendaciones> recomendaciones;
  private Collection<Emergencias> emergencias;

  public Inquilino(Uuid id, String strNombre, String strDireccion, Time tFechaNacimiento, Uuid idResponsable, char cEstatus, String strCuarto, String strPadecimientos) {
    this.id = id;
    this.strNombre = strNombre;
    this.strDireccion = strDireccion;
    this.tFechaNacimiento = tFechaNacimiento;
    this.idResponsable = idResponsable;
    this.cEstatus = cEstatus;
    this.strCuarto = strCuarto;
    this.strPadecimientos = strPadecimientos;
  }

  public Inquilino(Uuid id, String strNombre, String strDireccion, Time tFechaNacimiento, String strCuarto, String strPadecimientos){

    this.id = id;
    this.strNombre = strNombre;
    this.strDireccion = strDireccion;
    this.tFechaNacimiento = tFechaNacimiento;
    this.idResponsable = Uuid.NULL;
    this.cEstatus = 'a';
    this.strCuarto = strCuarto;
    this.strPadecimientos = strPadecimientos;
  }

  public Inquilino(Inquilino inquilino) {
    this.id = inquilino.id;
    this.strNombre = inquilino.strNombre;
    this.strDireccion = inquilino.strDireccion;
    this.tFechaNacimiento = inquilino.tFechaNacimiento;
    this.idResponsable = Uuid.NULL;
    this.cEstatus = inquilino.cEstatus;
    this.strCuarto = inquilino.strCuarto;
    this.strPadecimientos = inquilino.strPadecimientos;
  }

  public void setResponsable(Familiar responsable) {
    this.idResponsable = responsable.getId();
  }

  public void setFamiliares(Collection<Familiar> familiares) {
    this.familiares = familiares;
  }

  public void setRecetas(Collection<Receta> recetas) {
    this.recetas = recetas;
  }

  public void setRecomendaciones(Collection<Recomendaciones> recomendaciones) {
    this.recomendaciones = recomendaciones;
  }

  public void setEmergencias(Collection<Emergencias> emergencias) {
    this.emergencias = emergencias;
  }

  public void addReceta(Receta receta) {
    this.recetas.add(receta);
  }

  public Uuid getId()
  {
    return this.id;
  }

  public String getNombre()
  {
    return this.strNombre;
  }

  public String getDireccion()
  {
    return this.strDireccion;
  }

  public Time getFechaNacimiento()
  {
    return this.tFechaNacimiento;
  }

  public Uuid getIdResponsable()
  {
    return this.idResponsable;
  }

  public char getEstatus()
  {
    return this.cEstatus;
  }

  public String getCuarto()
  {
    return this.strCuarto;
  }

  public String getPadecimientos() {
    return this.strPadecimientos;
  }

  public Collection<Familiar> getFamiliares()
  {
    return this.familiares;
  }

  public Collection<Receta> getRecetas() {
    return this.recetas;
  }

  public Collection<Recomendaciones> getRecomendaciones() {
    return this.recomendaciones;
  }

  public Collection<Emergencias> getEmergencias() {
    return this.emergencias;
  }
}
