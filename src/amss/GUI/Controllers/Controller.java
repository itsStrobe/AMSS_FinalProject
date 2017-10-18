package amss.GUI.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jose Zavala y German on 10/15/17.
 */

public class Controller implements Initializable {

  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

  //En cuanto aprenda a pasar parametros, pongo la funcion mejor. :)

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
