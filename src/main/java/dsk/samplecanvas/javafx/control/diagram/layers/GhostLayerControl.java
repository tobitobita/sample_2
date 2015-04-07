package dsk.samplecanvas.javafx.control.diagram.layers;

import javafx.scene.control.Control;

public class GhostLayerControl extends Control {

    public GhostLayerControl() {
        this(320d, 240d);
    }

    public GhostLayerControl(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setSkin(new GhostLayerSkin(this));
    }

    public GhostLayerSkin getLayerSkin() {
        return (GhostLayerSkin) this.getSkin();
    }
}
