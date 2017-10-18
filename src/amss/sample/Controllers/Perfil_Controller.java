package amss.sample.Controllers;

import amss.app.Common.Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.SQLFormatter;
import amss.app.Connection.Inquilino_Model;
import amss.app.util.Uuid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import amss.sample.Main;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by German on 10/15/17.
 */
public class Perfil_Controller implements Initializable {

  private Inquilino selectedInquilino;
  private Inquilino_Model model = new Inquilino_Model();

  @FXML
  private Label inquilinoId = new Label();

  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
  }

  public void setSelectedInquilino(Inquilino inquilino) {
    System.out.println(inquilino.getNombre());
    this.inquilinoId.setText(SQLFormatter.sqlID(inquilino.getId()));
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void loadInfo() {
    Collection<Inquilino> inquilinos = null;
    try {
      inquilinos = model.getSingleInquilinoById(Uuid.parse(inquilinoId.getText()));
    } catch (IOException e) {}

    selectedInquilino = inquilinos.iterator().next();
    System.out.println(inquilinoId.getText());
  }

  public void transition_Back() throws Exception {
    String newScene = "Views/inquilinos.fxml";
    Main.changeScene(newScene);
  }

  public void transition_RecetaForm() throws Exception {
    String newScene = "Views/recetaForm.fxml";
    Main.changeScene(newScene);
  }
}
