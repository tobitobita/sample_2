package dsk.samplecanvas.javafx.control.diagram;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import static dsk.samplecanvas.javafx.control.diagram.utilities.DiagramUtility.hitTest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseEvent;

public class DiagramBehavior extends BehaviorBase<DiagramControl> {

    /**
     * クリックとして認識するのか、ドラッグとして認識するのかを内部で持つためのenum。
     */
    enum SelectType {

        DRAG,
        CLICK
    }

    private double x;
    private double y;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;

    private SelectType selectType = SelectType.CLICK;

    private final DoubleProperty draggingX = new SimpleDoubleProperty(this, "draggingX");
    private final DoubleProperty draggingY = new SimpleDoubleProperty(this, "draggingY");

    private Set<ElementControl> selectedControls = new HashSet<>();
    private ElementControl pressSelectedContorl;

    public DiagramBehavior(DiagramControl control) {
        super(control, TRAVERSAL_BINDINGS);
        control.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            control.setMouseMove(e.getSceneX(), e.getSceneY());
        });
        control.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            control.setMouseMove(e.getSceneX(), e.getSceneY());
        });
    }

    @Override
    public void mouseExited(MouseEvent event) {
        System.out.println("mouseExited");
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        System.out.println("mouseEntered");
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
            draggedX = event.getSceneX();
            draggedY = event.getSceneY();
            draggedW = 1d;
            draggedH = 1d;
        }
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
                draggingX.set(event.getSceneX());
                draggingY.set(event.getSceneY());
                //this.pressSelectedContorl.calcRelative(event.getSceneX(), event.getSceneY());
                this.pressSelectedContorl.bindMove();
                selectedControls.forEach((ElementControl ctrl) -> {
                    //ctrl.calcRelative(event.getSceneX(), event.getSceneY());
                    ctrl.bindMove();
                });
                this.getControl().setStatus(DiagramState.MOVE);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        //System.out.printf("mouseDragged, sceneX:%f, sceneY:%f\n", event.getSceneX(), event.getSceneY());
        {
            // Drag状態にする。
            selectType = SelectType.DRAG;
            // 前回のDraggedをクリアする。
            DiagramSkin skin = (DiagramSkin) this.getControl().getSkin();
            skin.clearRect(draggedX, draggedY, draggedW, draggedH);
            // 今回のRect用座標を保持。
            if (this.x < event.getSceneX()) {
                draggedX = this.x;
                draggedW = event.getSceneX() - this.x;
            } else {
                draggedX = event.getSceneX();
                draggedW = this.x - event.getSceneX();
            }
            if (this.y < event.getSceneY()) {
                draggedY = this.y;
                draggedH = event.getSceneY() - this.y;
            } else {
                draggedY = event.getSceneY();
                draggedH = this.y - event.getSceneY();
            }
            if (this.getControl().getStatus() == DiagramState.SELECT) {
                // draggedを描画する。
                skin.drawDraggedRect(draggedX, draggedY, draggedW, draggedH);
                selectType = SelectType.DRAG;
            }
        }
        {
            if (this.getControl().getStatus() == DiagramState.MOVE) {
                this.draggingX.set(event.getSceneX());
                this.draggingY.set(event.getSceneY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        System.out.println("mouseReleased");
        DiagramSkin skin = (DiagramSkin) this.getControl().getSkin();
        {
            // 前回をクリアする。
            skin.clearRect(draggedX, draggedY, draggedW, draggedH);
        }
        {
            if (this.getControl().getStatus() == DiagramState.EDIT) {
//                this.dispatcher.mouseEvent(MouseEvent.MOUSE_RELEASED, event);
//                Optional<ElementControl> elementContorl = this.dispatcher.get();
//                if (elementContorl.isPresent()) {
//                    elementsPane.getChildren().add(elementContorl.get());
//                }
                this.getControl().setStatus(DiagramState.SELECT);
            }
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
            this.getControl().setStatus(DiagramState.SELECT);
        }
        // 再描画リクエスト。
        this.getControl().requestLayout();
    }
}
