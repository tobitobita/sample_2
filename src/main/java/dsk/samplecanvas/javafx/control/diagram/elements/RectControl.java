package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.scene.control.Skin;

public class RectControl extends ElementControl {

    public RectControl(int number) {
        this(20d, 20d);
        this.number = number;
    }

    public RectControl() {
        this(20d, 20d);
    }

    public RectControl(double defaultWidth, double defaultHeight) {
        super(defaultWidth, defaultHeight);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new RectSkin(this);
    }
}
