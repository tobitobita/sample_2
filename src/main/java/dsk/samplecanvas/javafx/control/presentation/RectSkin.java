package dsk.samplecanvas.javafx.control.presentation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectSkin extends DrawSkin<RectControl> {

    public RectSkin(RectControl skin) {
        super(skin);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        Canvas canvas = this.getCanvas();
        ctx.setFill(Color.RED);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
