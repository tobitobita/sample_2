package dsk.samplecanvas2.viewElement.diagram;

import dsk.samplecanvas2.logging.MarkerConst;
import dsk.samplecanvas2.viewElement.ViewElementBase;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiagramSkinBase extends SkinBase<Control> {

	/**
	 * 範囲選択時に表示するラインの幅
	 */
	private static final double LINE_WIDTH = 0.5d;

	private Canvas selectedArea;

	private double pressedX;
	private double pressedY;
	private double layoutX;
	private double layoutY;
	private double selectedWidth;
	private double selectedHeight;
//	private DoubleProperty layoutX = new SimpleDoubleProperty(this, "layoutX");
//	private DoubleProperty layoutY = new SimpleDoubleProperty(this, "layoutY");
//	private DoubleProperty selectedWidth = new SimpleDoubleProperty(this, "selectedWidth");
//	private DoubleProperty selectedHeight = new SimpleDoubleProperty(this, "selectedHeight");

	public DiagramSkinBase(final DiagramBase c) {
		super(c);
		selectedArea = new Canvas();
		selectedArea.setMouseTransparent(true);
		selectedArea.widthProperty().bind(c.prefWidthProperty());
		selectedArea.heightProperty().bind(c.prefHeightProperty());
		final GraphicsContext context = selectedArea.getGraphicsContext2D();
		context.setStroke(Color.rgb(103, 176, 255));
		context.setFill(Color.rgb(103, 176, 255, 0.67d));
		context.setLineWidth(LINE_WIDTH);
		context.fillRect(0d, 0d, 100d, 100d);
		getChildren().add(selectedArea);
		// TODO initializeで行う。
		// mousePressedのフィルター時に選択状態を解除する。
		c.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			c.getViewElementPane().getChildren().stream()
					.map(ViewElementBase.class::cast)
					.forEach(viewElement -> {
						viewElement.setSelected(false);
					});
//			mode.set(SELECT);
		});
		c.setOnMousePressed(e -> {
			log.trace(MarkerConst.MOUSE_PRESSED, "HANDLER, {}", e);
			pressedX = e.getSceneX();
			pressedY = e.getSceneY();
			layoutX = e.getSceneX();
			layoutY = e.getSceneY();
		});
		c.setOnMouseDragged(e -> {
			log.trace(MarkerConst.MOUSE_DRAGGED, "HANDLER, {}", e);
			this.clearRect(layoutX, layoutY, selectedWidth, selectedHeight);
			if (this.pressedX < e.getSceneX()) {
				layoutX = this.pressedX;
				selectedWidth = e.getSceneX() - this.pressedX;
			} else {
				layoutX = e.getSceneX();
				selectedWidth = this.pressedX - e.getSceneX();
			}
			if (this.pressedY < e.getSceneY()) {
				layoutY = this.pressedY;
				selectedHeight = e.getSceneY() - this.pressedY;
			} else {
				layoutY = e.getSceneY();
				selectedHeight = this.pressedY - e.getSceneY();
			}
			this.drawDraggedRect(layoutX, layoutY, selectedWidth, selectedHeight);
		});
		c.setOnMouseReleased(e -> {
			log.trace(MarkerConst.MOUSE_RELEASED, "HANDLER, {}", e);
			this.clearRect(layoutX, layoutY, selectedWidth, selectedHeight);
//			getChildren().remove(selectedArea);
		}
		);
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		this.layoutInArea(selectedArea, contentX, contentY, contentWidth, contentHeight, -1, HPos.LEFT, VPos.TOP);
	}

	private void drawDraggedRect(double x, double y, double width, double height) {
		log.trace("{},{},{},{}", x, y, width, height);
		GraphicsContext context = this.selectedArea.getGraphicsContext2D();
		context.fillRect(x, y, width, height);
		context.strokeRect(x, y, width, height);
	}

	private void clearRect(double x, double y, double width, double height) {
		GraphicsContext context = this.selectedArea.getGraphicsContext2D();
		double halfLineWidth = LINE_WIDTH / 2;
		context.clearRect(x - halfLineWidth, y - halfLineWidth, width + LINE_WIDTH, height + LINE_WIDTH);
	}
}
