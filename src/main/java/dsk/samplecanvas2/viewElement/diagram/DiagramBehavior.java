package dsk.samplecanvas2.viewElement.diagram;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import dsk.samplecanvas2.logging.MarkerConst;
import static dsk.samplecanvas2.viewElement.ViewElementSelectionModel.SelectType.MULTI;
import static dsk.samplecanvas2.viewElement.ViewElementSelectionModel.SelectType.SINGLE;
import static dsk.samplecanvas2.viewElement.diagram.DiagramSkin.LINE_WIDTH;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * DiagramBehavior。
 */
@Slf4j
public class DiagramBehavior extends BehaviorBase<DiagramBase> {

	private double pressedX;
	private double pressedY;
	private final DoubleProperty selectX = new SimpleDoubleProperty(this, "selectX");
	private final DoubleProperty selectY = new SimpleDoubleProperty(this, "selectY");
	private final DoubleProperty selectWidth = new SimpleDoubleProperty(this, "selectWidth");
	private final DoubleProperty selectHeight = new SimpleDoubleProperty(this, "selectHeight");

	DoubleProperty selectXProperty() {
		return selectX;
	}

	DoubleProperty selectYProperty() {
		return selectY;
	}

	DoubleProperty selectWidthProperty() {
		return selectWidth;
	}

	DoubleProperty selectHeightProperty() {
		return selectHeight;
	}

	public DiagramBehavior(final DiagramBase diagram) {
		this(diagram, TRAVERSAL_BINDINGS);
	}

	public DiagramBehavior(final DiagramBase diagram, final List<KeyBinding> keyBindings) {
		super(diagram, keyBindings);
		// mousePressedのフィルター時に選択状態を解除する。
		diagram.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			diagram.clearSelection();
			diagram.setSelectType(SINGLE);
		});
		diagram.addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
			log.trace("HANLDER, {}", e);
			diagram.startFullDragAllViewElement();
			diagram.setSelectType(MULTI);
			e.consume();
		});
	}

	@Override
	public void mousePressed(MouseEvent e) {
		log.trace(MarkerConst.MOUSE_PRESSED, "HANDLER, {}", e);
		pressedX = e.getSceneX();
		pressedY = e.getSceneY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		log.trace(MarkerConst.MOUSE_DRAGGED, "HANDLER, {}", e);
		this.clearSelectArea();
		if (this.pressedX < e.getSceneX()) {
			selectX.set(this.pressedX);
			selectWidth.set(e.getSceneX() - this.pressedX);
		} else {
			selectX.set(e.getSceneX());
			selectWidth.set(this.pressedX - e.getSceneX());
		}
		if (this.pressedY < e.getSceneY()) {
			selectY.set(this.pressedY);
			selectHeight.set(e.getSceneY() - this.pressedY);
		} else {
			selectY.set(e.getSceneY());
			selectHeight.set(this.pressedY - e.getSceneY());
		}
		this.drawSelectArea();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.clearSelectArea();
	}

	/**
	 * 選択領域を表示する。
	 */
	void drawSelectArea() {
		final GraphicsContext context = this.getControl().getDiagramSkin().getSelectArea().getGraphicsContext2D();
		context.fillRect(selectX.get(), selectY.get(), selectWidth.get(), selectHeight.get());
		context.strokeRect(selectX.get(), selectY.get(), selectWidth.get(), selectHeight.get());
	}

	/**
	 * 選択領域を非表示する。
	 */
	void clearSelectArea() {
		final GraphicsContext context = this.getControl().getDiagramSkin().getSelectArea().getGraphicsContext2D();
		final double halfLineWidth = LINE_WIDTH / 2;
		context.clearRect(selectX.get() - halfLineWidth, selectY.get() - halfLineWidth, selectWidth.get() + LINE_WIDTH, selectHeight.get() + LINE_WIDTH);
	}
}
