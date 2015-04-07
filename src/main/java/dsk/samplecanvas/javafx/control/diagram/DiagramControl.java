package dsk.samplecanvas.javafx.control.diagram;

import javafx.scene.control.Control;

public class DiagramControl extends Control {

    public DiagramControl() {
        this.setSkin(new DiagramSkin(this));
    }

    public DiagramSkin getDiagramSkin() {
        return (DiagramSkin) this.getSkin();
    }
}
