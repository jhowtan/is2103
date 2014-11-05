/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author jon
 */
@Entity(name="Tutor")
public class TutorEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String staffNumber;
    private String name;
    private String email;

    public TutorEntity() {
        setId(System.nanoTime());
    }

    public void create(String staffNumber, String name, String email) {
        this.staffNumber = staffNumber;
        this.name = name;
        this.email = email;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof TutorEntity)) {
            return false;
        }
        TutorEntity other = (TutorEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.TutorEntity[ id=" + id + " ]";
    }
    
}
