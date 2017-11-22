package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Individuos.Familiar;
import amss.app.Individuos.Inquilino;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by strobe on 21/11/17.
 */
public class MedicinasFaltantes_Controller implements Initializable {

  @FXML
  private VBox medicinasFaltantesGrid = new VBox();

  Stage prevStage;
  Time selectedFecha;

  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Familiar_Model familiar_model = new Familiar_Model();
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final Receta_Model receta_model = new Receta_Model();
  private final PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private final RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();

  @Override
  public void initialize(URL Location, ResourceBundle resources) {
  }

  public void setSelectedFecha(Time fecha) {
    this.selectedFecha = fecha;
  }

  public void loadInfo() {
    for(Inquilino inquilino : inquilino_model.getAllInquilinos()) {
      Collection<MedicinaView> medicinasFaltantes = calcularPastillas(inquilino.getId(), selectedFecha);
      if(!medicinasFaltantes.isEmpty()) {
        HBox newRecord = new HBox();
        Label recordInquilino = new Label();
        TableView<MedicinaView> medicinasFaltantesTable = new TableView<>();
        TableColumn<MedicinaView, String> medicinaNom = new TableColumn<>();
        TableColumn<MedicinaView, Integer> medicinaCant = new TableColumn<>();
        Label recordFamiliar = new Label();
        Label recordTelefono = new Label();

        medicinaNom.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        medicinaCant.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));

        String inquilinoInfo = "No Nombre Disponible";
        String familiarResponsableInfo = "No hay familiares";
        String familiarResponsableTel = "No TEL";

        inquilinoInfo = inquilino.getNombre();
        recordInquilino.setText(inquilinoInfo);

        medicinasFaltantesTable.getColumns().add(medicinaNom);
        medicinasFaltantesTable.getColumns().add(medicinaCant);
        medicinasFaltantesTable.getItems().setAll(medicinasFaltantes);

        Familiar familiar;
        if(!inquilino.getIdResponsable().equals(Uuid.NULL)) {
          familiar = familiar_model.getSingleFamiliarById(inquilino.getIdResponsable()).iterator().next();
          familiarResponsableInfo = familiar.getNombre();
          familiarResponsableTel = familiar.getTelefono();
        }
        else if(!familiar_model.getFamiliaresOfInquilino(inquilino).isEmpty()) {
          familiar = familiar_model.getFamiliaresOfInquilino(inquilino).iterator().next();
          familiarResponsableInfo = familiar.getNombre();
          familiarResponsableTel = familiar.getTelefono();
        }
        recordFamiliar.setText(familiarResponsableInfo);
        recordTelefono.setText(familiarResponsableTel);

        newRecord.getChildren().add(recordInquilino);
        newRecord.getChildren().add(medicinasFaltantesTable);
        newRecord.getChildren().add(recordFamiliar);
        newRecord.getChildren().add(recordTelefono);
        medicinasFaltantesGrid.getChildren().add(newRecord);
      }
    }
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  private Collection<MedicinaView> calcularPastillas(Uuid inquilino, Time selectedFecha) {
    Collection<MedicinaView> found = new ArrayList<>();

    for(PacienteMedicina pacienteMedicina : pacienteMedicina_model.getAllMedicinasOfPaciente(inquilino)) {
      MedicinaView medicinaView = new MedicinaView(pacienteMedicina, selectedFecha);
      System.out.println(medicinaView.getCantidad());
      if(medicinaView.getCantidad() < 0) {
        found.add(medicinaView);
      }
    }

    return found;
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/MainWindow.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Controller controller = (Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Casa de Retiro Luis Elizondo");

    prevStage.close();

    stage.show();
  }
}
