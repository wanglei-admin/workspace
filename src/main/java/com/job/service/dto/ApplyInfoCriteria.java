package com.job.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.job.domain.ApplyInfo} entity. This class is used
 * in {@link com.job.web.rest.ApplyInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /apply-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplyInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter user;

    private StringFilter post;

    private StringFilter expCity;

    private DoubleFilter expSalary;

    private InstantFilter submitTime;

    private StringFilter status;

    private StringFilter education;

    private StringFilter specialty;

    private IntegerFilter graduation;

    public ApplyInfoCriteria() {
    }

    public ApplyInfoCriteria(ApplyInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.user = other.user == null ? null : other.user.copy();
        this.post = other.post == null ? null : other.post.copy();
        this.expCity = other.expCity == null ? null : other.expCity.copy();
        this.expSalary = other.expSalary == null ? null : other.expSalary.copy();
        this.submitTime = other.submitTime == null ? null : other.submitTime.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.education = other.education == null ? null : other.education.copy();
        this.specialty = other.specialty == null ? null : other.specialty.copy();
        this.graduation = other.graduation == null ? null : other.graduation.copy();
    }

    @Override
    public ApplyInfoCriteria copy() {
        return new ApplyInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUser() {
        return user;
    }

    public void setUser(StringFilter user) {
        this.user = user;
    }

    public StringFilter getPost() {
        return post;
    }

    public void setPost(StringFilter post) {
        this.post = post;
    }

    public StringFilter getExpCity() {
        return expCity;
    }

    public void setExpCity(StringFilter expCity) {
        this.expCity = expCity;
    }

    public DoubleFilter getExpSalary() {
        return expSalary;
    }

    public void setExpSalary(DoubleFilter expSalary) {
        this.expSalary = expSalary;
    }

    public InstantFilter getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(InstantFilter submitTime) {
        this.submitTime = submitTime;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getEducation() {
        return education;
    }

    public void setEducation(StringFilter education) {
        this.education = education;
    }

    public StringFilter getSpecialty() {
        return specialty;
    }

    public void setSpecialty(StringFilter specialty) {
        this.specialty = specialty;
    }

    public IntegerFilter getGraduation() {
        return graduation;
    }

    public void setGraduation(IntegerFilter graduation) {
        this.graduation = graduation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplyInfoCriteria that = (ApplyInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(user, that.user) &&
            Objects.equals(post, that.post) &&
            Objects.equals(expCity, that.expCity) &&
            Objects.equals(expSalary, that.expSalary) &&
            Objects.equals(submitTime, that.submitTime) &&
            Objects.equals(status, that.status) &&
            Objects.equals(education, that.education) &&
            Objects.equals(specialty, that.specialty) &&
            Objects.equals(graduation, that.graduation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        user,
        post,
        expCity,
        expSalary,
        submitTime,
        status,
        education,
        specialty,
        graduation
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplyInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (user != null ? "user=" + user + ", " : "") +
                (post != null ? "post=" + post + ", " : "") +
                (expCity != null ? "expCity=" + expCity + ", " : "") +
                (expSalary != null ? "expSalary=" + expSalary + ", " : "") +
                (submitTime != null ? "submitTime=" + submitTime + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (education != null ? "education=" + education + ", " : "") +
                (specialty != null ? "specialty=" + specialty + ", " : "") +
                (graduation != null ? "graduation=" + graduation + ", " : "") +
            "}";
    }

}
