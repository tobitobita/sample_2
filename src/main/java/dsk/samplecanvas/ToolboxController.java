package dsk.samplecanvas;

import dsk.samplecanvas.control.DrawControl;
import dsk.samplecanvas.control.OvalControl;
import dsk.samplecanvas.control.RectControl;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class ToolboxController implements Initializable, DrawControlFactory {

    @FXML
    private Pane titlebar;

    private double clickX;
    private double clickY;

    private int selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void handleSeeAction(ActionEvent event) {
        selected = 1;
    }

    @FXML
    protected void handleActionAction(ActionEvent event) {
        selected = 2;
    }

    @FXML
    protected void handleNextAction(ActionEvent event) {
        selected = 3;
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

    @Override
    public Optional<DrawControl> createControl() {
        System.out.printf("onClickDiagram, %d\n", selected);
        DrawControl control = null;
        switch (selected) {
            case 1:
                control = new RectControl();
                break;
            case 2:
                control = new OvalControl();
                break;
            case 3:
//                addNext(pane, event);
                break;
            default:
                break;
        }
        this.clearSelect();
        return Optional.ofNullable(control);
    }

    private void clearSelect() {
        selected = 0;
    }
}
