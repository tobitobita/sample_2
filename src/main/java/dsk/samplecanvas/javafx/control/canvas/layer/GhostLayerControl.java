package dsk.samplecanvas.javafx.control.canvas.layer;

import javafx.scene.control.Control;

public class GhostLayerControl extends Control {

    public GhostLayerControl() {
    }

    public GhostLayerControl(double width, double height) {
        this.setSkin(new GhostLayerSkin(this));
        this.initialize();
    }

    private void initialize() {
    }
}
