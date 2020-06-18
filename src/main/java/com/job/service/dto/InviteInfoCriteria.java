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
 * Criteria class for the {@link com.job.domain.InviteInfo} entity. This class is used
 * in {@link com.job.web.rest.InviteInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invite-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InviteInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter user;

    private StringFilter post;

    private StringFilter workCity;

    private DoubleFilter minSalary;

    private DoubleFilter maxSalary;

    private StringFilter education;

    private StringFilter specialty;

    private IntegerFilter graduation;

    private StringFilter company;

    private StringFilter mail;

    private StringFilter status;

    private InstantFilter submitTime;

    private StringFilter remark;

    public InviteInfoCriteria() {
    }

    public InviteInfoCriteria(InviteInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.user = other.user == null ? null : other.user.copy();
        this.post = other.post == null ? null : other.post.copy();
        this.workCity = other.workCity == null ? null : other.workCity.copy();
        this.minSalary = other.minSalary == null ? null : other.minSalary.copy();
        this.maxSalary = other.maxSalary == null ? null : other.maxSalary.copy();
        this.education = other.education == null ? null : other.education.copy();
        this.specialty = other.specialty == null ? null : other.specialty.copy();
        this.graduation = other.graduation == null ? null : other.graduation.copy();
        this.company = other.company == null ? null : other.company.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.submitTime = other.submitTime == null ? null : other.submitTime.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
    }

    @Override
    public InviteInfoCriteria copy() {
        return new InviteInfoCriteria(this);
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

    public StringFilter getWorkCity() {
        return workCity;
    }

    public void setWorkCity(StringFilter workCity) {
        this.workCity = workCity;
    }

    public DoubleFilter getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(DoubleFilter minSalary) {
        this.minSalary = minSalary;
    }

    public DoubleFilter getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(DoubleFilter maxSalary) {
        this.maxSalary = maxSalary;
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

    public StringFilter getCompany() {
        return company;
    }

    public void setCompany(StringFilter company) {
        this.company = company;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(InstantFilter submitTime) {
        this.submitTime = submitTime;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InviteInfoCriteria that = (InviteInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(user, that.user) &&
            Objects.equals(post, that.post) &&
            Objects.equals(workCity, that.workCity) &&
            Objects.equals(minSalary, that.minSalary) &&
            Objects.equals(maxSalary, that.maxSalary) &&
            Objects.equals(education, that.education) &&
            Objects.equals(specialty, that.specialty) &&
            Objects.equals(graduation, that.graduation) &&
            Objects.equals(company, that.company) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(status, that.status) &&
            Objects.equals(submitTime, that.submitTime) &&
            Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        user,
        post,
        workCity,
        minSalary,
        maxSalary,
        education,
        specialty,
        graduation,
        company,
        mail,
        status,
        submitTime,
        remark
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InviteInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (user != null ? "user=" + user + ", " : "") +
                (post != null ? "post=" + post + ", " : "") +
                (workCity != null ? "workCity=" + workCity + ", " : "") +
                (minSalary != null ? "minSalary=" + minSalary + ", " : "") +
                (maxSalary != null ? "maxSalary=" + maxSalary + ", " : "") +
                (education != null ? "education=" + education + ", " : "") +
                (specialty != null ? "specialty=" + specialty + ", " : "") +
                (graduation != null ? "graduation=" + graduation + ", " : "") +
                (company != null ? "company=" + company + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (submitTime != null ? "submitTime=" + submitTime + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
            "}";
    }

}
