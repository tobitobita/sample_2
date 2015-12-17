package dsk.samplemodel.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public abstract class JavaBeanBase {

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		this.vetoableChangeSupport.addVetoableChangeListener(listener);
	}

	public void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
		this.vetoableChangeSupport.addVetoableChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		this.vetoableChangeSupport.removeVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
		this.vetoableChangeSupport.removeVetoableChangeListener(propertyName, listener);
	}

	protected void fireP(String propertyName, Object oldValue, Object newValue) {
		this.propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void fireP(String propertyName, int oldValue, int newValue) {
		this.propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void fireP(String propertyName, boolean oldValue, boolean newValue) {
		this.propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void fireV(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException {
		this.vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected void fireV(String propertyName, int oldValue, int newValue) throws PropertyVetoException {
		this.vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected void fireV(String propertyName, boolean oldValue, boolean newValue) throws PropertyVetoException {
		this.vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}
}
