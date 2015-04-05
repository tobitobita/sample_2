package dsk.samplecanvas.javafx.control.diagram;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DiagramSkin implements Skin<DiagramControl> {

    private static final double LINE_WIDTH = 1d;
    private double x;
    private double y;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;

    private final DiagramControl control;

    private Canvas ghostCanvas;

    public DiagramSkin(DiagramControl control) {
        this.control = control;
        this.initialize();
    }

    private void initialize() {
        this.ghostCanvas = new Canvas();
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.rgb(103, 176, 255));
        context.setFill(Color.rgb(103, 176, 255, 0.67d));
        context.setLineWidth(LINE_WIDTH);
        this.ghostCanvas.widthProperty().bind(this.control.widthProperty());
        this.ghostCanvas.heightProperty().bind(this.control.heightProperty());
        ghostCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("GHOST -> OnMousePressed: " + event);
            x = event.getSceneX();
            y = event.getSceneY();
            draggedX = event.getSceneX();
            draggedY = event.getSceneY();
            draggedW = 1d;
            draggedH = 1d;

        });
        ghostCanvas.setOnMouseDragged((MouseEvent event) -> {
            this.clearRect();
            if (this.x < event.getSceneX()) {
                draggedX = this.x;
                draggedW = event.getSceneX() - this.x;
            } else {
                draggedX = event.getSceneX();
                draggedW = this.x - event.getSceneX();
            }
            if (this.y < event.getSceneY()) {
                draggedY = this.y;
                draggedH = event.getSceneY() - this.y;
            } else {
                draggedY = event.getSceneY();
                draggedH = this.y - event.getSceneY();
            }
//            if (mode == Mode.SELECT) {
            context.fillRect(draggedX, draggedY, draggedW, draggedH);
            context.strokeRect(draggedX, draggedY, draggedW, draggedH);
//            }
        });
        ghostCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("GHOST -> OnMouseReleased: " + event);
            this.clearRect();
        });
    }

    private void clearRect() {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
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
        return ghostCanvas;
    }
}
