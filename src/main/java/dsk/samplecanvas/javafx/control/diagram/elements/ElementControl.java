package dsk.samplecanvas.javafx.control.diagram.elements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import static dsk.samplecanvas.javafx.control.diagram.utilities.DiagramUtility.hitTest;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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

        this.diagramMouseMoveX.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            validateMouseEntered();
        });
        this.diagramMouseMoveY.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            validateMouseEntered();
        });
    }

    private void validateMouseEntered() {
        double sceneX = diagramMouseMoveX.get();
        double sceneY = diagramMouseMoveY.get();
        double layoutX = this.getLayoutX();
        double layoutY = this.getLayoutY();
        if (hitTest(sceneX, sceneY, 1d, 1d, layoutX, layoutX, this.getWidth(), this.getHeight())) {
            this.mouseEntered(new MouseEvent(MouseEvent.MOUSE_ENTERED, sceneX - layoutX, sceneY - layoutY, sceneX, sceneY, MouseButton.NONE, 0, false, false, false, false, false, false, false, false, false, false, null));
        }
    }

    void mouseEntered(MouseEvent event) {
        //System.out.printf("mouseEntered!, x:%f, y:%f\n", event.getX(), event.getY());
    }

    public double getCanvasX() {
        return this.getLayoutX() + ElementSkin.OVERLAY_MARGIN;
    }

    public void setCanvasX(double x) {
        this.setLayoutX(x - ElementSkin.OVERLAY_MARGIN);
    }

    public double getCanvasY() {
        return this.getLayoutY() + ElementSkin.OVERLAY_MARGIN;
    }

    public void setCanvasY(double y) {
        this.setLayoutY(y - ElementSkin.OVERLAY_MARGIN);
    }

    public double getCanvasWidth() {
        return ((ElementSkin) this.getSkin()).getCanvas().getWidth();
    }

    public double getCanvasHeight() {
        return ((ElementSkin) this.getSkin()).getCanvas().getHeight();
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean seledted) {
        selected.set(seledted);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    boolean isDragged() {
        return dragged.get();
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
}
