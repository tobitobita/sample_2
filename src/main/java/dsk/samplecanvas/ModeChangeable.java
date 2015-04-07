package dsk.samplecanvas;

import javafx.beans.property.ObjectProperty;

public interface ModeChangeable {

    public ObjectProperty<Mode> modeProperty();
    
    void setMode(Mode mode);
}
