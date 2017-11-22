package amss.GUI.Controllers;

import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Eventualidades;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 20/11/17.
 */
public class EventualidadDetails_Controller implements Initializable{
  @FXML
  private Label tituloLabel = new Label();
  @FXML
  private Label contenidoLabel = new Label();
  @FXML
  private Label staffLabel = new Label();
  @FXML
  private Label fechaLabel = new Label();

  private Stage prevStage;
  private Eventualidades selectedEventualidad;

  private Staff_Model staff_model = new Staff_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setSelectedEventualidad(Eventualidades eventualidad) {
    this.selectedEventualidad = eventualidad;
  }

  public void loadInfo() {
    this.tituloLabel.setText(selectedEventualidad.getTitulo());
    this.contenidoLabel.setText(selectedEventualidad.getContenido());
    if(!selectedEventualidad.getStaffId().equals(Uuid.NULL)) {
      this.staffLabel.setText(staff_model.getSingleStaffById(selectedEventualidad.getStaffId()).iterator().next().getNombre());
    }
    this.fechaLabel.setText(selectedEventualidad.getFecha().toString());
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/MainWindow.fxml"));

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
