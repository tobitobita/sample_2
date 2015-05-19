package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import java.util.List;
import javafx.scene.input.MouseEvent;

public abstract class ElementBehavior<C extends ElementControl> extends BehaviorBase<C> {

    public ElementBehavior(final C control, final List<KeyBinding> keyBindings) {
        super(control, keyBindings);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.printf("mouseReleased, x:%f, y:%f, sceneX:%f, sceneY:%f, screenX:%f, screenY:%f\n", e.getX(), e.getY(), e.getSceneX(), e.getSceneY(), e.getScreenX(), e.getScreenY());
        this.getControl().setSelected(!this.getControl().isSelected());
        this.getControl().requestLayout();
        e.consume();
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
