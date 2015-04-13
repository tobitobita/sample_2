package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public interface MouseEventDispatcher extends Gettable<ElementControl> {

    void mouseEvent(EventType<MouseEvent> type, MouseEvent event);
}
