package dsk.samplecanvas2.diagram;

import dsk.samplecanvas2.diagram.viewElement.RectViewElement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiagramController implements Initializable {

	@FXML
	private DiagramPane diagram;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.trace("initialize");
		RectViewElement rect = new RectViewElement();
		this.diagram.getChildren().add(rect);
		rect.setLayoutX(50d);
		rect.setLayoutY(50d);
		rect.setPrefSize(100d, 100d);
	}
}
