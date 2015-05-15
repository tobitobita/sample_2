package dsk.samplejavafx8.controls;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SampleSkin extends BehaviorSkinBase<SampleControl, SampleBehavior> {

    private Canvas canvas;

    public SampleSkin(SampleControl control) {
        super(control, new SampleBehavior(control));
        initialize(control);
        control.requestLayout();
    }

    private void initialize(SampleControl control) {
        canvas = new Canvas();
        canvas.widthProperty().bind(control.prefWidthProperty());
        canvas.heightProperty().bind(control.prefHeightProperty());
        //this.getChildren().clear();
        this.getChildren().add(canvas);
        paintComponent();
    }

    private void paintComponent() {
        SampleControl control = this.getSkinnable();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0d, 0d, control.getPrefWidth(), control.getPrefHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0d, 0d, control.getPrefWidth(), control.getPrefHeight());
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
    }    
}
