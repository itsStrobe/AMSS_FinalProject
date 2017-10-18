package amss.sample.Controllers;

import amss.app.Common.Model;
import javafx.fxml.Initializable;
import amss.sample.Main;
import amss.app.Connection.*;

import java.util.Collection;
import java.util.Vector;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String fileName=location.getFile().substring(location.getFile().lastIndexOf('/')+1,location.getFile().length());
    }

    //En cuanto aprenda a pasar parametros, pongo la funcion mejor. :)
    public void transition_Staff() throws Exception{
        String newScene = "Views/staff.fxml";
        Main.changeScene(newScene);
    }

    public void transition_Inquilinos() throws Exception{
        String newScene = "Views/inquilinos.fxml";
        Main.changeScene(newScene);
    }

}
