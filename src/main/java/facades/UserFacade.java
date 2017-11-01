package facades;

import entity.Adress;
import entity.Role;
import security.IUserFacade;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

  EntityManagerFactory emf;

  public UserFacade(EntityManagerFactory emf) {
    this.emf = emf;   
  }

 
  private EntityManager getEntityManager() {
    return emf.createEntityManager();
  }
  
  public List<Adress> getadresses(){
      List<Adress> adress;
      EntityManager em = getEntityManager();
      try{
          Query q = em.createNamedQuery("Address.findAllAddress");
          adress = q.getResultList();
          return adress;
      }catch(Exception e){
          return null;
      }finally{
          em.close();
      }
  }
  
  public Adress addadress(Adress adress){
      EntityManager em = getEntityManager();
      try{
          em.getTransaction().begin();
      em.persist(adress);
      em.getTransaction().commit();
      return adress;
  
      }catch(Exception e){
          
      em.getTransaction().rollback();
      return null;
  }finally{
          em.close();
      }
}
  
  
  
  public List<IUser> getuser(){
      EntityManager em = emf.createEntityManager();
      List<IUser> userlist;
      try{
          em.getTransaction().begin();
          Query q = em.createNamedQuery("User.findAllUsers");
          userlist = q.getResultList();
          return userlist;
      }catch(Exception e){
          return new ArrayList<>();
      }finally{
          em.close();
      }
             
  }
  
  
  public User deleteuser(String name){
      EntityManager em = emf.createEntityManager();
      try{
          em.getTransaction().begin();
          User u = em.find(User.class, name);
          em.remove(u);
          em.getTransaction().commit();
          return u;
      }finally{
          em.close();
          
      }
    
  }
  
  
  public User edituser(String newRole, String oldusername){
      EntityManager em = getEntityManager();
      try{
          User user = em.find(User.class, oldusername);
          System.out.println(user.getUserName());
          em.getTransaction().begin();
          Role role = em.find(Role.class, newRole);
          user.addRole(role);
          em.merge(user);
              em.getTransaction().commit();
          } catch(Exception e) 
          {
    e.printStackTrace();
      }finally{
    em.close();
      }
     
      return null;
      
  }
  
  public User adduser(String username, String password){
      EntityManager em = getEntityManager();
      try{
          User user = new User(username, password);
          Role role = em.find(Role.class, "User");
          user.addRole(role);
          em.getTransaction().begin();
          em.persist(user);
          em.getTransaction().commit();
          return user;
    
      }catch (PasswordStorage.CannotPerformOperationException e){
          return null;
      }finally{
          em.close();
      }
    
  }
  
  
  

  @Override
  public IUser getUserByUserId(String id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(User.class, id);
    } finally {
      em.close();
    }
  }

  /*
  Return the Roles if users could be authenticated, otherwise null
   */
  @Override
  public List<String> authenticateUser(String userName, String password) {
    try {
      System.out.println("User Before:" + userName+", "+password);
      IUser user = getUserByUserId(userName);  
      System.out.println("User After:" + user.getUserName()+", "+user.getPasswordHash());
      return user != null && PasswordStorage.verifyPassword(password, user.getPasswordHash()) ? user.getRolesAsStrings() : null;
    } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
      throw new NotAuthorizedException("Invalid username or password", Response.Status.FORBIDDEN);
    }
  }

}