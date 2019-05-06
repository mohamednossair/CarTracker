package com.tracker.car.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeeCars.
 */
@Entity
@Table(name = "employee_cars")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeeCars implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "previous_reading")
    private Double previousReading;

    @DecimalMin(value = "0")
    @Column(name = "current_reading")
    private Double currentReading;

    @Column(name = "working_days")
    private Integer workingDays;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("employeeCars")
    private Employee employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("employeeCars")
    private Car car;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPreviousReading() {
        return previousReading;
    }

    public EmployeeCars previousReading(Double previousReading) {
        this.previousReading = previousReading;
        return this;
    }

    public void setPreviousReading(Double previousReading) {
        this.previousReading = previousReading;
    }

    public Double getCurrentReading() {
        return currentReading;
    }

    public EmployeeCars currentReading(Double currentReading) {
        this.currentReading = currentReading;
        return this;
    }

    public void setCurrentReading(Double currentReading) {
        this.currentReading = currentReading;
    }

    public Integer getWorkingDays() {
        return workingDays;
    }

    public EmployeeCars workingDays(Integer workingDays) {
        this.workingDays = workingDays;
        return this;
    }

    public void setWorkingDays(Integer workingDays) {
        this.workingDays = workingDays;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public EmployeeCars updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeCars employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Car getCar() {
        return car;
    }

    public EmployeeCars car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeCars)) {
            return false;
        }
        return id != null && id.equals(((EmployeeCars) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeCars{" +
            "id=" + getId() +
            ", previousReading=" + getPreviousReading() +
            ", currentReading=" + getCurrentReading() +
            ", workingDays=" + getWorkingDays() +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
