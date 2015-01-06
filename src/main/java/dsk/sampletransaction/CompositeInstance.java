package dsk.sampletransaction;

import java.util.ArrayList;
import java.util.List;

public class CompositeInstance extends ParentInstance {

    private ParentInstance parent;

    private List<String> names = new ArrayList<>();

    public CompositeInstance() {
        super();
    }

    public CompositeInstance(int id, String name, ParentInstance parent) {
        super(id, name);
        this.parent = parent;
    }

    public ParentInstance getParent() {
        return parent;
    }

    public void setParent(ParentInstance parent) {
        ParentInstance oldParent = this.parent;
        this.parent = parent;
        this.propertyChangeSupport.firePropertyChange("parent", oldParent, this.parent);
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        List<String> oldNames = this.names;
        this.names = names;
        this.propertyChangeSupport.firePropertyChange("names", oldNames, this.names);
    }

    public void addName(String name) {
        int index = this.names.size();
        this.names.add(name);
        this.propertyChangeSupport.fireIndexedPropertyChange("name", index, null, name);
    }
}
