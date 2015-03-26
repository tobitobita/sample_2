package dsk.samplecanvas.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectSkin extends DrawSkin<RectControl> {

    public RectSkin(RectControl skin) {
        super(skin);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        RectControl control = this.getControl();
        ctx.setFill(Color.RED);
        ctx.fillRect(0, 0, control.getWidth(), control.getHeight());
    }
}
