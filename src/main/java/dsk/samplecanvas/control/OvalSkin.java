package dsk.samplecanvas.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OvalSkin extends DrawSkin<OvalControl> {

    public OvalSkin(OvalControl skin) {
        super(skin);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        OvalControl control = this.getControl();
        ctx.setStroke(Color.BLUE);
        ctx.strokeOval(0, 0, control.getWidth(), control.getHeight());
    }
}
