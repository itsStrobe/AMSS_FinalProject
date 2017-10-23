package amss.GUI.Controllers;

import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.Connection.Inquilino_Model;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import amss.GUI.Main;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala y German on 10/15/17.
 */
public class Perfil_Controller implements Initializable {

  private Inquilino selectedInquilino;
  private Inquilino_Model inquilino_model = new Inquilino_Model();

  @FXML
  private Label inqID = new Label();
  @FXML
  private Label inqNombre = new Label();
  @FXML
  private Label inqDireccion = new Label();
  @FXML
  private Label inqEdad = new Label();
  @FXML
  private Label inqEstatus = new Label();
  @FXML
  private Label inqCuarto = new Label();

  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
    this.inqNombre.setText(inquilino.getNombre());
    this.inqDireccion.setText(inquilino.getDireccion());
    this.inqEdad.setText(SQLFormatter.sqlInt(inquilino.getEdad()));
    this.inqEstatus.setText(SQLFormatter.sqlChar(inquilino.getEstatus()));
    this.inqCuarto.setText(inquilino.getCuarto());
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

  public void loadInfo() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println(inqID.getText());
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/inquilinos.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Inquilinos_Controller controller = (Inquilinos_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Inquilinos");

    prevStage.close();

    stage.show();
  }

  public void agregarFamiliar() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/familiarForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

    FamiliarForm_Controller controller = (FamiliarForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setInquilinoInfo(this.selectedInquilino);

    stage.setTitle("Nuevo Familiar");

    prevStage.close();

    stage.show();
  }

  public void transition_RecetaForm() throws Exception {
    String newScene = "Views/recetaForm.fxml";
    Main.changeScene(newScene);
  }
}
