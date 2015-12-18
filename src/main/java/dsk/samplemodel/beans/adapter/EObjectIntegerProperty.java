package dsk.samplemodel.beans.adapter;

import com.sun.javafx.binding.ExpressionHelper;
import com.sun.javafx.property.adapter.Disposer;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.adapter.JavaBeanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sun.reflect.misc.MethodUtil;

public final class EObjectIntegerProperty extends IntegerProperty implements JavaBeanProperty<Number> {

	private final PropertyDescriptor descriptor;
	private final PropertyDescriptor.Listener<Number> listener;

	private ObservableValue<? extends Number> observable = null;
	private ExpressionHelper<Number> helper = null;

	private final AccessControlContext acc = AccessController.getContext();

	EObjectIntegerProperty(PropertyDescriptor descriptor, Object bean) {
		this.descriptor = descriptor;
		this.listener = descriptor.new Listener<Number>(bean, this);
		descriptor.addListener(listener);
		Disposer.addRecord(this, new DescriptorListenerCleaner(descriptor, listener));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws UndeclaredThrowableException if calling the getter of the Java Bean property throws an
	 * {@code IllegalAccessException} or an {@code InvocationTargetException}.
	 */
	@Override
	public int get() {
		return AccessController.doPrivileged((PrivilegedAction<Integer>) () -> {
			try {
				return ((Number) MethodUtil.invoke(
						descriptor.getGetter(), getBean(), (Object[]) null)).intValue();
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new UndeclaredThrowableException(e);
			}
		}, acc);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws UndeclaredThrowableException if calling the getter of the Java Bean property throws an
	 * {@code IllegalAccessException} or an {@code InvocationTargetException}.
	 */
	@Override
	public void set(final int value) {
		if (isBound()) {
			throw new RuntimeException("A bound value cannot be set.");
		}
		AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
			try {
				MethodUtil.invoke(descriptor.getSetter(), getBean(), new Object[]{value});
				ExpressionHelper.fireValueChangedEvent(helper);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new UndeclaredThrowableException(e);
			}
			return null;
		}, acc);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(ObservableValue<? extends Number> observable) {
		if (observable == null) {
			throw new NullPointerException("Cannot bind to null");
		}

		if (!observable.equals(this.observable)) {
			unbind();
			set(observable.getValue().intValue());
			this.observable = observable;
			this.observable.addListener(listener);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unbind() {
		if (observable != null) {
			observable.removeListener(listener);
			observable = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBound() {
		return observable != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getBean() {
		return listener.getBean();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return descriptor.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(ChangeListener<? super Number> listener) {
		helper = ExpressionHelper.addListener(helper, this, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(ChangeListener<? super Number> listener) {
		helper = ExpressionHelper.removeListener(helper, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(InvalidationListener listener) {
		helper = ExpressionHelper.addListener(helper, this, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(InvalidationListener listener) {
		helper = ExpressionHelper.removeListener(helper, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireValueChangedEvent() {
		ExpressionHelper.fireValueChangedEvent(helper);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		descriptor.removeListener(listener);

	}
}
