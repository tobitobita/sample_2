package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import static dsk.samplecanvas2.viewElement.ViewElementBase.PADDING;
import static java.lang.String.format;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

	final static String BACKGROUND_ID_PREFIX = "VIEWELEMENT-BACKGROUND-";

	private Rectangle background;

	private Pane virtualViewElement;

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

	Pane getVirtualViewElement() {
		return this.virtualViewElement;
	}

	protected abstract void doLayout(double contentWidth, double contentHeight);

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
//		log.trace("layoutChildren, contentWidth:{}, contentHeight:{}, width:{}, height:{},",
//				contentWidth, contentHeight, this.viewElement.getPrefHeight(), this.viewElement.getPrefHeight());

//		super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
		if (this.background == null) {
			this.initBackground();
		}
		if (this.virtualViewElement == null) {
			initVirtualViewElement();
		}
		this.doLayout(this.virtualViewElement.getPrefWidth(), this.virtualViewElement.getPrefHeight());
//		if (this.topRight == null) {
//			this.initHolders();
//		}
		this.visibleHolders.set(this.viewElement.isSelected());
	}

	protected void add(Node node) {
		this.virtualViewElement.getChildren().add(node);
	}

	private void initBackground() {
		this.background = new Rectangle(0d, 0d, this.viewElement.getPrefWidth(), this.viewElement.getPrefHeight());
		this.background.setId(format("%s%s", BACKGROUND_ID_PREFIX, this.viewElement.getId()));
		this.background.setFill(Color.BLACK);
//		this.background.setMouseTransparent(true);
		this.getChildren().add(this.background);
	}

	private void initVirtualViewElement() {
		this.virtualViewElement = new Pane();
		this.virtualViewElement.setPrefSize(this.viewElement.getPrefWidth() - PADDING * 2, this.viewElement.getPrefHeight() - PADDING * 2);
		this.virtualViewElement.setLayoutX(PADDING);
		this.virtualViewElement.setLayoutY(PADDING);
		this.getChildren().add(this.virtualViewElement);
	}
}
