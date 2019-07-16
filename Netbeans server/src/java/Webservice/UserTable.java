/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Webservice;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johnny
 */
@Entity
@Table(name = "USER_TABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTable.findAll", query = "SELECT u FROM UserTable u")
    , @NamedQuery(name = "UserTable.findById", query = "SELECT u FROM UserTable u WHERE u.id = :id")
    , @NamedQuery(name = "UserTable.findByName", query = "SELECT u FROM UserTable u WHERE u.name = :name")
    , @NamedQuery(name = "UserTable.findBySurname", query = "SELECT u FROM UserTable u WHERE u.surname = :surname")
    , @NamedQuery(name = "UserTable.findByEmail", query = "SELECT u FROM UserTable u WHERE u.email = :email")
    , @NamedQuery(name = "UserTable.findByDob", query = "SELECT u FROM UserTable u WHERE u.dob = :dob")
    , @NamedQuery(name = "UserTable.findByHeight", query = "SELECT u FROM UserTable u WHERE u.height = :height")
    , @NamedQuery(name = "UserTable.findByWeight", query = "SELECT u FROM UserTable u WHERE u.weight = :weight")
    , @NamedQuery(name = "UserTable.findByGender", query = "SELECT u FROM UserTable u WHERE u.gender = :gender")
    , @NamedQuery(name = "UserTable.findByAddress", query = "SELECT u FROM UserTable u WHERE u.address = :address")
    , @NamedQuery(name = "UserTable.findByPostcode", query = "SELECT u FROM UserTable u WHERE u.postcode = :postcode")
    , @NamedQuery(name = "UserTable.findByLevelOfActivity", query = "SELECT u FROM UserTable u WHERE u.levelOfActivity = :levelOfActivity")
    , @NamedQuery(name = "UserTable.findByStepsPerMile", query = "SELECT u FROM UserTable u WHERE u.stepsPerMile = :stepsPerMile")})
public class UserTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "SURNAME")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "HEIGHT")
    private String height;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "WEIGHT")
    private String weight;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "GENDER")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "POSTCODE")
    private String postcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "LEVEL_OF_ACTIVITY")
    private String levelOfActivity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "STEPS_PER_MILE")
    private String stepsPerMile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Credential> credentialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Report> reportCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Consumption> consumptionCollection;

    public UserTable() {
    }

    public UserTable(Integer id) {
        this.id = id;
    }

    public UserTable(Integer id, String name, String surname, String email, Date dob, String height, String weight, String gender, String address, String postcode, String levelOfActivity, String stepsPerMile) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelOfActivity = levelOfActivity;
        this.stepsPerMile = stepsPerMile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLevelOfActivity() {
        return levelOfActivity;
    }

    public void setLevelOfActivity(String levelOfActivity) {
        this.levelOfActivity = levelOfActivity;
    }

    public String getStepsPerMile() {
        return stepsPerMile;
    }

    public void setStepsPerMile(String stepsPerMile) {
        this.stepsPerMile = stepsPerMile;
    }

    @XmlTransient
    public Collection<Credential> getCredentialCollection() {
        return credentialCollection;
    }

    public void setCredentialCollection(Collection<Credential> credentialCollection) {
        this.credentialCollection = credentialCollection;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @XmlTransient
    public Collection<Consumption> getConsumptionCollection() {
        return consumptionCollection;
    }

    public void setConsumptionCollection(Collection<Consumption> consumptionCollection) {
        this.consumptionCollection = consumptionCollection;
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
        if (!(object instanceof UserTable)) {
            return false;
        }
        UserTable other = (UserTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Webservice.UserTable[ id=" + id + " ]";
    }
    
}
