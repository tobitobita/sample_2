package dsk.samplejsonp.entity;

import java.io.Serializable;

public class Theme implements Serializable {

	private long id;
	private String theme;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
