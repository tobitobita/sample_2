package dsk.samplecanvas;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/canvas.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        
        stage.show();
        CanvasController controller = loader.getController();
        controller.show(stage);
        stage.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
