package amss.GUI.Controllers;

import amss.app.Connection.Staff_Model;
import amss.app.Individuos.Staff;
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
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by Jose Zavala y German Villacorta on 10/17/17.
 */
public class Staff_Controller implements Initializable {

  @FXML
  private TableView<Staff> staffTable = new TableView<>();
  @FXML
  private TableColumn<Staff, Uuid> Uuid;
  @FXML
  private TableColumn<Staff, String> Nombre;
  @FXML
  private TableColumn<Staff, String> Telefono;
  @FXML
  private TableColumn<Staff, String> Turno;
  @FXML
  private TableColumn<Staff, String> Posicion;
  @FXML
  private TableColumn<Staff, Time> FechaNacimiento;

  private Stage prevStage;

  private Staff_Model staff_model = new Staff_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid.setCellValueFactory(new PropertyValueFactory<Staff, Uuid>("Id"));
    Nombre.setCellValueFactory(new PropertyValueFactory<Staff, String>("Nombre"));
    Telefono.setCellValueFactory(new PropertyValueFactory<Staff, String>("Telefono"));
    Turno.setCellValueFactory(new PropertyValueFactory<Staff, String>("Turno"));
    Posicion.setCellValueFactory(new PropertyValueFactory<Staff, String>("Posicion"));
    FechaNacimiento.setCellValueFactory(new PropertyValueFactory<Staff, Time>("FechaNacimiento"));

    List<Staff> staff = parselist();
    staffTable.getItems().setAll(staff);
  }

  public void transition_NuevoStaff() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/staffForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    StaffForm_Controller controller = (StaffForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Nuevo Staff");

    prevStage.close();

    stage.show();
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void select_Staff() throws Exception {
    if (staffTable.getSelectionModel().getSelectedItem() != null) {
      Staff staff = staffTable.getSelectionModel().getSelectedItem();

      Stage stage = new Stage(StageStyle.DECORATED);
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/staffEditForm.fxml"));

      Pane myPane = (Pane) myLoader.load();
      Scene myScene = new Scene(myPane);
      stage.setScene(myScene);

      StaffEditForm_Controller controller = (StaffEditForm_Controller) myLoader.getController();
      System.out.println("Staff: " + staff.getNombre());
      controller.setStaffInfo(staff);
      controller.setSelectedStaff(staff);
      controller.setPrevStage(stage);

      stage.setTitle("Actualizar Informacion de Staff");

      prevStage.close();

      stage.show();
    }
  }

  private List<Staff> parselist() {
    List<Staff> allStaff = new ArrayList<>();
    Collection<Staff> staffList = staff_model.getAllStaff();
    for (Staff staff : staffList) {
      allStaff.add(staff);
    }

    return allStaff;
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
