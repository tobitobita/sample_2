package dsk.samplecanvas2.viewElement.leaf;

import dsk.samplecanvas2.viewElement.ViewElementSkinBase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RectSkin extends ViewElementSkinBase<Rect, RectBehavior> {

	private Rectangle rect;

	public RectSkin(Rect viewElement) {
		super(viewElement, new RectBehavior(viewElement));
		log.trace("RectSkin(), viewElement:{}", viewElement);
	}

	@Override
	protected void doLayout(double contentWidth, double contentHeight) {
//		log.trace("doLayout, contentWidth:{}, contentHeight:{}", contentWidth, contentHeight);
		if (this.rect == null) {
			this.initRect();
		}
		this.rect.setWidth(contentWidth);
		this.rect.setHeight(contentHeight);
	}

	private void initRect() {
		this.rect = new Rectangle();
		//feffc8
//		this.rect.setFill(Color.web("feffc8"));
		this.rect.setFill(Color.RED);
		this.rect.setArcWidth(5d);
		this.rect.setArcHeight(5d);
		this.add(rect);
	}
}
