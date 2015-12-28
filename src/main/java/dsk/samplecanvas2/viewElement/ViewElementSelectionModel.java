package dsk.samplecanvas2.viewElement;

import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Pane;

class ViewElementSelectionModel extends MultipleSelectionModel<ViewElementBase> {

	private final Pane viewElementPane;

	private final ObservableList<ViewElementBase> selectedList = FXCollections.observableArrayList();

	ViewElementSelectionModel(Pane viewElementPane) {
		super();
		this.viewElementPane = viewElementPane;
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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void selectAll() {
		this.selectedList.clear();
		this.getViewElementStream().forEach(viewElement -> {
			viewElement.setSelected(true);
			this.selectedList.add(viewElement);
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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void select(int index) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void select(ViewElementBase obj) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void clearSelection(int index) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void clearSelection() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isSelected(int index) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void selectPrevious() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void selectNext() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private Stream<ViewElementBase> getViewElementStream() {
		return this.viewElementPane.getChildren().stream().map(ViewElementBase.class::cast);
	}
}
