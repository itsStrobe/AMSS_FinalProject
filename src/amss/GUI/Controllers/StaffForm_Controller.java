package amss.GUI.Controllers;

import amss.app.Connection.Staff_Model;
import amss.app.Individuos.Staff;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class StaffForm_Controller implements Initializable{
  @FXML
  private TextField nombreField;
  @FXML
  private TextField telefonoField;
  @FXML
  private TextField turnoField;
  @FXML
  private ChoiceBox<String> posicionField;
  @FXML
  private DatePicker fechaNacimientoField;

  private Uuid.Generator uuidGenerator;
  private final Staff_Model staff_model = new Staff_Model();

  private Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("108.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());

    posicionField.getItems().add("Enfermero(a)");
    posicionField.getItems().add("Doctor(a)");
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void add_Staff() throws Exception {
    if(nombreField.getText().isEmpty() || telefonoField.getText().isEmpty() || turnoField.getText().isEmpty() || posicionField.getValue().isEmpty() || fechaNacimientoField.getValue() == null) {
      return;
    }

    Uuid uuid = uuidGenerator.make();
    String nombre = nombreField.getText();
    String telefono = telefonoField.getText();
    String turno = turnoField.getText();
    String posicion = posicionField.getValue();
    Time fechaN = Time.fromMs(Time.getDateInMs(fechaNacimientoField.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));

    Staff newStaff = new Staff(uuid, nombre, telefono, turno, posicion, fechaN);

    staff_model.add(newStaff);

    transition_Back();
  }

  public void transition_Back() throws Exception {
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
