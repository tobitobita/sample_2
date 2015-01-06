package dsk.sampletransaction;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class MainClass2 implements PropertyChangeListener {

    private final List<String> list = new ArrayList<>();

    public void start() {
        CollectionProperty obj = new CollectionProperty();
        obj.addPropertyChangeListener(this);

        obj.addName("name1");
        System.out.println("obj: " + obj.getNames());
        System.out.println("local: " + this.list);
        obj.addName("name2");
        System.out.println("obj: " + obj.getNames());
        System.out.println("local: " + this.list);
        obj.addName("name3");
        System.out.println("obj: " + obj.getNames());
        System.out.println("local: " + this.list);
        obj.removeName("name2");
        System.out.println("obj: " + obj.getNames());
        System.out.println("local: " + this.list);
    }

    public static void main(String[] args) {
        new MainClass2().start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt);
        // あくまでListの場合。ArrayやQuereの場合は場合分けした方がよい。
        IndexedPropertyChangeEvent idxEvt = (IndexedPropertyChangeEvent) evt;
        if (idxEvt.getOldValue() == null) {
            this.list.add(idxEvt.getIndex(), (String) idxEvt.getNewValue());
        } else if (idxEvt.getNewValue() == null) {
            this.list.remove(idxEvt.getIndex());
        } else {
            this.list.remove(idxEvt.getIndex());
            this.list.add(idxEvt.getIndex(), (String) idxEvt.getNewValue());
        }
    }

    public static class CollectionProperty {

        List<String> names = new ArrayList<>();

        private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

        public CollectionProperty() {
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
            this.propertyChangeSupport.fireIndexedPropertyChange("names", index, null, name);
        }

        public void removeName(String name) {
            int index = this.names.indexOf(name);
            this.names.remove(name);
            this.propertyChangeSupport.fireIndexedPropertyChange("names", index, name, null);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }
    }
}
