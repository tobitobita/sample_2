package cv.sample.metaModel;

import javafx.beans.property.ReadOnlyProperty;

public interface Property {

    String getName();

    String getDisplayName();

    java.lang.Class<?> getType();

    default <T extends ReadOnlyProperty> java.lang.Class<?> getTypeByProperty(java.lang.Class<T> type) {
        if (type.getClass().getSimpleName().contains("String")) {
            return String.class;
        }
        if (type.getClass().getSimpleName().contains("Integer")) {
            return int.class;
        }
        return null;
    }
}
