package dsk.samplecanvas.javafx.control.canvas.layer;

import javafx.scene.control.Control;

public class LayerControl extends Control {

    public LayerControl() {
        this.setSkin(new LayerSkin(this));
        this.initialize();
    }

    private void initialize() {
    }
}
