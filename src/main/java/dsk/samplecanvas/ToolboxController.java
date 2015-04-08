package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import dsk.samplecanvas.javafx.control.diagram.elements.LineControl;
import dsk.samplecanvas.javafx.control.diagram.elements.OvalControl;
import dsk.samplecanvas.javafx.control.diagram.elements.RectControl;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class ToolboxController implements Initializable, MouseEventDispatcher, ModeChangeable {

    @FXML
    private Pane titlebar;

    private double clickX;
    private double clickY;

    private int selected;
    private ElementControl createdControl;

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this, "mode", Mode.SELECT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mode.addListener((ObservableValue<? extends Mode> observable, Mode oldValue, Mode newValue) -> {
            System.out.printf("ToolboxController: %s\n", newValue);
        });
    }

    @FXML
    protected void handleSeeAction(ActionEvent event) {
        selected = 1;
        createdControl = new RectControl();
        this.mode.set(Mode.EDIT);
    }

    @FXML
    protected void handleActionAction(ActionEvent event) {
        selected = 2;
        createdControl = new OvalControl();
        this.mode.set(Mode.EDIT);
    }

    @FXML
    protected void handleNextAction(ActionEvent event) {
        selected = 3;
        createdControl = new LineControl();
        this.mode.set(Mode.EDIT);
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
    public void mousePressed(double pressedX, double pressedY) {
        if (selected == 3) {
            LineControl.class.cast(createdControl).setBeginPoint(pressedX, pressedY);
            createdControl.setCanvasX(pressedX);
            createdControl.setCanvasY(pressedY);
        }
    }

    @Override
    public void mouseReleased(double releasedX, double releasedY) {
        if (selected == 3) {
            LineControl.class.cast(createdControl).setEndPoint(releasedX, releasedY);
        } else {
            createdControl.setCanvasX(releasedX);
            createdControl.setCanvasY(releasedY);
        }
    }

    @Override
    public Optional<ElementControl> getControl() {
        this.clearSelect();
        return Optional.ofNullable(this.createdControl);
    }

    private Window getWindow() {
        return this.titlebar.getScene().getWindow();
    }

    @Override
    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    @Override
    public Mode getMode() {
        return this.mode.get();
    }
}
