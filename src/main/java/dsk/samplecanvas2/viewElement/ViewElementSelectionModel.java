package dsk.samplecanvas2.viewElement;

import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Pane;

public class ViewElementSelectionModel extends MultipleSelectionModel<ViewElementBase> {

	public static enum SelectType {
		SINGLE,
		MULTI
	}

	private final Pane viewElementPane;

	private final FilteredList<ViewElementBase> selectedList;

	public ViewElementSelectionModel(Pane viewElementPane) {
		super();
		this.viewElementPane = viewElementPane;
		this.selectedList = new FilteredList<>(new NodeToTransformationList<ViewElementBase>(this.viewElementPane.getChildren()), viewElement -> {
			return viewElement.isSelected();
		});
	}

	@Override
	public ObservableList<Integer> getSelectedIndices() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ObservableList<ViewElementBase> getSelectedItems() {
		return this.selectedList;
	}

	@Override
	public void selectIndices(int index, int... indices) {
		this.select(index);
		for (int idx : indices) {
			this.select(idx);
		}
	}

	@Override
	public void selectAll() {
		this.getViewElementStream().forEach(viewElement -> {
			viewElement.setSelected(true);
		});
	}

	@Override
	public void selectFirst() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void selectLast() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void clearAndSelect(int index) {
		this.clearSelection();
		this.select(index);
	}

	@Override
	public void select(int index) {
		this.get(index).setSelected(true);
	}

	@Override
	public void select(ViewElementBase obj) {
		this.select(this.indexOf(obj));
	}

	@Override
	public void clearSelection(int index) {
		final ViewElementBase viewElement = this.get(index);
		viewElement.setSelected(false);
	}

	@Override
	public void clearSelection() {
		this.getViewElementStream().forEach(viewElement -> {
			viewElement.setSelected(false);
		});
	}

	@Override
	public boolean isSelected(final int index) {
		return this.get(index).isSelected();
	}

	@Override
	public boolean isEmpty() {
		return this.selectedList.isEmpty();
	}

	@Override
	public void selectPrevious() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void selectNext() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private ViewElementBase get(final int index) {
		return (ViewElementBase) this.viewElementPane.getChildren().get(index);
	}

	private int indexOf(ViewElementBase value) {
		return this.viewElementPane.getChildren().indexOf(value);
	}

	private Stream<ViewElementBase> getViewElementStream() {
		return this.viewElementPane.getChildren().stream().map(ViewElementBase.class::cast);
	}
}
