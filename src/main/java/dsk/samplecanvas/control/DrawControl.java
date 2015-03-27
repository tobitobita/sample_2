package dsk.samplecanvas.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skinnable;

public abstract class DrawControl<T extends DrawSkin> extends Control implements Skinnable {

    private final double defaultWidth;
    private final double defaultHeight;
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        System.out.println(value);
        selected.set(value);
        getDrawSkin().paint();
        
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

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
        this.selected.set(false);
    }
}
