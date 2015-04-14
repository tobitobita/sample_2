package dsk.samplecanvas.javafx.control.diagram.elements;

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
    protected RectSkin getDrawSkin() {
        return new RectSkin(this);
    }

}
