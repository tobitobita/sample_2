package dsk.samplecanvas.javafx.control.diagram.layers;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Control;

public class GhostLayerControl extends Control {

    private Canvas canvas;

    public Canvas getCanvas() {
        return canvas;
    }

    public GhostLayerControl() {
        this(320d, 240d);
    }

    public GhostLayerControl(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        GhostLayerSkin skin = new GhostLayerSkin(this);
        this.canvas = skin.getCanvas();
        this.setSkin(skin);
        this.initialize();
    }

    private void initialize() {
    }
}
