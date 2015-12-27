package dsk.samplecanvas2.viewElement.diagram;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import dsk.samplecanvas2.logging.MarkerConst;
import static javafx.geometry.HPos.LEFT;
import static javafx.geometry.VPos.TOP;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

/**
 * DiagramSkin。
 */
@Slf4j
public class DiagramSkin extends BehaviorSkinBase<DiagramBase, DiagramBehavior> {

	/**
	 * 範囲選択時に表示するラインの幅
	 */
	static final double LINE_WIDTH = 0.5d;

	/**
	 * 選択表示エリア。
	 */
	private Canvas selectArea;

	/**
	 * Diagram。
	 */
	private final DiagramBase diagram;

	/**
	 * コンストラクタ。
	 *
	 * @param diagram Diagram。
	 */
	public DiagramSkin(final DiagramBase diagram) {
		super(diagram, new DiagramBehavior(diagram));
		this.diagram = diagram;
	}

	/**
	 * 子を配置する。
	 *
	 * @param contentX
	 * @param contentY
	 * @param contentWidth
	 * @param contentHeight
	 */
	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		log.trace(MarkerConst.DIAGRAM_BASE, "x:{}, y:{}, width:{}, height:{}", contentX, contentY, contentWidth, contentHeight);
		// 選択エリアを初期化する。
		if (this.selectArea == null) {
			initSelectArea();
		}
		this.layoutInArea(selectArea, contentX, contentY, contentWidth, contentHeight, -1, LEFT, TOP);
	}

	Canvas getSelectArea() {
		return this.selectArea;
	}

	/**
	 * 選択エリアを初期化する。
	 */
	private void initSelectArea() {
		this.selectArea = new Canvas();
		this.selectArea.widthProperty().bind(this.diagram.prefWidthProperty());
		this.selectArea.heightProperty().bind(this.diagram.prefHeightProperty());
		this.selectArea.setMouseTransparent(true);
		final GraphicsContext gc = this.selectArea.getGraphicsContext2D();
		gc.setFill(Color.rgb(103, 176, 255, 0.67d));
		gc.setStroke(Color.rgb(103, 176, 255));
		gc.setLineWidth(LINE_WIDTH);
		getChildren().add(this.selectArea);
	}
}
