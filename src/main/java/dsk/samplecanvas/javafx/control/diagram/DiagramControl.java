
package dsk.samplecanvas.javafx.control.diagram;

import javafx.scene.control.Control;

public class DiagramControl extends Control {

    public DiagramControl() {
        this(100d, 100d);
    }

    public DiagramControl(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
        this.setSkin(new DiagramSkin(this));
        this.initialize();
    }

    private void initialize() {
    }
}
