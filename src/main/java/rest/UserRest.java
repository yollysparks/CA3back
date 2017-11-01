package rest;

import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("demouser")
@RolesAllowed("User")
public class UserRest {
    
    private UserFacade uf;
    
//  @Path("/register")  
//  @POST
//  @Consumes(MediaType.APPLICATION_JSON)
//  public String register(){
//     return "{\"message\" : \"Registered (Accesible by only authenticated USERS)\"}";
//  }
  
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSomething(){
    return "{\"message\" : \"Hello User from Server (Accesible by only authenticated USERS)\"}"; 
  }
  
  
  
}
  
  
