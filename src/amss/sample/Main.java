package amss.sample;

import amss.app.Individuos.Inquilino;
import amss.app.util.Time;
import amss.app.util.Uuid;

import amss.sample.Controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
//import java.awt.*;

public class Main extends Application {

  static Stage stage;

  public Inquilino selectedInquilino = new Inquilino(Uuid.NULL, "German", "pos", 24, Time.now(), "3243");

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("sample.fxml"));

    Pane myPane = (Pane) myLoader.load();

    Controller controller = (Controller) myLoader.getController();
    controller.setPrevStage(primaryStage);

    stage.setTitle("Casa de Retiro Luis Elizondo");

    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);
    stage.show();
  }

  public void MoveToInquilinos(ActionEvent e) {

  }

  public static void changeScene(String sceneName) throws Exception {
    Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
    stage.setScene(new Scene(root, 793, 547));
  }

  public static void main(String[] args) {
    launch(args);
  }
}