package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Emergencias;
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
 * Created by Jose Zavala on 31/10/17.
 */
public class EmergenciaDetails_Controller implements Initializable{
  @FXML
  private Label tituloLabel = new Label();
  @FXML
  private Label contenidoLabel = new Label();
  @FXML
  private Label inquilinoLabel = new Label();
  @FXML
  private Label staffLabel = new Label();
  @FXML
  private Label hospitalLabel = new Label();

  private Stage prevStage;
  private Emergencias selectedEmergencia;

  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Staff_Model staff_model = new Staff_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setSelectedEmergencia(Emergencias emergencia) {
    this.selectedEmergencia = emergencia;
  }

  public void loadInfo() {
    this.tituloLabel.setText(selectedEmergencia.getTitulo());
    this.contenidoLabel.setText(selectedEmergencia.getContenido());
    if(!selectedEmergencia.getInquilinoId().equals(Uuid.NULL)) {
      this.inquilinoLabel.setText(inquilino_model.getSingleInquilinoById(selectedEmergencia.getInquilinoId()).iterator().next().getNombre());
    }
    if(!selectedEmergencia.getStaffId().equals(Uuid.NULL)) {
      this.staffLabel.setText(staff_model.getSingleStaffById(selectedEmergencia.getStaffId()).iterator().next().getNombre());
    }
    this.hospitalLabel.setText(selectedEmergencia.getHospital());
  }

  public void transition_Back() throws Exception {
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
}
