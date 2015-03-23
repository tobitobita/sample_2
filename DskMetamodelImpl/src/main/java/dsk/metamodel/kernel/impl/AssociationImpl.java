package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.Association;
import dsk.metamodel.kernel.Element;
import dsk.metamodel.kernel.Property;
import dsk.metamodel.kernel.Relationship;
import java.util.List;

public class AssociationImpl extends ClassifierImpl implements Relationship, Association {

    @Override
    public List<Element> getRelatedElements() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Property> getMemberEnds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
