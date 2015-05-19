package dsk.samplejavafx8.controls;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SampleSkin extends BehaviorSkinBase<SampleControl, SampleBehavior> {

    private final Canvas canvas;

    public SampleSkin(SampleControl control) {
        super(control, new SampleBehavior(control));
        canvas = new Canvas();
        canvas.widthProperty().bind(control.prefWidthProperty());
        canvas.heightProperty().bind(control.prefHeightProperty());
        this.getChildren().add(canvas);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        System.out.printf("contentX:%f, contentY:%f, contentWidth:%f, contentHeight:%f\n", contentX, contentY, contentWidth, contentHeight);
        this.paintComponent();
        this.layoutInArea(canvas, contentX, contentY, contentWidth, contentHeight, -1, HPos.LEFT, VPos.TOP);
    }

    private void paintComponent() {
        SampleControl control = this.getSkinnable();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0d, 0d, control.getPrefWidth(), control.getPrefHeight());
        if (control.isSelected()) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.BLACK);
        }
        gc.fillRect(0d, 0d, control.getPrefWidth(), control.getPrefHeight());
    }
}
