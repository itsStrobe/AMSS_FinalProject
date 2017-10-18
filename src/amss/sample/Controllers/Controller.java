package amss.sample.Controllers;

import amss.app.Individuos.Inquilino;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import amss.sample.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

  Stage prevStage;

  @FXML
  private Button btnInquilinos;

  @FXML
  private Button btnStaff;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

  //En cuanto aprenda a pasar parametros, pongo la funcion mejor. :)
  public void transition_Staff() throws Exception {
    String newScene = "Views/staff.fxml";
    Main.changeScene(newScene);
  }

  public void transition_Inquilinos() throws Exception {
    String newScene = "Views/inquilinos.fxml";
    Main.changeScene(newScene);
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void goToInquilinos() throws IOException {
    Stage stage = prevStage;
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/inquilinos.fxml"));

    Pane myPane = (Pane) myLoader.load();

    Inquilinos_Controller controller = (Inquilinos_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Inquilinos");

    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);
    stage.show();
  }

  @FXML
  public void goToStaff() throws IOException {
    Stage stage = new Stage();
    stage.setTitle("Staff");
    Pane myPane = null;
    myPane = FXMLLoader.load(getClass().getResource("../Views/staff.fxml"));
    Scene scene = new Scene(myPane);
    stage.setScene(scene);

    prevStage.close();

    stage.show();
  }

}
