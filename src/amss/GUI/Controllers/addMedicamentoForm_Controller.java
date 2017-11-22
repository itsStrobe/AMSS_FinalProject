package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.Depositos;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
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
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 15/11/17.
 */
public class addMedicamentoForm_Controller implements Initializable{
  @FXML
  private ChoiceBox<String> medBox = new ChoiceBox<>();
  @FXML
  private TextField canText = new TextField();
  @FXML
  private ChoiceBox<String> famBox = new ChoiceBox<>();

  private Uuid.Generator uuidGenerator;
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final Familiar_Model familiar_model = new Familiar_Model();
  private final PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private final Depositos_Model depositos_model = new Depositos_Model();
  private Stage prevStage;
  private Inquilino selectedInquilino;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("110.0000000000");
    } catch (IOException ex) {
    }

    for(Medicina medicina : medicina_model.getAllMedicinas()) {
      medBox.getItems().add(medicina.getNombre());
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setSelectedInquilino(Inquilino inquilino) {
    this.selectedInquilino = inquilino;
  }

  public void setFamiliares() {
    for(Familiar familiar : familiar_model.getFamiliaresOfInquilino(selectedInquilino)) {
      famBox.getItems().add(familiar.getNombre());
    }
  }

  public void addMedicamento() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/medicamentoForm.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    medicamentoForm_Controller controller = (medicamentoForm_Controller) myLoader.getController();
    controller.setPrevStage(stage);
    controller.setSelectedInquilino(selectedInquilino );
    controller.setPrevScreen("addMedicamentoForm");

    stage.setTitle("Nuevo Medicamento");

    prevStage.close();

    stage.show();
  }

  public void create() throws Exception{
    if(medBox.getValue().isEmpty() || canText.getText().isEmpty()) {
      return;
    }

    Uuid depositoId = uuidGenerator.make();
    Medicina depositoMed = medicina_model.getSingleMedicinaByName(medBox.getValue()).iterator().next();
    Uuid depositoFam = Uuid.NULL;
    int depositoCant = Integer.parseInt(canText.getText());

    if(famBox.getValue() != null) {
      depositoFam = familiar_model.getFamiliaresOfInquilinoByName(selectedInquilino, famBox.getValue()).iterator().next().getId();
    }

    Depositos depositos = new Depositos(depositoId, selectedInquilino.getId(), depositoFam, depositoMed.getId(), depositoCant, Time.now());
    System.out.println(depositoMed.getNombre());

    depositos_model.add(depositos);

    PacienteMedicina pacienteMedicina;

    Collection<PacienteMedicina> pacienteMedicinas = pacienteMedicina_model.getSingleMedicinaOfPaciente(depositoMed.getId(), selectedInquilino.getId());

    if(pacienteMedicinas.isEmpty()) {
      pacienteMedicina_model.add(new PacienteMedicina(depositoMed, selectedInquilino.getId(), depositoCant));
    }
    else {
      pacienteMedicina = pacienteMedicina_model.getSingleMedicinaOfPaciente(depositoMed.getId(), selectedInquilino.getId()).iterator().next();
      pacienteMedicina.update(depositoCant);
      pacienteMedicina_model.update(pacienteMedicina);
    }

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
