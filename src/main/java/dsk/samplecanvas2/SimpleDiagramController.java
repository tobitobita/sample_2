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
	private SimpleDiagram diagram;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.trace("initialize");

		AnchorPane.setTopAnchor(diagram, 0d);
		AnchorPane.setLeftAnchor(diagram, 0d);
		AnchorPane.setBottomAnchor(diagram, 0d);
		AnchorPane.setRightAnchor(diagram, 0d);

		final double defaultX = 50d;
		final double defaultY = 50d;

		for (int i = 0; i < 2; ++i) {
			Rect rect = new Rect();
			rect.setVirtualLayoutX(defaultX + 50d * i);
			rect.setVirtualLayoutY(defaultY + 50d * i);
			rect.setVirtualPrefSize(100d, 100d);
			rect.setId(String.format("Rect%d", i));
			this.diagram.getChildren().add(rect);
		}
	}
}
