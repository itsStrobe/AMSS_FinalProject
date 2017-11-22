package amss.GUI.Controllers;

import amss.app.Connection.Familiar_Model;
import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Uuid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 22/10/17.
 */
public class FamiliarForm_Controller implements Initializable{
  @FXML
  private TextField famNombre;
  @FXML
  private TextField famTelefono;
  @FXML
  private TextField famDireccion;
  @FXML
  private CheckBox famResponsable;
  @FXML
  private Label inqID = new Label();

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Familiar_Model familiar_model = new Familiar_Model();
  private Inquilino selectedInquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("102.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setSelectedInquilino(Inquilino inquilino) {
    this.selectedInquilino = inquilino;
  }

  public void add_Familiar() throws Exception {
    if(famNombre.getText().isEmpty() || famTelefono.getText().isEmpty() || famDireccion.getText().isEmpty()) {
      return;
    }

    Uuid uuid = uuidGenerator.make();
    String nombre = famNombre.getText();
    String telefono = famTelefono.getText();
    String direccion = famDireccion.getText();
    boolean isResponsable = famResponsable.isSelected();

    Familiar newFamiliar = new Familiar(uuid, this.selectedInquilino.getId(), nombre, telefono, direccion);

    if(isResponsable) {
      Inquilino newInquilino = new Inquilino(this.selectedInquilino);
      newInquilino.setResponsable(newFamiliar);
      inquilino_model.update(newInquilino);
      System.out.println();
    }

    familiar_model.add(newFamiliar);

    transition_Back();
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/perfil.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Perfil_Controller controller = (Perfil_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setInquilinoInfo(this.selectedInquilino);
    controller.setSelectedInquilino(this.selectedInquilino);
    controller.loadInfo();

    stage.setTitle("Inquilino");

    prevStage.close();

    stage.show();
  }
}
