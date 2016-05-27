package cv.sample.model;

import javafx.beans.property.ReadOnlyListProperty;
import cv.sample.metaModel.Class;

public interface ListModel<T extends Model> extends Model {

    Class getListTemplateType();
    
    ReadOnlyListProperty<T> listProperty();

    void add(T model);

    void remove(T model);
}
