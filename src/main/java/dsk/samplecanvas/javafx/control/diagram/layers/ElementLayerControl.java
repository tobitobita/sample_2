package dsk.samplecanvas.javafx.control.diagram.layers;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Control;

public class ElementLayerControl extends Control implements LayerEventDispatcher {

    private Node next;

    public ElementLayerControl() {
        this(320d, 240d);
    }

    public ElementLayerControl(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setSkin(new ElementLayerSkin(this));
        this.initialize();
    }

    private void initialize() {
    }

    @Override
    public void setLayerEventDispatcher(Node dispatcher) {
        this.next = dispatcher;
    }

    @Override
    public void fireLayerEvent(Event event) {
        if (this.next != null) {
            this.next.fireEvent(event);
        }
    }
}
