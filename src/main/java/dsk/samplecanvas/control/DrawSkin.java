package dsk.samplecanvas.control;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Skin;

public abstract class DrawSkin<T extends DrawControl> implements Skin<T> {

    private final T control;

    private Canvas canvas;

    protected DrawSkin(T skin) {
        this.control = skin;
    }

    protected T getControl() {
        return this.control;
    }

    private Canvas getCanvas() {
        if (this.canvas == null) {
            System.out.printf("CANVAS w:%f, h:%f\n", this.control.getWidth(), this.control.getHeight());
            this.canvas = new Canvas(this.control.getWidth(), this.control.getHeight());
        }
        return this.canvas;
    }

    @Override
    public Node getNode() {
        GraphicsContext ctx = this.getCanvas().getGraphicsContext2D();
        ctx.clearRect(0, 0, this.control.getWidth(), this.control.getHeight());
        this.paintComponent(ctx);
        return this.getCanvas();
    }

    protected abstract void paintComponent(GraphicsContext ctx);

    @Override
    public T getSkinnable() {
        return this.control;
    }

    @Override
    public void dispose() {
    }
}
