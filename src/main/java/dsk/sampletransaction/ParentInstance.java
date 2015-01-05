package dsk.sampletransaction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ParentInstance implements Serializable, Cloneable {

    private static final long serialVersionUID = 1;

    private long id;
    private String name;

    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ParentInstance() {
    }

    public ParentInstance(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        long oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", oldId, this.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, this.name);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public ParentInstance clone() {
        try {
            // シャローコピー
            return (ParentInstance) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return "ParentInstance{" + "id=" + id + ", name=" + name + '}';
    }
}
