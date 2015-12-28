package dsk.samplecanvas2.viewElement.diagram;

import dsk.samplecanvas2.viewElement.OperationMode;
import static dsk.samplecanvas2.viewElement.OperationMode.SELECT;
import dsk.samplecanvas2.viewElement.ViewElement;
import dsk.samplecanvas2.viewElement.ViewElementBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

/**
 * ダイアグラムのベースクラス。
 */
@Slf4j
public class DiagramBase extends Control implements ViewElement {

	/**
	 * ViewElementを表示する枠。
	 */
	private Pane viewElementPane;

	/**
	 * ダイアグラム状態を表す。
	 */
	private final ObjectProperty<OperationMode> mode = new SimpleObjectProperty<>(this, "mode", SELECT);

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
	 * DiagramSkinを取得する。
	 *
	 * @return DiagramSkin。
	 */
	DiagramSkin getDiagramSkin() {
		return (DiagramSkin) super.getSkin();
	}

	/**
	 * コンストラクタ。
	 */
	public DiagramBase() {
		super();
		// ViewElementの枠を初期化する。
		initViewElementPane();
	}

	/**
	 * 標準のスキンを作成する。
	 *
	 * @return 作成されたスキン。
	 */
	@Override
	protected DiagramSkin createDefaultSkin() {
		return new DiagramSkin(this);
	}

	/**
	 * 全選択する。
	 */
	public void selectAll() {
		this.viewElementPane.getChildren().stream()
				.map(ViewElementBase.class::cast)
				.forEach(viewElement -> {
					viewElement.setSelected(true);
				});
	}

	/**
	 * 全選択解除する。
	 */
	public void deselectAll() {
		this.viewElementPane.getChildren().stream()
				.map(ViewElementBase.class::cast)
				.forEach(viewElement -> {
					viewElement.setSelected(false);
				});
	}

	/**
	 * ViewElementを追加する。
	 *
	 * @param viewElement
	 */
	public void addViewElement(final Node viewElement) {
		this.viewElementPane.getChildren().add(viewElement);
	}

	void startFullDragAllViewElement() {
		this.viewElementPane.getChildren().stream()
				.map(ViewElementBase.class::cast)
				.forEach(viewElement -> {
					viewElement.startFullDrag();
				});
	}

	/**
	 * ViewElementPaneを初期化する。
	 */
	private void initViewElementPane() {
		viewElementPane = new Pane();
		viewElementPane.prefWidthProperty().bind(this.prefWidthProperty());
		viewElementPane.prefHeightProperty().bind(this.prefHeightProperty());
		getChildren().add(viewElementPane);
	}
}
