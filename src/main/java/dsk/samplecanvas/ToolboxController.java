package dsk.samplecanvas;

import dsk.samplecanvas.control.DrawControl;
import dsk.samplecanvas.control.LineControl;
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
    private DrawControl createdControl;

    private ModeChanged modeChangeHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void handleSeeAction(ActionEvent event) {
        selected = 1;
        createdControl = new RectControl();
        this.modeChangeHandler.modeChanged(Mode.EDIT);
    }

    @FXML
    protected void handleActionAction(ActionEvent event) {
        selected = 2;
        createdControl = new OvalControl();
        this.modeChangeHandler.modeChanged(Mode.EDIT);
    }

    @FXML
    protected void handleNextAction(ActionEvent event) {
        selected = 3;
        createdControl = new LineControl();
        this.modeChangeHandler.modeChanged(Mode.EDIT);
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

    private void clearSelect() {
        selected = 0;
    }

    @Override
    public void setByPressed(double pressedX, double pressedY) {
        if (selected == 3) {
            LineControl.class.cast(createdControl).setBeginPoint(pressedX, pressedY);
            createdControl.setCanvasX(pressedX);
            createdControl.setCanvasY(pressedY);
        }
    }

    @Override
    public void setByReleased(double releasedX, double releasedY) {
        if (selected == 3) {
            LineControl.class.cast(createdControl).setEndPoint(releasedX, releasedY);
        } else {
            createdControl.setCanvasX(releasedX);
            createdControl.setCanvasY(releasedY);
        }
    }

    @Override
    public Optional<DrawControl> getControl() {
        this.clearSelect();
        return Optional.ofNullable(this.createdControl);
    }

    public void setModeChangeHandler(ModeChanged modeChangeHandler) {
        this.modeChangeHandler = modeChangeHandler;
    }

    private Window getWindow() {
        return this.titlebar.getScene().getWindow();
    }

}
