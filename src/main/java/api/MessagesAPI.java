package api;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Text;

//[START echo_api_annotation]
@Api(
 name = "messages",
 version = "v1",
 namespace =
 @ApiNamespace(
     ownerDomain = "m1-tiny-tweet.appspot.com",
     ownerName = "m1-tiny-tweet.appspot.com",
     packagePath = ""
 ),
 // [START_EXCLUDE]
 issuers = {
     @ApiIssuer(
         name = "Quentin_Mazoua",
         issuer = "https://securetoken.google.com/m1-tiny-tweet",
         jwksUri =
             "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system"
                 + ".gserviceaccount.com"
     )
 }
//[END_EXCLUDE]
)
//[END echo_api_annotation]

public class MessagesAPI 
{
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  // [START echo_method]
	  @ApiMethod(name = "echo_path_parameter", path = "echo/{n}")
	  public Message echoPathParameter(Message message, @Named("n") int n) 
	  {
	    return doEcho(message, n);
	  }
	  // [END echo_method]
	  
	  // [START getMessages_method]
	  @ApiMethod(name = "get_messages", path = "users/{user}/messages")
	  public List<Message> getMessages(@Named("user") String user)
	  {
		  return retrieveMessages(user);
	  }
	  // [END getMessages_method]
  
	  // [START getMessages_method]
	  @ApiMethod(name = "add_message", path = "messages/add", httpMethod = ApiMethod.HttpMethod.POST)
	  public void addMessage(@Named("sender") String sender, Text message, @Named("receivers") List<String> receivers)
	  {
		  createMessage(sender, message, receivers);
	  }
	  // [END getMessages_method]
	  
	  // [START addUser_method]
	  @ApiMethod(name = "add_user", path = "users/add", httpMethod = ApiMethod.HttpMethod.POST)
	  public void addUser(@Named("user") String name)
	  {
		  createUser(name);
	  }
	  // [END addUser_method]
	  
	  // [START followUser_method]
	  @ApiMethod(name = "follow_user", path = "users/follow", httpMethod = ApiMethod.HttpMethod.POST)
	  public void followUser(@Named("name") String name, @Named("follower")  String follower) throws EntityNotFoundException
	  {
		  follow_User(name,follower);
	  }
	  // [END followUser_method]
	  
	  // [START getUser_method]
	  public Key getUser(@Named("name") String name)
	  {
		  Filter propertyFilter = new FilterPredicate("name", FilterOperator.EQUAL, name);
			
			Query query = new Query("User").setFilter(propertyFilter);
			List<Entity> results =
			    datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());// Run the query
			
			Entity usr = null;
			
			for(Entity e:results)
			{	
				usr = e;
			}
				
			return usr.getKey();
	  }
	  // [END getUser_method]
	  
	  


	private Message doEcho(Message message, Integer n) 
	  {
		    if (n != null && n >= 0) 
		    {
		      StringBuilder sb = new StringBuilder();
		      for (int i = 0; i < n; i++) 
		      {
		        if (i > 0) {
		          sb.append(" ");
		        }
		        sb.append(message.getBody());
		      }
		      message.setBody(new Text(sb.toString()));
		    }
		    return message;
	  }
	  
	  private List<Message> retrieveMessages(String user)
	  {	  
			Filter propertyFilter = new FilterPredicate("receivers", FilterOperator.EQUAL, user);
			
			  Query query = new Query("MessageIndex").setKeysOnly().setFilter(propertyFilter);
			  List<Entity> results =
					    datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());// Run the query
			  
			  List<Message> messages = new ArrayList<Message>();
			try {
				for(Entity e:results)
				{	
					messages.add(Message.entityToMessage(datastore.get(e.getParent())));
				}
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return messages; 	  
	  }
	  
	  private void createMessage(String sender, Text body, List<String> receivers)
	  {
		  Message m = new Message.Builder()
				  .sender(sender)
				  .body(body)
				  .build();
		  
		  Key parent = datastore.put(m.toEntity());
		  
		  MessageIndex mInd = new MessageIndex.Builder()
				  .receivers(receivers)
				  .parent(parent)
				  .build();
		  
		  datastore.put(mInd.toEntity());
		  
	  }
	  
	  private void createUser(String name) {
		User u = new User.Builder()
				.name(name)
				.followers(new ArrayList<String>())
				.build();

		datastore.put(u.toEntity());
	  }
	  
	  
	@SuppressWarnings("unchecked")
	private void follow_User(String name, String follower) throws EntityNotFoundException {
		Key usrKey = getUser(name);
		Entity usr = datastore.get(usrKey);
		List<String> followers;
		try {
			followers = (List<String>) usr.getProperty("followers");
			if (followers == null)
			{
				followers = new ArrayList<String>();
			}
		}
		catch (NullPointerException ex) {
			followers = new ArrayList<String>();
		}
		followers.add(follower);
		usr.setProperty("followers",followers);
		
		datastore.put(usr);
	  }
}
