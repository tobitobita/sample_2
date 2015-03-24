package dsk.metamodel.kernel;

public interface Type extends PackageableElement {

    Package getPackage();

    void setPackage(Package value);

    Iterable<Association> getAssociations();
}
