package dsk.samplemodel.beans;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class MainJavaBean {

	public static void main(String[] args) throws NoSuchMethodException {
		SampleJavaBean bean = new SampleJavaBean();
		bean.addPropertyChangeListener(event -> {
			System.out.printf("propertyName:%s, oldValue:%s, newValue:%s\n", event.getPropertyName(), event.getOldValue(), event.getNewValue());
		});
		bean.addPropertyChangeListener("id", event -> {
			System.out.printf("id only, propertyName:%s, oldValue:%s, newValue:%s\n", event.getPropertyName(), event.getOldValue(), event.getNewValue());
		});

		JavaBeanIntegerProperty idProperty = JavaBeanIntegerPropertyBuilder.create().bean(bean).name("id").build();

		idProperty.addListener(observable -> {
			System.out.printf("JavaBeanIntegerProperty %s \n", observable);
		});
		JavaBeanStringProperty nameProperty = JavaBeanStringPropertyBuilder.create().bean(bean).name("name").build();
		nameProperty.addListener(observable -> {
			System.out.printf("JavaBeanStringProperty %s \n", observable);
		});

		SampleJavaFxBean fxBean = new SampleJavaFxBean();
		fxBean.idProperty().bindBidirectional(idProperty);
		fxBean.nameProperty().bindBidirectional(nameProperty);

		idProperty.set(10);
		nameProperty.set("AAA");
		System.out.printf("fxbean, id:%d, name:%s\n", fxBean.getId(), fxBean.getName());

		idProperty.set(10);
		nameProperty.set("BBB");

		System.out.printf("fxbean, id:%d, name:%s\n", fxBean.getId(), fxBean.getName());
	}
}
