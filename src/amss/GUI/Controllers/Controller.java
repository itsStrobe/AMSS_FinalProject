package amss.GUI.Controllers;

import amss.app.Connection.*;
import amss.app.Elementos.Medicina;
import amss.app.Elementos.PacienteMedicina;
import amss.app.Elementos.Receta;
import amss.app.Elementos.RecetaMedicina;
import amss.app.Individuos.Inquilino;
import amss.app.Individuos.Staff;
import amss.app.util.Time;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala y German on 10/15/17.
 */

public class Controller implements Initializable {

  Stage prevStage;

  private Inquilino_Model inquilino_model = new Inquilino_Model();
  private Medicina_Model medicina_model = new Medicina_Model();
  private PacienteMedicina_Model pacienteMedicina_model = new PacienteMedicina_Model();
  private Receta_Model receta_model = new Receta_Model();
  private RecetaMedicina_Model recetaMedicina_model = new RecetaMedicina_Model();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    calcularPastillas();
  }

  private void calcularPastillas() {

    Medicina medicinaTemp;
    HashMap<String, Long> medicinasCount = new HashMap<>();
    for(Inquilino inquilino : inquilino_model.getAllInquilinos()) {
      medicinasCount.clear();

      for(PacienteMedicina pacienteMedicina : pacienteMedicina_model.getAllMedicinasOfPaciente(inquilino.getId())) {
        medicinaTemp = medicina_model.getSingleMedicinaByID(pacienteMedicina.getId()).iterator().next();

        medicinasCount.put(medicinaTemp.getNombre(), 0L);
      }

      for(Receta receta : receta_model.getAllRecetasOfPaciente(inquilino.getId())) {
        for(RecetaMedicina recetaMedicina : recetaMedicina_model.getAllMedicinasOfReceta(receta.getId())) {
          medicinaTemp = medicina_model.getSingleMedicinaByID(recetaMedicina.getId()).iterator().next();

          if(recetaMedicina.getEndDate().inMs() > Time.now().inMs()) {
            long perDay;
            perDay = recetaMedicina.getMorning() + recetaMedicina.getEvening() + recetaMedicina.getNight();

            long pillsLeft = perDay * Time.getDiferenceInDays(recetaMedicina.getEndDate(), Time.now());

            medicinasCount.put(medicinaTemp.getNombre(), medicinasCount.get(medicinaTemp.getNombre()) + pillsLeft);
          }

        }
      }

      for (PacienteMedicina pacienteMedicina : pacienteMedicina_model.getAllMedicinasOfPaciente(inquilino.getId())) {
        medicinaTemp = medicina_model.getSingleMedicinaByID(pacienteMedicina.getId()).iterator().next();
        System.out.println(medicinaTemp.getNombre() + " se requieren " + medicinasCount.get(medicinaTemp.getNombre()));

        if(medicinasCount.get(medicinaTemp.getNombre()) > pacienteMedicina.getCantidad()) {
          System.out.println("Faltan " + (medicinasCount.get(medicinaTemp.getNombre()) - pacienteMedicina.getCantidad()) + " pastillas para paciente " + inquilino.getNombre() + " en la medicina: " + medicinaTemp.getNombre());
        }
      }

    }
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void goToInquilinos() throws IOException {
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

  public void goToEmergencias() throws IOException {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/emergencias.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Emergencias_Controller controller = (Emergencias_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Emergencias");

    prevStage.close();

    stage.show();
  }

  @FXML
  public void goToStaff() throws IOException {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/staff.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Staff_Controller controller = (Staff_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Staff");

    prevStage.close();

    stage.show();
  }

}
