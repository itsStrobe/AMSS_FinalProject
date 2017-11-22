package amss.app.util;

import amss.app.Connection.Medicina_Model;
import amss.app.Connection.Staff_Model;
import amss.app.Elementos.*;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.Individuos.Staff;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Jose Zavala on 14/11/17.
 */
public class PDF_Generator {
  private static String FILE = "Reportes/";
  private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
  private static Font nmlFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
  private static Font bluFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLUE);
  private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
  private static Font smlBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

  public static void generateInquilinoReport(Inquilino inquilino) {
    File f = new File("Reportes/");
    if(!f.exists() && !f.isDirectory()) {
      try{
        f.mkdir();
      }
      catch(SecurityException se){ }
    }

    try {
      Document document = new Document();
      String fileDir = inquilino.getNombre() + Time.now();
      fileDir = fileDir.replace(' ', '_');
      fileDir = fileDir.replace('.', '_');
      fileDir = fileDir.replace('á', 'a');
      fileDir = fileDir.replace('é', 'e');
      fileDir = fileDir.replace('í', 'i');
      fileDir = fileDir.replace('ó', 'o');
      fileDir = fileDir.replace('ú', 'u');
      fileDir = fileDir.replace('ñ', 'n');
      fileDir = fileDir.replace('Ñ', 'N');
      fileDir = fileDir.replace('ü', 'u');
      fileDir = fileDir.replace(':', '_');
      fileDir = FILE + fileDir + ".pdf";
      PdfWriter.getInstance(document, new FileOutputStream(fileDir));
      document.open();
      addMetaData(document, inquilino.getNombre());
      addInquilinoMainInfo(document, inquilino);
      addInquilinoFamiliares(document, inquilino);
      addInquilinoRecetas(document, inquilino);
      addInquilinoEmergencias(document, inquilino);
      addInquilinoRecomendaciones(document, inquilino);

      document.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void addMetaData(Document document, String inqName) {
    String titulo = "Reporte del Inquilino: " + inqName;
    String subject = "Reporte autogenerado de estadia en el asilo";
    String author = "Reporte Automatico";
    String creator = "Sistema de Administracion de Asilo";

    document.addTitle(titulo);
    document.addSubject(subject);
    document.addAuthor(author);
    document.addCreator(creator);
    document.addCreationDate();
  }

  private static void addInquilinoMainInfo(Document document, Inquilino inquilino) throws DocumentException {
    Paragraph preface = new Paragraph();
    addEmptyLine(preface, 1);

    preface.add(new Paragraph("Reporte del Inquilino: " + inquilino.getNombre(), catFont));
    addEmptyLine(preface, 1);

    preface.add(new Paragraph(" Reporte Generado a la Fecha de: " + Time.now(), smlBold));
    addEmptyLine(preface, 3);

    preface.add(new Paragraph("Informacion del Inquilino:", subFont));
    preface.add(new Paragraph("Nombre: \t" + inquilino.getNombre(), nmlFont));
    preface.add(new Paragraph("Direccion: \t" + inquilino.getDireccion(), nmlFont));
    preface.add(new Paragraph("Fecha de Nacimiento: \t" + inquilino.getFechaNacimiento(), nmlFont));
    preface.add(new Paragraph("Cuarto: \t" + inquilino.getCuarto(), nmlFont));
    preface.add(new Paragraph("Padecimientos: \t" + inquilino.getPadecimientos(), nmlFont));
    String estatus = "Estatus:";
    switch (inquilino.getEstatus()) {
      case 'a':
        preface.add(new Paragraph("\t\tACTIVO", redFont));
        break;
      case 'i':
        preface.add(new Paragraph("\t\tINACTIVO", redFont));
        break;
      case 'f':
        preface.add(new Paragraph("\t\tFALLECIDO", redFont));
        break;
    }
    addEmptyLine(preface, 5);

    document.add(preface);
    document.newPage();
  }

  private static void addInquilinoFamiliares (Document document, Inquilino inquilino) throws BadElementException, DocumentException{
    if(inquilino.getFamiliares().isEmpty()) return;

    Paragraph preface = new Paragraph();
    PdfPTable familiares = new PdfPTable(4);
    PdfPCell celda;
    addEmptyLine(preface, 1);

    preface.add(new Paragraph("Familiares del Inquilino:", subFont));
    addEmptyLine(preface, 1);
    document.add(preface);

    celda = new PdfPCell(new Phrase("NOMBRE"));
    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    familiares.addCell(celda);

    celda = new PdfPCell(new Phrase("TELEFONO"));
    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    familiares.addCell(celda);

    celda = new PdfPCell(new Phrase("DIRECCION"));
    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    familiares.addCell(celda);

    celda = new PdfPCell(new Phrase("RESPONSABLE"));
    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
    familiares.addCell(celda);

    for(Familiar familiar : inquilino.getFamiliares()) {
      familiares.addCell(familiar.getNombre());
      familiares.addCell(familiar.getTelefono());
      familiares.addCell(familiar.getDireccion());
      if(familiar.getId().equals(inquilino.getIdResponsable())) {
        familiares.addCell("SI");
      }
      else {
        familiares.addCell("NO");
      }
    }

    document.add(familiares);
    document.newPage();
  }

  private static void addInquilinoRecetas(Document document, Inquilino inquilino) throws DocumentException {
    if(inquilino.getRecetas().isEmpty()) return;

    Medicina_Model medicina_model = new Medicina_Model();

    Paragraph preface = new Paragraph();
    addEmptyLine(preface, 1);

    preface.add(new Paragraph("Recetas del Inquilino:", subFont));
    addEmptyLine(preface, 1);

    for(Receta receta : inquilino.getRecetas()) {
      preface.add(new Paragraph("Fecha: " + receta.getFechaInicio(), smlBold));
      preface.add(new Paragraph("Recetada por: " + receta.getDocNombre(), nmlFont));
      for(RecetaMedicina recetaMedicina : receta.getMedicinas()) {
        preface.add(new Paragraph("Medicamento: " + medicina_model.getSingleMedicinaByID(recetaMedicina.getId()).iterator().next().getNombre(), nmlFont));
        preface.add(new Paragraph("Manana: \t" + recetaMedicina.getMorning() + " \tTarde: \t" + recetaMedicina.getEvening() + " \tNoche: \t"+ recetaMedicina.getNight(), bluFont));
        preface.add(new Paragraph("Tomar hasta: " + recetaMedicina.getEndDate(), redFont));
      }
      preface.add(new Paragraph("------------------------------------------------------------", nmlFont));
    }

    document.add(preface);
    document.newPage();
  }

  private static void addInquilinoRecomendaciones(Document document, Inquilino inquilino) throws DocumentException{
    if(inquilino.getRecomendaciones().isEmpty()) return;

    Staff_Model staff_model = new Staff_Model();

    Paragraph preface = new Paragraph();
    addEmptyLine(preface, 1);

    preface.add(new Paragraph("Recomendaciones del Inquilino:", subFont));
    addEmptyLine(preface, 1);

    for(Recomendaciones recomendacion : inquilino.getRecomendaciones()) {
      preface.add(new Paragraph(recomendacion.getTitulo(), subFont));
      preface.add(new Paragraph("Fecha: " + recomendacion.getFecha(), nmlFont));
      if(!recomendacion.getStaffId().equals(Uuid.NULL)) {
        Staff staff = staff_model.getSingleStaffById(recomendacion.getStaffId()).iterator().next();
        preface.add(new Paragraph("Expedido por: " + staff.getNombre(), nmlFont));
      }
      preface.add(new Paragraph("Contenido: ", nmlFont));
      preface.add(new Paragraph("\t" + recomendacion.getContenido(), bluFont));
    }

    document.add(preface);
    document.newPage();
  }

  private static void addInquilinoEmergencias(Document document, Inquilino inquilino) throws DocumentException{
    if(inquilino.getEmergencias().isEmpty()) return;

    Staff_Model staff_model = new Staff_Model();

    Paragraph preface = new Paragraph();
    addEmptyLine(preface, 1);

    preface.add(new Paragraph("Emergencias del Inquilino:", subFont));
    addEmptyLine(preface, 1);

    for(Emergencias emergencia : inquilino.getEmergencias()) {
      preface.add(new Paragraph(emergencia.getTitulo(), subFont));
      preface.add(new Paragraph("Fecha: " + emergencia.getFecha(), nmlFont));
      preface.add(new Paragraph("Hospital: " + emergencia.getHospital(), nmlFont));
      if(!emergencia.getStaffId().equals(Uuid.NULL)) {
        Staff staff = staff_model.getSingleStaffById(emergencia.getStaffId()).iterator().next();
        preface.add(new Paragraph("Expedido por: " + staff.getNombre(), nmlFont));
      }
      preface.add(new Paragraph("Contenido: ", nmlFont));
      preface.add(new Paragraph("\t" + emergencia.getContenido(), bluFont));
    }

    document.add(preface);
    document.newPage();
  }

  private static void addEmptyLine(Paragraph paragraph, int num) {
    for(int i = 0; i < num; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
}
