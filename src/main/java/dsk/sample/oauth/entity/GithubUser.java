package dsk.sample.oauth.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class GithubUser {

	@XmlElement
	private String login;
	@XmlElement
	private long id;
	@XmlElement(name = "avatar_url")
	private String avatarUrl;
	@XmlElement(name = "gravatar_id")
	private String gravatarId;
	@XmlElement
	private String url;
	@XmlElement(name = "html_url")
	private String htmlUrl;
	@XmlElement(name = "followers_url")
	private String followersUrl;
	@XmlElement(name = "following_url")
	private String followingUrl;
	@XmlElement(name = "gists_url")
	private String gistsUrl;
	@XmlElement(name = "starred_url")
	private String starredUrl;
	@XmlElement(name = "subscriptions_url")
	private String subscriptionsUrl;
	@XmlElement(name = "organizations_url")
	private String organizationsUrl;
	@XmlElement(name = "repos_url")
	private String reposUrl;
	@XmlElement(name = "events_url")
	private String eventsUrl;
	@XmlElement(name = "received_events_url")
	private String receivedEventsUrl;
	@XmlElement
	private String type;
	@XmlElement(name = "site_admin")
	private String siteAdmin;
	@XmlElement
	private String name;
	@XmlElement
	private String company;
	@XmlElement
	private String blog;
	@XmlElement
	private String location;
	@XmlElement
	private String email;
	@XmlElement
	private boolean hireable;
	@XmlElement
	private String bio;
	@XmlElement(name = "public_repos")
	private int publicRepos;
	@XmlElement(name = "public_gists")
	private int publicGists;
	@XmlElement
	private int followers;
	@XmlElement
	private int following;
	@XmlElement(name = "created_at")
	private String createdAt;
	@XmlElement(name = "updated_at")
	private String updatedAt;
	@XmlElement(name = "private_gists")
	private int privateGists;
	@XmlElement(name = "total_private_repos")
	private int totalPrivateRepos;
	@XmlElement(name = "owned_private_repos")
	private int ownedPrivateRepos;
	@XmlElement(name = "disk_usage")
	private int diskUsage;
	@XmlElement
	private int collaborators;
	@XmlElement
	private GitHubPlan plan;
}
