package it.adastra.profilglass.configuratore.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link it.adastra.profilglass.configuratore.domain.CLLEGA} entity.
 */
public class CLLEGADTO implements Serializable {

    private Long id;

    @NotNull
    private String opzione;

    private String descrizione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpzione() {
        return opzione;
    }

    public void setOpzione(String opzione) {
        this.opzione = opzione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CLLEGADTO)) {
            return false;
        }

        CLLEGADTO cLLEGADTO = (CLLEGADTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cLLEGADTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CLLEGADTO{" +
            "id=" + getId() +
            ", opzione='" + getOpzione() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
