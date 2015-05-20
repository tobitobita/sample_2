package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

public abstract class ElementControl extends Control {

    private final double defaultWidth;
    private final double defaultHeight;

    private final DoubleProperty relativeX = new SimpleDoubleProperty(this, "relativeX");
    private final DoubleProperty relativeY = new SimpleDoubleProperty(this, "relativeY");

    private final DoubleProperty moveX = new SimpleDoubleProperty(this, "moveX");
    private final DoubleProperty moveY = new SimpleDoubleProperty(this, "moveY");

    private NumberBinding calcX;
    private NumberBinding calcY;

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    protected int number;

    public ElementControl(double defaultWidth, double defaultHeight) {
        super();
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.initControl();
    }

    private void initControl() {
        this.setWidth(defaultWidth + (ElementSkin.OVERLAY_MARGIN * 2));
        this.setHeight(defaultHeight + (ElementSkin.OVERLAY_MARGIN * 2));
        this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            requestLayout();
        });
        this.selected.set(false);
        calcX = Bindings.add(moveX, relativeX);
        calcY = Bindings.add(moveY, relativeY);
    }

    public double getCanvasX() {
        return this.getLayoutX() + ElementSkin.OVERLAY_MARGIN;
    }

    public void setCanvasX(double x) {
        this.setLayoutX(x - ElementSkin.OVERLAY_MARGIN);
    }

    public double getCanvasY() {
        return this.getLayoutY() + ElementSkin.OVERLAY_MARGIN;
    }

    public void setCanvasY(double y) {
        this.setLayoutY(y - ElementSkin.OVERLAY_MARGIN);
    }

    public double getCanvasWidth() {
        return ((ElementSkin) this.getSkin()).getCanvas().getWidth();
    }

    public double getCanvasHeight() {
        return ((ElementSkin) this.getSkin()).getCanvas().getHeight();
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

    public void calcRelative(double sceneX, double sceneY) {
        this.relativeX.set(this.getLayoutX() - sceneX);
        this.relativeY.set(this.getLayoutY() - sceneY);
    }

    public void bindMove(DoubleProperty sceneX, DoubleProperty sceneY) {
        this.moveX.bind(sceneX);
        this.moveY.bind(sceneY);
        this.layoutXProperty().bind(calcX);
        this.layoutYProperty().bind(calcY);
    }

    public void unbindMove() {
        this.moveX.unbind();
        this.moveY.unbind();
        this.layoutXProperty().unbind();
        this.layoutYProperty().unbind();
    }

    @Override
    public String toString() {
        return Integer.toString(this.number);
    }
}
