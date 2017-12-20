package api;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class Message 
{
	private String sender;
	private Text body;
	private Long id;

	  public Text getBody() 
	  {
	    return this.body;
	  }

	  public void setBody(Text text) 
	  {
	    this.body = text;
	  }

	public String getSender() 
	{
		return sender;
	}

	public void setSender(String sender) 
	{
		this.sender = sender;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	private Message(Builder builder)
	{
		this.sender = builder.sender;
		this.body = builder.body;
		this.id = builder.id;
	}
	
	public static Message entityToMessage(Entity entity)
	{
		return new Message.Builder()
			  .sender(entity.getProperty("sender").toString())
			  .id(new Long(entity.getKey().getId()))
			  .body((Text)entity.getProperty("body"))
			  .build();
	}
	
	public Entity toEntity()
	{
		Entity m = new Entity("Message");
		m.setIndexedProperty("sender", this.getSender());
		m.setProperty("body", this.getBody());
		
		return m;	
	}
	
	// Builder pattern
	public static class Builder
	{
		private String sender;
		private Text body;
		private Long id;
		
		public Builder sender(String sender)
		{
			this.sender = sender;
			return this;
		}
		
		public Builder body(Text body)
		{
			this.body = body;
			return this;
		}
		
		public Builder id(Long id)
		{
			this.id = id;
			return this;
		}
		
		public Message build()
		{
			return new Message(this);
		}
	}
}
