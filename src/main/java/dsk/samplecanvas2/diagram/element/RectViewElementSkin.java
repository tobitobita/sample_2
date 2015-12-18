package dsk.samplecanvas2.diagram.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RectViewElementSkin extends ViewElementSkinBase<RectViewElement, RectViewElementBehavior> {

	private final Rectangle rect;

	public RectViewElementSkin(RectViewElement control) {
		super(control, new RectViewElementBehavior(control));
		log.trace("RectViewElementSkin(), control:{}", control);
		this.rect = new Rectangle();
		rect.layoutXProperty().bind(control.layoutXProperty());
		rect.layoutYProperty().bind(control.layoutYProperty());
		this.getChildren().add(rect);
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		log.trace("layoutChildren, contentX:{}, contentY:{}, contentWidth:{}, contentHeight:{}", contentX, contentY, contentWidth, contentHeight);
		rect.setWidth(contentWidth);
		rect.setHeight(contentHeight);
		rect.setArcWidth(20);
		rect.setArcHeight(20);
		rect.setFill(Color.BLACK);
	}
}
