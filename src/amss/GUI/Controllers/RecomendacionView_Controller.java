package amss.GUI.Controllers;

import amss.app.Connection.Staff_Model;
import amss.app.Elementos.Recomendaciones;
import amss.app.Individuos.Inquilino;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala on 1/11/17.
 */
public class RecomendacionView_Controller implements Initializable{
  @FXML
  private Label recoTituloLabel = new Label();
  @FXML
  private Label recoContenidoLabel = new Label();
  @FXML
  private Label recoStaffLabel = new Label();

  private Stage prevStage;
  private Inquilino selectedInquilino;
  private Recomendaciones selectedRecomendacion;

  private Staff_Model staff_model = new Staff_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setSelectedInquilino(Inquilino inquilino) {
    this.selectedInquilino = inquilino;
  }

  public void setSelectedRecomendacion(Recomendaciones recomendacion) {
    this.selectedRecomendacion = recomendacion;
  }

  public void loadInfo() {
    this.recoTituloLabel.setText(selectedRecomendacion.getTitulo());
    this.recoContenidoLabel.setText(selectedRecomendacion.getContenido());
    if(!selectedRecomendacion.getStaffId().equals(Uuid.NULL)) {
      this.recoStaffLabel.setText(staff_model.getSingleStaffById(selectedRecomendacion.getStaffId()).iterator().next().getNombre());
    }
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
