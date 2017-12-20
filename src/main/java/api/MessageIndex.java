package api;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class MessageIndex 
{
	private List<String> receivers;
	private Key parent;
	private Long id;
	
	public List<String> getReceivers() 
	{
		return receivers;
	}
	
	public void setReceivers(List<String> receivers)
	{
		this.receivers = receivers;
	}
	
	public Key getParent() 
	{
		return parent;
	}

	public void setParent(Key parent) 
	{
		this.parent = parent;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	private MessageIndex(Builder builder)
	{
		this.receivers = builder.receivers;
		this.parent = builder.parent;
		this.id = builder.id;
	}
	
	public Entity toEntity()
	{
		Entity m = new Entity("MessageIndex", this.getParent());
		m.setIndexedProperty("receivers", this.getReceivers());
		
		return m;	
	}
	
	// Builder pattern
	public static class Builder
	{
		private List<String> receivers;
		private Key parent;
		private Long id;
		
		public Builder receivers(List<String> receivers)
		{
			this.receivers = receivers;
			return this;
		}
		
		public Builder parent(Key parent)
		{
			this.parent = parent;
			return this;
		}
		
		public Builder id(Long id)
		{
			this.id = id;
			return this;
		}
		
		public MessageIndex build()
		{
			return new MessageIndex(this);
		}
	}
}
