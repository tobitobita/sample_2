package dsk.metamodel.kernel;

import java.util.List;

public interface Association extends Classifier, Relationship {

    List<Property> getMemberEnds();
}
