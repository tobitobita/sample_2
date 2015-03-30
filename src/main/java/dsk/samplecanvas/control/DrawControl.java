package dsk.samplecanvas.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Skinnable;

public abstract class DrawControl<T extends DrawSkin> extends Control implements Skinnable {

    private final double defaultWidth;
    private final double defaultHeight;

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public DrawControl(double defaultWidth, double defaultHeight) {
        super();
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.initControl();
    }

    private void initControl() {
        this.setWidth(defaultWidth + (DrawSkin.OVERLAY_MARGIN * 2));
        this.setHeight(defaultHeight + (DrawSkin.OVERLAY_MARGIN * 2));
        this.selected.set(false);
        this.selected.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            requestLayout();
        });
        this.setSkin(this.getDrawSkin());
    }

    protected abstract T getDrawSkin();

    public double getCanvasX() {
        return this.getLayoutX() + DrawSkin.OVERLAY_MARGIN;
    }

    public void setCanvasX(double x) {
        this.setLayoutX(x - DrawSkin.OVERLAY_MARGIN);
    }

    public double getCanvasY() {
        return this.getLayoutY() + DrawSkin.OVERLAY_MARGIN;
    }

    public void setCanvasY(double y) {
        this.setLayoutY(y - DrawSkin.OVERLAY_MARGIN);
    }

    public double getCanvasWidth() {
        return this.getDrawSkin().getCanvas().getWidth();
    }

    public double getCanvasHeight() {
        return this.getDrawSkin().getCanvas().getHeight();
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

}
