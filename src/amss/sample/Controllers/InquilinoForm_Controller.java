package amss.sample.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.Initializable;
import amss.sample.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.stage.Stage;


/**
 * Created by Gallo(chillon) on 10/17/17.
 */
public class InquilinoForm_Controller implements Initializable {

  @FXML
  private TextField nombreField;
  @FXML
  private TextField direccionField;
  @FXML
  private TextField edadField;
  @FXML
  private TextField padecimientoField;
  @FXML
  private TextField cuartoField;

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private Inquilino inquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
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

  public void setInquilino(Inquilino inquilino) {
    this.inquilino = inquilino;
  }

  public void transition_Back() throws Exception {
    String newScene = "Views/inquilinos.fxml";
    Main.changeScene(newScene);

  }

  public void add_Inquilino() throws Exception {
    Uuid uuid = uuidGenerator.make();
    String nombre = nombreField.getText();
    String direccion = direccionField.getText();
    int edad = Integer.parseInt(edadField.getText());
    Time fechaN = Time.now();
    String cuarto = cuartoField.getText();

    Inquilino newInquilino = new Inquilino(uuid, nombre, direccion, edad, fechaN, cuarto);


    inquilino_model.add(newInquilino);

    String newScene = "Views/inquilinos.fxml";
    Main.changeScene(newScene);

  }
}
