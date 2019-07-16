/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Webservice.Report;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
import static jdk.nashorn.tools.ShellFunctions.input;

/**
 *
 * @author Johnny
 */
@Stateless
@Path("webservice.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "FIT5046A1PU")
    private EntityManager em;
    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findByUserId/{id}")
    @Produces({"application/json"})
    public List<Report> findByUserId(@PathParam("id") int id) {
        Query query = em.createNamedQuery("Report.findByUserId");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Report> findByDate(@PathParam("date") java.sql.Date date) {
        Query query = em.createNamedQuery("Report.findByDate");
        query.setParameter("date", date);
        return query.getResultList();
    }
    
    
    @GET
    @Path("findByIDAndDate/{id}/{date}")
    @Produces({"application/json"})
    public List<Report> findByIDAndDate(@PathParam("id") int id, @PathParam("date") java.sql.Date date) {
        TypedQuery query = em.createQuery("SELECT r FROM Report r WHERE r.userId.id = :id AND r.date = :date", Report.class);
        query.setParameter("id", id);
        query.setParameter("date", date);
        return query.getResultList();
    }


    @GET
    @Path("findByTotalCaloriesConsumed/{totalCaloriesConsumed}")
    @Produces({"application/json"})
    public List<Report> findByTotalCaloriesConsumed(@PathParam("totalCaloriesConsumed") String totalCaloriesConsumed) {
        Query query = em.createNamedQuery("Report.findByTotalCaloriesConsumed");
        query.setParameter("totalCaloriesConsumed", totalCaloriesConsumed);
        return query.getResultList();
    }

    @GET
    @Path("findByTotalCaloriesBurned/{totalCaloriesBurned}")
    @Produces({"application/json"})
    public List<Report> findByTotalCaloriesBurned(@PathParam("totalCaloriesBurned") String totalCaloriesBurned) {
        Query query = em.createNamedQuery("Report.findByTotalCaloriesBurned");
        query.setParameter("totalCaloriesBurned", totalCaloriesBurned);
        return query.getResultList();
    }

    @GET
    @Path("findByTotalSteps/{totalSteps}")
    @Produces({"application/json"})
    public List<Report> findByTotalSteps(@PathParam("totalSteps") String totalSteps) {
        Query query = em.createNamedQuery("Report.findByTotalSteps");
        query.setParameter("totalSteps", totalSteps);
        return query.getResultList();
    }

    @GET
    @Path("findByCaloriesGoal/{caloriesGoal}")
    @Produces({"application/json"})
    public List<Report> findBycaloriesGoal(@PathParam("caloriesGoal") String caloriesGoal) {
        Query query = em.createNamedQuery("Report.findByCaloriesGoal");
        query.setParameter("caloriesGoal", caloriesGoal);
        return query.getResultList();
    }

    //TASK 3 C
    @GET
    @Path("findByNameANDCalorieBurned/{name}/{totalCaloriesBurned}")
    @Produces({"application/json"})
    public List<Report> findByNameANDCalorieBurned(@PathParam("name") String name, @PathParam("totalCaloriesBurned") String totalCaloriesBurned) {
        TypedQuery query = em.createQuery("SELECT r FROM Report r,UserTable u WHERE r.userId.name = :name AND "
                + "r.totalCaloriesBurned = :totalCaloriesBurned AND r.userId.id = u.id", Report.class);
        query.setParameter("name", name);
        query.setParameter("totalCaloriesBurned", totalCaloriesBurned);
        return query.getResultList();
    }

    //TASK 3 D
    @GET
    @Path("findByNameANDCaloriesConsumed/{name}/{totalCaloriesConsumed}")
    @Produces({"application/json"})
    public List<Report> findByNameANDCaloriesConsumed(@PathParam("name") String name, @PathParam("totalCaloriesConsumed") String totalCaloriesConsumed) {
        Query query = em.createNamedQuery("Report.findByNameANDCaloriesConsumed");
        query.setParameter("name", name);
        query.setParameter("totalCaloriesConsumed", totalCaloriesConsumed);
        return query.getResultList();
    }

    //TASK 5 A
    @GET
    @Path("findListCalories/{id}/{date}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findCalorieList(@PathParam("id") int id, @PathParam("date") java.sql.Date date) {

        List<Object[]> queryList = em.createQuery("SELECT r.totalCaloriesConsumed,r.totalCaloriesBurned,r.caloriesGoal "
                + " FROM Report r , UserTable u Where r.date = :date AND r.userId.id = :id AND r.userId.id = u.id", Object[].class)
                .setParameter("id", id).setParameter("date", date).getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder().
                    add("totalCaloriesConsumed", (String) row[0])
                    .add("totalCaloriesBurned", (String) row[1])
                    .add("remainCalories", Integer.parseInt((String) row[2]) - Integer.parseInt((String) row[1])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    //TASK 5 B
    @GET
    @Path("findListCalories/{id}/{startdate}/{enddate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object approach2(@PathParam("id") int id, @PathParam("startdate") java.sql.Date startdate,
            @PathParam("enddate") java.sql.Date enddate) throws ParseException {

        List<Object[]> queryList = em.createQuery("SELECT r.totalCaloriesConsumed,r.totalCaloriesBurned,r.totalSteps,r.date "
                + " FROM Report r Where r.userId.id = :id ", Object[].class)
                .setParameter("id", id).getResultList();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        int totalsteps = 0;
        int totalcaloriesconsumed = 0;
        int totalcaloriesburned = 0;

        for (int i = 0; i < queryList.size(); i++) {

            String sDate = queryList.get(i)[3].toString();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date olddate = formatter.parse(sDate);
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            String stringdate = formatter.format(olddate);
            Date date = formatter.parse(stringdate);

            if (date.after(startdate) && date.before(enddate)) {
                totalcaloriesconsumed = Integer.parseInt(queryList.get(i)[0].toString()) + totalcaloriesconsumed;
                totalcaloriesburned = Integer.parseInt(queryList.get(i)[1].toString()) + totalcaloriesburned;
                totalsteps = Integer.parseInt(queryList.get(i)[2].toString()) + totalsteps;

            }
        }

        JsonObject personObject = Json.createObjectBuilder().
                add("TotalCaloriesConsumed", totalcaloriesconsumed)
                .add("TotalCaloriesBurned", totalcaloriesburned)
                .add("TotalSteps", totalsteps).build();
        arrayBuilder.add(personObject);

        JsonArray jArray = arrayBuilder.build();
        return jArray;

    }
    
    
    
    
        @GET
    @Path("findReportInsideRange/{id}/{startdate}/{enddate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object approach3(@PathParam("id") int id, @PathParam("startdate") java.sql.Date startdate,
            @PathParam("enddate") java.sql.Date enddate) throws ParseException {

        List<Object[]> queryList = em.createQuery("SELECT r.totalCaloriesConsumed,r.totalCaloriesBurned,r.totalSteps,r.date "
                + " FROM Report r Where r.userId.id = :id ", Object[].class)
                .setParameter("id", id).getResultList();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (int i = 0; i < queryList.size(); i++) {

            String sDate = queryList.get(i)[3].toString();          
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date olddate = formatter.parse(sDate);
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            String stringdate = formatter.format(olddate);
            Date date = formatter.parse(stringdate);

            if (date.after(startdate) && date.before(enddate)) {
                int totalcaloriesconsumed = Integer.parseInt(queryList.get(i)[0].toString());
                int totalcaloriesburned = Integer.parseInt(queryList.get(i)[1].toString());
                int totalsteps = Integer.parseInt(queryList.get(i)[2].toString());
                
                
               JsonObject personObject = Json.createObjectBuilder().
                add("CaloriesConsumed", totalcaloriesconsumed)
                .add("CaloriesBurned", totalcaloriesburned)                     
                .add("Date",stringdate).build();
                        arrayBuilder.add(personObject);
            }
        }

        JsonArray jArray = arrayBuilder.build();
        return jArray;

    }
    

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
