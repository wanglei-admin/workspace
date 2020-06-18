package com.job.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "mail")
    private String mail;

    @Column(name = "idno")
    private String idno;

    @Column(name = "school")
    private String school;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "education")
    private String education;

    @Column(name = "graduation")
    private Integer graduation;

    @Column(name = "company")
    private String company;

    @Column(name = "company_mail")
    private String companyMail;

    @Column(name = "mail_validated")
    private Boolean mailValidated;

    @Column(name = "company_mail_validated")
    private Boolean companyMailValidated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public UserInfo login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public UserInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public UserInfo gender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public UserInfo birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public UserInfo mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public UserInfo mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdno() {
        return idno;
    }

    public UserInfo idno(String idno) {
        this.idno = idno;
        return this;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getSchool() {
        return school;
    }

    public UserInfo school(String school) {
        this.school = school;
        return this;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpecialty() {
        return specialty;
    }

    public UserInfo specialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEducation() {
        return education;
    }

    public UserInfo education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public UserInfo graduation(Integer graduation) {
        this.graduation = graduation;
        return this;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    public String getCompany() {
        return company;
    }

    public UserInfo company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public UserInfo companyMail(String companyMail) {
        this.companyMail = companyMail;
        return this;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public Boolean isMailValidated() {
        return mailValidated;
    }

    public UserInfo mailValidated(Boolean mailValidated) {
        this.mailValidated = mailValidated;
        return this;
    }

    public void setMailValidated(Boolean mailValidated) {
        this.mailValidated = mailValidated;
    }

    public Boolean isCompanyMailValidated() {
        return companyMailValidated;
    }

    public UserInfo companyMailValidated(Boolean companyMailValidated) {
        this.companyMailValidated = companyMailValidated;
        return this;
    }

    public void setCompanyMailValidated(Boolean companyMailValidated) {
        this.companyMailValidated = companyMailValidated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", name='" + getName() + "'" +
            ", gender=" + getGender() +
            ", birthday='" + getBirthday() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", mail='" + getMail() + "'" +
            ", idno='" + getIdno() + "'" +
            ", school='" + getSchool() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", education='" + getEducation() + "'" +
            ", graduation=" + getGraduation() +
            ", company='" + getCompany() + "'" +
            ", companyMail='" + getCompanyMail() + "'" +
            ", mailValidated='" + isMailValidated() + "'" +
            ", companyMailValidated='" + isCompanyMailValidated() + "'" +
            "}";
    }
}
