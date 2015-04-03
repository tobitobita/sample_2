package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.presentation.DrawControl;
import java.util.Optional;

/**
 * @deprecated 実装がよくないね。
 */
@Deprecated
public interface DrawControlFactory {

    /**
     * @return @deprecated 実装がよくないね。
     */
    @Deprecated()
    void setByPressed(double pressedX, double pressedY);

    /**
     * @return @deprecated 実装がよくないね。
     */
    @Deprecated()
    void setByReleased(double releasedX, double releasedY);

    /**
     * @return @deprecated 実装がよくないね。
     */
    @Deprecated()
    Optional<DrawControl> getControl();
}
