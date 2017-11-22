package amss.GUI.Controllers;

import amss.app.Connection.Staff_Model;
import amss.app.Individuos.Staff;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 31/10/17.
 */
public class StaffEditForm_Controller implements Initializable{
  @FXML
  private TextField stfId = new TextField();
  @FXML
  private TextField nombreField = new TextField();
  @FXML
  private TextField telefonoField = new TextField();
  @FXML
  private TextField turnoField = new TextField();
  @FXML
  private ChoiceBox<String> posicionField;
  @FXML
  private DatePicker fechaNacimientoField;

  private final Staff_Model staff_model = new Staff_Model();

  private Stage prevStage;
  private Staff selectedStaff;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    posicionField.getItems().add("Enfermero(a)");
    posicionField.getItems().add("Doctor(a)");
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setStaffInfo(Staff staff) {
    this.stfId.setText(SQLFormatter.sqlID(staff.getId()));
    this.nombreField.setText(staff.getNombre());
    this.telefonoField.setText(staff.getTelefono());
    this.posicionField.setValue(staff.getPosicion());
  }

  public void setSelectedStaff(Staff staff) {
    this.selectedStaff = staff;
  }

  public void update_Staff() throws Exception {
    if(nombreField.getText().isEmpty() || telefonoField.getText().isEmpty() || turnoField.getText().isEmpty() || posicionField.getValue().isEmpty() || fechaNacimientoField.getValue() == null) {
      return;
    }

    String nombre = nombreField.getText();
    String telefono = telefonoField.getText();
    String turno = turnoField.getText();
    String posicion = posicionField.getValue();
    Time fechaN;

    if(fechaNacimientoField.getValue() == null) {
      fechaN = selectedStaff.getFechaNacimiento();
    }
    else {
      fechaN = Time.fromMs(Time.getDateInMs(fechaNacimientoField.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));
    }

    Staff newStaff = new Staff(selectedStaff.getId(), nombre, telefono, turno, posicion, fechaN);


    staff_model.update(newStaff);

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
