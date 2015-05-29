package dsk.samplecanvas.javafx.control.diagram;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DiagramBehavior extends BehaviorBase<DiagramControl> {

    /**
     * クリックとして認識するのか、ドラッグとして認識するのかを内部で持つためのenum。
     */
    enum SelectType {

        DRAG,
        CLICK
    }

    private final DoubleProperty mouseMoveX = new SimpleDoubleProperty(this, "mouseMoveX");
    private final DoubleProperty mouseMoveY = new SimpleDoubleProperty(this, "mouseMoveY");

    private final DoubleProperty clickX = new SimpleDoubleProperty(this, "clickX");
    private final DoubleProperty clickY = new SimpleDoubleProperty(this, "clickY");

    private final DoubleProperty draggedWidth = new SimpleDoubleProperty(this, "draggedWidth");
    private final DoubleProperty draggedHeight = new SimpleDoubleProperty(this, "draggedHeight");

    private final EventHandler<MouseEvent> mouseMoveListener = e -> {
        this.mouseMoveX.set(e.getSceneX());
        this.mouseMoveY.set(e.getSceneY());
    };

    private double x;
    private double y;
//    private double draggedX;
//    private double draggedY;
//    private double draggedW;
//    private double draggedH;

    private SelectType selectType = SelectType.CLICK;

    private Set<ElementControl> selectedControls = new HashSet<>();
    private ElementControl pressSelectedContorl;

    public DiagramBehavior(DiagramControl control) {
        super(control, TRAVERSAL_BINDINGS);
        control.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveListener);
        control.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseMoveListener);
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        System.out.println("mousePressed");
        {
            // Click状態にする。
            selectType = SelectType.CLICK;
            // ドラッグがしない場合もあるので、そのときのRect座標を保持する。
            x = event.getSceneX();
            y = event.getSceneY();
            this.setClick(event.getSceneX(), event.getSceneX());
            draggedWidth.set(1d);
            draggedHeight.set(1d);
        }
        /*
         {
         DiagramSkin skin = (DiagramSkin) this.getControl().getSkin();
         Optional<ElementControl> nowSelected = skin.getDrawControlStream().filter((ElementControl c) -> {
         return hitTest(draggedX, draggedY, draggedW, draggedH, c.getCanvasLayoutX(), c.getCanvasLayoutY(), c.getCanvasWidth(), c.getCanvasHeight());
         }).findFirst();
         if (nowSelected.isPresent()) {
         System.out.println("HIT");
         this.pressSelectedContorl = nowSelected.get();
         if (!selectedControls.contains(this.pressSelectedContorl)) {
         selectedControls.forEach((ElementControl ctrl) -> {
         ctrl.setSelected(false);
         });
         selectedControls = new HashSet<>();
         }
         this.pressSelectedContorl.bindMove();
         selectedControls.forEach((ElementControl ctrl) -> {
         ctrl.bindMove();
         });
         this.getControl().setStatus(DiagramState.MOVE);
         }
         }
         */
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        //System.out.printf("mouseDragged, sceneX:%f, sceneY:%f\n", event.getSceneX(), event.getSceneY());
        {
            // Drag状態にする。
            selectType = SelectType.DRAG;
            // 前回のDraggedをクリアする。
            DiagramSkin skin = (DiagramSkin) this.getControl().getSkin();
            skin.clearRect(clickX.get(), clickY.get(), draggedWidth.get(), draggedHeight.get());
            // 今回のRect用座標を保持。
            if (this.x < event.getSceneX()) {
                clickX.set(this.x);
                draggedWidth.set(event.getSceneX() - this.x);
            } else {
                clickX.set(event.getSceneX());
                draggedHeight.set(this.x - event.getSceneX());
            }
            if (this.y < event.getSceneY()) {
                clickY.set(this.y);
                draggedHeight.set(event.getSceneY() - this.y);
            } else {
                clickY.set(event.getSceneY());
                draggedHeight.set(this.y - event.getSceneY());
            }
            if (this.getControl().getStatus() == DiagramState.SELECT) {
                // draggedを描画する。
                skin.drawDraggedRect(clickX.get(), clickY.get(), draggedWidth.get(), draggedHeight.get());
                selectType = SelectType.DRAG;
            }
        }
        {
            if (this.getControl().getStatus() == DiagramState.MOVE) {
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        System.out.println("mouseReleased");
        DiagramSkin skin = (DiagramSkin) this.getControl().getSkin();
        {
            // 前回をクリアする。
            skin.clearRect(clickX.get(), clickY.get(), draggedWidth.get(), draggedHeight.get());
        }
        this.setClick(event.getSceneX(), event.getSceneX());
        {
            if (this.getControl().getStatus() == DiagramState.EDIT) {
//                this.dispatcher.mouseEvent(MouseEvent.MOUSE_RELEASED, event);
//                Optional<ElementControl> elementContorl = this.dispatcher.get();
//                if (elementContorl.isPresent()) {
//                    elementsPane.getChildren().add(elementContorl.get());
//                }
                this.getControl().setStatus(DiagramState.SELECT);
            }
            /*
             if (this.getControl().getStatus() == DiagramState.MOVE) {
             this.selectedControls.forEach((ElementControl ctrl) -> {
             ctrl.unbindMove();
             });
             if (this.pressSelectedContorl != null) {
             this.pressSelectedContorl.unbindMove();
             this.pressSelectedContorl.setSelected(true);
             }
             draggedX = event.getSceneX();
             draggedY = event.getSceneY();
             draggedW = 1d;
             draggedH = 1d;
             }
             if (this.pressSelectedContorl == null || selectedControls.isEmpty()) {
             if (selectType == SelectType.DRAG) {
             this.selectedControls = skin.getDrawControlStream().map((ElementControl c) -> {
             c.setSelected(hitTest(draggedX, draggedY, draggedW, draggedH, c.getCanvasLayoutX(), c.getCanvasLayoutY(), c.getCanvasWidth(), c.getCanvasHeight()));
             return c;
             }).filter((ElementControl c) -> {
             return c.isSelected();
             }).collect(Collectors.toSet());
             } else if (selectType == SelectType.CLICK) {
             skin.getDrawControlStream().forEach(c -> {
             c.setSelected(false);
             });
             Optional<ElementControl> overControl = skin.getDrawControlStream().filter((ElementControl c) -> {
             return hitTest(draggedX, draggedY, draggedW, draggedH, c.getCanvasLayoutX(), c.getCanvasLayoutY(), c.getCanvasWidth(), c.getCanvasHeight());
             }).findFirst();
             if (overControl.isPresent()) {
             this.selectedControls = new HashSet<>();
             ElementControl ec = overControl.get();
             ec.setSelected(true);
             this.selectedControls.add(overControl.get());
             }
             }
             }
             this.pressSelectedContorl = null;
             */
             this.getControl().setStatus(DiagramState.SELECT);
        }
        // 再描画リクエスト。
        this.getControl().requestLayout();
    }

    private void setClick(double sceneX, double sceneY) {
        this.clickX.set(sceneX);
        this.clickY.set(sceneY);
    }

    DoubleProperty mouseMoveXProperty() {
        return this.mouseMoveX;
    }

    DoubleProperty mouseMoveYProperty() {
        return this.mouseMoveY;
    }

    DoubleProperty clickXProperty() {
        return this.clickX;
    }

    DoubleProperty clickYProperty() {
        return this.clickY;
    }

    DoubleProperty draggedWidthProperty() {
        return this.draggedWidth;
    }

    DoubleProperty draggedHeightProperty() {
        return this.draggedHeight;
    }
}
