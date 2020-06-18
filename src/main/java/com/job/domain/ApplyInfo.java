package com.job.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ApplyInfo.
 */
@Entity
@Table(name = "apply_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "post")
    private String post;

    @Column(name = "exp_city")
    private String expCity;

    @Column(name = "exp_salary")
    private Double expSalary;

    @Column(name = "submit_time")
    private Instant submitTime;

    @Column(name = "status")
    private String status;

    @Column(name = "education")
    private String education;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;

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

    public ApplyInfo user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPost() {
        return post;
    }

    public ApplyInfo post(String post) {
        this.post = post;
        return this;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getExpCity() {
        return expCity;
    }

    public ApplyInfo expCity(String expCity) {
        this.expCity = expCity;
        return this;
    }

    public void setExpCity(String expCity) {
        this.expCity = expCity;
    }

    public Double getExpSalary() {
        return expSalary;
    }

    public ApplyInfo expSalary(Double expSalary) {
        this.expSalary = expSalary;
        return this;
    }

    public void setExpSalary(Double expSalary) {
        this.expSalary = expSalary;
    }

    public Instant getSubmitTime() {
        return submitTime;
    }

    public ApplyInfo submitTime(Instant submitTime) {
        this.submitTime = submitTime;
        return this;
    }

    public void setSubmitTime(Instant submitTime) {
        this.submitTime = submitTime;
    }

    public String getStatus() {
        return status;
    }

    public ApplyInfo status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEducation() {
        return education;
    }

    public ApplyInfo education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpecialty() {
        return specialty;
    }

    public ApplyInfo specialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public ApplyInfo graduation(Integer graduation) {
        this.graduation = graduation;
        return this;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplyInfo)) {
            return false;
        }
        return id != null && id.equals(((ApplyInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplyInfo{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", post='" + getPost() + "'" +
            ", expCity='" + getExpCity() + "'" +
            ", expSalary=" + getExpSalary() +
            ", submitTime='" + getSubmitTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", education='" + getEducation() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", graduation=" + getGraduation() +
            "}";
    }
}
