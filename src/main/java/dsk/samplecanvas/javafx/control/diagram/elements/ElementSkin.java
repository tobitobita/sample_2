package dsk.samplecanvas.javafx.control.diagram.elements;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class ElementSkin<C extends ElementControl, BB extends ElementBehavior<C>> extends BehaviorSkinBase<C, BB> {

    static final double OVERLAY_MARGIN = 3d;
    private static final double SELECTED_CORNER_DIAMETER = OVERLAY_MARGIN * 2;
    private static final double SELECTED_CORNER_MARGIN = 0.5d;

    private final C control;

    private final Pane pane;
    private final Canvas canvas;
    private final Canvas overlayCanvas;

    private final BooleanProperty mouseOver = new SimpleBooleanProperty(this, "mouseOver");

    public boolean isMouseOver() {
        return mouseOver.get();
    }

    public void setMouseOver(boolean value) {
        mouseOver.set(value);
    }

    public BooleanProperty mouseOverProperty() {
        return mouseOver;
    }

    public ElementSkin(C control, BB behavior) {
        super(control, behavior);
        this.control = control;
        this.canvas = new Canvas(this.control.getWidth() - SELECTED_CORNER_DIAMETER, this.control.getHeight() - SELECTED_CORNER_DIAMETER);
        this.canvas.setLayoutX(OVERLAY_MARGIN);
        this.canvas.setLayoutY(OVERLAY_MARGIN);
        this.overlayCanvas = new Canvas(this.control.getWidth(), this.control.getHeight());
        this.overlayCanvas.setLayoutX(0d);
        this.overlayCanvas.setLayoutY(0d);
        this.pane = new Pane();
        this.pane.getChildren().addAll(this.canvas, this.overlayCanvas);
    }

    /**
     * TODO
     *
     * @return
     */
    public Node getNode2() {
        {
            GraphicsContext ctx = this.canvas.getGraphicsContext2D();
            ctx.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            paintComponent(ctx);
        }
        {
            GraphicsContext ctx = this.overlayCanvas.getGraphicsContext2D();
            ctx.clearRect(0, 0, this.overlayCanvas.getWidth(), this.overlayCanvas.getHeight());
            if (control.isSelected()) {
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
        return pane;
    }

    Canvas getCanvas() {
        return this.canvas;
    }

    protected abstract void paintComponent(GraphicsContext ctx);

    @Override
    public void dispose() {
    }

    public void mouseOver(MouseEvent event) {
        System.out.printf("x: %f, y: %f\n", event.getSceneX() - this.control.getLayoutX() - OVERLAY_MARGIN, event.getSceneY() - this.control.getLayoutY() - OVERLAY_MARGIN);
    }
}
