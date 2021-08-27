package it.adastra.profilglass.configuratore.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CLLEGA.
 */
@Entity
@Table(name = "cllega")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CLLEGA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "opzione", nullable = false)
    private String opzione;

    @Column(name = "descrizione")
    private String descrizione;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CLLEGA id(Long id) {
        this.id = id;
        return this;
    }

    public String getOpzione() {
        return this.opzione;
    }

    public CLLEGA opzione(String opzione) {
        this.opzione = opzione;
        return this;
    }

    public void setOpzione(String opzione) {
        this.opzione = opzione;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public CLLEGA descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CLLEGA)) {
            return false;
        }
        return id != null && id.equals(((CLLEGA) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CLLEGA{" +
            "id=" + getId() +
            ", opzione='" + getOpzione() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
