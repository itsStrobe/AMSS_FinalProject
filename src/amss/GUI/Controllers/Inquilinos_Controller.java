package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.Uuid;

import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;


/**
 * Created by Jose Zavala y German on 10/15/17.
 */

public class Inquilinos_Controller implements Initializable {

  @FXML
  private TableView<Inquilino> inquilinosTable = new TableView<>();
  @FXML
  private TableColumn<Inquilino, Uuid> Uuid;
  @FXML
  private TableColumn<Inquilino, String> Nombre;
  @FXML
  private TableColumn<Inquilino, String> Direccion;
  @FXML
  private TableColumn<Inquilino, Integer> Edad;
  @FXML
  private TableColumn<Inquilino, String> Cuarto;

  Stage prevStage;

  private Inquilino_Model model = new Inquilino_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());

    Nombre.setCellValueFactory(new PropertyValueFactory<Inquilino, String>("Nombre"));
    Direccion.setCellValueFactory(new PropertyValueFactory<Inquilino, String>("Direccion"));
    Edad.setCellValueFactory(new PropertyValueFactory<Inquilino, Integer>("Edad"));
    Cuarto.setCellValueFactory(new PropertyValueFactory<Inquilino, String>("Cuarto"));

    List<Inquilino> inquilinos = parselist();
    inquilinosTable.getItems().setAll(inquilinos);
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

  public void transition_NuevoInquilino() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/inquilinoForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    InquilinoForm_Controller controller = (InquilinoForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Nuevo Inquilino");

    prevStage.close();

    stage.show();
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void select_Inquilino() throws Exception {
    if (inquilinosTable.getSelectionModel().getSelectedItem() != null) {
      Inquilino inquilino = inquilinosTable.getSelectionModel().getSelectedItem();

      Stage stage = new Stage(StageStyle.DECORATED);
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/perfil.fxml"));

      Pane myPane = (Pane) myLoader.load();
      Scene myScene = new Scene(myPane);
      stage.setScene(myScene);

      Perfil_Controller controller = (Perfil_Controller) myLoader.<Perfil_Controller>getController();
      controller.setSelectedInquilino(inquilino);
      controller.setPrevStage(stage);

      stage.setTitle("Nuevo Inquilino");

      prevStage.close();

      stage.show();
    }
  }

  private List<Inquilino> parselist() {
    List<Inquilino> allInquilinos = new ArrayList<>();
    Collection<Inquilino> inquilinos = model.getAllInquilinos();
    for (Inquilino inquilino : inquilinos) {
      System.out.println(inquilino.getCuarto());
      allInquilinos.add(inquilino);
    }

    return allInquilinos;
  }
}
