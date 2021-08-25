package it.adastra.profilglass.configuratore.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link it.adastra.profilglass.configuratore.domain.CLLEGA} entity. This class is used
 * in {@link it.adastra.profilglass.configuratore.web.rest.CLLEGAResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cllegas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CLLEGACriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter opzione;

    private StringFilter descrizione;

    public CLLEGACriteria() {}

    public CLLEGACriteria(CLLEGACriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.opzione = other.opzione == null ? null : other.opzione.copy();
        this.descrizione = other.descrizione == null ? null : other.descrizione.copy();
    }

    @Override
    public CLLEGACriteria copy() {
        return new CLLEGACriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOpzione() {
        return opzione;
    }

    public StringFilter opzione() {
        if (opzione == null) {
            opzione = new StringFilter();
        }
        return opzione;
    }

    public void setOpzione(StringFilter opzione) {
        this.opzione = opzione;
    }

    public StringFilter getDescrizione() {
        return descrizione;
    }

    public StringFilter descrizione() {
        if (descrizione == null) {
            descrizione = new StringFilter();
        }
        return descrizione;
    }

    public void setDescrizione(StringFilter descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CLLEGACriteria that = (CLLEGACriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(opzione, that.opzione) && Objects.equals(descrizione, that.descrizione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opzione, descrizione);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CLLEGACriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (opzione != null ? "opzione=" + opzione + ", " : "") +
            (descrizione != null ? "descrizione=" + descrizione + ", " : "") +
            "}";
    }
}
