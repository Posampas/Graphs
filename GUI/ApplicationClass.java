package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationClass extends Application  {

    static KeyEvent keyEvent;
    static boolean newCode;
//    Tu zostanie stworzony lekki potorek ale inaczej to poprostu nie działa. Więc hadlery zdarzeń
//    Będą tutaj ale controler aplikacji jest w innej klasie dlatego info na temat kodu zdarzenia będzie
//     do kontrolera ciekawe czy to zadziała

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = (BorderPane)loader.load(getClass().getResource("GraphGUI.fxml").openStream());
            Scene scene = new Scene(root);

            scene.setOnKeyPressed(event -> {
                    keyEvent = event;
                    newCode = true;

            });

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
