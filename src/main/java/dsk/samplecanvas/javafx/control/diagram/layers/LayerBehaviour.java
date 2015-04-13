package dsk.samplecanvas.javafx.control.diagram.layers;

import javafx.event.EventType;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public interface LayerBehaviour {

    void mouseEvent(EventType<MouseEvent> eventType, MouseEvent event);

    void mouseDragEvent(EventType<MouseDragEvent> eventType, MouseDragEvent event);

    LayerBehaviour getLayerBehaviour();
}
