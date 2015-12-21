package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

/**
 * Nodeが持つスキンのベース。
 *
 * @param <VE> ViewElement。
 * @param <B> 振る舞い。
 */
@Slf4j
public abstract class ViewElementSkinBase<VE extends ViewElementBase, B extends ViewElementBehaviorBase<VE>> extends BehaviorSkinBase<VE, B> {

	private enum WindowHolder {
		TOP_RIGHT,
		TOP_LEFT,
		BOTTOM_LEFT,
		BOTTOM_RIGHT
	}

	private final VE viewElement;

	private final BooleanProperty visibleHolders = new SimpleBooleanProperty(this, "visibleHolders");

	private Rectangle background;

	private Circle topRight;

	private double prefWidth;
	private double prefHeight;

	private boolean isInit;

	/**
	 * コンストラクタ。
	 *
	 * @param viewElement ViewElement。
	 * @param behavior 振る舞い。
	 */
	public ViewElementSkinBase(VE viewElement, B behavior) {
		super(viewElement, behavior);
		this.viewElement = viewElement;
	}

	protected abstract void doLayout(double contentWidth, double contentHeight);

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		log.trace("layoutChildren, contentWidth:{}, contentHeight:{}, width:{}, height:{},",
				contentWidth, contentHeight, this.viewElement.getPrefHeight(), this.viewElement.getPrefHeight());

//		super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
		if (this.background == null) {
			this.initBackground();
		}
//		if (this.virtualViewElement == null) {
//			initVirtualViewElement();
//		}
//		this.doLayout(this.virtualViewElement.getPrefWidth(), this.virtualViewElement.getPrefHeight());
		if (!this.isInit) {
			this.prefWidth = contentWidth;
			this.prefHeight = contentHeight;
			this.isInit = true;
		}
		this.viewElement.setPrefSize(prefWidth, prefHeight);
		this.doLayout(this.prefWidth, this.prefHeight);
		if (this.topRight == null) {
			this.initHolders();
		}
		this.visibleHolders.set(this.viewElement.isSelected());
	}

	private void initBackground() {
		this.background = new Rectangle(0d, 0d, this.viewElement.getPrefWidth(), this.viewElement.getPrefHeight());
		this.background.setFill(Color.BLACK);
		this.getChildren().add(this.background);
	}

	private void initHolders() {
		this.topRight = new Circle(0d, 0d, 4d, Color.BLACK);
		this.topRight.visibleProperty().bind(this.visibleHolders);
		this.getChildren().add(this.topRight);
	}
}
