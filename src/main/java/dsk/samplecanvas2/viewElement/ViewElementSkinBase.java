package dsk.samplecanvas2.viewElement;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import static dsk.samplecanvas2.logging.MarkerConst.VIEW_ELEMENT_BASE;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import javafx.scene.shape.Circle;
import static javafx.scene.shape.StrokeType.INSIDE;
import static dsk.samplecanvas2.logging.MarkerConst.UPDATE;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;

/**
 * Nodeが持つスキンのベース。
 *
 * @param <VE> ViewElement。
 * @param <B> 振る舞い。
 */
@Slf4j
public abstract class ViewElementSkinBase<VE extends ViewElementBase, B extends ViewElementBehaviorBase<VE>> extends BehaviorSkinBase<VE, B> {

	/**
	 * ViewElementの背景を表す接頭語。
	 */
	final static String BACKGROUND_ID_PREFIX = "VIEWELEMENT-BACKGROUND-";

	/**
	 * 枠を表す点の半径。
	 */
	private static final double HOLDER_RADIUS = 3.5d;
	/**
	 * 枠を表す点の線幅。
	 */
	private static final double HOLDER_STROKE_WIDTH = 1d;

	/**
	 * 選択を表すHolderのタイプ（内部で使う）。
	 */
	private enum ViewElementHolderType {
		/**
		 * 右上。
		 */
		TOP_RIGHT,
		/**
		 * 左上。
		 */
		TOP_LEFT,
		/**
		 * 左下。
		 */
		BOTTOM_LEFT,
		/**
		 * 右下。
		 */
		BOTTOM_RIGHT
	}

	/**
	 * ViewElement。
	 */
	private final VE viewElement;

	/**
	 * 選択を表すHoler。
	 */
	private Circle[] holders;

	/**
	 * 背景。
	 */
	private Rectangle background;

	/**
	 * 内部領域。
	 */
	private Pane innerPane;

	/**
	 * 内部領域を取得する。
	 *
	 * @return 内部領域。
	 */
	Pane getInnerPane() {
		return this.innerPane;
	}

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

	/**
	 * 子を追加する。
	 *
	 * @param node 子ノード。
	 */
	protected void addChild(Node node) {
		this.innerPane.getChildren().add(node);
	}

	/**
	 * サブクラスのレイアウト変更を実行する。
	 *
	 * @param innerWidth 内部領域の幅。
	 * @param innerHeight 内部領域の高さ。
	 */
	protected abstract void update(final double innerWidth, final double innerHeight);

	/**
	 * Called during the layout pass of the scenegraph.
	 *
	 * @param contentX
	 * @param contentY
	 * @param contentWidth
	 * @param contentHeight
	 */
	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		log.trace(VIEW_ELEMENT_BASE, "layoutChildren, contextX:{}, contextY:{}, contextWidth:{}, contextHeight:{}", contentX, contentY, contentWidth, contentHeight);
		// 背景を初期化する。
		if (this.background == null) {
			this.initBackground();
		}
		// 内部領域を初期化する。
		if (this.innerPane == null) {
			this.initInnerPane();
		}
		// サブクラスのレイアウト変更を実行する。
		log.trace(UPDATE, "innerWidth:{}, innerheight:{}", this.innerPane.getPrefWidth(), this.innerPane.getPrefHeight());
		this.update(this.innerPane.getPrefWidth(), this.innerPane.getPrefHeight());
		// 枠を初期化。
		if (this.holders == null) {
			this.initHolders();
		}
	}

	/**
	 * 背景を初期化する。
	 */
	private void initBackground() {
		log.trace(VIEW_ELEMENT_BASE, "initBackground");
		this.background = new Rectangle(0d, 0d, this.viewElement.getPrefWidth(), this.viewElement.getPrefHeight());
		this.background.setId(format("%s%s", BACKGROUND_ID_PREFIX, this.viewElement.getId()));
		// nullにするとBehavior.isTarget判定ができなくなるので、不透明度を0とする。
		this.background.setFill(new Color(0d, 0d, 0d, 0d));
		this.getChildren().add(this.background);
	}

	/**
	 * 表示される内部領域を初期化する。
	 */
	private void initInnerPane() {
		log.trace(VIEW_ELEMENT_BASE, "initInnerPane");
		this.innerPane = new Pane();
		this.innerPane.setPrefSize(this.viewElement.getViewElementPrefWidth(), this.viewElement.getViewElementPrefHeight());
		this.innerPane.setLayoutX(this.viewElement.getViewElementPadding());
		this.innerPane.setLayoutY(this.viewElement.getViewElementPadding());
		this.getChildren().add(this.innerPane);
	}

	/**
	 * 枠を初期化する。
	 */
	private void initHolders() {
		log.trace(VIEW_ELEMENT_BASE, "initHolders");
		this.holders = new Circle[ViewElementHolderType.values().length];
		// TODO viewelementとbindすること。
		this.holders[ViewElementHolderType.TOP_RIGHT.ordinal()] = this.createHolder(this.innerPane.getLayoutX(), this.innerPane.getLayoutY());
		this.holders[ViewElementHolderType.TOP_LEFT.ordinal()] = this.createHolder(this.innerPane.getLayoutX() + this.innerPane.getPrefWidth(), this.innerPane.getLayoutY());
		this.holders[ViewElementHolderType.BOTTOM_LEFT.ordinal()] = this.createHolder(this.innerPane.getLayoutX() + this.innerPane.getPrefWidth(), this.innerPane.getLayoutY() + this.innerPane.getPrefHeight());
		this.holders[ViewElementHolderType.BOTTOM_RIGHT.ordinal()] = this.createHolder(this.innerPane.getLayoutX(), this.innerPane.getLayoutY() + this.innerPane.getPrefHeight());
	}

	/**
	 * 枠を作成する。
	 *
	 * @param centerX 中心となるX。
	 * @param centerY 中心となるY。
	 * @return 作成した枠。
	 */
	private Circle createHolder(final double centerX, final double centerY) {
		log.trace(VIEW_ELEMENT_BASE, "createHolder, centerX:{}, centerY:{}", centerX, centerY);
		final Circle holder = new Circle(centerX, centerY, HOLDER_RADIUS);
		holder.setStrokeWidth(HOLDER_STROKE_WIDTH);
		holder.setStrokeType(INSIDE);
		holder.setStroke(BLACK);
		holder.setFill(WHITE);
		holder.visibleProperty().bind(this.viewElement.selectedProperty());
		this.getChildren().add(holder);
		return holder;
	}
}
