package amss.sample.Controllers;

import amss.app.Individuos.Inquilino;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import amss.sample.Main;


import java.net.URL;
import java.util.*;


import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.Initializable;
import amss.sample.Main;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import sun.misc.Perf;


/**
 * Created by German on 10/15/17.
 */
public class Inquilinos_Controller implements Initializable {

    @FXML private TableView<Inquilino> inquilinosTable =  new TableView<>();
    @FXML private TableColumn<Inquilino, Uuid> Uuid;
    @FXML private TableColumn<Inquilino, String> Nombre;
    @FXML private TableColumn<Inquilino, String> Direccion;
    @FXML private TableColumn<Inquilino, Integer> Edad;
    @FXML private TableColumn<Inquilino, String> Cuarto;


    private Inquilino_Model model = new Inquilino_Model();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String fileName=location.getFile().substring(location.getFile().lastIndexOf('/')+1,location.getFile().length());

        Uuid.setCellValueFactory(new PropertyValueFactory<Inquilino, Uuid>("Uuid"));
        Nombre.setCellValueFactory(new PropertyValueFactory<Inquilino, String>("Nombre"));
        Direccion.setCellValueFactory(new PropertyValueFactory<Inquilino, String>("Direccion"));
        Edad.setCellValueFactory(new PropertyValueFactory<Inquilino, Integer>("Edad"));
        Cuarto.setCellValueFactory(new PropertyValueFactory<Inquilino, String>("Cuarto"));

        List<Inquilino> inquilinos = parselist();
        inquilinosTable.getItems().setAll(parselist());


    }

    public void transition_Back() throws Exception{
        String newScene = "sample.fxml";
        Main.changeScene(newScene);
    }

    public void transition_Perfil() throws Exception{
        String newScene = "Views/perfil.fxml";
        Main.changeScene(newScene);
    }

    public void transition_NuevoInquilino() throws Exception{
        String newScene = "Views/inquilinoForm.fxml";
        Main.changeScene(newScene);
    }

    public void select_Inquilino() throws Exception{
      if (inquilinosTable.getSelectionModel().getSelectedItem() != null) {
          Inquilino selectedPerson = inquilinosTable.getSelectionModel().getSelectedItem();

          String newScene = "Views/perfil.fxml";
          Main.changeScene(newScene);
      }
    }


    private List<Inquilino> parselist(){
        List<Inquilino> allInquilinos = new ArrayList<>();
        Collection<Inquilino> inquilinos = model.getAllInquilinos();
        for (Inquilino inquilino: inquilinos) {
            System.out.println(inquilino.getCuarto());
            allInquilinos.add(inquilino);
        }

        return allInquilinos;
    }


}
