package dsk.metamodel.kernel;

import java.util.List;

public interface Type extends PackageableElement {

    Package getPackage();

    void setPackage(Package value);

    List<Association> getAssociations();
}
