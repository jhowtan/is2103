/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author jon
 */
@Entity(name="Tutorial")
public class TutorialEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String groupNumber;
    private String time;
    private String venue;
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy="tutorials")
    private Set<StudentEntity> students = new HashSet<StudentEntity>();
    @ManyToOne(cascade = {CascadeType.ALL})
    private TutorEntity tutor = new TutorEntity();

    public void create(String groupNumber, String time, String venue) {
        this.groupNumber = groupNumber;
        this.time = time;
        this.venue = venue;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }

    public TutorEntity getTutor() {
        return tutor;
    }

    public void setTutor(TutorEntity tutor) {
        this.tutor = tutor;
    }

    public TutorialEntity() {
        setId(System.nanoTime());
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
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
        if (!(object instanceof TutorialEntity)) {
            return false;
        }
        TutorialEntity other = (TutorialEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.TutorialEntity[ id=" + id + " ]";
    }
    
}
