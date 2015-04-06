package dsk.samplecanvas.javafx.control.diagram.layers;

import javafx.event.Event;
import javafx.scene.Node;

public interface LayerEventDispatcher {

    void setLayerEventDispatcher(Node dispatcher);

    void fireLayerEvent(Event event);
}
