package dsk.samplecanvas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MainController implements Initializable {

    private Stage toolbox;
    private Stage dialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // menu
        ToolboxController toolboxController = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toolbox.fxml"));
            Scene scene = new Scene(loader.load());
            toolbox = new Stage();
            toolbox.setScene(scene);
            toolbox.setResizable(false);
            toolbox.initStyle(StageStyle.TRANSPARENT);
            toolboxController = loader.getController();
            toolboxController.postInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // dialog
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/diagram.fxml"));
            Scene scene = new Scene(loader.load());
            dialog = new Stage();
            dialog.setScene(scene);
            dialog.initStyle(StageStyle.DECORATED);
            //DiagramController controller = loader.getController();
            //toolboxController.modeProperty().bindBidirectional(controller.modeProperty());
            //controller.set(toolboxController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(Window owner) {
        this.dialog.initOwner(owner);
        dialog.show();
        this.toolbox.initOwner(owner);
        toolbox.show();
        toolbox.setX(dialog.getX() - toolbox.getWidth());
        toolbox.setY(dialog.getY());
        dialog.requestFocus();
    }
}
