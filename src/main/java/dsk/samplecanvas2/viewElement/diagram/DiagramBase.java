package dsk.samplecanvas2.viewElement.diagram;

import dsk.samplecanvas2.viewElement.OperationMode;
import static dsk.samplecanvas2.viewElement.OperationMode.SELECT;
import dsk.samplecanvas2.viewElement.ViewElement;
import dsk.samplecanvas2.viewElement.ViewElementBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * ダイアグラムのベースクラス。
 */
public class DiagramBase extends Pane implements ViewElement {

	/**
	 * ダイアグラム状態を表す。
	 */
	private ObjectProperty<OperationMode> mode = new SimpleObjectProperty<>(this, "mode", SELECT);

	/**
	 * ダイアグラム状態のプロパティ。
	 *
	 * @return ダイアグラム状態。
	 */
	public ObjectProperty<OperationMode> modeProperty() {
		return this.mode;
	}

	/**
	 * ダイアグラムは余白なしとする。
	 *
	 * @return 余白。
	 */
	@Override
	public double getViewElementPadding() {
		return 0d;
	}

	/**
	 * コンストラクタ。
	 */
	public DiagramBase() {
		super();
		this.setStyle("-fx-background-color: white;");
		// mousePressedのフィルター時に選択状態を解除する。
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			getChildren().stream().map(ViewElementBase.class::cast).forEach(viewElement -> {
				viewElement.setSelected(false);
			});
			mode.set(SELECT);
		});
	}
}
