package api;

import java.util.List;

import com.google.appengine.api.datastore.Entity;

public class User {
	
	private Long id;
	private String name;
	private List<String> followers;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFollowers() {
		return followers;
	}
	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}
	private User(Builder builder)
	{
		this.followers = builder.followers;
		this.name = builder.name;
		this.id = builder.id;
	}
	
	@SuppressWarnings("unchecked")
	public static User entityToUser(Entity entity)
	{
		return new User.Builder()
			  .name(entity.getProperty("name").toString())
			  .id(new Long(entity.getKey().getId()))
			  .followers((List<String>)entity.getProperty("followers"))
			  .build();
	}
	
	public Entity toEntity()
	{
		Entity u = new Entity("User");
		u.setIndexedProperty("name", this.getName());
		u.setProperty("followers", this.getFollowers());
		
		return u;	
	}
	
// Builder pattern
	public static class Builder
	{
		private Long id;
		private String name;
		private List<String> followers;
		
		public Builder followers(List<String> followers)
		{
			this.followers = followers;
			return this;
		}
		
		public Builder name(String name)
		{
			this.name = name;
			return this;
		}
		
		public Builder id(Long id)
		{
			this.id = id;
			return this;
		}
		
		public User build()
		{
			return new User(this);
		}
	}
}

