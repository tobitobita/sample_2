package dsk.samplecanvas.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectSkin extends DrawSkin<RectControl> {

    public RectSkin(RectControl skin) {
        super(skin);
    }

    @Override
    protected void paintComponent(GraphicsContext ctx) {
        System.out.println("paintComponent");
        RectControl control = this.getControl();
        if (control.isSelected()) {
            ctx.setFill(Color.BLACK);
            ctx.fillRect(0, 0, control.getWidth(), control.getHeight());
        } else {
            ctx.setFill(Color.RED);
            ctx.fillRect(0, 0, control.getWidth(), control.getHeight());
        }
    }
}
