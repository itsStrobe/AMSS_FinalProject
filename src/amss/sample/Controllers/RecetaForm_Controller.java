package amss.sample.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import amss.sample.Main;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by German on 10/17/17.
 */
public class RecetaForm_Controller implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

  public void transition_Back() throws Exception {
    String newScene = "Views/perfil.fxml";
    Main.changeScene(newScene);
  }
}
