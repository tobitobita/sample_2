package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import static dsk.samplecanvas2.utilities.ViewElementUtility.hitTest;
import static dsk.samplecanvas2.viewElement.ViewElementSkinBase.BACKGROUND_ID_PREFIX;
import java.util.List;
import javafx.scene.Node;
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
	}

	protected ViewElementBase getViewElement() {
		return (ViewElementBase) this.getControl();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isBackground(e)) {
			List<Node> list = getControl().getParent().getChildrenUnmodifiable();
			final int index = list.indexOf(getControl());
			if (index - 1 >= 0) {
				ViewElementBase nextNode = (ViewElementBase) list.get(index - 1);
				if (hitTest(e.getSceneX() - nextNode.getVirtualLayoutX(), e.getSceneY() - nextNode.getVirtualLayoutY(), 1d, 1d,
						nextNode.getVirtualLayoutX(), nextNode.getVirtualLayoutY(), nextNode.getVirtualPrefWidth(), nextNode.getVirtualPrefHeight())) {
					nextNode.fireEvent(e.copyFor(nextNode, e.getTarget()));
				} else {
					final Node parent = getControl().getParent();
					parent.fireEvent(e.copyFor(parent, e.getTarget()));
				}
			} else {
				final Node parent = getControl().getParent();
				parent.fireEvent(e.copyFor(parent, e.getTarget()));
			}
//				e.consume();
			return;
		}
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
