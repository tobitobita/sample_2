package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import dsk.samplecanvas2.logging.MarkerConst;
import static dsk.samplecanvas2.utilities.ViewElementUtility.hitTest;
import static dsk.samplecanvas2.viewElement.ViewElementSelectionModel.SelectType.SINGLE;
import static dsk.samplecanvas2.viewElement.ViewElementSkinBase.BACKGROUND_ID_PREFIX;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
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
		viewElement.addEventHandler(MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET, e -> {
			if (!isTarget(e)) {
				return;
			}
			log.trace(MarkerConst.MOUSE_DRAG_ENTERED, "HANDLER, {}", e);
			this.getControl().getOwner().select(this.getControl().getIndex());
		});
		viewElement.addEventHandler(MouseDragEvent.MOUSE_DRAG_EXITED_TARGET, e -> {
			if (!isTarget(e)) {
				return;
			}
			log.trace(MarkerConst.MOUSE_DRAG_EXITED, "HANDLER, {}", e);
			// TODO 選択エリアと比べてヒットしているか確認する。
//			this.getControl().getOwner().clearSelection(this.getControl().getIndex());
		});
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
		if (this.getControl().getOwner().getSelectType() == SINGLE) {
			this.getControl().getOwner().clearSelection();
		}
		this.getControl().getOwner().select(this.getControl().getIndex());
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
			// 背景の場合は前の兄弟を取得する。
			Optional<Node> prev = this.getControl().getPreviousSiblingNode();
			// 再イベントするターゲット。
			Node target = null;
			// 前の兄弟のクリック場所が描画枠内の場合はその兄弟を設定する。
			if (prev.isPresent()) {
				final ViewElementBase prevNode = (ViewElementBase) prev.get();
				if (hitTest(e.getSceneX(), e.getSceneY(), 1d, 1d,
						prevNode.getViewElementLayoutX(), prevNode.getViewElementLayoutY(), prevNode.getViewElementPrefWidth(), prevNode.getViewElementPrefHeight())) {
					target = prevNode;
				}
			}
			// 再イベントするターゲットがない場合は親を設定する。
			if (target == null) {
				target = this.getControl().getParent();
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
