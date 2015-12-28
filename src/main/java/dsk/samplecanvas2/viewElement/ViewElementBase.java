package dsk.samplecanvas2.viewElement;

import dsk.samplecanvas2.viewElement.diagram.DiagramBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewElementのベース。
 *
 * @param <S> ViewElementSkinBase。
 */
@Slf4j
public abstract class ViewElementBase<S extends ViewElementSkinBase> extends Control implements ViewElement, Selectable {

	private DiagramBase owner;

	/**
	 * 選択状態を表すプロパティ。
	 */
	private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

	public DiagramBase getOwner() {
		return this.owner;
	}

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
	 * インデックスを返す。
	 *
	 * @return インデックス。
	 */
	public int getIndex() {
		return this.getParent().getChildrenUnmodifiable().indexOf(this);
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
	public ViewElementBase(final DiagramBase owner) {
		super();
		this.owner = owner;
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
