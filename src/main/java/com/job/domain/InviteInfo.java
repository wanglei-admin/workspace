package com.job.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InviteInfo.
 */
@Entity
@Table(name = "invite_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InviteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "post")
    private String post;

    @Column(name = "work_city")
    private String workCity;

    @Column(name = "min_salary")
    private Double minSalary;

    @Column(name = "max_salary")
    private Double maxSalary;

    @Column(name = "education")
    private String education;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;

    @Column(name = "company")
    private String company;

    @Column(name = "mail")
    private String mail;

    @Column(name = "status")
    private String status;

    @Column(name = "submit_time")
    private Instant submitTime;

    @Lob
    @Column(name = "raquirement")
    private String raquirement;

    @Column(name = "remark")
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public InviteInfo user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPost() {
        return post;
    }

    public InviteInfo post(String post) {
        this.post = post;
        return this;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWorkCity() {
        return workCity;
    }

    public InviteInfo workCity(String workCity) {
        this.workCity = workCity;
        return this;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public InviteInfo minSalary(Double minSalary) {
        this.minSalary = minSalary;
        return this;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public InviteInfo maxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
        return this;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getEducation() {
        return education;
    }

    public InviteInfo education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpecialty() {
        return specialty;
    }

    public InviteInfo specialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public InviteInfo graduation(Integer graduation) {
        this.graduation = graduation;
        return this;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    public String getCompany() {
        return company;
    }

    public InviteInfo company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMail() {
        return mail;
    }

    public InviteInfo mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public InviteInfo status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSubmitTime() {
        return submitTime;
    }

    public InviteInfo submitTime(Instant submitTime) {
        this.submitTime = submitTime;
        return this;
    }

    public void setSubmitTime(Instant submitTime) {
        this.submitTime = submitTime;
    }

    public String getRaquirement() {
        return raquirement;
    }

    public InviteInfo raquirement(String raquirement) {
        this.raquirement = raquirement;
        return this;
    }

    public void setRaquirement(String raquirement) {
        this.raquirement = raquirement;
    }

    public String getRemark() {
        return remark;
    }

    public InviteInfo remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InviteInfo)) {
            return false;
        }
        return id != null && id.equals(((InviteInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InviteInfo{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", post='" + getPost() + "'" +
            ", workCity='" + getWorkCity() + "'" +
            ", minSalary=" + getMinSalary() +
            ", maxSalary=" + getMaxSalary() +
            ", education='" + getEducation() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", graduation=" + getGraduation() +
            ", company='" + getCompany() + "'" +
            ", mail='" + getMail() + "'" +
            ", status='" + getStatus() + "'" +
            ", submitTime='" + getSubmitTime() + "'" +
            ", raquirement='" + getRaquirement() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
