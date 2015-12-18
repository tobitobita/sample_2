package dsk.samplecanvas2.diagram.viewElement;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * ViewElementのベース。
 *
 * @param <S> ViewElementSkinBase。
 */
public abstract class ViewElementBase<S extends ViewElementSkinBase> extends Control {

	/**
	 * デフォルトViewElementスキンを取得する。
	 *
	 * @return デフォルトのViewElementSkin。
	 */
	protected abstract S createDefaultViewElementSkin();

	/**
	 * 標準のスキンを作成する。
	 *
	 * @return 作成されたスキン。
	 */
	@Override
	protected Skin<?> createDefaultSkin() {
		return this.createDefaultViewElementSkin();
	}
}
