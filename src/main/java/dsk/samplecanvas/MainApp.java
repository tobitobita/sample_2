package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.canvas.layer.GhostLayerControl;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
//        Scene scene = new Scene(loader.load());
        GhostLayerControl c = new GhostLayerControl(320, 240);
        //c.set
        Scene scene = new Scene(c);
//        scene.setFill(null);
        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.setResizable(false);
//        stage.setX(0);
//        stage.setY(0);
        stage.show();

//        MainController controller = loader.getController();
//        controller.show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
