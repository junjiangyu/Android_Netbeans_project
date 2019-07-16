/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webservice;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Johnny
 */
@Entity
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findById", query = "SELECT r FROM Report r WHERE r.id = :id")
    , @NamedQuery(name = "Report.findByUserId", query = "SELECT r FROM Report r WHERE r.userId.id = :id")
    , @NamedQuery(name = "Report.findByDate", query = "SELECT r FROM Report r WHERE r.date = :date")
    , @NamedQuery(name = "Report.findByTotalCaloriesConsumed", query = "SELECT r FROM Report r WHERE r.totalCaloriesConsumed = :totalCaloriesConsumed")
    , @NamedQuery(name = "Report.findByTotalCaloriesBurned", query = "SELECT r FROM Report r WHERE r.totalCaloriesBurned = :totalCaloriesBurned")
    , @NamedQuery(name = "Report.findByTotalSteps", query = "SELECT r FROM Report r WHERE r.totalSteps = :totalSteps")
    , @NamedQuery(name = "Report.findByCaloriesGoal", query = "SELECT r FROM Report r WHERE r.caloriesGoal = :caloriesGoal")
    , @NamedQuery(name = "Report.findByNameANDCaloriesConsumed", query = "SELECT r FROM Report r,UserTable u WHERE r.userId.name = :name AND "
            + "r.totalCaloriesConsumed = :totalCaloriesConsumed AND r.userId.id = u.id")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "TOTAL_CALORIES_CONSUMED")
    private String totalCaloriesConsumed;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "TOTAL_CALORIES_BURNED")
    private String totalCaloriesBurned;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "TOTAL_STEPS")
    private String totalSteps;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "CALORIES_GOAL")
    private String caloriesGoal;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UserTable userId;

    public Report() {
    }

    public Report(Integer id) {
        this.id = id;
    }

    public Report(Integer id, Date date, String totalCaloriesConsumed, String totalCaloriesBurned, String totalSteps, String caloriesGoal) {
        this.id = id;
        this.date = date;
        this.totalCaloriesConsumed = totalCaloriesConsumed;
        this.totalCaloriesBurned = totalCaloriesBurned;
        this.totalSteps = totalSteps;
        this.caloriesGoal = caloriesGoal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(String totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public String getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(String totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(String caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }

    public UserTable getUserId() {
        return userId;
    }

    public void setUserId(UserTable userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Webservice.Report[ id=" + id + " ]";
    }

}
