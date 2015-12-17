package dsk.samplemodel.beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SampleJavaFxBean {

	private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
	private final StringProperty name = new SimpleStringProperty(this, "name");

	public int getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public IntegerProperty idProperty() {
		return this.id;
	}

	public StringProperty nameProperty() {
		return this.name;
	}
}
