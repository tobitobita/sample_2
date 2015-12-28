package dsk.samplecanvas2.viewElement.diagram;

import dsk.samplecanvas2.viewElement.ViewElement;
import dsk.samplecanvas2.viewElement.ViewElementBase;
import dsk.samplecanvas2.viewElement.ViewElementSelectionModel;
import dsk.samplecanvas2.viewElement.ViewElementSelectionModel.SelectType;
import static dsk.samplecanvas2.viewElement.ViewElementSelectionModel.SelectType.SINGLE;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
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
	private final Pane viewElementPane;

	/**
	 * 選択を管理するモデル。
	 */
	private final ViewElementSelectionModel selectionModel;

	private ObjectProperty<SelectType> selectType = new SimpleObjectProperty<>(this, "selectType", SINGLE);

	void setSelectType(SelectType type) {
		this.selectType.set(type);
	}

	public SelectType getSelectType() {
		return this.selectType.get();
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
		this.viewElementPane = createViewElementPane();
		this.selectionModel = new ViewElementSelectionModel(this.viewElementPane);
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

	public void clearAndSelect(int index) {
		selectionModel.clearAndSelect(index);
	}

	public void select(int index) {
		selectionModel.select(index);
	}

	public void clearSelection(int index) {
		selectionModel.clearSelection(index);
	}

	/**
	 * 全選択する。
	 */
	public void selectAll() {
		this.selectionModel.selectAll();
	}

	/**
	 * 全選択解除する。
	 */
	public void clearSelection() {
		this.selectionModel.clearSelection();
	}

	/**
	 * ViewElementを追加する。
	 *
	 * @param viewElement
	 */
	public void addViewElement(final ViewElementBase viewElement) {
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
	 * ViewElementPaneを作成する。
	 */
	private Pane createViewElementPane() {
		final Pane pane = new Pane();
		pane.prefWidthProperty().bind(this.prefWidthProperty());
		pane.prefHeightProperty().bind(this.prefHeightProperty());
		getChildren().add(pane);
		return pane;
	}
}
