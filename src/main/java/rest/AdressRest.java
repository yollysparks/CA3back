/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Adress;
import facades.UserFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author marcofrydshou1
 */
@Path("adress")
public class AdressRest {

   
   private UserFacade uf;
    private EntityManagerFactory emf;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AdressRest()
    {
        this.emf = Persistence.createEntityManagerFactory("pu_development");
        this.uf = new UserFacade(emf);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson()
    {
        return "{\"I'm serious as a heartattack\" : \"TEST\"}";
    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlaces()
    {
        List<Adress> adress = uf.getadresses();
        String result = gson.toJson(adress);
        return result;

    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postadrs (String jsonstring){
        JsonObject json = new JsonParser().parse(jsonstring).getAsJsonObject();
        String city = json.get("city").getAsString();
        String street = json.get("street").getAsString();
        int zip = json.get("zip").getAsInt();
        String descrip = json.get("descrip").getAsString();
        String imageUri = json.get("imageUri").getAsString();
        String geo = json.get("geo").getAsString();
        int rating = json.get("rating").getAsInt();
        
        Adress adress = new Adress(street, zip, city, imageUri, descrip, geo, rating);
        
        Adress adressreturn = uf.addadress(adress);
        return gson.toJson(adressreturn);
        
    }
    
}
