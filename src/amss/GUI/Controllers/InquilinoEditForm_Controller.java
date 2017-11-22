package amss.GUI.Controllers;

import amss.app.Connection.Familiar_Model;
import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 25/10/17.
 */
public class InquilinoEditForm_Controller implements Initializable{

  @FXML
  private Label inqID = new Label();

  @FXML
  private TextField edNombreField;
  @FXML
  private TextField edDireccionField;
  @FXML
  private ChoiceBox<String> edEstatusField;
  @FXML
  private TextField edCuartoField;
  @FXML
  private TextField edPadecimientoField;
  @FXML
  private DatePicker edFechaNacField;
  @FXML
  private ChoiceBox<String> edFamiliaresField;

  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Familiar_Model familiar_model = new Familiar_Model();
  private Inquilino selectedInquilino;
  private Stage prevStage;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    edEstatusField.getItems().add("Activo");
    edEstatusField.getItems().add("Inactivo");
    edEstatusField.getItems().add("Fallecido");
  }

  public void setInquilinoInfo() {
    System.out.println(selectedInquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(selectedInquilino.getId()));
    this.edNombreField.setText(selectedInquilino.getNombre());
    this.edDireccionField.setText(selectedInquilino.getDireccion());
    this.edCuartoField.setText(selectedInquilino.getCuarto());
    this.edPadecimientoField.setText(selectedInquilino.getPadecimientos());

    if(!selectedInquilino.getIdResponsable().equals(Uuid.NULL)) {
      this.edFamiliaresField.setValue(familiar_model.getSingleFamiliarById(selectedInquilino.getIdResponsable()).iterator().next().getNombre());
    }

    switch (selectedInquilino.getEstatus()) {
      case 'a':
        edEstatusField.setValue("Activo");
        break;
      case 'i':
        edEstatusField.setValue("Inactivo");
        break;
      case 'f':
        edEstatusField.setValue("Fallecido");
        break;
    }
  }

  public void setSelectedInquilino(Inquilino inquilino) {
    this.selectedInquilino = inquilino;
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void loadInfo() {
    Collection<Familiar> familiares = familiar_model.getFamiliaresOfInquilino(this.selectedInquilino);

    for(Familiar familiar : familiares) {
      edFamiliaresField.getItems().add(familiar.getNombre());
    }
  }

  public void update_Inquilino() throws Exception {
    if(edNombreField.getText().isEmpty() || edDireccionField.getText().isEmpty() || edCuartoField.getText().isEmpty() || edPadecimientoField.getText().isEmpty()) {
      return;
    }

    String nombre = edNombreField.getText();
    String direccion = edDireccionField.getText();
    Time fechaN = selectedInquilino.getFechaNacimiento();
    String cuarto = edCuartoField.getText();
    Uuid responsable = selectedInquilino.getIdResponsable();
    char estatus = selectedInquilino.getEstatus();
    String padecimientos = edPadecimientoField.getText();

    if(edEstatusField.getValue().equals("Activo")) {
      estatus = 'a';
    }
    if(edEstatusField.getValue().equals("Inactivo")) {
      estatus = 'i';
    }
    if(edEstatusField.getValue().equals("Fallecido")) {
      estatus = 'f';
    }

    if(edFechaNacField.getValue() != null) {
      fechaN = Time.fromMs(Time.getDateInMs(edFechaNacField.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));
    }

    Inquilino newInquilino = new Inquilino(selectedInquilino.getId(), nombre, direccion, fechaN, responsable, estatus, cuarto, padecimientos);

    if(edFamiliaresField.getValue() != null) {
      newInquilino.setResponsable(familiar_model.getFamiliaresOfInquilinoByName(newInquilino, edFamiliaresField.getValue()).iterator().next());
    }

    this.selectedInquilino = newInquilino;

    inquilino_model.update(newInquilino);

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
