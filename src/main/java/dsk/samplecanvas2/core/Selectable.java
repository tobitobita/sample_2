package dsk.samplecanvas2.core;

import javafx.beans.property.BooleanProperty;

/**
 * 選択可能かを表すインターフェイス。
 *
 * @author makoto
 */
public interface Selectable {

    boolean isSelected();

    void setSelected(boolean selected);

    BooleanProperty selectedProperty();
}
