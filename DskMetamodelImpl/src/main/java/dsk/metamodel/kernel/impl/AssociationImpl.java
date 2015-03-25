package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.Association;
import dsk.metamodel.kernel.Element;
import dsk.metamodel.kernel.Property;
import dsk.metamodel.kernel.Relationship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssociationImpl extends ClassifierImpl implements Relationship, Association {

    private final ObservableList<Element> relatedElements = FXCollections.observableArrayList();

    private final ObservableList<Property> memberEnds = FXCollections.observableArrayList();

    @Override
    public Iterable<Element> getRelatedElements() {
        return relatedElements;
    }

    @Override
    public Iterable<Property> getMemberEnds() {
        return memberEnds;
    }
}
