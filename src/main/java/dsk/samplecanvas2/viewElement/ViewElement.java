package dsk.samplecanvas2.viewElement;

import static dsk.samplecanvas2.viewElement.ViewElementBase.PADDING;
import java.util.List;
import java.util.Optional;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;

public interface ViewElement {

	static final double PADDING = 15d;

	default Optional<ViewElement> getNextSiblingViewElement() {
		List<Node> list = this.getParent().getChildrenUnmodifiable();
		final int index = list.indexOf(this);
		if (index - 1 >= 0) {
			return Optional.of((ViewElement) list.get(index - 1));
		}
		return Optional.empty();
	}

	default double getVirtualPadding() {
		return PADDING;
	}

	default void setVirtualPrefSize(double prefWidth, double prefHeight) {
		this.setPrefSize(prefWidth + getVirtualPadding() * 2, prefHeight + getVirtualPadding() * 2);
	}

	default double getVirtualLayoutX() {
		return this.getLayoutX() + getVirtualPadding();
	}

	default void setVirtualLayoutX(double layoutX) {
		this.setLayoutX(layoutX - getVirtualPadding());
	}

	default double getVirtualLayoutY() {
		return this.getLayoutY() + getVirtualPadding();
	}

	default void setVirtualLayoutY(double layoutY) {
		this.setLayoutY(layoutY - getVirtualPadding());
	}

	default double getVirtualPrefWidth() {
		return this.getPrefWidth() - getVirtualPadding() * 2;
	}

	default double getVirtualPrefHeight() {
		return this.getPrefHeight() - getVirtualPadding() * 2;
	}

	void fireEvent(Event e);

	List<Node> getChildrenUnmodifiable();

	Parent getParent();

	double getLayoutX();

	void setPrefSize(double prefWidth, double prefHeigh);

	void setLayoutX(double layoutX);

	double getLayoutY();

	void setLayoutY(double layoutY);

	double getPrefWidth();

	double getPrefHeight();
}
