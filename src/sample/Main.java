package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritablePixelFormat;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;

import java.nio.IntBuffer;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("field.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("automaton");
        primaryStage.setScene(new Scene(root, 768, 512));
        primaryStage.show();
        primaryStage.getScene().getWindow().setOnCloseRequest(new CloseRequestHandler<>(controller));
        controller.init();
        System.out.println(controller.getCellCount());
        Timer t = new Timer();
        t.schedule(new TimerTask(){
            public void run() {
                if(!controller.getPaused())
                    controller.update();
                //System.out.println(controller.getCellCount());
            }
        }, 0, 50);

    }


    public static void main(String[] args) {
        launch(args);
    }

}
