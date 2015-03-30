package dsk.samplecanvas;

import dsk.samplecanvas.control.DrawControl;
import java.util.Optional;

/**
 * @deprecated 実装がよくないね。
 */
@Deprecated
public interface DrawControlFactory {

    /**
     * @return
     * @deprecated 実装がよくないね。
     */
    @Deprecated()
    Optional<DrawControl> createControl();
}
