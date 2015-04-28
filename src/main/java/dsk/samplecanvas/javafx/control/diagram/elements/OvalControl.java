package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.scene.control.Skin;

public class OvalControl extends ElementControl {

    public OvalControl(int number) {
        this(20d, 20d);
        this.number = number;
    }

    public OvalControl() {
        this(20d, 20d);
    }

    public OvalControl(double defaultWidth, double defaultHeight) {
        super(defaultWidth, defaultHeight);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new OvalSkin(this);
    }
}
