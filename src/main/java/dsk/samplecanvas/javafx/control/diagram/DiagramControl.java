package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class DiagramControl extends Control {

    private final ObjectProperty<DiagramState> status = new SimpleObjectProperty<>(this, "status", DiagramState.SELECT);

    public DiagramControl() {
    }

    public void addElement(ElementControl element) {
        DiagramBehavior behavior = ((DiagramSkin) this.getSkin()).getBehavior();
        element.diagramMouseMoveXProperty().bind(behavior.mouseMoveXProperty());
        element.diagramMouseMoveYProperty().bind(behavior.mouseMoveYProperty());
        element.diagramClickXProperty().bind(behavior.clickXProperty());
        element.diagramClickYProperty().bind(behavior.clickYProperty());
        element.diagramDraggedWidthProperty().bind(behavior.draggedWidthProperty());
        element.diagramDraggedHeightProperty().bind(behavior.draggedHeightProperty());
        this.getChildren().add(element);
    }

    public ObjectProperty<DiagramState> statusProperty() {
        return this.status;
    }

    public void setStatus(DiagramState state) {
        this.status.set(state);
    }

    public DiagramState getStatus() {
        return this.status.get();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DiagramSkin(this);
    }

}
