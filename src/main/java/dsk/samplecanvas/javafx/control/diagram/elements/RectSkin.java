package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectSkin extends ElementSkin<RectControl, RectBehavior> {

    public RectSkin(RectControl control) {
        super(control, new RectBehavior(control));
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        Canvas canvas = this.getCanvas();
        ctx.setFill(Color.RED);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
