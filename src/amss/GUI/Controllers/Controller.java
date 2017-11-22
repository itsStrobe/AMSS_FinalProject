package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.*;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Jose Zavala y German on 10/15/17.
 */

public class Controller implements Initializable {

  @FXML
  private TableView<EventualidadView> eventualidadesTable = new TableView<>();
  @FXML
  private TableColumn<EventualidadView, Uuid> Uuid;
  @FXML
  private TableColumn<EventualidadView, String> Titulo;
  @FXML
  private TableColumn<EventualidadView, String> Staff;
  @FXML
  private TableColumn<EventualidadView, Time> Fecha;

  @FXML
  private DatePicker fechaSelect = new DatePicker();

  private Stage prevStage;

  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private final Receta_Model receta_model = new Receta_Model();
  private final RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();
  private final Eventualidades_Model eventualidades_model = new Eventualidades_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid.setCellValueFactory(new PropertyValueFactory<EventualidadView, Uuid>("Id"));
    Titulo.setCellValueFactory(new PropertyValueFactory<EventualidadView, String>("Titulo"));
    Staff.setCellValueFactory(new PropertyValueFactory<EventualidadView, String>("Staff"));
    Fecha.setCellValueFactory(new PropertyValueFactory<EventualidadView, Time>("Fecha"));

    List<EventualidadView> eventualidades = parselist();
    eventualidadesTable.getItems().setAll(eventualidades);
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  private List<EventualidadView> parselist() {
    List<EventualidadView> allEventualidades = new ArrayList<>();
    Collection<Eventualidades> eventualidades = eventualidades_model.getAllEventualidades();
    for (Eventualidades eventualidad : eventualidades) {
      allEventualidades.add(new EventualidadView(eventualidad));
    }

    return allEventualidades;
  }

  public void select_Eventualidad() throws IOException{
    if (eventualidadesTable.getSelectionModel().getSelectedItem() != null) {
      EventualidadView eventualidadView = eventualidadesTable.getSelectionModel().getSelectedItem();
      Eventualidades eventualidad = eventualidades_model.getSingleEventualidadById(eventualidadView.getId()).iterator().next();

      Stage stage = new Stage(StageStyle.DECORATED);
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/eventualidadDetails.fxml"));

      Pane myPane = (Pane) myLoader.load();
      Scene myScene = new Scene(myPane);
      stage.setScene(myScene);

      EventualidadDetails_Controller controller = (EventualidadDetails_Controller) myLoader.<EventualidadDetails_Controller>getController();
      controller.setSelectedEventualidad(eventualidad);
      controller.setPrevStage(stage);
      controller.loadInfo();

      stage.setTitle("Informacion de Eventualidad");

      prevStage.close();

      stage.show();
    }
  }

  public void goToInquilinos() throws IOException {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/inquilinos.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Inquilinos_Controller controller = (Inquilinos_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Inquilinos");

    prevStage.close();

    stage.show();
  }

  public void goToMedicinasFaltantes() throws IOException {
    if(fechaSelect.getValue() == null) return;

    Time selectedFecha = Time.fromMs(Time.getDateInMs(fechaSelect.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));

    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/medicinasFaltantes.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    MedicinasFaltantes_Controller controller = (MedicinasFaltantes_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setSelectedFecha(selectedFecha);
    controller.loadInfo();

    stage.setTitle("Medicinas Faltantes");

    prevStage.close();

    stage.show();
  }

  public void transition_NuevaEventualidad() throws Exception{
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/eventualidadForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    EventualidadForm_Controller controller = (EventualidadForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Nueva Eventualidad");

    prevStage.close();

    stage.show();
  }

  public void goToEmergencias() throws IOException {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/emergencias.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Emergencias_Controller controller = (Emergencias_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Emergencias");

    prevStage.close();

    stage.show();
  }

  public void goToStaff() throws IOException {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/staff.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Staff_Controller controller = (Staff_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Staff");

    prevStage.close();

    stage.show();
  }

}
