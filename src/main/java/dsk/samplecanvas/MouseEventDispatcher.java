package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import java.util.Optional;

public interface MouseEventDispatcher {

    void mousePressed(double pressedX, double pressedY);

    void mouseReleased(double releasedX, double releasedY);

    Optional<ElementControl> getControl();
}
