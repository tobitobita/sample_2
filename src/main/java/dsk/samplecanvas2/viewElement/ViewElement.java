package dsk.samplecanvas2.viewElement;

import static dsk.samplecanvas2.viewElement.ViewElementBase.PADDING;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;

public interface ViewElement {

	static final double PADDING = 15d;

	List<Node> getChildrenUnmodifiable();

	default Optional<ViewElement> getNextSiblingViewElement() {
		List<Node> list = this.getChildrenUnmodifiable();
		final int index = list.indexOf(this);
		if (index - 1 >= 0) {
			return Optional.of((ViewElementBase) list.get(index - 1));
		}
		return Optional.empty();
	}

	default double getVirtualPadding() {
		return PADDING;
	}

	default void setVirtualPrefSize(double prefWidth, double prefHeight) {
		this.setPrefSize(prefWidth + getVirtualPadding() * 2, prefHeight + getVirtualPadding() * 2);
	}

	void setPrefSize(double prefWidth, double prefHeigh);

	default double getVirtualLayoutX() {
		return this.getLayoutX() - getVirtualPadding();
	}

	double getLayoutX();

	default void setVirtualLayoutX(double layoutX) {
		this.setLayoutX(layoutX + getVirtualPadding());
	}

	void setLayoutX(double layoutX);

	default double getVirtualLayoutY() {
		return this.getLayoutY() - getVirtualPadding();
	}

	double getLayoutY();

	default void setVirtualLayoutY(double layoutY) {
		this.setLayoutY(layoutY + getVirtualPadding());
	}

	void setLayoutY(double layoutY);

	default double getVirtualPrefWidth() {
		return this.getPrefWidth() - getVirtualPadding() * 2;
	}

	double getPrefWidth();

	default double getVirtualPrefHeight() {
		return this.getPrefHeight() - getVirtualPadding() * 2;
	}

	double getPrefHeight();

}
