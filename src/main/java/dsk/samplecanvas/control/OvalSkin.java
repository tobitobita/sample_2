package dsk.samplecanvas.control;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OvalSkin extends DrawSkin<OvalControl> {

    public OvalSkin(OvalControl skin) {
        super(skin);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        Canvas canvas = this.getCanvas();
        ctx.setStroke(Color.BLUE);
        ctx.strokeOval(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
