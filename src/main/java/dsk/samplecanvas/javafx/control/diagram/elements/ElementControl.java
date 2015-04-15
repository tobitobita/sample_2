package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Skinnable;
import javafx.scene.input.MouseEvent;

public abstract class ElementControl<T extends ElementSkin> extends Control implements Skinnable {

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
        this.selected.set(false);
        this.selected.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            requestLayout();
        });
        calcX = Bindings.add(moveX, relativeX);
        calcY = Bindings.add(moveY, relativeY);
        this.setSkin(this.getDrawSkin());
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            System.out.println("EventFilter(MouseEvent.MOUSE_PRESSED");
        });
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            System.out.println("EventHandler(MouseEvent.MOUSE_PRESSED");
        });
    }

    protected abstract T getDrawSkin();

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
