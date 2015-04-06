package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.javafx.control.diagram.layers.ElementLayerControl;
import dsk.samplecanvas.javafx.control.diagram.layers.GhostLayerControl;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DiagramSkin implements Skin<DiagramControl> {

    private final DiagramControl control;

    private AnchorPane pane;

    private GhostLayerControl ghostLayer;
    private ElementLayerControl elementLayer;

    public DiagramSkin(DiagramControl control) {
        this.control = control;
        this.initialize();
    }

    private void initialize() {
        this.ghostLayer = new GhostLayerControl();
        this.elementLayer = new ElementLayerControl();
        this.elementLayer.setLayerEventDispatcher(this.ghostLayer);
        this.pane = new AnchorPane(this.ghostLayer, this.elementLayer);
        AnchorPane.setTopAnchor(this.ghostLayer, 0d);
        AnchorPane.setRightAnchor(this.ghostLayer, 0d);
        AnchorPane.setBottomAnchor(this.ghostLayer, 0d);
        AnchorPane.setLeftAnchor(this.ghostLayer, 0d);
        AnchorPane.setTopAnchor(this.elementLayer, 0d);
        AnchorPane.setRightAnchor(this.elementLayer, 0d);
        AnchorPane.setBottomAnchor(this.elementLayer, 0d);
        AnchorPane.setLeftAnchor(this.elementLayer, 0d);
        this.pane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.printf("Diagram, %s\n", event);
//            event.consume();
            this.ghostLayer.getCanvas().fireEvent(event);
//            event.consume();
        });
        this.pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            //System.out.printf("Diagram, %s\n", event);
//            event.consume();
            this.ghostLayer.getCanvas().fireEvent(event);
//            event.consume();
        });
        this.pane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            System.out.printf("Diagram, %s\n", event);
//            event.consume();
            this.ghostLayer.getCanvas().fireEvent(event);
//            event.consume();
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
}
