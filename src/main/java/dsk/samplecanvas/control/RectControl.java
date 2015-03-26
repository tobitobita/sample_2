package dsk.samplecanvas.control;

public class RectControl extends DrawControl {

    public RectControl() {
        this.setWidth(20d);
        this.setHeight(20d);
        this.setSkin(new RectSkin(this));
    }
}
