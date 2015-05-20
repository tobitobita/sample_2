package dsk.samplecanvas.javafx.control.diagram.geometry;

/**
 * javafx.geometry.Rectangle2Dでもよいのだが、不変オブジェクトなため<br>
 * 都度生成するコストを考えて独自のRectクラスを作成する。
 *
 * @author tobitobita
 */
public class Rect {

    private double x;
    private double y;
    private double w;
    private double h;
    private double maxX;
    private double maxY;

    public Rect(double x, double y, double w, double h) {
        this.setValues(x, y, w, h);
    }

    private void setValues(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.maxX = x + w;
        this.maxY = y + h;
    }

    /**
     * 値を設定する。
     *
     * @param x x
     * @param y y
     * @param w width
     * @param h height
     */
    public void set(double x, double y, double w, double h) {
        this.setValues(x, y, w, h);
    }

    /**
     * Rectの左上を表すx。
     *
     * @return x
     */
    public double getMixX() {
        return this.x;
    }

    /**
     * Rectの左上を表すy。
     *
     * @return y
     */
    public double getMixY() {
        return this.x;
    }

    /**
     * Rectの幅。
     *
     * @return w
     */
    public double getWidth() {
        return this.w;
    }

    /**
     * Rectの高さ。
     *
     * @return h
     */
    public double getHeight() {
        return this.h;
    }

    /**
     * Rectの右下を表すx。
     *
     * @return x
     */
    public double getMaxX() {
        return this.maxX;
    }

    /**
     * Rectの右下を表すy。
     *
     * @return y
     */
    public double getMaxY() {
        return this.maxY;
    }
}
