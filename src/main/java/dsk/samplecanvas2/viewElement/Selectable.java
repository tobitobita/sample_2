package dsk.samplecanvas2.viewElement;

import javafx.beans.property.BooleanProperty;

/**
 * 選択可能である。
 */
public interface Selectable {

	/**
	 * 選択状態を取得する。
	 *
	 * @return 選択されている場合はtrueを返す。
	 */
	boolean isSelected();

	/**
	 * 選択状態を設定する。
	 *
	 * @param selected true:選択、false:未選択。
	 */
	void setSelected(boolean selected);

	/**
	 * 選択状態を表すプロパティ。
	 *
	 * @return 選択状態を表すプロパティ
	 */
	BooleanProperty selectedProperty();
}
