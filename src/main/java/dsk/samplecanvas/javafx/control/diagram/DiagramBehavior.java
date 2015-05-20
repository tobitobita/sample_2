package dsk.samplecanvas.javafx.control.diagram;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
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

    public DiagramBehavior(DiagramControl control) {
        super(control, TRAVERSAL_BINDINGS);
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
    public void mouseReleased(MouseEvent event) {
        System.out.println("mouseReleased");
        // 前回をクリアする。
        DiagramSkin skin = (DiagramSkin) this.getControl().getSkin();
        skin.clearRect(draggedX, draggedY, draggedW, draggedH);
        // 再描画リクエスト。
        this.getControl().requestLayout();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        System.out.println("mouseDragged");
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
        // draggedを描画する。
        skin.drawDraggedRect(draggedX, draggedY, draggedW, draggedH);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        System.out.println("mousePressed");
        // Click状態にする。
        selectType = SelectType.CLICK;
        // Draggedがない場合もあるので、そのときのRect用座標を保持する。
        x = event.getSceneX();
        y = event.getSceneY();
        draggedX = event.getSceneX();
        draggedY = event.getSceneY();
        draggedW = 1d;
        draggedH = 1d;
    }
}
