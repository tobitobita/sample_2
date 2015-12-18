package dsk.samplecanvas2.diagram.viewElement;

import com.sun.javafx.scene.control.behavior.BehaviorBase;

/**
 * ViewElementが持つ振る舞いのベース。
 *
 * @param <C> ViewElementBase
 */
public abstract class ViewElementBehaviorBase<C extends ViewElementBase> extends BehaviorBase<C> {

	/**
	 * コンストラクタ。
	 *
	 * @param control ViewElement。
	 */
	public ViewElementBehaviorBase(C control) {
		super(control, TRAVERSAL_BINDINGS);
	}
}
