package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Connection.Medicina_Model;
import amss.app.Connection.RecetaMedicina_Model;
import amss.app.Connection.Receta_Model;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 25/10/17.
 */
public class RecetaView_Controller implements Initializable {

  @FXML
  private Label inqID = new Label();
  @FXML
  private Label recetaID = new Label();

  @FXML
  private TableView<RecetaMedicina> recetaMedicinaTable;
  @FXML
  private TableColumn<RecetaMedicina, Uuid> Id;
  @FXML
  private TableColumn<RecetaMedicina, String> Nombre;
  @FXML
  private TableColumn<RecetaMedicina, Time> EndDate;
  @FXML
  private TableColumn<RecetaMedicina, Integer> Morning;
  @FXML
  private TableColumn<RecetaMedicina, Integer> Evening;
  @FXML
  private TableColumn<RecetaMedicina, Integer> Night;

  private Stage prevStage;

  Inquilino selectedInquilino;
  Receta selectedReceta;

  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private final Medicina_Model medicina_model = new Medicina_Model();
  private final Receta_Model receta_model = new Receta_Model();
  private final RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();

  public void initialize(URL location, ResourceBundle resources) {
  }

  private List<RecetaMedicina> parseListRecetaMedicina() {
    setSelectedReceta();

    List<RecetaMedicina> recetaMedicinas = new ArrayList<>();

    for(RecetaMedicina recetaMedicina : recetaMedicina_model.getAllMedicinasOfReceta(selectedReceta.getId())) {
      Medicina medicina = medicina_model.getSingleMedicinaByID(recetaMedicina.getId()).iterator().next();
      recetaMedicinas.add(new RecetaMedicina(medicina, recetaMedicina.getRecetaId(), recetaMedicina.getMorning(), recetaMedicina.getEvening(), recetaMedicina.getNight(), recetaMedicina.getEndDate())) ;
    }

    return recetaMedicinas;
  }

  public void loadInfo() {
    setSelectedInquilino();

    // Inicializa TABLA PASTILLERO
    Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
    EndDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    Morning.setCellValueFactory(new PropertyValueFactory<>("Morning"));
    Evening.setCellValueFactory(new PropertyValueFactory<>("Evening"));
    Night.setCellValueFactory(new PropertyValueFactory<>("Night"));

    List<RecetaMedicina> pastilleroViewList = parseListRecetaMedicina();
    recetaMedicinaTable.getItems().setAll(pastilleroViewList);
  }

  private void setSelectedInquilino() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = inquilino_model.getSingleInquilinoById(Uuid.parse(inqID.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println("This Inquilino: " + selectedInquilino.getNombre());
  }

  private void setSelectedReceta() {
    Collection<Receta> recetas = null;
    try {
      recetas = receta_model.getSingleRecetaById(Uuid.parse(recetaID.getText()));
    } catch (IOException e) {}

    selectedReceta = recetas.iterator().next();
    System.out.println("This Receta: " + selectedReceta.getId());
  }

  public void setInquilinoInfo(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inqID.setText(SQLFormatter.sqlID(inquilino.getId()));
  }

  public void setRecetaInfo(Receta receta) {
    this.recetaID.setText(SQLFormatter.sqlID(receta.getId()));
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void addMedicamento() throws Exception {

  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Views/perfil.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    setSelectedInquilino();

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
