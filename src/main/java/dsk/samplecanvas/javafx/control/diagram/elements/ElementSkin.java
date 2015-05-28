package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import static dsk.samplecanvas.javafx.control.diagram.utilities.DiagramUtility.hitTest;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class ElementSkin<C extends ElementControl, BB extends ElementBehavior<C>> extends BehaviorSkinBase<C, BB> {

    static final double OVERLAY_MARGIN = 3d;
    private static final double SELECTED_CORNER_DIAMETER = OVERLAY_MARGIN * 2;
    private static final double SELECTED_CORNER_MARGIN = 0.5d;

    private final C control;

    private final Canvas canvas;
    private final Canvas overlayCanvas;

    public ElementSkin(C control, BB behavior) {
        super(control, behavior);
        this.control = control;
        this.canvas = new Canvas(this.control.getWidth() - SELECTED_CORNER_DIAMETER, this.control.getHeight() - SELECTED_CORNER_DIAMETER);
        this.canvas.setLayoutX(OVERLAY_MARGIN);
        this.canvas.setLayoutY(OVERLAY_MARGIN);
        this.overlayCanvas = new Canvas(this.control.getWidth(), this.control.getHeight());
        this.overlayCanvas.setLayoutX(0d);
        this.overlayCanvas.setLayoutY(0d);
        this.getChildren().addAll(this.canvas, this.overlayCanvas);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        {
            GraphicsContext ctx = this.canvas.getGraphicsContext2D();
            ctx.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            paintComponent(ctx);
        }
        {
            GraphicsContext ctx = this.overlayCanvas.getGraphicsContext2D();
            ctx.clearRect(0, 0, this.overlayCanvas.getWidth(), this.overlayCanvas.getHeight());
            if (control.isSelected() && !control.isDragged()) {
                ctx.setFill(Color.WHITE);
                ctx.fillOval(SELECTED_CORNER_MARGIN, SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.fillOval(canvas.getWidth() - SELECTED_CORNER_MARGIN, SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.fillOval(SELECTED_CORNER_MARGIN, canvas.getHeight() - SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.fillOval(canvas.getWidth() - SELECTED_CORNER_MARGIN, canvas.getHeight() - SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.setStroke(Color.BLACK);
                ctx.setLineWidth(1d);
                ctx.strokeOval(SELECTED_CORNER_MARGIN, SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.strokeOval(canvas.getWidth() - SELECTED_CORNER_MARGIN, SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.strokeOval(SELECTED_CORNER_MARGIN, canvas.getHeight() - SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
                ctx.strokeOval(canvas.getWidth() - SELECTED_CORNER_MARGIN, canvas.getHeight() - SELECTED_CORNER_MARGIN, SELECTED_CORNER_DIAMETER, SELECTED_CORNER_DIAMETER);
            }
        }
    }

    protected abstract void paintComponent(GraphicsContext ctx);

    void changeResizeCursor() {
        if (!control.isSelected()) {
            control.getScene().setCursor(Cursor.DEFAULT);
            return;
        }
        double sceneX = control.getDiagramMouseMovedX();
        double sceneY = control.getDiagramMouseMovedY();
        double layoutX = control.getCanvasLayoutX();
        double layoutY = control.getCanvasLayoutY();
        double width = control.getCanvasWidth();
        double height = control.getCanvasHeight();
        if (control.isSelected()) {
            if (hitTest(sceneX, sceneY, 1d, 1d, layoutX - 1d, layoutY - 1d, 4d, 4d)) {
                control.getScene().setCursor(Cursor.NW_RESIZE);
                control.setResize(true);
            } else if (hitTest(sceneX, sceneY, 1d, 1d, layoutX + width - 1d, layoutY - 1d, 4d, 4d)) {
                control.getScene().setCursor(Cursor.NE_RESIZE);
                control.setResize(true);
            } else if (hitTest(sceneX, sceneY, 1d, 1d, layoutX - 1d, layoutY + height - 1d, 4d, 4d)) {
                control.getScene().setCursor(Cursor.SW_RESIZE);
                control.setResize(true);
            } else if (hitTest(sceneX, sceneY, 1d, 1d, layoutX + width - 1d, layoutY + height - 1d, 4d, 4d)) {
                control.getScene().setCursor(Cursor.SE_RESIZE);
                control.setResize(true);
            } else if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY, 1d, height)
                    || hitTest(sceneX, sceneY, 1d, 1d, layoutX + width, layoutY, 1d, height)) {
                control.getScene().setCursor(Cursor.H_RESIZE);
                control.setResize(true);
            } else if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY, height, 1d)
                    || hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutY + height, height, 1d)) {
                control.getScene().setCursor(Cursor.V_RESIZE);
                control.setResize(true);
            } else {
                control.getScene().setCursor(Cursor.DEFAULT);
                control.setResize(false);
            }
        }
    }

    @Override
    public void dispose() {
    }

    protected Canvas getCanvas() {
        return this.canvas;
    }

}
