package dsk.samplecanvas.javafx.control.diagram;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class DiagramControl extends Control {

    private final ObjectProperty<DiagramState> status = new SimpleObjectProperty<>(this, "status", DiagramState.SELECT);

    public ObjectProperty<DiagramState> statusProperty() {
        return this.status;
    }

    public void setStatus(DiagramState state) {
        this.status.set(state);
    }

    public DiagramState getStatus() {
        return this.status.get();
    }

    public DiagramControl() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DiagramSkin(this);
    }

}
