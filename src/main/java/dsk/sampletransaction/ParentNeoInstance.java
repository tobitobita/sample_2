package dsk.sampletransaction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class ParentNeoInstance extends ParentInstance implements PropertyChangeListener, Serializable, Cloneable {

    private static final long serialVersionUID = 1;

    private String parentName;

    public ParentNeoInstance() {
        super();
    }

    public ParentNeoInstance(long id, String name, String parentName) {
        super(id, name);
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof ParentInstance && evt.getPropertyName().equals("name")) {
            this.parentName = (String) evt.getNewValue();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", ParentNeoInstance{" + "parentName=" + parentName + '}';
    }
}
