package dsk.samplecanvas2.utilities;

public class ViewElementUtility {

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
