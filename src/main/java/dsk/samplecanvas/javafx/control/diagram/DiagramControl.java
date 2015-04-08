package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.Mode;
import dsk.samplecanvas.ModeChangeable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

public class DiagramControl extends Control implements ModeChangeable {

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this, "mode", Mode.SELECT);

    public DiagramControl() {
        this.setSkin(new DiagramSkin(this));
        mode.addListener((ObservableValue<? extends Mode> observable, Mode oldValue, Mode newValue) -> {
            System.out.printf("DiagramControl: %s\n", newValue);
        });
    }

    public DiagramSkin getDiagramSkin() {
        return (DiagramSkin) this.getSkin();
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
