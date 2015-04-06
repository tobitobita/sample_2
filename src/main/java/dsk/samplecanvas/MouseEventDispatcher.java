package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.canvas.drawer.DrawControl;
import java.util.Optional;

public interface MouseEventDispatcher {

    void MousePressed(double pressedX, double pressedY);

    void MouseReleased(double releasedX, double releasedY);

    Optional<DrawControl> getControl();
}
