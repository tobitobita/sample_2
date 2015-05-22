package dsk.samplecanvas.javafx.control.diagram.utilities;

/**
 * ダイアグラムに関する関数を集めたクラス。
 *
 * @author tobitobita
 */
public final class DiagramUtility {

    /**
     * Rectに対してのhittestを判定する。
     *
     * @param sourceX
     * @param sourceY
     * @param sourceWidth
     * @param sourceHeight
     * @param targetX
     * @param targetY
     * @param targetWidth
     * @param targetHeight
     * @return hitしたらtrueを返す。
     */
    public static boolean hitTest(double sourceX, double sourceY, double sourceWidth, double sourceHeight, double targetX, double targetY, double targetWidth, double targetHeight) {
        return ((sourceX + sourceWidth > targetX) && (sourceX <= targetX + targetWidth)
                && (sourceY + sourceHeight > targetY) && (sourceY <= targetY + targetHeight));
    }
}
