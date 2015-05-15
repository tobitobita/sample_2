package dsk.samplejavafx8.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class SampleControl extends Control {

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public boolean isSelected() {
        return this.selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    public SampleControl() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SampleSkin(this);
    }
}
