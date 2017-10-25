package amss.GUI.Controllers;

import javafx.fxml.Initializable;


import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by German on 10/17/17.
 */
public class Staff_Controller implements Initializable {

  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

}
