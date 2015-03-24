package dsk.metamodel.kernel;

public interface Association extends Classifier, Relationship {

    Iterable<Property> getMemberEnds();
}
