package dsk.samplecanvas.control;

import javafx.scene.control.Control;
import javafx.scene.control.Skinnable;

public abstract class DrawControl<T extends DrawSkin> extends Control implements Skinnable {

    private double defaultWidth;
    private double defaultHeight;

    public DrawControl(double defaultWidth, double defaultHeight) {
        super();
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.init();
    }

    private void init() {
        this.reset();
        this.setSkin(this.getDrawSkin());
    }

    protected abstract T getDrawSkin();

    public void reset() {
        this.setWidth(defaultWidth);
        this.setHeight(defaultHeight);
    }
}
