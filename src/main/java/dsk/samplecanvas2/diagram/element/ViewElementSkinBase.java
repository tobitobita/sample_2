package dsk.samplecanvas2.diagram.element;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;

/**
 * ViewElementが持つスキンのベース。
 *
 * @param <C> ViewElement。
 * @param <B> 振る舞い。
 */
public abstract class ViewElementSkinBase<C extends ViewElementBase, B extends ViewElementBehaviorBase<C>> extends BehaviorSkinBase<C, B> {

	private final C control;

	/**
	 * コンストラクタ。
	 *
	 * @param control ViewElement。
	 * @param behavior 振る舞い。
	 */
	public ViewElementSkinBase(C control, B behavior) {
		super(control, behavior);
		this.control = control;
	}

	protected C getViewElement() {
		return this.control;
	}
}
