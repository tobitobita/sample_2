package dsk.samplecanvas2.viewElement;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;

/**
 * ViewElementのベース。
 *
 * @param <S> ViewElementSkinBase。
 */
public abstract class ViewElementBase<S extends ViewElementSkinBase> extends Control implements ViewElement, Selectable {

	/**
	 * 選択状態を表すプロパティ。
	 */
	private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

	/**
	 * 選択状態を取得する。
	 *
	 * @return 選択されている場合はtrueを返す。
	 */
	@Override
	public boolean isSelected() {
		return selected.get();
	}

	/**
	 * 選択状態を設定する。
	 *
	 * @param selected true:選択、false:未選択。
	 */
	@Override
	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}

	/**
	 * 選択状態を表すプロパティ。
	 *
	 * @return 選択状態を表すプロパティ
	 */
	@Override
	public BooleanProperty selectedProperty() {
		return selected;
	}

	/**
	 * ViewElementSkinを取得する。
	 *
	 * @return ViewElementSkin。
	 */
	S getViewElementSkin() {
		return (S) super.getSkin();
	}

	/**
	 * コンストラクタ。
	 */
	public ViewElementBase() {
		super();
		// 選択状態変更時にレイアウト変更を要求する。
		this.selected.addListener((observable, newValue, oldValue) -> {
			requestLayout();
		});
	}

	/**
	 * 標準のスキンを作成する。
	 *
	 * @return 作成されたスキン。
	 */
	@Override
	protected abstract S createDefaultSkin();
}
