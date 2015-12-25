package dsk.samplecanvas2.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * ログ用Markerの定義。
 */
public final class MarkerConst {

	private MarkerConst() {
	}

	// --------
	// イベントなどのマーカー。
	// --------
	/**
	 * update呼び出し。
	 */
	public static final Marker UPDATE = MarkerFactory.getMarker("update");
	/**
	 * Behavior.mousePressed呼び出し。
	 */
	public static final Marker MOUSE_PRESSED = MarkerFactory.getMarker("mousePressed");
	/**
	 * Behavior.mouseDragged呼び出し。
	 */
	public static final Marker MOUSE_DRAGGED = MarkerFactory.getMarker("mouseDragged");

	/**
	 * Behavior.mouseReleased呼び出し。
	 */
	public static final Marker MOUSE_RELEASED = MarkerFactory.getMarker("mouseReleased");

	// --------
	// Framework系のマーカー。
	// --------
	/**
	 * ViewElementBase、Skin、Behavior。
	 */
	public static final Marker VIEW_ELEMENT_BASE = MarkerFactory.getMarker("ViewElementBase");
	/**
	 * DiagramBase等。
	 */
	public static final Marker DIAGRAM_BASE = MarkerFactory.getMarker("DiagramBase");
}
