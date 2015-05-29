package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

/**
 * ダイアグラム要素を表すコントロールの基底クラス。<br>
 * ダイアグラム要素はすべてこの基底クラスを継承する。
 *
 * @author tobitobita
 */
public abstract class ElementControl extends Control {

    private static int count = 0;

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
    /**
     * ダイアグラムのClickX。<br>
     * Pressed、Releasedで更新される。
     */
    private final DoubleProperty diagramClickX = new SimpleDoubleProperty(this, "diagramClickX");
    /**
     * ダイアグラムのClickY。<br>
     * Pressed、Releasedで更新される。
     */
    private final DoubleProperty diagramClickY = new SimpleDoubleProperty(this, "diagramClickY");
    /**
     * ダイアグラムでドラッグした場所のwidth。<br>
     */
    private final DoubleProperty diagramDraggedWidth = new SimpleDoubleProperty(this, "diagramDraggedWidth");
    /**
     * ダイアグラムでドラッグした場所のheight。
     */
    private final DoubleProperty diagramDraggedHeight = new SimpleDoubleProperty(this, "diagramDraggedHeight");

    private final DoubleProperty relativeX = new SimpleDoubleProperty(this, "relativeX");
    private final DoubleProperty relativeY = new SimpleDoubleProperty(this, "relativeY");

    private NumberBinding calcX;
    private NumberBinding calcY;

    /**
     * コントロールの状態。<br>
     * 選択状態の場合はtrueとなる。
     */
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    /**
     * コントロールの状態。<br>
     * ドラッグ中の場合はtrueとなる。
     */
    private final BooleanProperty dragged = new SimpleBooleanProperty(this, "dragged", false);

    private final BooleanProperty resized = new SimpleBooleanProperty(this, "resized", false);

    /**
     * 移動中に呼ばれるリスナー
     */
    private final ChangeListener<Number> layoutChangeListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
        dragged.set(true);
    };

    /**
     * デバッグ用
     */
    private int number;

    /**
     * デバッグ用
     */
    int getNumber() {
        return number;
    }

    public ElementControl(double defaultWidth, double defaultHeight) {
        super();
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.initControl();
        number = ++count;
    }

    private void initControl() {
        this.setWidth(defaultWidth + (ElementSkin.OVERLAY_MARGIN * 2));
        this.setHeight(defaultHeight + (ElementSkin.OVERLAY_MARGIN * 2));
        this.selected.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            requestLayout();
        });
        this.dragged.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            requestLayout();
        });
        calcX = Bindings.add(diagramMouseMoveX, relativeX);
        calcY = Bindings.add(diagramMouseMoveY, relativeY);
    }

    public void bindLayout() {
        this.relativeX.set(this.getLayoutX() - this.diagramMouseMoveX.get());
        this.relativeY.set(this.getLayoutY() - this.diagramMouseMoveY.get());
        this.layoutXProperty().addListener(layoutChangeListener);
        this.layoutYProperty().addListener(layoutChangeListener);
        if (this.resized.get()) {
        } else {
            this.layoutXProperty().bind(calcX);
            this.layoutYProperty().bind(calcY);
        }
    }

    public void unbindLayout() {
        this.layoutXProperty().removeListener(layoutChangeListener);
        this.layoutYProperty().removeListener(layoutChangeListener);
        this.dragged.set(false);
        this.resized.set(false);
        this.layoutXProperty().unbind();
        this.layoutYProperty().unbind();
    }

    public double getCanvasLayoutX() {
        return this.getLayoutX() + ElementSkin.OVERLAY_MARGIN;
    }

    public double getCanvasLayoutY() {
        return this.getLayoutY() + ElementSkin.OVERLAY_MARGIN;
    }

    public void setCanvasLayoutX(double layoutX) {
        this.setLayoutX(layoutX - ElementSkin.OVERLAY_MARGIN);
    }

    public void setCanvasLayoutY(double layoutY) {
        this.setLayoutY(layoutY - ElementSkin.OVERLAY_MARGIN);
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

    void setResized(boolean resize) {
        this.resized.set(resize);
    }

    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    double getDiagramMouseMovedX() {
        return this.diagramMouseMoveX.get();
    }

    double getDiagramMouseMovedY() {
        return this.diagramMouseMoveY.get();
    }

    double getDiagramClickX() {
        return this.diagramClickX.get();
    }

    double getDiagramClickY() {
        return this.diagramClickY.get();
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

    /**
     * ダイアグラムのClickXプロパティ。
     *
     * @return DoubleProperty
     */
    public DoubleProperty diagramClickXProperty() {
        return this.diagramClickX;
    }

    /**
     * ダイアグラムのClickYプロパティ。
     *
     * @return DoubleProperty
     */
    public DoubleProperty diagramClickYProperty() {
        return this.diagramClickY;
    }

    /**
     * ダイアグラムでドラッグした場所のwidthプロパティ。
     *
     * @return DoubleProperty
     */
    public DoubleProperty diagramDraggedWidthProperty() {
        return this.diagramDraggedWidth;
    }

    /**
     * ダイアグラムでドラッグした場所のheightプロパティ。
     *
     * @return DoubleProperty
     */
    public DoubleProperty diagramDraggedHeightProperty() {
        return this.diagramDraggedHeight;
    }
}
