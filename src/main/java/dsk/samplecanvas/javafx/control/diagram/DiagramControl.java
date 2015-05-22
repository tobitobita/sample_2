package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

    private final DoubleProperty mouseMoveX = new SimpleDoubleProperty(this, "mouseMoveX");
    private final DoubleProperty mouseMoveY = new SimpleDoubleProperty(this, "mouseMoveY");

    void setMouseMove(double x, double y) {
        this.mouseMoveX.set(x);
        this.mouseMoveY.set(y);
    }

    public DiagramControl() {
    }

    public void addElement(ElementControl element) {
        element.ownerMouseMoveXProperty().bind(this.mouseMoveX);
        element.ownerMouseMoveYProperty().bind(this.mouseMoveY);
        this.getChildren().add(element);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DiagramSkin(this);
    }

}
