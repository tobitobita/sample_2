package dsk.samplecanvas2.viewElement;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;

/**
 * ViewElementのベース。
 *
 * @param <S> ViewElementSkinBase。
 */
public abstract class ViewElementBase<S extends ViewElementSkinBase> extends Control implements ViewElement {

	private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

	public ViewElementBase() {
		super();
		this.selected.addListener((observable, newValue, oldValue) -> {
			requestLayout();
		});
	}

	public boolean isSelected() {
		return selected.get();
	}

	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}

	public BooleanProperty selectedProperty() {
		return selected;
	}

	S getViewElementSkin() {
		return (S) super.getSkin();
	}

	/**
	 * 標準のスキンを作成する。
	 *
	 * @return 作成されたスキン。
	 */
	@Override
	protected abstract S createDefaultSkin();
}
