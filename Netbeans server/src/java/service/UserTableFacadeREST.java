/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Webservice.Consumption;
import Webservice.Credential;
import Webservice.UserTable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
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
@Path("webservice.usertable")
public class UserTableFacadeREST extends AbstractFacade<UserTable> {

    @PersistenceContext(unitName = "FIT5046A1PU")
    private EntityManager em;

    public UserTableFacadeREST() {
        super(UserTable.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserTable entity) {
        super.create(entity);
    }
    
    @POST
    @Path("{username}/{password}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserTable user, @PathParam("username") String username,@PathParam("password") String password){
        user = em.merge(user);
        em.flush();
        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        credential.setUserId(user);
        credential.setSignupDate(new java.util.Date());
        em.persist(credential);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, UserTable entity) {
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
    public UserTable find(@PathParam("id") Integer id) {
        return super.find(id);
    }


    @GET
    @Path("findByName/{name}")
    @Produces({"application/json"})
    public List<UserTable> findByName(@PathParam("name") String name) {
        Query query = em.createNamedQuery("UserTable.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }

    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<UserTable> findBySurname(@PathParam("surname") String surname) {
        Query query = em.createNamedQuery("UserTable.findBySurname");
        query.setParameter("surname", surname);
        return query.getResultList();
    }

    @GET
    @Path("findByEmail/{email}")
    @Produces({"application/json"})
    public List<UserTable> findByEmail(@PathParam("email") String email) {
        Query query = em.createNamedQuery("UserTable.findByEmail");
        query.setParameter("email", email);
        return query.getResultList();
    }

    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<UserTable> findByDob(@PathParam("dob") java.sql.Date dob) {
        Query query = em.createNamedQuery("UserTable.findByDob");
        query.setParameter("dob", dob);
        return query.getResultList();
    }

    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<UserTable> findByHeight(@PathParam("height") String height) {
        Query query = em.createNamedQuery("UserTable.findByHeight");
        query.setParameter("height", height);
        return query.getResultList();
    }

    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<UserTable> findByWeight(@PathParam("weight") String weight) {
        Query query = em.createNamedQuery("UserTable.findByWeight");
        query.setParameter("weight", weight);
        return query.getResultList();
    }

    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<UserTable> findByGender(@PathParam("gender") String gender) {
        Query query = em.createNamedQuery("UserTable.findByGender");
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<UserTable> findByAddress(@PathParam("address") String address) {
        Query query = em.createNamedQuery("UserTable.findByAddress");
        query.setParameter("address", address);
        return query.getResultList();
    }

    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<UserTable> findByPostcode(@PathParam("postcode") String postcode) {
        Query query = em.createNamedQuery("UserTable.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }

    @GET
    @Path("findByLevelOfActivity/{levelOfActivity}")
    @Produces({"application/json"})
    public List<UserTable> findByLevelOfActivity(@PathParam("levelOfActivity") String levelOfActivity) {
        Query query = em.createNamedQuery("UserTable.findByLevelOfActivity");
        query.setParameter("levelOfActivity", levelOfActivity);
        return query.getResultList();
    }

    @GET
    @Path("findByStepsPerMile/{stepsPerMile}")
    @Produces({"application/json"})
    public List<UserTable> findByStepsPerMile(@PathParam("stepsPerMile") String stepsPerMile) {
        Query query = em.createNamedQuery("UserTable.findByStepsPerMile");
        query.setParameter("stepsPerMile", stepsPerMile);
        return query.getResultList();
    }

    //TASK 3 B
    @GET
    @Path("findByNameANDEmail/{name}/{email}")
    @Produces({"application/json"})
    public List<UserTable> findByNameANDEmail(@PathParam("name") String name, @PathParam("email") String email) {
        TypedQuery query = em.createQuery("SELECT u FROM UserTable u WHERE u.name = :name AND u.email = :email", UserTable.class);
        query.setParameter("name", name);
        query.setParameter("email", email);
        return query.getResultList();
    }

    //TASK 4 A    
    @GET
    @Path("findCaloriePerStep/{id}")
    @Produces("text/plain")
    public String findByidCalculation(@PathParam("id") int id) {
        Query query = em.createNamedQuery("UserTable.findById");
        query.setParameter("id", id);
        List<UserTable> list = query.getResultList();
        double spm = Integer.parseInt(list.get(0).getStepsPerMile());
        double weight = Integer.parseInt(list.get(0).getWeight());
        String Result = Double.toString(weight * 2.20462 * 0.49 / spm);
        return Result;
    }

    //TASK 4 B
    @GET
    @Path("findBMR/{id}")
    @Produces("text/plain")
    public Double findBMR(@PathParam("id") int id) {
        Query query = em.createNamedQuery("UserTable.findById");
        Calendar cal = Calendar.getInstance();
        query.setParameter("id", id);
        List<UserTable> list = query.getResultList();
        String gender = list.get(0).getGender();
        double height = Integer.parseInt(list.get(0).getHeight());
        double weight = Integer.parseInt(list.get(0).getWeight());

        //Calculate Age, first get the dob into int
        Date date = list.get(0).getDob();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //use localdate for calculate period
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(year, month, day);
        Period p = Period.between(birthday, today);

        int age = p.getYears();

        if (gender.equals("Female")) {
            Double result = (9.563 * weight) + (1.85 * height) - (4.676 * age) + 655.1;
            return result;
        } else {
            Double result = (13.75 * weight) + (5.003 * height) - (6.755 * age) + 66.5;
            return result;
        }
    }

    //TASK 4 C
    @GET
    @Path("findCaloriesPerDay/{id}")
    @Produces("text/plain")
    public Double findDailyCalorieBurned(@PathParam("id") int id) {
        Query query = em.createNamedQuery("UserTable.findById");
        Calendar cal = Calendar.getInstance();
        query.setParameter("id", id);
        List<UserTable> list = query.getResultList();
        String level = list.get(0).getLevelOfActivity();
        if (level.equals("1")) {
            return findBMR(id) * 1.2;
        } else if (level.equals("2")) {
            return findBMR(id) * 1.375;
        } else if (level.equals("3")) {
            return findBMR(id) * 1.55;
        } else if (level.equals("4")) {
            return findBMR(id) * 1.725;
        } else {
            return findBMR(id) * 1.9;
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserTable> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserTable> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
