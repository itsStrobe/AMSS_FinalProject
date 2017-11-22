package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.Recomendaciones_Model;
import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Recomendaciones;
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
 * Created by Jose Zavala on 1/11/17.
 */
public class RecomendacionForm_Controller implements Initializable{
  @FXML
  private TextField recoTituloField;
  @FXML
  private TextField recoContenidoField;
  @FXML
  private ChoiceBox<String> recoStaffBox;

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Staff_Model staff_model = new Staff_Model();
  private final Recomendaciones_Model recomendaciones_model = new Recomendaciones_Model();

  private Inquilino selectedInquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("109.0000000000");
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

  public void setSelectedInquilino(Inquilino inquilino) {
    this.selectedInquilino = inquilino;
  }

  public void add_Recomendacion() throws Exception {
    if(recoTituloField.getText().isEmpty() || recoContenidoField.getText().isEmpty() || recoStaffBox.getValue().isEmpty()) {
      return;
    }

    Uuid uuid = uuidGenerator.make();
    String titulo = recoTituloField.getText();
    String contenido = recoContenidoField.getText();
    Uuid inquilino = selectedInquilino.getId();
    Uuid staff = Uuid.NULL;
    Time fechaN = Time.now();

    if(recoStaffBox.getItems() != null) {
      staff = staff_model.getSingleStaffByName(recoStaffBox.getValue()).iterator().next().getId();
    }

    Recomendaciones newRecomendacion = new Recomendaciones(uuid, titulo, contenido, inquilino, staff, fechaN);


    recomendaciones_model.add(newRecomendacion);

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

    stage.setTitle("Informacion de Inquilino");

    prevStage.close();

    stage.show();
  }
}
