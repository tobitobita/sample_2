package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import static dsk.samplecanvas.javafx.control.diagram.utilities.DiagramUtility.hitTest;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class ElementBehavior<C extends ElementControl> extends BehaviorBase<C> {

    private final BooleanProperty exited = new SimpleBooleanProperty(this, "exited", false);

    public ElementBehavior(final C control, final List<KeyBinding> keyBindings) {
        super(control, keyBindings);
        control.diagramMouseMoveXProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            validateMouseEntered();
        });
        control.diagramMouseMoveYProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            validateMouseEntered();
        });
        this.exited.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            validatemouseExited();
        });
    }

    private void validatemouseExited() {
        ElementControl control = this.getControl();
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getLayoutX();
        double layoutY = control.getLayoutY();
        mouseExited(new MouseEvent(MouseEvent.MOUSE_EXITED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
    }

    private void validateMouseEntered() {
        ElementControl control = this.getControl();
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getLayoutX();
        double layoutY = control.getLayoutY();
        if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutX, control.getWidth(), control.getHeight())) {
            exited.set(true);
            this.mouseEntered(new MouseEvent(MouseEvent.MOUSE_ENTERED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
        } else {
            exited.set(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("mouseExited");
        this.getControl().getScene().setCursor(Cursor.DEFAULT);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouseEntered");
        this.getControl().getScene().setCursor(Cursor.SW_RESIZE);
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
