package dsk.samplecanvas.javafx.control.diagram.layers;

import dsk.samplecanvas.Mode;
import dsk.samplecanvas.ModeChangeable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

public class ElementLayerControl extends Control implements ModeChangeable {

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this, "mode", Mode.SELECT);

    public ElementLayerControl() {
        this(320d, 240d);
    }

    public ElementLayerControl(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setSkin(new ElementLayerSkin(this));
        this.mode.addListener((ObservableValue<? extends Mode> observable, Mode oldValue, Mode newValue) -> {
            System.out.printf("ElementLayerControl: %s\n", newValue);
        });
    }

    public ElementLayerSkin getLayerSkin() {
        return (ElementLayerSkin) this.getSkin();
    }

    @Override
    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    @Override
    public Mode getMode() {
        return this.mode.get();
    }

}
