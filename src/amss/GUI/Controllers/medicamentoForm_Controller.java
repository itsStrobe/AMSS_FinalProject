package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.Medicina_Model;
import amss.app.Connection.PacienteMedicina_Model;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.SQLFormatter;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 24/10/17.
 */
public class medicamentoForm_Controller implements Initializable {

  @FXML
  private Label inqID = new Label();
  @FXML
  private Label prev = new Label();
  @FXML
  private TextField medNombre = new TextField();

  private Uuid.Generator uuidGenerator;
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private Medicina medicina;
  private Inquilino selectedInquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("105.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
  }

  public void setPrevScreen(String prevScreen) {
    this.prev.setText(prevScreen);
  }

  private void setSelectedInquilino() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println("This Inquilino: " + selectedInquilino.getNombre());
  }

  public void create() throws Exception {
    Uuid uuid = uuidGenerator.make();
    String nombre = medNombre.getText();

    Medicina medicina = new Medicina(uuid, nombre);

    medicina_model.add(medicina);

    transition_Back();
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();

    if(prev.getText().equals("Receta")) {
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/recetaForm.fxml"));

      Pane myPane = (Pane) myLoader.load();
      Scene myScene = new Scene(myPane);
      stage.setScene(myScene);

      setSelectedInquilino();

      RecetaForm_Controller controller = (RecetaForm_Controller) myLoader.getController();
      controller.setPrevStage(stage);
      controller.setInquilinoInfo(selectedInquilino);

      stage.setTitle("Nueva Receta");
    }
    else {
      FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/perfil.fxml"));

      Pane myPane = (Pane) myLoader.load();
      Scene myScene = new Scene(myPane);
      stage.setScene(myScene);

      Perfil_Controller controller = (Perfil_Controller) myLoader.getController();
      controller.setPrevStage(stage);
      controller.setInquilinoInfo(selectedInquilino);

      stage.setTitle("Inquilino");
    }

    prevStage.close();

    stage.show();
  }
}
