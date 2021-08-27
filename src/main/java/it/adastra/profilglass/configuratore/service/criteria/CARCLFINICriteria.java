package it.adastra.profilglass.configuratore.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link it.adastra.profilglass.configuratore.domain.CARCLFINI} entity. This class is used
 * in {@link it.adastra.profilglass.configuratore.web.rest.CARCLFINIResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /carclfinis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CARCLFINICriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter classe;

    public CARCLFINICriteria() {}

    public CARCLFINICriteria(CARCLFINICriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.classe = other.classe == null ? null : other.classe.copy();
    }

    @Override
    public CARCLFINICriteria copy() {
        return new CARCLFINICriteria(this);
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

    public StringFilter getClasse() {
        return classe;
    }

    public StringFilter classe() {
        if (classe == null) {
            classe = new StringFilter();
        }
        return classe;
    }

    public void setClasse(StringFilter classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CARCLFINICriteria that = (CARCLFINICriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(classe, that.classe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classe);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CARCLFINICriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (classe != null ? "classe=" + classe + ", " : "") +
            "}";
    }
}
