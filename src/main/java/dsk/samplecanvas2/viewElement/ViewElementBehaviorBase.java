package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import dsk.samplecanvas2.logging.MarkerConst;
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
	 * 親のViewElementを取得する。
	 *
	 * @return 親のViewElement。
	 */
	protected ViewElement getParentViewElement() {
		return (ViewElement) this.getControl().getParent();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param viewElement ViewElementBase。
	 */
	public ViewElementBehaviorBase(final VE viewElement) {
		super(viewElement, TRAVERSAL_BINDINGS);
	}

	// --------
	// イベント処理。
	// --------
	@Override
	public void mousePressed(MouseEvent e) {
		if (!isTarget(e)) {
			return;
		}
		log.trace(MarkerConst.MOUSE_PRESSED, "HANDLER, {}", e);
		this.getControl().setSelected(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		log.trace(MarkerConst.MOUSE_DRAGGED, "HANDLER, {}", e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		log.trace(MarkerConst.MOUSE_RELEASED, "HANDLER, {}", e);
	}

	/**
	 * クリックされた対象をターゲット（自分の描画領域）と見なすかを返す。<br>
	 * <br>
	 * クリックされるのは当然自分自身だが、そのクリック領域が描画領域外（例えば背景）の場合は兄弟・もしくは親へイベントを投げ直す。<br>
	 * そうすることにより、描画領域外クリック時にはその下に描画されているNodeがイベントに反応することができるため。<br>
	 * <br>
	 * なお、ここでは背景のみチェックしている。その他特殊な場合がある場合は各自オーバーライドすること。
	 *
	 * @param e MouseEvent。
	 * @return クリックされた対象が（自分の描画領域）だった場合はtrueを返す。
	 */
	protected boolean isTarget(MouseEvent e) {
		// 自身のクリックされた場所が背景か確認する。
		if (isBackground(e)) {
			// 背景の場合は次の兄弟を取得する。
			Optional<ViewElement> nextViewElement = this.getControl().getNextSiblingViewElement();
			// 再イベントするターゲット。
			ViewElement target = null;
			// 次の兄弟のクリック場所が描画枠内の場合はその兄弟を設定する。
			if (nextViewElement.isPresent()) {
				final ViewElement nextNode = nextViewElement.get();
				if (hitTest(e.getSceneX() - nextNode.getViewElementLayoutX(), e.getSceneY() - nextNode.getViewElementLayoutY(), 1d, 1d,
						nextNode.getViewElementLayoutX(), nextNode.getViewElementLayoutY(), nextNode.getViewElementPrefWidth(), nextNode.getViewElementPrefHeight())) {
					target = nextNode;
				}
			}
			// 再イベントするターゲットがない場合は親を設定する。
			if (target == null) {
				target = this.getParentViewElement();
			}
			// イベント再送。
			target.fireEvent(e.copyFor(target, e.getTarget()));
			// 背景なので対象ではない。
			return false;
		}
		// 背景ではないので対象である。
		return true;
	}

	/**
	 * クリック場所が背景かを取得する。
	 *
	 * @param e MouseEvent。
	 * @return クリック場所が背景の場合はtrueを返す。
	 */
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
