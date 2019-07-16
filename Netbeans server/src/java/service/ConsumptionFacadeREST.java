/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Webservice.Consumption;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Johnny
 */
@Stateless
@Path("webservice.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "FIT5046A1PU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
        
    
    @GET
    @Path("findByUserId/{id}")
    @Produces({"application/json"})
    public List<Consumption> findByUserId(@PathParam("id") int id) {
        Query query = em.createNamedQuery("Consumption.findByUserId");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Consumption> findByDate(@PathParam("date") Date date) {
        Query query = em.createNamedQuery("Consumption.findByDate");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @GET
    @Path("findByQuantity/{quantity}")
    @Produces({"application/json"})
    public List<Consumption> findByQuantity(@PathParam("quantity") String quantity) {
        Query query = em.createNamedQuery("Consumption.findByQuantity");
        query.setParameter("quantity", quantity);
        return query.getResultList();
    }
        

    
    //Task 4 D
    @GET
    @Path("findByFoodCalorie/{id}/{date}")
    @Produces({"text/plain"})
    public int findByFoodCalorie(@PathParam("id") int id, @PathParam("date") Date date) {
        TypedQuery query = em.createQuery("SELECT c FROM Consumption c,UserTable u,Food f WHERE c.userId.id = :id AND c.date = :date"
                + " AND c.userId.id = u.id AND c.foodId.id = f.id ", Consumption.class);
        query.setParameter("id", id);
        query.setParameter("date", date);   
        List<Consumption> list = query.getResultList();
        int totalCalorie = 0;
       for (int i=0;i<list.size();i++) {
        totalCalorie = totalCalorie + 
                     Integer.parseInt(list.get(i).foodId.getCalorieAmount()) * Integer.parseInt(list.get(i).getQuantity());
       }
       
        return totalCalorie;
    } 


    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
