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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author jon
 */
@Entity(name="Module")
public class ModuleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String moduleCode;
    private String moduleTitle;
    private String time;
    private String venue;
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private SynopsisEntity synopsis;
    @JoinColumn(name="LECTURER_ID", referencedColumnName="id")
    @ManyToOne(cascade={CascadeType.MERGE})
    private LecturerEntity lecturer;
    @ManyToMany(cascade={CascadeType.PERSIST}, mappedBy="modules")
    private Set<StudentEntity> students = new HashSet<StudentEntity>();
    @OneToMany(cascade={CascadeType.MERGE, CascadeType.REMOVE})
    private Collection<TutorialEntity> tutorials = new ArrayList<TutorialEntity>();

    public void create(String moduleCode, String moduleTitle, String time, String venue) {
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        this.time = time;
        this.venue = venue;
    }
    
    public Collection<TutorialEntity> getTutorials() {
        return tutorials;
    }

    public void setTutorials(Collection<TutorialEntity> tutorials) {
        this.tutorials = tutorials;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }
    
    public LecturerEntity getLecturer() {
        return lecturer;
    }

    public void setLecturer(LecturerEntity lecturer) {
        this.lecturer = lecturer;
    }

    public SynopsisEntity getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(SynopsisEntity synopsis) {
        this.synopsis = synopsis;
    }
    
    public ModuleEntity() {
        setId(System.nanoTime());
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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
        if (!(object instanceof ModuleEntity)) {
            return false;
        }
        ModuleEntity other = (ModuleEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.ModuleEntity[ id=" + id + " ]";
    }
    
}
