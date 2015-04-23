package dsk.samplejavafx8.controls;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class SampleControl extends Control {

    public SampleControl() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SampleSkin(this);
    }
}
