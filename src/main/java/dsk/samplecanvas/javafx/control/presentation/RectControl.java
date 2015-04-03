package dsk.samplecanvas.javafx.control.presentation;

public class RectControl extends DrawControl {

    public RectControl() {
        this(20d, 20d);
    }

    public RectControl(double defaultWidth, double defaultHeight) {
        super(defaultWidth, defaultHeight);
    }

    @Override
    protected RectSkin getDrawSkin() {
        return new RectSkin(this);
    }

}
