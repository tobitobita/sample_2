package dsk.samplemodel.beans;

public class SampleJavaBean extends JavaBeanBase {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.fireP("id", this.id, id);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.fireP("name", this.name, name);
		this.name = name;
	}
}
