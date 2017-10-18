package amss.sample.Controllers;

import javafx.fxml.Initializable;
import amss.sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gallo(chillon) on 10/17/17.
 */
public class InquilinoForm_Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String fileName=location.getFile().substring(location.getFile().lastIndexOf('/')+1,location.getFile().length());
    }

    public void transition_Back() throws Exception{
        String newScene = "Views/inquilinos.fxml";
        Main.changeScene(newScene);
    }
}
