package dsk.samplecanvas2.diagram.viewElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RectViewElement extends ViewElementBase<RectViewElementSkin> {

	public RectViewElement() {
		log.trace("RectViewElement()");
	}

	@Override
	protected RectViewElementSkin createDefaultViewElementSkin() {
		return new RectViewElementSkin(this);
	}
}
