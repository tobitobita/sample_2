package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.diagram.DiagramState;
import javafx.beans.property.ObjectProperty;

public interface ModeChangeable {

    public ObjectProperty<DiagramState> modeProperty();

    void setMode(DiagramState mode);

    DiagramState getMode();
}
