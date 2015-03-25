package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.StructuralFeature;
import dsk.metamodel.kernel.Type;
import dsk.metamodel.kernel.TypedElement;

public class StructuralFeatureImpl extends FeatureImpl implements TypedElement, StructuralFeature {

    private Type type;

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
