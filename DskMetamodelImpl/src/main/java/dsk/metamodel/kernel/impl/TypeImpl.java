package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.Association;
import dsk.metamodel.kernel.Package;
import dsk.metamodel.kernel.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TypeImpl extends PackageableElementImpl implements Type {

    private Package pkg;

    private final ObservableList<Association> associations = FXCollections.observableArrayList();

    @Override
    public Package getPackage() {
        return this.pkg;
    }

    @Override
    public void setPackage(Package pkg) {
        this.pkg = pkg;
    }

    @Override
    public Iterable<Association> getAssociations() {
        return associations;
    }
}
