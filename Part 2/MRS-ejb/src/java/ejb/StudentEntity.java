/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author jon
 */
@Entity(name="Student")
public class StudentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String matricNumber;
    private String name;
    private String password;
    private String email;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "student")
    private Collection<AppealEntity> appeals = new ArrayList<AppealEntity>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name="STUDENTMMBI_TUTORIALMMBI")
    private Set<TutorialEntity> tutorials = new HashSet<TutorialEntity>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name="STUDENTMMBI_MODULEMMBI")
    private Set<ModuleEntity> modules = new HashSet<ModuleEntity>();
    
    public void create(String matricNumber, String name, String password, String email) {
        this.matricNumber = matricNumber;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Set<TutorialEntity> getTutorials() {
        return tutorials;
    }

    public void setTutorials(Set<TutorialEntity> tutorials) {
        this.tutorials = tutorials;
    }

    public Collection<AppealEntity> getAppeals() {
        return appeals;
    }

    public void setAppeals(Collection<AppealEntity> appeals) {
        this.appeals = appeals;
    }

    public Set<ModuleEntity> getModules() {
        return modules;
    }

    public void setModules(Set<ModuleEntity> modules) {
        this.modules = modules;
    }

    public StudentEntity() {
        setId(System.nanoTime());
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof StudentEntity)) {
            return false;
        }
        StudentEntity other = (StudentEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.StudentEntity[ id=" + id + " ]";
    }
    
}
