package dsk.samplecanvas2;

import dsk.samplecanvas2.viewElement.leaf.Rect;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleDiagramController implements Initializable {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private SimpleDiagram diagram;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.trace("initialize");
		diagram.setLayoutX(0d);
		diagram.setLayoutY(0d);
		diagram.prefWidthProperty().bind(anchorPane.widthProperty());
		diagram.prefHeightProperty().bind(anchorPane.heightProperty());

		final double defaultX = 50d;
		final double defaultY = 50d;

		for (int i = 0; i < 2; ++i) {
			Rect rect = new Rect();
			rect.setViewElementLayoutX(defaultX + 20d * i);
			rect.setViewElementLayoutY(defaultY + 20d * i);
			rect.setViewElementPrefWidth(40d);
			rect.setViewElementPrefHeight(30d);
			rect.setId(String.format("Rect%d", i));
			this.diagram.addViewElement(rect);
		}
	}
}
