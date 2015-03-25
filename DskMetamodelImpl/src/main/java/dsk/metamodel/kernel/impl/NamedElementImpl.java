package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.NamedElement;

public class NamedElementImpl extends ElementImpl implements NamedElement {

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getQualifiedName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
