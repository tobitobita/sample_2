package dsk.samplejavafx8.controls;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;

public class SampleBehavior extends BehaviorBase<SampleControl> {

    public SampleBehavior(SampleControl control) {
        super(control, TRAVERSAL_BINDINGS);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.printf("mouseReleased, x:%f, y:%f, sceneX:%f, sceneY:%f, screenX:%f, screenY:%f\n", e.getX(), e.getY(), e.getSceneX(), e.getSceneY(), e.getScreenX(), e.getScreenY());
        //this.getControl().setSelected(!this.getControl().isSelected());
        this.getControl().requestLayout();
        //e.consume();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseDragged");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }
}
