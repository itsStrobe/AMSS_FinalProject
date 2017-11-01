package amss.GUI.Controllers;

import amss.app.Connection.Emergencias_Model;
import amss.app.Connection.Inquilino_Model;
import amss.app.Elementos.Emergencias;
import amss.app.Individuos.Inquilino;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by strobe on 31/10/17.
 */
public class Emergencias_Controller implements Initializable{
  @FXML
  private TableView<EmergenciasView> emergenciasTable = new TableView<>();
  @FXML
  private TableColumn<EmergenciasView, amss.app.util.Uuid> Uuid;
  @FXML
  private TableColumn<EmergenciasView, String> Titulo;
  @FXML
  private TableColumn<EmergenciasView, String> Inquilino;
  @FXML
  private TableColumn<EmergenciasView, Time> Fecha;

  private Stage prevStage;

  private Inquilino_Model inquilino_model = new Inquilino_Model();
  private Emergencias_Model emergencias_model = new Emergencias_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    Uuid.setCellValueFactory(new PropertyValueFactory<EmergenciasView, Uuid>("Id"));
    Titulo.setCellValueFactory(new PropertyValueFactory<EmergenciasView, String>("Titulo"));
    Inquilino.setCellValueFactory(new PropertyValueFactory<EmergenciasView, String>("Inquilino"));
    Fecha.setCellValueFactory(new PropertyValueFactory<EmergenciasView, Time>("Fecha"));

    List<EmergenciasView> emergenciasViewList = parselist();
    emergenciasTable.getItems().setAll(emergenciasViewList);
  }

  private List<EmergenciasView> parselist() {
    List<EmergenciasView> allEmergencias = new ArrayList<>();
    Collection<Emergencias> emergencias = emergencias_model.getAllEmergencias();
    for (Emergencias emergencia : emergencias) {
      allEmergencias.add(new EmergenciasView(emergencia));
    }

    return allEmergencias;
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void select_Emergencia() {
    System.out.println("Emergencia Selected");
  }

  public void transition_NuevaEmergencia() throws IOException{
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/emergenciasForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    EmergenciasForm_Controller controller = (EmergenciasForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Nueva Emergencia");

    prevStage.close();

    stage.show();
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../MainWindow.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Controller controller = (Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Casa de Retiro Luis Elizondo");

    prevStage.close();

    stage.show();
  }
}
