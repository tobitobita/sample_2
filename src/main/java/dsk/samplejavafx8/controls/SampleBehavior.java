package dsk.samplejavafx8.controls;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;

public class SampleBehavior extends BehaviorBase<SampleControl> {

    public SampleBehavior(SampleControl control) {
        super(control, TRAVERSAL_BINDINGS);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        System.out.println("mouseExited");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        System.out.println("mouseReleased");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        System.out.println("mouseDragged");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        System.out.println("mousePressed");
    }
}
