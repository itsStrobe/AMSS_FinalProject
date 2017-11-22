package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by German on 10/17/17.
 */
public class RecetaForm_Controller implements Initializable {

  @FXML
  private TextField doctorNombre = new TextField();
  @FXML
  private TextField medMa = new TextField();
  @FXML
  private TextField medTa = new TextField();
  @FXML
  private TextField medNo = new TextField();
  @FXML
  private TextField medMa1 = new TextField();
  @FXML
  private TextField medTa1 = new TextField();
  @FXML
  private TextField medNo1 = new TextField();
  @FXML
  private TextField medMa2 = new TextField();
  @FXML
  private TextField medTa2 = new TextField();
  @FXML
  private TextField medNo2 = new TextField();
  @FXML
  private Label inqID = new Label();
  @FXML
  private DatePicker fechaSelect = new DatePicker();
  @FXML
  private DatePicker fechaSelect1 = new DatePicker();
  @FXML
  private DatePicker fechaSelect2 = new DatePicker();
  @FXML
  private ChoiceBox<String> medSelect = new ChoiceBox<>();
  @FXML
  private ChoiceBox<String> medSelect1 = new ChoiceBox<>();
  @FXML
  private ChoiceBox<String> medSelect2 = new ChoiceBox<>();

  private Uuid.Generator uuidGenerator;
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final Receta_Model receta_model = new Receta_Model();
  private final RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();
  private final PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private Inquilino selectedInquilino;
  private Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("104.0000000000");
    } catch (IOException ex) {
    }

    for(Medicina medicina : medicina_model.getAllMedicinas()) {
      medSelect.getItems().add(medicina.getNombre());
      medSelect1.getItems().add(medicina.getNombre());
      medSelect2.getItems().add(medicina.getNombre());
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setSelectedInquilino(Inquilino inquilino) {
    this.selectedInquilino = inquilino;
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void addMedicamento() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/medicamentoForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    medicamentoForm_Controller controller = (medicamentoForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setPrevScreen("Receta");
    controller.setSelectedInquilino(selectedInquilino);

    stage.setTitle("Nuevo Medicamento");

    prevStage.close();

    stage.show();
  }

  public void create() throws Exception {
    if(doctorNombre.getText().isEmpty()) {
      return;
    }

    Receta receta;
    Uuid uuid;
    String docNombre;
    Time tiempoInicio;

    uuid = uuidGenerator.make();
    docNombre = doctorNombre.getText();
    tiempoInicio = Time.now();

    receta = new Receta(uuid, docNombre, this.selectedInquilino.getId(), tiempoInicio);

    receta_model.add(receta);

    // ADD MED 1
    int dosisMa;
    int dosisTa;
    int dosisNo;
    Time tiempoFin;

    PacienteMedicina pacienteMedicina;
    RecetaMedicina recetaMedicina;

    // MED 1
    if(medSelect.getValue() != null) {
      Medicina selectedMedicina = medicina_model.getSingleMedicinaByName(medSelect.getValue()).iterator().next();

      Collection<PacienteMedicina> pacienteMedicinas = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId());

      if(pacienteMedicinas.isEmpty()) {
        pacienteMedicina_model.add(new PacienteMedicina(selectedMedicina, selectedInquilino.getId(), 0));
      }

      pacienteMedicina = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId()).iterator().next();

      if(!medMa.getText().isEmpty()) {
        dosisMa = Integer.parseInt(medMa.getText());
      }
      else {
        dosisMa = 0;
      }
      if(!medTa.getText().isEmpty()) {
        dosisTa = Integer.parseInt(medTa.getText());
      }
      else {
        dosisTa = 0;
      }
      if(!medNo.getText().isEmpty()) {
        dosisNo = Integer.parseInt(medNo.getText());
      }
      else {
        dosisNo = 0;
      }
      tiempoFin = Time.fromMs(Time.getDateInMs(fechaSelect.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));

      recetaMedicina = new RecetaMedicina(pacienteMedicina, receta, dosisMa, dosisTa, dosisNo, tiempoFin);

      recetaMedicina_model.add(recetaMedicina);
    }

    // MED 2
    if(medSelect1.getValue() != null) {
      Medicina selectedMedicina = medicina_model.getSingleMedicinaByName(medSelect1.getValue()).iterator().next();

      Collection<PacienteMedicina> pacienteMedicinas = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId());

      if(pacienteMedicinas.isEmpty()) {
        pacienteMedicina_model.add(new PacienteMedicina(selectedMedicina, selectedInquilino.getId(), 0));
      }

      pacienteMedicina = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId()).iterator().next();

      if(!medMa1.getText().isEmpty()) {
        dosisMa = Integer.parseInt(medMa1.getText());
      }
      else {
        dosisMa = 0;
      }
      if(!medTa1.getText().isEmpty()) {
        dosisTa = Integer.parseInt(medTa1.getText());
      }
      else {
        dosisTa = 0;
      }
      if(!medNo1.getText().isEmpty()) {
        dosisNo = Integer.parseInt(medNo1.getText());
      }
      else {
        dosisNo = 0;
      }
      tiempoFin = Time.fromMs(Time.getDateInMs(fechaSelect1.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));

      recetaMedicina = new RecetaMedicina(pacienteMedicina, receta, dosisMa, dosisTa, dosisNo, tiempoFin);

      recetaMedicina_model.add(recetaMedicina);
    }

    // MED 3
    if(medSelect2.getValue() != null) {
      Medicina selectedMedicina = medicina_model.getSingleMedicinaByName(medSelect2.getValue()).iterator().next();

      Collection<PacienteMedicina> pacienteMedicinas = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId());

      if(pacienteMedicinas.isEmpty()) {
        pacienteMedicina_model.add(new PacienteMedicina(selectedMedicina, selectedInquilino.getId(), 0));
      }

      pacienteMedicina = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId()).iterator().next();

      if(!medMa2.getText().isEmpty()) {
        dosisMa = Integer.parseInt(medMa2.getText());
      }
      else {
        dosisMa = 0;
      }
      if(!medTa2.getText().isEmpty()) {
        dosisTa = Integer.parseInt(medTa2.getText());
      }
      else {
        dosisTa = 0;
      }
      if(!medNo2.getText().isEmpty()) {
        dosisNo = Integer.parseInt(medNo2.getText());
      }
      else {
        dosisNo = 0;
      }
      tiempoFin = Time.fromMs(Time.getDateInMs(fechaSelect2.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));

      recetaMedicina = new RecetaMedicina(pacienteMedicina, receta, dosisMa, dosisTa, dosisNo, tiempoFin);

      recetaMedicina_model.add(recetaMedicina);
    }

    transition_Back();
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/perfil.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Perfil_Controller controller = (Perfil_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setInquilinoInfo(this.selectedInquilino);
    controller.setSelectedInquilino(this.selectedInquilino);
    controller.loadInfo();

    stage.setTitle("Inquilino");

    prevStage.close();

    stage.show();
  }
}
