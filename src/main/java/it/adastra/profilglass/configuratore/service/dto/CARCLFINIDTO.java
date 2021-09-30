package it.adastra.profilglass.configuratore.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link it.adastra.profilglass.configuratore.domain.CARCLFINI} entity.
 */
public class CARCLFINIDTO implements Serializable {

    private Long id;

    @NotNull
    private String classe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CARCLFINIDTO)) {
            return false;
        }

        CARCLFINIDTO cARCLFINIDTO = (CARCLFINIDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cARCLFINIDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CARCLFINIDTO{" +
            "id=" + getId() +
            ", classe='" + getClasse() + "'" +
            "}";
    }
}
