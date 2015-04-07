package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.javafx.control.diagram.layers.ElementLayerControl;
import dsk.samplecanvas.javafx.control.diagram.layers.ElementLayerSkin;
import dsk.samplecanvas.javafx.control.diagram.layers.GhostLayerControl;
import dsk.samplecanvas.javafx.control.diagram.layers.GhostLayerSkin;
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
            ((GhostLayerSkin) ghostLayer.getSkin()).mousePressed(event);
            ((ElementLayerSkin) elementLayer.getSkin()).mousePressed(event);
            event.consume();
        });
        this.pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            //System.out.printf("Diagram, %s\n", event);
            ((GhostLayerSkin) ghostLayer.getSkin()).mouseDragged(event);
            ((ElementLayerSkin) elementLayer.getSkin()).mouseDragged(event);
            event.consume();
        });
        this.pane.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            System.out.printf("Diagram, %s\n", event);
            ((GhostLayerSkin) ghostLayer.getSkin()).mouseReleased(event);
            ((ElementLayerSkin) elementLayer.getSkin()).mouseReleased(event);
            event.consume();
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
