package it.adastra.profilglass.configuratore.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CARCLFINI.
 */
@Entity
@Table(name = "carclfini")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CARCLFINI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "classe", nullable = false)
    private String classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CARCLFINI id(Long id) {
        this.id = id;
        return this;
    }

    public String getClasse() {
        return this.classe;
    }

    public CARCLFINI classe(String classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CARCLFINI)) {
            return false;
        }
        return id != null && id.equals(((CARCLFINI) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CARCLFINI{" +
            "id=" + getId() +
            ", classe='" + getClasse() + "'" +
            "}";
    }
}
