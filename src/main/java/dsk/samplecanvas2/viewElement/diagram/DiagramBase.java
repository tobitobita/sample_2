package dsk.samplecanvas2.viewElement.diagram;

import dsk.samplecanvas2.viewElement.OperationMode;
import static dsk.samplecanvas2.viewElement.OperationMode.SELECT;
import dsk.samplecanvas2.viewElement.ViewElement;
import dsk.samplecanvas2.viewElement.ViewElementBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DiagramBase extends Pane implements ViewElement {

	private ObjectProperty<OperationMode> mode = new SimpleObjectProperty<>(this, "mode", SELECT);

	public DiagramBase() {
		super();
		this.setStyle("-fx-background-color: white;");
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			System.out.printf("FILTER, %s\n", e);
			getChildren().stream().map(ViewElementBase.class::cast).forEach(viewElement -> {
				viewElement.setSelected(false);
//				viewElement.setMouseTransparent(false);
			});
			mode.set(SELECT);
		});
//		this.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
//			System.out.printf("FILTER, %s\n", e);
//			mode.set(MOVE);
//		});
//		this.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
//			System.out.printf("FILTER, %s\n", e);
//			mode.set(SELECT);
//			requestLayout();
//		});
//		this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
//			System.out.printf("HANDLER, %s\n", e);
//		});
//		this.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
//			System.out.printf("HANDLER, %s\n", e);
//			getChildren().stream().map(ViewElementBase.class::cast).forEach(viewElement -> {
//				viewElement.setMouseTransparent(false);
//			});
//		});
	}

	public ReadOnlyObjectProperty<OperationMode> modeProperty() {
		return this.mode;
	}
}
