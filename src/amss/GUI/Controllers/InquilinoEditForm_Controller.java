package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 25/10/17.
 */
public class InquilinoEditForm_Controller implements Initializable{

  @FXML
  private Label inqID = new Label();

  @FXML
  private TextField nombreField;
  @FXML
  private TextField direccionField;
  @FXML
  private TextField edadField;
  @FXML
  private TextField estatusField;
  @FXML
  private TextField cuartoField;

  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private Inquilino selectedInquilino;
  private Stage prevStage;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
    this.nombreField.setText(inquilino.getNombre());
    this.direccionField.setText(inquilino.getDireccion());
    this.edadField.setText(SQLFormatter.sqlInt(inquilino.getEdad()));
    this.estatusField.setText(SQLFormatter.sqlChar(inquilino.getEstatus()));
    this.cuartoField.setText(inquilino.getCuarto());
  }

  private void setSelectedInquilino() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println("This Inquilino: " + selectedInquilino.getNombre());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void update_Inquilino() throws Exception {
    setSelectedInquilino();

    String nombre = nombreField.getText();
    String direccion = direccionField.getText();
    int edad = Integer.parseInt(edadField.getText());
    Time fechaN = Time.now();
    String cuarto = cuartoField.getText();

    Inquilino newInquilino = new Inquilino(selectedInquilino.getId(), nombre, direccion, edad, fechaN, cuarto);


    inquilino_model.update(newInquilino);

    transition_Back();

  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/perfil.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);



    Perfil_Controller controller = (Perfil_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Informacion de Inquilino");

    prevStage.close();

    stage.show();
  }
}
