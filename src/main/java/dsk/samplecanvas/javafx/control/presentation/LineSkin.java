package dsk.samplecanvas.javafx.control.presentation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineSkin extends DrawSkin<LineControl> {

    public LineSkin(LineControl skin) {
        super(skin);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        LineControl control = getSkinnable();
        //control.
        ctx.setStroke(Color.CHOCOLATE);
        ctx.setLineWidth(1d);
        ctx.strokeLine(control.getBeginX(), control.getBeginY(), control.getEndX(), control.getEndY());
    }
}
