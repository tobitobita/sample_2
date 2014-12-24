/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsk.samplejava8bind;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author makoto
 */
public class MainClass implements VetoableChangeListener, PropertyChangeListener {

    public static void main(String[] args) throws Exception {
        new MainClass().start();
    }

    private void start() throws Exception {
        System.out.printf("--- start ---\n");
        {
            Foo foo = new Foo();
            foo.addVetoableChangeListener(this);
            foo.addPropertyChangeListener(this);
            System.out.printf("%s\n", foo.getName());

            foo.setName("NAME");
            foo.setName("NAME2");

            System.out.printf("%s\n", foo.getName());
        }
        {
            IntegerProperty num1 = new SimpleIntegerProperty(1);
            IntegerProperty num2 = new SimpleIntegerProperty(2);
            NumberBinding sum = num1.add(num2);
            System.out.println(sum.getValue());
            num1.set(2);
            System.out.println(sum.getValue());
        }
        {

        }

        System.out.printf("---  end  ---\n");
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        System.out.println(evt);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt);
    }
}

class Foo {

    private String name;

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    public Foo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws PropertyVetoException {
        vetoableChangeSupport.fireVetoableChange("name", this.name, name);
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

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    public VetoableChangeListener[] getVetoableChangeListeners() {
        return vetoableChangeSupport.getVetoableChangeListeners();
    }
}

class SampleLasy {

    private static final String DEFAULT_STR = "Hello";
    private StringProperty str;
    private String cacheStr = DEFAULT_STR;

    public final String getStr() {
        if (str != null) {
            return this.str.get();
        }
        return cacheStr;
    }

    public final void setStr(String str) {
        if (this.str != null) {
            this.str.set(str);
        }
        cacheStr = str;
    }

    public StringProperty strProerty() {
        if (this.str == null) {
            str = new SimpleStringProperty(this, cacheStr, DEFAULT_STR);
        }
        return str;
    }
}
