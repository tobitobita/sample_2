package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;

/**
 * ViewElementが持つ振る舞いのベース。
 *
 * @param <VE> ViewElementBase
 */
public abstract class ViewElementBehaviorBase<VE extends ViewElementBase> extends BehaviorBase<VE> {

	/**
	 * コンストラクタ。
	 *
	 * @param viewElement ViewElementBase。
	 */
	public ViewElementBehaviorBase(VE viewElement) {
		super(viewElement, TRAVERSAL_BINDINGS);
		viewElement.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			System.out.printf("FILTER, %s\n", e);
			viewElement.setSelected(true);
		});
		viewElement.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
			System.out.printf("FILTER, %s\n", e);
		});
//		viewElement.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
//			System.out.printf("HANDLER, %s\n", e);
//		});
//		viewElement.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
//			System.out.printf("HANDLER, %s\n", e);
//		});
	}

//	@Override
//	public void mousePressed(MouseEvent e) {
//		System.out.println(e);
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		System.out.println(e);
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		System.out.println(e);
//	}
}
