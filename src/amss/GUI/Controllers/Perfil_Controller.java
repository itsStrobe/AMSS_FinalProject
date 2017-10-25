package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import amss.GUI.Main;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala y German on 10/15/17.
 */
public class Perfil_Controller implements Initializable {

  private Inquilino selectedInquilino;
  private Inquilino_Model inquilino_model = new Inquilino_Model();
  private Familiar_Model familiar_model = new Familiar_Model();
  private PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();
  private Receta_Model receta_model = new Receta_Model();

  @FXML
  private Label inqID = new Label();
  @FXML
  private Label inqNombre = new Label();
  @FXML
  private Label inqDireccion = new Label();
  @FXML
  private Label inqEdad = new Label();
  @FXML
  private Label inqEstatus = new Label();
  @FXML
  private Label inqCuarto = new Label();

  @FXML
  private TableView<PastilleroView> pastTable = new TableView<>();
  @FXML
  private TableColumn<PastilleroView, String> pastNombre;
  @FXML
  private TableColumn<PastilleroView, Integer> pastManana;
  @FXML
  private TableColumn<PastilleroView, Integer> pastTarde;
  @FXML
  private TableColumn<PastilleroView, Integer> pastNoche;

  @FXML
  private TableView<MedicinaView> medTable = new TableView<>();
  @FXML
  private TableColumn<MedicinaView, Uuid> medNombre;
  @FXML
  private TableColumn<MedicinaView, Integer> medCantidad;

  @FXML
  private TableView<Familiar> famTable = new TableView<>();
  @FXML
  private TableColumn<Familiar, String> famNombre;
  @FXML
  private TableColumn<Familiar, String> famTelefono;

  @FXML
  private TableView<Receta> recTable = new TableView<>();
  @FXML
  private TableColumn<Receta, Uuid> recId;
  @FXML
  private TableColumn<Receta, Time> recFechaInicio;
  @FXML
  private TableColumn<Receta, String> recDocNombre;

  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());

  }

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
    this.inqNombre.setText(inquilino.getNombre());
    this.inqDireccion.setText(inquilino.getDireccion());
    this.inqEdad.setText(SQLFormatter.sqlInt(inquilino.getEdad()));
    this.inqEstatus.setText(SQLFormatter.sqlChar(inquilino.getEstatus()));
    this.inqCuarto.setText(inquilino.getCuarto());
  }

  private void setSelectedInquilino() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println("This Inquilino: " + selectedInquilino.getNombre());
  }

  private List<Familiar> parseListFamiliares() {
    setSelectedInquilino();

    List<Familiar> familiares = new ArrayList<>();

    for(Familiar familiar : familiar_model.getFamiliaresOfInquilino(selectedInquilino)) {
      familiares.add(familiar);
    }

    return familiares;
  }

  private List<MedicinaView> parseListMedicinaView() {
    setSelectedInquilino();

    List<MedicinaView> medicinaViewList = new ArrayList<>();

    for(PacienteMedicina pacienteMedicina : pacienteMedicina_model.getAllMedicinasOfPaciente(selectedInquilino)) {
      MedicinaView medicinaView = new MedicinaView(pacienteMedicina);
      System.out.println(medicinaView.getCantidad());
      medicinaViewList.add(medicinaView);
    }

    return medicinaViewList;
  }

  private List<PastilleroView> parseListPastilleroView() {
    setSelectedInquilino();

    List<PastilleroView> pastilleroViewList = new ArrayList<>();

    for(PacienteMedicina pacienteMedicina : pacienteMedicina_model.getAllMedicinasOfPaciente(selectedInquilino.getId())) {
      PastilleroView pastilleroView = null;
      for(Receta receta : receta_model.getAllRecetasOfPaciente(selectedInquilino.getId())) {
        for(RecetaMedicina recetaMedicina : recetaMedicina_model.getSingleMedicinaOfReceta(receta.getId(), pacienteMedicina.getId())) {
          if(Time.now().inRange(receta.getFechaInicio(), recetaMedicina.getEndDate())) {
            if(pastilleroView == null) {
              pastilleroView = new PastilleroView(recetaMedicina);
            }
            else {
              System.out.println(recetaMedicina.getMorning() + " " +  recetaMedicina.getEvening() + " " + recetaMedicina.getNight());
              pastilleroView.update(recetaMedicina.getMorning(), recetaMedicina.getEvening(), recetaMedicina.getNight());
            }
          }
        }
      }

      if(pastilleroView != null){
        pastilleroViewList.add(pastilleroView);
      }
    }

    return pastilleroViewList;
  }

  private List<Receta> parseListReceta() {
    setSelectedInquilino();

    List<Receta> recetaArrayList = new ArrayList<>();

    for(Receta receta : receta_model.getAllRecetasOfPaciente(selectedInquilino.getId())) {
      recetaArrayList.add(receta);
    }

    return recetaArrayList;
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void loadInfo() {
    setSelectedInquilino();

    // Inicializa TABLA PASTILLERO
    pastNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
    pastManana.setCellValueFactory(new PropertyValueFactory<>("Manana"));
    pastTarde.setCellValueFactory(new PropertyValueFactory<>("Tarde"));
    pastNoche.setCellValueFactory(new PropertyValueFactory<>("Noche"));

    List<PastilleroView> pastilleroViewList = parseListPastilleroView();
    pastTable.getItems().setAll(pastilleroViewList);

    // Inicializa TABLA MEDICINAS
    medNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
    medCantidad.setCellValueFactory(new PropertyValueFactory<MedicinaView, Integer>("Cantidad"));

    List<MedicinaView> medicinaViewList = parseListMedicinaView();
    medTable.getItems().setAll(medicinaViewList);

    // Inicializa TABLA FAMILIARES
    famNombre.setCellValueFactory(new PropertyValueFactory<Familiar, String>("Nombre"));
    famTelefono.setCellValueFactory(new PropertyValueFactory<Familiar, String>("Telefono"));

    List<Familiar> familiares = parseListFamiliares();
    famTable.getItems().setAll(familiares);

    // Inicializa TABLA RECETAS
    recId.setCellValueFactory(new PropertyValueFactory<>("Id"));
    recDocNombre.setCellValueFactory(new PropertyValueFactory<>("DocNombre"));
    recFechaInicio.setCellValueFactory(new PropertyValueFactory<>("FechaInicio"));

    List<Receta> recetaList = parseListReceta();
    recTable.getItems().setAll(recetaList);
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
    controller.setPrevScreen("Perfil");

    stage.setTitle("Nuevo Medicamento");

    prevStage.close();

    stage.show();
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/inquilinos.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Inquilinos_Controller controller = (Inquilinos_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Inquilinos");

    prevStage.close();

    stage.show();
  }

  public void agregarFamiliar() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/familiarForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

    FamiliarForm_Controller controller = (FamiliarForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setInquilinoInfo(this.selectedInquilino);

    stage.setTitle("Nuevo Familiar");

    prevStage.close();

    stage.show();
  }

  public void select_Receta() throws Exception {
    if (recTable.getSelectionModel().getSelectedItem() != null) {
      Receta receta = recTable.getSelectionModel().getSelectedItem();

      Stage stage = new Stage(StageStyle.DECORATED);
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/recetaView.fxml"));

      Pane myPane = (Pane) myLoader.load();
      Scene myScene = new Scene(myPane);
      stage.setScene(myScene);

      RecetaView_Controller controller = (RecetaView_Controller) myLoader.<RecetaView_Controller>getController();
      controller.setInquilinoInfo(selectedInquilino);
      controller.setRecetaInfo(receta);
      controller.setPrevStage(stage);

      stage.setTitle("Nuevo Inquilino");

      prevStage.close();

      stage.show();
    }
  }

  public void transition_RecetaForm() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/recetaForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

    RecetaForm_Controller controller = (RecetaForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setInquilinoInfo(selectedInquilino);

    stage.setTitle("Nueva Receta");

    prevStage.close();

    stage.show();
  }
}
