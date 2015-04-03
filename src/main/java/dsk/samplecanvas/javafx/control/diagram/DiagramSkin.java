package dsk.samplecanvas.javafx.control.diagram;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

public class DiagramSkin implements Skin<DiagramControl> {

    private final DiagramControl control;

    private Pane pane;
    private Canvas ghostCanvas;

    public DiagramSkin(DiagramControl control) {
        this.control = control;
        this.initialize();
    }

    private void initialize() {
        this.ghostCanvas = new Canvas();
        this.ghostCanvas.widthProperty().bind(this.control.widthProperty());
        this.ghostCanvas.heightProperty().bind(this.control.heightProperty());
        this.pane = new Pane(this.ghostCanvas);
        this.pane.prefWidthProperty().bind(this.control.widthProperty());
        this.pane.prefHeightProperty().bind(this.control.heightProperty());
//        this.pane.setStyle("-fx-background-color:#ffcccc;");
    }

    @Override
    public void dispose() {
    }

    @Override
    public DiagramControl getSkinnable() {
        return this.control;
    }

    @Override
    public Node getNode() {
        return pane;
    }
}
