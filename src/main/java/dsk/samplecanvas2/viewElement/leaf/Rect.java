package dsk.samplecanvas2.viewElement.leaf;

import dsk.samplecanvas2.viewElement.ViewElementBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Rect extends ViewElementBase<RectSkin> {

	public Rect() {
		log.trace("Rect()");
	}

	@Override
	protected RectSkin createDefaultSkin() {
		return new RectSkin(this);
	}
}
