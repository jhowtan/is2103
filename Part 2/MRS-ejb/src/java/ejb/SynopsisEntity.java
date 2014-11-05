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
@Entity(name="Synopsis")
public class SynopsisEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String content;
    
    public SynopsisEntity() {
        setId(System.nanoTime());
    }
    
    public void create(String content) {
        this.setContent(content);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(object instanceof SynopsisEntity)) {
            return false;
        }
        SynopsisEntity other = (SynopsisEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.SynopsisEntity[ id=" + id + " ]";
    }
    
}
