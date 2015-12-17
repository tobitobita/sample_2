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

		bean.setId(10);
		bean.setName("AAA");
		bean.setId(10);
		bean.setName("BBB");

//		idProperty.fireValueChangedEvent();
		System.out.println(idProperty.get());
//		nameProperty.fireValueChangedEvent();
		System.out.println(nameProperty.get());
	}

}
