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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.job.domain.UserWork} entity. This class is used
 * in {@link com.job.web.rest.UserWorkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-works?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserWorkCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private StringFilter company;

    private StringFilter post;

    private DoubleFilter salary;

    public UserWorkCriteria() {
    }

    public UserWorkCriteria(UserWorkCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.company = other.company == null ? null : other.company.copy();
        this.post = other.post == null ? null : other.post.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
    }

    @Override
    public UserWorkCriteria copy() {
        return new UserWorkCriteria(this);
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

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getCompany() {
        return company;
    }

    public void setCompany(StringFilter company) {
        this.company = company;
    }

    public StringFilter getPost() {
        return post;
    }

    public void setPost(StringFilter post) {
        this.post = post;
    }

    public DoubleFilter getSalary() {
        return salary;
    }

    public void setSalary(DoubleFilter salary) {
        this.salary = salary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserWorkCriteria that = (UserWorkCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(company, that.company) &&
            Objects.equals(post, that.post) &&
            Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        login,
        startDate,
        endDate,
        company,
        post,
        salary
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserWorkCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (company != null ? "company=" + company + ", " : "") +
                (post != null ? "post=" + post + ", " : "") +
                (salary != null ? "salary=" + salary + ", " : "") +
            "}";
    }

}
