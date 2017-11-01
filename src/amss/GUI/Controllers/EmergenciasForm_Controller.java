package amss.GUI.Controllers;

import amss.app.Connection.Emergencias_Model;
import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Emergencias;
import amss.app.Elementos.Medicina;
import amss.app.Individuos.Inquilino;
import amss.app.Individuos.Staff;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by strobe on 31/10/17.
 */
public class EmergenciasForm_Controller implements Initializable {
  @FXML
  private TextField tituloField;
  @FXML
  private TextField contenidoField;
  @FXML
  private ChoiceBox<String> inquilinoBox;
  @FXML
  private ChoiceBox<String> staffBox;
  @FXML
  private TextField hospitalField;

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Staff_Model staff_model = new Staff_Model();
  private final Emergencias_Model emergencias_model = new Emergencias_Model();

  private Emergencias emergencia;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("106.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());

    for(Inquilino inquilino : inquilino_model.getAllInquilinos()) {
      inquilinoBox.getItems().add(inquilino.getNombre());
    }

    for(Staff staff : staff_model.getAllStaff()) {
      staffBox.getItems().add(staff.getNombre());
    }
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setEmergencia(Emergencias emergencia) {
    this.emergencia = emergencia;
  }

  public void add_Emergencia() throws Exception {
    Uuid uuid = uuidGenerator.make();
    String titulo = tituloField.getText();
    String contenido = contenidoField.getText();
    Inquilino inquilino = inquilino_model.getSingleInquilinoByName(inquilinoBox.getValue()).iterator().next();
    Staff staff = staff_model.getSingleStaffByName(staffBox.getValue()).iterator().next();
    String hospital = hospitalField.getText();
    Time fechaN = Time.now();

    Emergencias newEmergencia = new Emergencias(uuid, titulo, contenido, inquilino.getId(), staff.getId(), hospital, fechaN);


    emergencias_model.add(newEmergencia);

    transition_Back();

  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/emergencias.fxml"));

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
