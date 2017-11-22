package amss.GUI.Controllers;

import amss.app.Connection.Eventualidades_Model;
import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Eventualidades;
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
 * Created by strobe on 8/11/17.
 */
public class EventualidadForm_Controller implements Initializable {
  @FXML
  private TextField recoTituloField;
  @FXML
  private TextField recoContenidoField;
  @FXML
  private ChoiceBox<String> recoStaffBox;

  private Uuid.Generator uuidGenerator;
  private final Staff_Model staff_model = new Staff_Model();
  private final Eventualidades_Model eventualidades_model = new Eventualidades_Model();

  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("106.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());

    for(Staff staff : staff_model.getAllStaff()) {
      recoStaffBox.getItems().add(staff.getNombre());
    }
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void add_Eventualidad() throws Exception {
    if(recoTituloField.getText().isEmpty() || recoContenidoField.getText().isEmpty() || recoStaffBox.getValue().isEmpty()) {
      return;
    }

    Uuid uuid = uuidGenerator.make();
    String titulo = recoTituloField.getText();
    String contenido = recoContenidoField.getText();
    Uuid staff = Uuid.NULL;
    Time fechaN = Time.now();

    if(recoStaffBox.getItems() != null) {
      staff = staff_model.getSingleStaffByName(recoStaffBox.getValue()).iterator().next().getId();
    }

    Eventualidades newEventualidad = new Eventualidades(uuid, titulo, contenido, staff, fechaN);


    eventualidades_model.add(newEventualidad);

    transition_Back();
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
