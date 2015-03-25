package dsk.samplecanvas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class ToolboxController implements Initializable {

    @FXML
    private Pane titlebar;

    private double clickX;

    private double clickY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void handleSeeAction(ActionEvent event) {
        System.out.printf("handleSeeAction\n");
    }

    @FXML
    protected void handleActionAction(ActionEvent event) {
        System.out.printf("handleActionAction\n");
    }

    @FXML
    protected void handleNextAction(ActionEvent event) {
        System.out.printf("handleNextAction\n");
    }

    public void postInit() {
        final Window window = getWindow();
        this.titlebar.setOnMousePressed((MouseEvent event) -> {
            clickX = event.getX();
            clickY = event.getY();
        });
        this.titlebar.setOnMouseDragged((MouseEvent event) -> {
            window.setX(event.getScreenX() - clickX);
            window.setY(event.getScreenY() - clickY);
        });
    }

    private Window getWindow() {
        return this.titlebar.getScene().getWindow();
    }
}
