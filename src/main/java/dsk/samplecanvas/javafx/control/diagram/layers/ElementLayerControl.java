package dsk.samplecanvas.javafx.control.diagram.layers;

import javafx.scene.control.Control;

public class ElementLayerControl extends Control {

    public ElementLayerControl() {
        this(320d, 240d);
    }

    public ElementLayerControl(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setSkin(new ElementLayerSkin(this));
    }

    public ElementLayerSkin getLayerSkin() {
        return (ElementLayerSkin) this.getSkin();
    }
}
