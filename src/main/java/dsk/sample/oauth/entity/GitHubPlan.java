package dsk.sample.oauth.entity;

import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
public class GitHubPlan {

	@XmlElement
	private String name;
	@XmlElement
	private int space;
	@XmlElement(name = "private_repos")
	private int privateRepos;
	@XmlElement
	private int collaborators;
}
