package amss.GUI;

import amss.app.Common.Model;
import amss.GUI.Controllers.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Jose Zavala y German on 10/15/17.
 */

public class Main extends Application {

  static Stage stage;


  @Override
  public void start(Stage primaryStage) throws Exception {
    File f = new File("./Database.db");
    if(!f.exists() && !f.isDirectory()) {
      Model.createDatabase();
    }

    stage = primaryStage;
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Controllers/Views/MainWindow.fxml"));

    Pane myPane = (Pane) myLoader.load();

    Controller controller = (Controller) myLoader.getController();
    controller.setPrevStage(primaryStage);

    stage.setTitle("Casa de Retiro Luis Elizondo");

    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);
    stage.show();
  }

  public static void changeScene(String sceneName) throws Exception {
    Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
    stage.setScene(new Scene(root, 793, 547));
  }

  public static void main(String[] args) {
    launch(args);
  }
}