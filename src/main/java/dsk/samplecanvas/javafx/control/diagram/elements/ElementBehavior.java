package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import java.util.List;

public abstract class ElementBehavior<C extends ElementControl> extends BehaviorBase<C> {

    public ElementBehavior(final C control, final List<KeyBinding> keyBindings) {
        super(control, keyBindings);
    }
}
