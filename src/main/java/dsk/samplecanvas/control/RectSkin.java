package dsk.samplecanvas.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectSkin extends DrawSkin<RectControl> {

    public RectSkin(RectControl control) {
        super(control);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        RectControl control = this.getControl();
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, control.getWidth(), control.getHeight());
    }
}
