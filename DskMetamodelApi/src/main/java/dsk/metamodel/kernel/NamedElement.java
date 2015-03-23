package dsk.metamodel.kernel;

public interface NamedElement extends Element {

    String getName();

    void setName(String name);

    String getQualifiedName();
}
