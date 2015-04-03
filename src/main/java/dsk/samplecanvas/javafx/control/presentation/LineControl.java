package dsk.samplecanvas.javafx.control.presentation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class LineControl extends DrawControl {

    private final DoubleProperty beginX = new SimpleDoubleProperty(this, "beginX");
    private final DoubleProperty beginY = new SimpleDoubleProperty(this, "beginY");
    private final DoubleProperty endX = new SimpleDoubleProperty(this, "endX");
    private final DoubleProperty endY = new SimpleDoubleProperty(this, "endY");

    public LineControl() {
        this(1d, 1d);
    }

    public LineControl(double defaultWidth, double defaultHeight) {
        super(defaultWidth, defaultHeight);
        this.beginX.set(0d);
        this.beginY.set(0d);
        this.endX.set(0d);
        this.endY.set(0d);
    }

    @Override
    protected LineSkin getDrawSkin() {
        return new LineSkin(this);
    }

    public void setBeginPoint(double x, double y) {
        this.beginX.set(x);
        this.beginY.set(y);
    }

    public void setEndPoint(double x, double y) {
        this.endX.set(x);
        this.endY.set(y);
    }

    public double getBeginX() {
        return beginX.get();
    }

    public DoubleProperty beginXProperty() {
        return beginX;
    }

    public double getBeginY() {
        return beginY.get();
    }

    public DoubleProperty beginYProperty() {
        return beginY;
    }

    public double getEndX() {
        return endX.get();
    }

    public DoubleProperty endXProperty() {
        return endX;
    }

    public double getEndY() {
        return endY.get();
    }

    public DoubleProperty endYProperty() {
        return endY;
    }
}
