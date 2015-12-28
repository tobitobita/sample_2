package dsk.samplecanvas2.viewElement;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import javafx.scene.Node;

public class NodeToTransformationList<E> extends TransformationList<E, Node> {

	public NodeToTransformationList(ObservableList<Node> source) {
		super(source);
	}

	@Override
	protected void sourceChanged(ListChangeListener.Change<? extends Node> c) {
		// TODO 何もしない。
	}

	@Override
	public int getSourceIndex(int index) {
		return index;
	}

	@Override
	public E get(int index) {
		return (E) getSource().get(index);
	}

	@Override
	public int size() {
		return getSource().size();
	}
}
