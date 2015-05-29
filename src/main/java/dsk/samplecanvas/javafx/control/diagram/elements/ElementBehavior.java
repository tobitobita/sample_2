package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import static dsk.samplecanvas.javafx.control.diagram.utilities.DiagramUtility.hitTest;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class ElementBehavior<C extends ElementControl> extends BehaviorBase<C> {

    private final BooleanProperty onMouse = new SimpleBooleanProperty(this, "onMouseCursor", false);

    private final ChangeListener<Number> ｍouseMovedListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
        ElementControl control = this.getControl();
        onMouse.set(hitTest(control.getDiagramMouseMovedX(), control.getDiagramMouseMovedY(), 1d, 1d,
                control.getCanvasLayoutX(), control.getCanvasLayoutY(), control.getCanvasWidth(), control.getCanvasHeight()));
    };
    private final ChangeListener<Boolean> onMouseListener = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//        if (oldValue.equals(newValue)) {
//            return;
//        }
        ElementControl control = this.getControl();
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getCanvasLayoutX();
        double layoutY = control.getCanvasLayoutY();
        if (newValue) {
            mouseEntered(new MouseEvent(MouseEvent.MOUSE_ENTERED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
        } else {
            mouseExited(new MouseEvent(MouseEvent.MOUSE_EXITED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
        }
    };

    public ElementBehavior(final C control, final List<KeyBinding> keyBindings) {
        super(control, keyBindings);
        control.diagramMouseMoveXProperty().addListener(ｍouseMovedListener);
        control.diagramMouseMoveYProperty().addListener(ｍouseMovedListener);
        this.onMouse.addListener(onMouseListener);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((ElementSkin) getControl().getSkin()).clearResizeCursor();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ((ElementSkin) getControl().getSkin()).changeResizeCursor();
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
