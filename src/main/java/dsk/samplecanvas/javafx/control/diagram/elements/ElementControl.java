package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

/**
 * ダイアグラム要素を表すコントロールの基底クラス。<br>
 * ダイアグラム要素はすべてこの基底クラスを継承する。
 *
 * @author tobitobita
 */
public abstract class ElementControl extends Control {

    /**
     * 幅のデフォルト値。
     */
    private final double defaultWidth;
    /**
     * 高さのデフォルト値。
     */
    private final double defaultHeight;
    /**
     * ダイアグラムのMouseMoveX。
     */
    private final DoubleProperty diagramMouseMoveX = new SimpleDoubleProperty(this, "diagramMouseMoveX");
    /**
     * ダイアグラムのMouseMoveY。
     */
    private final DoubleProperty diagramMouseMoveY = new SimpleDoubleProperty(this, "diagramMouseMoveY");

    private final DoubleProperty relativeX = new SimpleDoubleProperty(this, "relativeX");
    private final DoubleProperty relativeY = new SimpleDoubleProperty(this, "relativeY");

    private final DoubleProperty dragMoveX = new SimpleDoubleProperty(this, "dragMoveX");
    private final DoubleProperty dragMoveY = new SimpleDoubleProperty(this, "dragMoveY");

    private NumberBinding calcX;
    private NumberBinding calcY;

    /**
     * コントロールの状態。<br>
     * 選択状態の場合はtrueとなる。
     */
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    /**
     * コントロールの状態。<br>
     * ドラッグ中の場合はtrueとなる。
     */
    private final BooleanProperty dragged = new SimpleBooleanProperty(this, "dragged");

    public ElementControl(double defaultWidth, double defaultHeight) {
        super();
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.initControl();
    }

    private void initControl() {
        this.setWidth(defaultWidth + (ElementSkin.OVERLAY_MARGIN * 2));
        this.setHeight(defaultHeight + (ElementSkin.OVERLAY_MARGIN * 2));
        this.selected.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!oldValue.equals(newValue)) {
                requestLayout();
            }
        });
        this.selected.set(false);
        this.dragged.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            System.out.println(newValue);
            if (!oldValue.equals(newValue)) {
                requestLayout();
            }
        });
        calcX = Bindings.add(dragMoveX, relativeX);
        calcY = Bindings.add(dragMoveY, relativeY);

        this.dragMoveX.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            dragged.set(true);
        });
        this.dragMoveY.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            dragged.set(true);
        });
    }

    public void calcRelative(double sceneX, double sceneY) {
        this.relativeX.set(this.getLayoutX() - sceneX);
        this.relativeY.set(this.getLayoutY() - sceneY);
    }

    public void bindMove(DoubleProperty sceneX, DoubleProperty sceneY) {
        this.dragMoveX.bind(sceneX);
        this.dragMoveY.bind(sceneY);
        this.layoutXProperty().bind(calcX);
        this.layoutYProperty().bind(calcY);
    }

    public void unbindMove() {
        this.dragMoveX.unbind();
        this.dragMoveY.unbind();
        this.layoutXProperty().unbind();
        this.layoutYProperty().unbind();
        this.dragged.set(false);
    }

    public double getCanvasX() {
        return this.getLayoutX() + ElementSkin.OVERLAY_MARGIN;
    }

    public double getCanvasY() {
        return this.getLayoutY() + ElementSkin.OVERLAY_MARGIN;
    }

    public double getCanvasWidth() {
        return ((ElementSkin) this.getSkin()).getCanvas().getWidth();
    }

    public double getCanvasHeight() {
        return ((ElementSkin) this.getSkin()).getCanvas().getHeight();
    }

    public boolean isSelected() {
        return this.selected.get();
    }

    public void setSelected(boolean seledted) {
        this.selected.set(seledted);
    }

    boolean isDragged() {
        return this.dragged.get();
    }

    double getDiagramMouseMovedX() {
        return this.diagramMouseMoveX.get();
    }

    double getDiagramMouseMovedY() {
        return this.diagramMouseMoveY.get();
    }

    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    /**
     * ダイアグラムのMouseMoveXプロパティ。
     *
     * @return DoubleProperty
     */
    public DoubleProperty diagramMouseMoveXProperty() {
        return this.diagramMouseMoveX;
    }

    /**
     * ダイアグラムのMouseMoveYプロパティ。
     *
     * @return DoubleProperty
     */
    public DoubleProperty diagramMouseMoveYProperty() {
        return this.diagramMouseMoveY;
    }

}
