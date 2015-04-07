package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.Mode;
import dsk.samplecanvas.ModeChangeable;
import dsk.samplecanvas.javafx.control.diagram.layers.ElementLayerControl;
import dsk.samplecanvas.javafx.control.diagram.layers.GhostLayerControl;
import dsk.samplecanvas.javafx.control.diagram.layers.LayerBehaviour;
import java.util.Arrays;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DiagramSkin implements Skin<DiagramControl>, ModeChangeable {

    private final DiagramControl control;

    private AnchorPane pane;

    private GhostLayerControl ghostLayer;
    private ElementLayerControl elementLayer;

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this, "mode", Mode.SELECT);

    public DiagramSkin(DiagramControl control) {
        this.control = control;
        this.initialize();
    }

    private void initialize() {
        this.ghostLayer = new GhostLayerControl();
        this.elementLayer = new ElementLayerControl();
        this.mode.bindBidirectional(this.elementLayer.getLayerSkin().modeProperty());
        this.pane = new AnchorPane(this.elementLayer, this.ghostLayer);
        AnchorPane.setTopAnchor(this.ghostLayer, 0d);
        AnchorPane.setRightAnchor(this.ghostLayer, 0d);
        AnchorPane.setBottomAnchor(this.ghostLayer, 0d);
        AnchorPane.setLeftAnchor(this.ghostLayer, 0d);
        AnchorPane.setTopAnchor(this.elementLayer, 0d);
        AnchorPane.setRightAnchor(this.elementLayer, 0d);
        AnchorPane.setBottomAnchor(this.elementLayer, 0d);
        AnchorPane.setLeftAnchor(this.elementLayer, 0d);
        this.pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.printf("Diagram, %s\n", event);
            this.mouseEvent(MouseEvent.MOUSE_PRESSED, event, ghostLayer.getLayerSkin().getLayerBehaviour(), elementLayer.getLayerSkin().getLayerBehaviour());
            event.consume();
        });
        this.pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            this.mouseEvent(MouseEvent.MOUSE_DRAGGED, event, ghostLayer.getLayerSkin().getLayerBehaviour(), elementLayer.getLayerSkin().getLayerBehaviour());
            event.consume();
        });
        this.pane.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            System.out.printf("Diagram, %s\n", event);
            this.mouseEvent(MouseEvent.MOUSE_RELEASED, event, ghostLayer.getLayerSkin().getLayerBehaviour(), elementLayer.getLayerSkin().getLayerBehaviour());
            event.consume();
        });
    }

    private void mouseEvent(EventType<MouseEvent> eventType, MouseEvent event, LayerBehaviour... behaviours) {
        Arrays.stream(behaviours).forEach((LayerBehaviour behaviour) -> {
            behaviour.mouseEvent(eventType, event);
        });
    }

    @Override
    public DiagramControl getSkinnable() {
        return this.control;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void dispose() {
    }

    @Override
    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    public ElementLayerControl getElementLayerControl() {
        return this.elementLayer;
    }
}
