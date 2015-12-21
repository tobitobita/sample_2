package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import static dsk.samplecanvas2.utilities.ViewElementUtility.hitTest;
import static dsk.samplecanvas2.viewElement.ViewElementSkinBase.BACKGROUND_ID_PREFIX;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

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
	public ViewElementBehaviorBase(final VE viewElement) {
		super(viewElement, TRAVERSAL_BINDINGS);
		viewElement.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			if (isBackground(e)) {
				getControl().setMouseTransparent(true);
				List<Node> list = getControl().getParent().getChildrenUnmodifiable();
				final int index = list.indexOf(viewElement);
				if (index - 1 >= 0) {
					Control nextNode = (Control) list.get(index - 1);
					if (hitTest(e.getSceneX(), e.getSceneY(), 1d, 1d,
							nextNode.getLayoutX(), nextNode.getLayoutY(), nextNode.getPrefWidth(), nextNode.getPrefHeight())) {
						nextNode.fireEvent(e.copyFor(nextNode, e.getTarget()));
					}
				}
				return;
			}
			System.out.printf("FILTER, %s\n", e);
//			viewElement.setSelected(true);
		});
//		viewElement.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
//			if (isBackground(e.getTarget())) {
//				return;
//			}
//			System.out.printf("FILTER, %s\n", e);
//		});
//		viewElement.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
//			System.out.printf("HANDLER, %s\n", e);
//		});
//		viewElement.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
//			System.out.printf("HANDLER, %s\n", e);
//		});
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		if (isBackground(e)) {
//			this.getControl().setMouseTransparent(true);
//			((Rectangle) e.getTarget()).setMouseTransparent(true);
//			return;
//		}
		System.out.printf("HANDLER, %s\n", e);
//		((VE) this.getControl()).setSelected(true);
	}

	//
	//	@Override
	//	public void mouseDragged(MouseEvent e) {
	//		System.out.println(e);
	//	}
	//
	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.printf("HANDLER, %s\n", e);
		getControl().setMouseTransparent(false);
//		((Rectangle) e.getTarget()).setMouseTransparent(false);
	}

	static boolean isBackground(final MouseEvent e) {
		if (e.getTarget() instanceof Rectangle) {
			final Rectangle rect = (Rectangle) e.getTarget();
			if (rect.getId() != null && rect.getId().indexOf(BACKGROUND_ID_PREFIX) == 0) {
				return true;
			}
		}
		return false;
	}
}
