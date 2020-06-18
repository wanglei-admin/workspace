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
 * Criteria class for the {@link com.job.domain.UserInfo} entity. This class is used
 * in {@link com.job.web.rest.UserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private StringFilter name;

    private IntegerFilter gender;

    private InstantFilter birthday;

    private StringFilter mobile;

    private StringFilter mail;

    private StringFilter idno;

    private StringFilter school;

    private StringFilter specialty;

    private StringFilter education;

    private IntegerFilter graduation;

    private StringFilter company;

    private StringFilter companyMail;

    private BooleanFilter mailValidated;

    private BooleanFilter companyMailValidated;

    public UserInfoCriteria() {
    }

    public UserInfoCriteria(UserInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.idno = other.idno == null ? null : other.idno.copy();
        this.school = other.school == null ? null : other.school.copy();
        this.specialty = other.specialty == null ? null : other.specialty.copy();
        this.education = other.education == null ? null : other.education.copy();
        this.graduation = other.graduation == null ? null : other.graduation.copy();
        this.company = other.company == null ? null : other.company.copy();
        this.companyMail = other.companyMail == null ? null : other.companyMail.copy();
        this.mailValidated = other.mailValidated == null ? null : other.mailValidated.copy();
        this.companyMailValidated = other.companyMailValidated == null ? null : other.companyMailValidated.copy();
    }

    @Override
    public UserInfoCriteria copy() {
        return new UserInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getGender() {
        return gender;
    }

    public void setGender(IntegerFilter gender) {
        this.gender = gender;
    }

    public InstantFilter getBirthday() {
        return birthday;
    }

    public void setBirthday(InstantFilter birthday) {
        this.birthday = birthday;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getIdno() {
        return idno;
    }

    public void setIdno(StringFilter idno) {
        this.idno = idno;
    }

    public StringFilter getSchool() {
        return school;
    }

    public void setSchool(StringFilter school) {
        this.school = school;
    }

    public StringFilter getSpecialty() {
        return specialty;
    }

    public void setSpecialty(StringFilter specialty) {
        this.specialty = specialty;
    }

    public StringFilter getEducation() {
        return education;
    }

    public void setEducation(StringFilter education) {
        this.education = education;
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

    public StringFilter getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(StringFilter companyMail) {
        this.companyMail = companyMail;
    }

    public BooleanFilter getMailValidated() {
        return mailValidated;
    }

    public void setMailValidated(BooleanFilter mailValidated) {
        this.mailValidated = mailValidated;
    }

    public BooleanFilter getCompanyMailValidated() {
        return companyMailValidated;
    }

    public void setCompanyMailValidated(BooleanFilter companyMailValidated) {
        this.companyMailValidated = companyMailValidated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserInfoCriteria that = (UserInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(name, that.name) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(idno, that.idno) &&
            Objects.equals(school, that.school) &&
            Objects.equals(specialty, that.specialty) &&
            Objects.equals(education, that.education) &&
            Objects.equals(graduation, that.graduation) &&
            Objects.equals(company, that.company) &&
            Objects.equals(companyMail, that.companyMail) &&
            Objects.equals(mailValidated, that.mailValidated) &&
            Objects.equals(companyMailValidated, that.companyMailValidated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        login,
        name,
        gender,
        birthday,
        mobile,
        mail,
        idno,
        school,
        specialty,
        education,
        graduation,
        company,
        companyMail,
        mailValidated,
        companyMailValidated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (birthday != null ? "birthday=" + birthday + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (idno != null ? "idno=" + idno + ", " : "") +
                (school != null ? "school=" + school + ", " : "") +
                (specialty != null ? "specialty=" + specialty + ", " : "") +
                (education != null ? "education=" + education + ", " : "") +
                (graduation != null ? "graduation=" + graduation + ", " : "") +
                (company != null ? "company=" + company + ", " : "") +
                (companyMail != null ? "companyMail=" + companyMail + ", " : "") +
                (mailValidated != null ? "mailValidated=" + mailValidated + ", " : "") +
                (companyMailValidated != null ? "companyMailValidated=" + companyMailValidated + ", " : "") +
            "}";
    }

}
