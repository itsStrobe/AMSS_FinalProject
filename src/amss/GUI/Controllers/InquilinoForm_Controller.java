package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by German y Jose Zavala on 10/17/17.
 */
public class InquilinoForm_Controller implements Initializable {

  @FXML
  private TextField nombreField;
  @FXML
  private TextField direccionField;
  @FXML
  private TextField padecimientoField;
  @FXML
  private TextField cuartoField;
  @FXML
  private DatePicker fechaNacField;

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("101.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void add_Inquilino() throws Exception {
    if(nombreField.getText().isEmpty() || direccionField.getText().isEmpty() || fechaNacField.getValue() == null || cuartoField.getText().isEmpty() || padecimientoField.getText().isEmpty()) {
      return;
    }

    Uuid uuid = uuidGenerator.make();
    String nombre = nombreField.getText();
    String direccion = direccionField.getText();
    Time fechaNac = Time.fromMs(Time.getDateInMs(fechaNacField.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));
    String cuarto = cuartoField.getText();
    String padecimientos = padecimientoField.getText();

    Inquilino newInquilino = new Inquilino(uuid, nombre, direccion, fechaNac, cuarto, padecimientos);

    inquilino_model.add(newInquilino);

    transition_Back();
  }

  public void transition_Back() throws Exception {
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
}
