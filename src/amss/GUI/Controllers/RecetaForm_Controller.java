package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import amss.GUI.Main;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.junit.Test;


import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
  private Label inqID = new Label();
  @FXML
  private DatePicker fechaSelect = new DatePicker();
  @FXML
  private ChoiceBox<String> medSelect = new ChoiceBox<>();

  private Collection<Medicina> medicinas;

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final Receta_Model receta_model = new Receta_Model();
  private final RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();
  private final PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private Inquilino selectedInquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("104.0000000000");
    } catch (IOException ex) {
    }

    for(Medicina medicina : medicina_model.getAllMedicinas()) {
      medSelect.getItems().add(medicina.getNombre());
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
  }

  private void setSelectedInquilino() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println("This Inquilino: " + selectedInquilino.getNombre());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void addMedicamento() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/medicamentoForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

    medicamentoForm_Controller controller = (medicamentoForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setPrevScreen("Receta");
    controller.setInquilinoInfo(selectedInquilino);

    stage.setTitle("Nuevo Medicamento");

    prevStage.close();

    stage.show();
  }

  public void create() throws Exception {
    setSelectedInquilino();

    Uuid uuid;
    String docNombre;
    Time tiempoInicio;

    int dosisMa;
    int dosisTa;
    int dosisNo;
    Time tiempoFin;

    Receta receta;
    PacienteMedicina pacienteMedicina;
    RecetaMedicina recetaMedicina;
    Medicina selectedMedicina = medicina_model.getSingleMedicinaByName(medSelect.getValue()).iterator().next();

    Collection<PacienteMedicina> pacienteMedicinas = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId());

    if(pacienteMedicinas.isEmpty()) {
      pacienteMedicina_model.add(new PacienteMedicina(selectedMedicina, selectedInquilino.getId(), 0));
    }

    pacienteMedicina = pacienteMedicina_model.getSingleMedicinaOfPaciente(selectedMedicina.getId(), selectedInquilino.getId()).iterator().next();

    uuid = uuidGenerator.make();
    docNombre = doctorNombre.getText();
    tiempoInicio = Time.now();

    receta = new Receta(uuid, docNombre, selectedInquilino.getId(), tiempoInicio);

    receta_model.add(receta);

    dosisMa = Integer.parseInt(medMa.getText());
    dosisTa = Integer.parseInt(medTa.getText());
    dosisNo = Integer.parseInt(medNo.getText());
    tiempoFin = Time.fromMs(Time.getDateInMs(fechaSelect.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));

    recetaMedicina = new RecetaMedicina(pacienteMedicina, receta, dosisMa, dosisTa, dosisNo, tiempoFin);

    recetaMedicina_model.add(recetaMedicina);

    transition_Back();
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/perfil.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

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
