/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Adress;
import facades.UserFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author marcofrydshou1
 */
@Path("adress")
public class AdressRest {

   List<Adress> adress;
   private UserFacade uf;
    private EntityManagerFactory emf;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AdressRest()
    {
        this.emf = Persistence.createEntityManagerFactory("pu_development");
        this.uf = new UserFacade(emf);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCity(@PathParam("id") int id) {
        List<Adress> ad = uf.getadresses();
        if (ad == null) {
        }
        return gson.toJson(ad);
    }

    @GET
    @Path("cities")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCities() {
        String city = null;
        try {
             List<Adress> ad = uf.getadresses();
            System.out.println(gson.toJson(adress));
            city = gson.toJson(adress);
        } catch (Exception e) {
            System.out.println("Exception Error"); //Console 
        }
        return city;
    }
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson()
    {
        return "{\"Yolo\" : \"TEST\"}";
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
}
