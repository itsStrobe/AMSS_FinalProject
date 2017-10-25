package amss.GUI.Controllers;

import amss.app.Connection.Familiar_Model;
import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.PacienteMedicina_Model;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.SQLFormatter;
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
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 22/10/17.
 */
public class MedicinaForm_Controller implements Initializable{
  @FXML
  private TextField idLabel;
  @FXML
  private TextField cantidadLabel;
  @FXML
  private Label inqID = new Label();

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Familiar_Model familiar_model = new Familiar_Model();
  private final PacienteMedicina_Model medicina_model = new PacienteMedicina_Model();
  private Inquilino selectedInquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
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

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
  }

  private void setSelectedInquilino() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println("Selected Inquilino: " + selectedInquilino.getId());
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/perfil.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

    Perfil_Controller controller = (Perfil_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setInquilinoInfo(this.selectedInquilino);

    stage.setTitle("Inquilino");

    prevStage.close();

    stage.show();
  }

  public void add_Medicina() throws Exception {
    Uuid uuid = uuidGenerator.make();
    String idMedicina = idLabel.getText();
    Integer cantidad = Integer.parseInt(cantidadLabel.getText());

    setSelectedInquilino();

    PacienteMedicina newMedicina = new PacienteMedicina(Uuid.parse(idMedicina),this.selectedInquilino.getId(),cantidad);

    medicina_model.add(newMedicina);

    transition_Back();
  }

}
