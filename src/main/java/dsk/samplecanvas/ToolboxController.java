package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.diagram.DiagramState;
import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import dsk.samplecanvas.javafx.control.diagram.elements.OvalControl;
import dsk.samplecanvas.javafx.control.diagram.elements.RectControl;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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

    private final ObjectProperty<DiagramState> mode = new SimpleObjectProperty<>(this, "mode", DiagramState.SELECT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mode.addListener((ObservableValue<? extends DiagramState> observable, DiagramState oldValue, DiagramState newValue) -> {
            System.out.printf("ToolboxController: %s\n", newValue);
        });
    }

    @FXML
    protected void handleSeeAction(ActionEvent event) {
        createdControl = new RectControl();
        this.mode.set(DiagramState.EDIT);
    }

    @FXML
    protected void handleActionAction(ActionEvent event) {
        createdControl = new OvalControl();
        this.mode.set(DiagramState.EDIT);
    }

    @FXML
    protected void handleNextAction(ActionEvent event) {
//        selected = 3;
//        createdControl = new LineControl();
//        this.mode.set(Mode.EDIT);
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
    public void mouseEvent(EventType<MouseEvent> type, MouseEvent event) {
        if (type == MouseEvent.MOUSE_PRESSED) {
//            if (selected == 3) {
//                LineControl.class.cast(createdControl).setBeginPoint(event.getSceneX(), event.getSceneY());
//                createdControl.setCanvasX(event.getSceneX());
//                createdControl.setCanvasY(event.getSceneY());
//            }
        } else if (type == MouseEvent.MOUSE_RELEASED) {
//            if (selected == 3) {
//                LineControl.class
//                        .cast(createdControl).setEndPoint(event.getSceneX(), event.getSceneY());
//            } else {
            createdControl.setCanvasX(event.getSceneX());
            createdControl.setCanvasY(event.getSceneY());
//            }
        }
    }

    @Override
    public Optional<ElementControl> get() {
        this.clearSelect();
        return Optional.ofNullable(this.createdControl);
    }

    private Window getWindow() {
        return this.titlebar.getScene().getWindow();
    }

    @Override
    public ObjectProperty<DiagramState> modeProperty() {
        return this.mode;
    }

    @Override
    public void setMode(DiagramState mode) {
        this.mode.set(mode);
    }

    @Override
    public DiagramState getMode() {
        return this.mode.get();
    }
}
