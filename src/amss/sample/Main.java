package amss.sample;

import amss.app.Individuos.Inquilino;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    public Inquilino selectedInquilino = new Inquilino(Uuid.NULL, "German", "pos", 24, Time.now(), "3243");

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Casa de Retiro Luis Elizondo");
        stage.setScene(new Scene(root, 793, 547));
        stage.show();
    }


    public static void changeScene(String sceneName) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
        stage.setScene(new Scene(root, 793,547));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void mamada1(Inquilino inq){
        selectedInquilino = inq;
    }

    public Inquilino mamada2(){
        return selectedInquilino;
    }
}
