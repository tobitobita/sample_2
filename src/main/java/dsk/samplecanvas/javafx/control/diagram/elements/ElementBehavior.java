package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import static dsk.samplecanvas.javafx.control.diagram.utilities.DiagramUtility.hitTest;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class ElementBehavior<C extends ElementControl> extends BehaviorBase<C> {

    private final BooleanProperty exited = new SimpleBooleanProperty(this, "exited", false);

    private final ChangeListener<Number> validateMouseEntered = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
        ElementControl control = this.getControl();
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getCanvasLayoutX();
        double layoutY = control.getCanvasLayoutY();
        if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY, control.getCanvasWidth(), control.getCanvasHeight())) {
            exited.set(true);
            this.mouseEntered(new MouseEvent(MouseEvent.MOUSE_ENTERED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
        } else {
            exited.set(false);
        }
    };

    private final ChangeListener<Boolean> validatemouseExited = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
        ElementControl control = this.getControl();
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getCanvasLayoutX();
        double layoutY = control.getCanvasLayoutY();
        mouseExited(new MouseEvent(MouseEvent.MOUSE_EXITED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
    };

    public ElementBehavior(final C control, final List<KeyBinding> keyBindings) {
        super(control, keyBindings);
        control.diagramMouseMoveXProperty().addListener(validateMouseEntered);
        control.diagramMouseMoveYProperty().addListener(validateMouseEntered);
        this.exited.addListener(validatemouseExited);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("ElementBehavior.mouseExited");
        this.getControl().getScene().setCursor(Cursor.DEFAULT);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ElementControl control = this.getControl();
        if (!control.isSelected()) {
            return;
        }
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getCanvasLayoutX();
        double layoutY = control.getCanvasLayoutY();
        if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY, 1d, control.getCanvasHeight())
                || hitTest(sceneX, sceneY, 1d, 1d, layoutX + control.getCanvasWidth(), layoutY, 1d, control.getCanvasHeight())) {
            control.getScene().setCursor(Cursor.H_RESIZE);
        } else if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY, control.getCanvasWidth(), 1d)
                || hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY + control.getCanvasHeight(), control.getCanvasWidth(), 1d)) {
            control.getScene().setCursor(Cursor.V_RESIZE);
        } else {
            this.getControl().getScene().setCursor(Cursor.DEFAULT);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.printf("mouseReleased, x:%f, y:%f, sceneX:%f, sceneY:%f, screenX:%f, screenY:%f\n", e.getX(), e.getY(), e.getSceneX(), e.getSceneY(), e.getScreenX(), e.getScreenY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseDragged");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }
}
