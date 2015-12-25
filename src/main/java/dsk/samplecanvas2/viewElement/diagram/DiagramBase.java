package dsk.samplecanvas2.viewElement.diagram;

import dsk.samplecanvas2.viewElement.OperationMode;
import static dsk.samplecanvas2.viewElement.OperationMode.SELECT;
import dsk.samplecanvas2.viewElement.ViewElement;
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
	 * コンストラクタ。
	 */
	public DiagramBase() {
		super();
//		this.setStyle("-fx-background-color: white;");
		viewElementPane = new Pane();
		viewElementPane.prefWidthProperty().bind(this.prefWidthProperty());
		viewElementPane.prefHeightProperty().bind(this.prefHeightProperty());
		getChildren().add(viewElementPane);
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		log.trace("x:{}, y:{}, width:{}, height:{}", getLayoutX(), getLayoutY(), getPrefWidth(), getPrefHeight());
	}

	/**
	 * 標準のスキンを作成する。
	 *
	 * @return 作成されたスキン。
	 */
	@Override
	protected DiagramSkinBase createDefaultSkin() {
		return new DiagramSkinBase(this);
	}

	Pane getViewElementPane() {
		return this.viewElementPane;
	}

	public void addViewElement(Node viewElement) {
		this.viewElementPane.getChildren().add(viewElement);
	}

}
