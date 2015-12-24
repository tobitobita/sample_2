package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import static dsk.samplecanvas2.utilities.ViewElementUtility.hitTest;
import static dsk.samplecanvas2.viewElement.ViewElementSkinBase.BACKGROUND_ID_PREFIX;
import java.util.Optional;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewElementが持つ振る舞いのベース。
 *
 * @param <VE> ViewElementBase
 */
@Slf4j
public abstract class ViewElementBehaviorBase<VE extends ViewElementBase> extends BehaviorBase<VE> {

	/**
	 * コンストラクタ。
	 *
	 * @param viewElement ViewElementBase。
	 */
	public ViewElementBehaviorBase(final VE viewElement) {
		super(viewElement, TRAVERSAL_BINDINGS);
	}

	protected ViewElement getParentViewElement() {
		return (ViewElement) this.getControl().getParent();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isTarget(e)) {
			return;
		}
		log.trace("HANDLER, {}", e);
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

	private boolean isTarget(MouseEvent e) {
		if (isBackground(e)) {
			Optional<ViewElement> nextViewElement = this.getControl().getNextSiblingViewElement();
			ViewElement target = null;
			if (nextViewElement.isPresent()) {
				final ViewElement nextNode = nextViewElement.get();
				if (hitTest(e.getSceneX() - nextNode.getVirtualLayoutX(), e.getSceneY() - nextNode.getVirtualLayoutY(), 1d, 1d,
						nextNode.getVirtualLayoutX(), nextNode.getVirtualLayoutY(), nextNode.getVirtualPrefWidth(), nextNode.getVirtualPrefHeight())) {
					target = nextNode;
				}
			}
			if (target == null) {
				target = this.getParentViewElement();
			}
			target.fireEvent(e.copyFor(target, e.getTarget()));
			return false;
		}
		return true;
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
